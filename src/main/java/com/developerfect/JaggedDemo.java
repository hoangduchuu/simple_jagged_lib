package com.developerfect;

import com.exceptionfactory.jagged.x25519.X25519KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class JaggedDemo {
    public static void main(String[] args) {
        try {
            // 1. Generate Age Key Pair
            System.out.println("1. Generating Age Key Pair...");
            X25519KeyPairGenerator keyPairGenerator = new X25519KeyPairGenerator();
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            System.out.println(" ---> Age Key Pair: " +keyPair.getPrivate());
            System.out.println(" ---> Age Key Pair: " +keyPair.getPublic());
            // Save keys to files
            String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            
            Files.write(Paths.get("age_public.key"), publicKeyBase64.getBytes());
            Files.write(Paths.get("age_private.key"), privateKeyBase64.getBytes());
            
            System.out.println("✅ Keys generated and saved:");
            System.out.println("Public Key saved to: age_public.key");
            System.out.println("Private Key saved to: age_private.key");
            System.out.println();

            // 2. Read and Encrypt hello.txt
            System.out.println("2. Reading and Encrypting hello.txt...");
            String originalContent = new String(Files.readAllBytes(Paths.get("hello.txt")));
            System.out.println("Original content: " + originalContent);
            
            // Create a secret key from the public key
            SecretKey secretKey = createSecretKey(keyPair.getPublic().getEncoded());
            
            // Encrypt the content
            byte[] encryptedContent = encryptContent(originalContent.getBytes(), secretKey);
            Files.write(Paths.get("hello_encrypted.txt"), encryptedContent);
            System.out.println("✅ Content encrypted and saved to: hello_encrypted.txt");
            System.out.println();

            // 3. Decrypt hello_encrypted.txt
            System.out.println("3. Decrypting hello_encrypted.txt...");
            byte[] encryptedData = Files.readAllBytes(Paths.get("hello_encrypted.txt"));
            byte[] decryptedContent = decryptContent(encryptedData, secretKey);
            Files.write(Paths.get("hello_decrypted.txt"), decryptedContent);
            
            String decryptedText = new String(decryptedContent);
            System.out.println("Decrypted content: " + decryptedText);
            System.out.println("✅ Content decrypted and saved to: hello_decrypted.txt");
            System.out.println();

            // 4. Verify
            System.out.println("4. Verification:");
            if (originalContent.equals(decryptedText)) {
                System.out.println("✅ Success: Original and decrypted contents match!");
            } else {
                System.out.println("❌ Error: Contents do not match!");
            }

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error with file operations: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static SecretKey createSecretKey(byte[] keyBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(keyBytes);
        return new SecretKeySpec(Arrays.copyOf(hash, 16), "AES");
    }

    private static byte[] encryptContent(byte[] content, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content);
    }

    private static byte[] decryptContent(byte[] encryptedContent, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedContent);
    }
} 