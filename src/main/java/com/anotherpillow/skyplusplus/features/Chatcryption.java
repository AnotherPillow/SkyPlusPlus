package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.StringChecker;
import com.anotherpillow.skyplusplus.util.TextStringifier;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.anotherpillow.skyplusplus.util.Filesystem.resolveConfigPath;
import static java.awt.SystemColor.text;

public class Chatcryption {
    private static class KeyStore {
        private static final Path CONFIG_FILE = resolveConfigPath("skyplusplus-keys.json");
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        private static ConcurrentHashMap<String, PublicKey> keys = new ConcurrentHashMap<>();

        public static PublicKey base64ToPublicKey(String base64Key) throws Exception {
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        }

        public static void load() {
            try {
                if (Files.exists(CONFIG_FILE)) {
                    String json = Files.readString(CONFIG_FILE);
                    if (!json.trim().isEmpty()) {
                        Map<String, String> serializedKeys = GSON.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());

                        keys.clear();
                        for (Map.Entry<String, String> entry : serializedKeys.entrySet()) {
                            try {
                                PublicKey publicKey = base64ToPublicKey(entry.getValue());
                                keys.put(entry.getKey(), publicKey);
                            } catch (Exception e) {
                                System.err.println("Failed to load key for player " + entry.getKey() + ": " + e.getMessage());
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to load keys from file: " + e.getMessage());
            }
        }

        public static void save() {
            try {
                Map<String, String> serializedKeys = new HashMap<>();
                for (Map.Entry<String, PublicKey> entry : keys.entrySet()) {
                    String encodedKey = Base64.getEncoder().encodeToString(entry.getValue().getEncoded());
                    serializedKeys.put(entry.getKey(), encodedKey);
                }

                String json = GSON.toJson(serializedKeys);
                Files.createDirectories(CONFIG_FILE.getParent());
                Files.writeString(CONFIG_FILE, json);
            } catch (IOException e) {
                System.err.println("Failed to save keys to file: " + e.getMessage());
            }
        }

        public static void importKey(String playerName, PublicKey publicKey) {
            keys.put(playerName, publicKey);
            save();//autosave
        }

        public static boolean hasKeyForPlayer(String name) {
            return keys.containsKey(name);
        }

        public static PublicKey getKeyForPlayer(String name) {
            return keys.get(name);
        }
    }

    private static class IDEncoder {
        private static final Random generator = new Random(SkyPlusPlusClient.client.player.getUuid().hashCode() + System.currentTimeMillis());
        private static final short uid = (short) (generator.nextInt(Short.MAX_VALUE - Short.MIN_VALUE + 1) + Short.MIN_VALUE);;
        private static short counter = 0;

        public static int getNext() {
            int n = ((uid & 0xFFFF) << 16) | (++counter & 0xFFFF);
            // Chat.send(Chat.addLogo(String.format("§6Generated next ID: %d", n)));
            return n;
        }
    }

    private static class SplitIncomingMessage {
        public int id;
        public int size;

        private final Map<Integer, String> parts = new HashMap<>();

        public SplitIncomingMessage(int id, int size) {
            this.id = id;
            this.size = size;
        }

        public void addMessage(int pos, String message) {
            if (pos > this.size) return;
             // Chat.send(Chat.addLogo(String.format("Incoming split message %d: Adding [%s] @ %d", this.id, message, pos)));
            this.parts.put(pos, message);
        }

        public String merge(boolean silentFail) {
            StringBuilder out = new StringBuilder();
            for (int i = 0; i < this.size; i++) {
                String got = this.parts.get(i);
                if (got == null) out.append(silentFail ? "" : String.format("[failed receiving %d/%d]", i, this.size));
                else out.append(got);
            }
            return out.toString();
        }

        public boolean isComplete() {
            for (int i = 0; i < this.size; i++) {
                if (!this.parts.containsKey(i)) {
                     // Chat.send(Chat.addLogo(String.format("incoming message %d is not complete @ %d (keys: %s)", this.id, i, this.parts.keySet().toString())));
                    return false;
                };
            }
            return true;
        }
    }

    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static final List<String> messageAliases = Arrays.asList("w", "whisper", "m", "message", "msg", "t", "tell", "pm", "epm", "emsg", "etell", "ewhisper");
    public static final List<String> replyAliases = Arrays.asList("r", "reply", "er", "ereply");

    public static final String MESSAGE_START_SIGN = "$+EM"; // [S]ky[+]+ [E]ncrypted [M]essage
    public static final Integer PROTOCOL_VERSION = 1;

    public static final String REQUESTING_PUBLIC_KEY_UNIQUE = "{PUB_REQ}";
    public static final String REQUESTING_PUBLIC_KEY_COMMENT = "# &6A Sky++ user is requesting your public key to send encrypted messages. If you do not use Sky++, let them know you don't and can't continue.";
    public static final String MESSAGE_SEND_UNIQUE = "{DM}";
    public static final String SHARING_PUBLIC_KEY_UNIQUE = "{PK}";

    public static PublicKey SELF_PUB = null;
    public static PrivateKey SELF_PRIV = null;

    private static final Map<Integer, SplitIncomingMessage> SplitIncomingMessages = new HashMap<>();

    public static String lastCommunicatedUser = null;

    public static void requestPublicKey(String username) {
        Chat.sendCommandToServer(String.format("message %s %s%d%s%s", username, MESSAGE_START_SIGN, PROTOCOL_VERSION, REQUESTING_PUBLIC_KEY_UNIQUE, REQUESTING_PUBLIC_KEY_COMMENT));
    }

    public static void sharePublicKey(String username) {
        String b64PublicKey = Base64.getEncoder().encodeToString(SELF_PUB.getEncoded());
        int uid = IDEncoder.getNext();

        // split into 128 character chunks (why 128? idk felt like it)
        List<String> chunked = new ArrayList<>();
        for (int start = 0; start < b64PublicKey.length(); start += 128) {
            int end = Math.min(b64PublicKey.length(), start + 128);
            chunked.add(b64PublicKey.substring(start, end));
        }

        Timer timer = new Timer();

        Iterator<String> iterator = chunked.iterator();

        // loop every 400ms to reduce spam kick chance
        timer.schedule(new TimerTask() {
            private int i = 0;
            @Override
            public void run() {
                if (i < chunked.size()) {
                    Chat.send(Chat.addLogo(String.format("§7Sending public key part §5%d§8/§5%d§7 to §5%s", i + 1, chunked.size(), username)));
                    Chat.sendCommandToServer(String.format("message %s %s%d%sM%d_%d/%d:%s",
                            username,
                            MESSAGE_START_SIGN,
                            PROTOCOL_VERSION,
                            SHARING_PUBLIC_KEY_UNIQUE,
                            uid,
                            i + 1, // human-readable 1/4 not 0/4 for start
                            chunked.size(),
                            chunked.get(i)
                    ));
                    i++;
                } else timer.cancel();

            }
        }, 0, 400);
    }

    public static void processOutgoingChatMessage(String destinationUsername, String messageContent) {
        if (!KeyStore.hasKeyForPlayer(destinationUsername.toLowerCase())) {
            Chat.send(Chat.addLogo(String.format(
                    "§cFailed to send message - %s's public key is not in database. Requesting now. Try sending message again later.", destinationUsername)));
            requestPublicKey(destinationUsername);
            return;
        }
        try {
            // for some reason can't encrypt more than 190 bytes
            if (messageContent.length() > 175) {
                Chat.send(Chat.addLogo(String.format("§cFailed to encrypt message due to it being too long. Cut it down by %d characters and try again.",
                        messageContent.length() - 175)));
            }
            PublicKey publicKey = KeyStore.getKeyForPlayer(destinationUsername.toLowerCase());
            byte[] byteEncrypted = encrypt(messageContent, publicKey);
            String b64Encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
            int uid = IDEncoder.getNext();

            List<String> chunked = new ArrayList<>();
            for (int start = 0; start < b64Encrypted.length(); start += 128) {
                int end = Math.min(b64Encrypted.length(), start + 128);
                chunked.add(b64Encrypted.substring(start, end));
            }
            Timer timer = new Timer();

            Iterator<String> iterator = chunked.iterator();

            // loop every 400ms to reduce spam kick chance
            timer.schedule(new TimerTask() {
                private int i = 0;
                @Override
                public void run() {
                    if (i < chunked.size()) {
                        Chat.send(Chat.addLogo(String.format("§7Sending encrypted private message part §5%d§8/§5%d§7 to §5%s", i + 1, chunked.size(), destinationUsername)));
                        lastCommunicatedUser = destinationUsername;
                        Chat.sendCommandToServer(String.format("message %s %s%d%sM%d_%d/%d:%s",
                                destinationUsername,
                                MESSAGE_START_SIGN,
                                PROTOCOL_VERSION,
                                MESSAGE_SEND_UNIQUE,
                                uid,
                                i + 1, // human-readable 1/4 not 0/4 for start
                                chunked.size(),
                                chunked.get(i)
                        ));
                        i++;
                    } else timer.cancel();

                }
            }, 0, 400);
//            Chat.sendCommandToServer("message " + destinationUsername + " " + MESSAGE_START_SIGN + "hihihihihi");
//            Chat.sendCommandToServer(String.format("message %s %s%d%sM%d_%d/%d:%s",
//                    username,
//                    MESSAGE_START_SIGN,
//                    PROTOCOL_VERSION,
//                    SHARING_PUBLIC_KEY_UNIQUE,
//                    uid,
//                    i + 1, // human-readable 1/4 not 0/4 for start
//                    chunked.size(),
//                    chunked.get(i)
//            ));
        } catch (Exception e) {
            Chat.send(Chat.addLogo(String.format("§cFailed to encrypt message. Received error %s", e.getMessage())));
        }
    }

    public static boolean processIncomingMessage(Text msg) {
        String stringified = TextStringifier.from(msg);
        String cleaned = stringified.replaceAll("&[0-9A-Fa-fKkL-Ol-oRrXx]", "");
        Matcher matcher = StringChecker.directMessageRawPattern.matcher(cleaned);
        if (matcher.find()) {
            String username = matcher.group(1);
            String content = matcher.group(2);

            lastCommunicatedUser = username;

            if (!content.startsWith(MESSAGE_START_SIGN)) return false;

            String expectedPreUnique = (MESSAGE_START_SIGN + PROTOCOL_VERSION.toString());
            String startingUnique = content.substring(expectedPreUnique.length());

            if (startingUnique.startsWith(REQUESTING_PUBLIC_KEY_UNIQUE)) {
                sharePublicKey(username);
            } else if (startingUnique.startsWith(MESSAGE_SEND_UNIQUE)) {
                // i reallyy should clean this up
                // Chat.send(Chat.addLogo("received encrypted message"));
                String dmMarkMessage = startingUnique.substring(MESSAGE_SEND_UNIQUE.length());
                String dmSpecifics = Arrays.stream(dmMarkMessage.split("M"))
                        .skip(1)
                        .collect(Collectors.joining("M"));

                String uid_str = dmSpecifics.split("_")[0];
                Integer uid = Integer.parseInt(uid_str);

                SplitIncomingMessage splitMessage = SplitIncomingMessages.get(uid);
                if (splitMessage != null && splitMessage.isComplete()) {
                    // don't need to re-get same message when it's done already
                    Chat.send(Chat.addLogo(String.format("§7Already have full message (was trying to be resent) (%d parts), skipping.", splitMessage.size)));
                    return true;
                }

                String partsAndMessage = Arrays.stream(dmSpecifics.split("_"))
                        .skip(1)
                        .collect(Collectors.joining("_"));
                String partCounter = partsAndMessage.split(":")[0];
                Integer partSoFar = Integer.parseInt(partCounter.split("/")[0]);
                Integer partIndex = partSoFar - 1;
                Integer partTotal = Integer.parseInt(partCounter.split("/")[1]);

                Chat.send(Chat.addLogo(String.format("§7Received encrypted message part §5%d§8/§5%d§7 from §5%s", partSoFar, partTotal, username)));

                String encryptedMessage = Arrays.stream(partsAndMessage.split(":"))
                        .skip(1)
                        .collect(Collectors.joining(":"));

                if (!SplitIncomingMessages.containsKey(uid)) {
                    splitMessage = new SplitIncomingMessage(uid, partTotal);
                    SplitIncomingMessages.put(uid, splitMessage);
                };

                splitMessage.addMessage(partIndex, encryptedMessage);

                if (splitMessage.isComplete()) {
                    String message = splitMessage.merge(false);
                    if (message.contains("[failed receiving")) {
                        Chat.send(Chat.addLogo("§cFailed to decode message, one or more sections did not deliver correctly. Received: " + message));
                        return false;
                    }
                    try {
                        String decrypted = decrypt(Base64.getDecoder().decode(message), SELF_PRIV);
                        Chat.send(String.format("§l§2[§r§aE§l§2] §8[§5%s §7-> me§8]§f: %s", username, decrypted));
                    } catch (Exception e) {
                        Chat.send(Chat.addLogo("§cFailed to decrypt message. Received error: §4" + e.getMessage()));
                    }
                }

                return true;
            } else if (startingUnique.startsWith(SHARING_PUBLIC_KEY_UNIQUE)) {
                // see this is where I probably should've used regex but didn't
                String pKeyMessage = startingUnique.substring(SHARING_PUBLIC_KEY_UNIQUE.length());
                String pKeySpecifics = Arrays.stream(pKeyMessage.split("M"))
                        .skip(1)
                        .collect(Collectors.joining("M"));

                String uid_str = pKeySpecifics.split("_")[0];
                Integer uid = Integer.parseInt(uid_str);

                SplitIncomingMessage splitMessage = SplitIncomingMessages.get(uid);
                if (splitMessage != null && splitMessage.isComplete()) {
                    // don't need to re-get same message when it's done already
                    Chat.send(Chat.addLogo("§7Already have full message (was trying to be resent), skipping."));
                    return true;
                }

                String partsAndMessage = Arrays.stream(pKeySpecifics.split("_"))
                        .skip(1)
                        .collect(Collectors.joining("_"));
                String partCounter = partsAndMessage.split(":")[0];
                Integer partSoFar = Integer.parseInt(partCounter.split("/")[0]);
                Integer partIndex = partSoFar - 1;
                Integer partTotal = Integer.parseInt(partCounter.split("/")[1]);

                Chat.send(Chat.addLogo(String.format("§7Received public key part §5%d§8/§5%d§7 from §5%s", partSoFar, partTotal, username)));

                String encryptedMessage = Arrays.stream(partsAndMessage.split(":"))
                        .skip(1)
                        .collect(Collectors.joining(":"));

                if (!SplitIncomingMessages.containsKey(uid) || splitMessage == null) {
                    splitMessage = new SplitIncomingMessage(uid, partTotal);
                    SplitIncomingMessages.put(uid, splitMessage);
                };

                splitMessage.addMessage(partIndex, encryptedMessage);

                if (splitMessage.isComplete()) {
                    String fullKey = splitMessage.merge(false);
                    if (fullKey.contains("[failed receiving")) {
                        Chat.send(Chat.addLogo("§cFailed to decode public key, one or more sections did not deliver correctly. Received: " + fullKey));
                        return false;
                    }
                    try {
                        KeyStore.importKey(username.toLowerCase(), KeyStore.base64ToPublicKey(fullKey));
                        Chat.send(Chat.addLogo(String.format("§7Imported §5%s§7's public key.", username)));
                    } catch (Exception e) {
                        Chat.send(Chat.addLogo("§cFailed to import public key. Received error: §4" + e.getMessage()));
                    }
                }

                return true;
            }

            // Chat.send(String.format("received encrypted message from: %s with content %s", username, content));
        }

        return false; // true to cancel it being added to screen
    }

    private static byte[] encrypt(String data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String decrypt(byte[] encrypted, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainBytes = cipher.doFinal(encrypted);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keySize);
        return kpg.generateKeyPair();
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(2048);
    }

    public static void loadSelfKeys() {
        try {
            Path pub = FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("sky++/keys/$self-pub.key");
            Path priv = FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("sky++/keys/$self-priv.key");

            String pubB64 = Files.readString(pub).trim();
            String privB64 = Files.readString(priv).trim();

            SELF_PUB = loadPublicKey(pubB64);
            SELF_PRIV = loadPrivateKey(privB64);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load self keys", e);
        }
    }

    private static PrivateKey loadPrivateKey(String base64) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(String base64) throws Exception {
        byte[] encoded = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePublic(spec);
    }

    public static void generateKeysAndSave() {
        try {
            Path folder = FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("sky++/keys");
            Files.createDirectories(folder);

            Path priv = FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("sky++/keys/$self-priv.key");
            Path pub = FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("sky++/keys/$self-pub.key");
            if (new File(priv.toString()).exists() || new File(pub.toString()).exists()) {
                loadSelfKeys();
                return;
            }
            KeyPair pair = generateKeyPair();

            Files.writeString(
                    pub,
                    Base64.getEncoder().encodeToString(pair.getPublic().getEncoded())
            );

            Files.writeString(
                    priv,
                    Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded())
            );

            loadSelfKeys();
        } catch (Exception e) {
            System.out.println("[err] failed to write sky++ priv/pub keys " + e.getMessage() + e.toString());
        }
    }
}
