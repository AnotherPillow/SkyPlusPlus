package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Chatcryption {
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    public static final List<String> messageAliases = Arrays.asList("w", "whisper", "m", "message", "msg", "t", "tell", "pm", "epm", "emsg", "etell", "ewhisper");
    public static final List<String> replyAliases = Arrays.asList("r", "reply", "er", "ereply");
    public static final String MESSAGE_START_SIGN = "\u200C"; //  U+200C ZERO WIDTH NON-JOINER - invisible in chat and doesn't trigger plain text only please (nvm, doesn't but it errors and doesn't care)

    public static void processOutgoingChatMessage(String destinationUsername, String messageContent) {
        Chat.sendCommandToServer("message " + destinationUsername + " " + MESSAGE_START_SIGN + "hihihihihi");
    }

    public static void processIncomingMessage(Text msg) {

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
            if (new File(priv.toString()).exists() || new File(pub.toString()).exists()) return;
            KeyPair pair = generateKeyPair();

            Files.writeString(
                    pub,
                    Base64.getEncoder().encodeToString(pair.getPublic().getEncoded())
            );

            Files.writeString(
                    priv,
                    Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded())
            );
        } catch (Exception e) {
            System.out.println("[err] failed to write sky++ priv/pub keys " + e.getMessage() + e.toString());
        }

    }

    public static String obfuscateBytesAsColour(String input) {
        return obfuscateBytesAsColour(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String obfuscateBytesAsColour(byte[] bytes) {
        StringBuilder out = new StringBuilder();
        List<byte[]> zeroprefixed3bytesections = splitIntoSections(bytes);

        for (int cci = 0; cci < zeroprefixed3bytesections.size(); cci++) {
            out.append("&x");
            byte[] element = zeroprefixed3bytesections.get(cci);
            for (int ei = 0; ei < element.length; ei++) {
                String stringifiedByte = String.format("%02X", element[ei]);
                String[] byteparts = stringifiedByte.split("");
                for (int bpi = 0; bpi < byteparts.length; bpi++) {
                    out.append("&");
                    out.append(byteparts[bpi]);
                }

            }
        }

        return out.toString();
    }

    public static List<byte[]> splitIntoSections(byte[] array) {
        List<byte[]> sections = new ArrayList<>();
        for (int i = 0; i < array.length; i += 3) {
            byte[] section = new byte[3];
            int offset = Math.max(0, 3 - (array.length - i));
            for (int j = 0; j < 3; j++) {
                if (j < offset) {
                    section[j] = 0;
                } else if (i + j - offset < array.length) {
                    section[j] = array[i + j - offset];
                }
            }
            sections.add(section);
        }
        return sections;
    }
}
