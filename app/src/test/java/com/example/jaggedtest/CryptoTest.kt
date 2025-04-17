package com.example.jaggedtest

import com.exceptionfactory.jagged.x25519.X25519KeyPairGenerator
import com.exceptionfactory.jagged.scrypt.ScryptRecipientStanzaWriter
import com.exceptionfactory.jagged.scrypt.ScryptRecipientStanzaReader
import org.junit.Test
import java.security.KeyPair
import java.security.NoSuchAlgorithmException
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CryptoTest {
    private val keyPairGenerator: X25519KeyPairGenerator = try {
        X25519KeyPairGenerator()
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }

    private val scryptWriter = ScryptRecipientStanzaWriter()
    private val scryptReader = ScryptRecipientStanzaReader()

    @Test
    fun testKeyGeneration() = runBlocking {
        // Generate key pair
        val keyPair = keyPairGenerator.generateKeyPair()
        
        // Verify key pair is not null
        assertNotNull(keyPair)
        assertNotNull(keyPair.public)
        assertNotNull(keyPair.private)
        
        println("Public Key: ${keyPair.public.encoded.contentToString()}")
        println("Private Key: ${keyPair.private.encoded.contentToString()}")
    }

    @Test
    fun testEncryptionAndDecryption() = runBlocking {
        // Original data
        val originalData = "Hello, Jagged!".toByteArray()
        val password = "testPassword123"

        // Encrypt data
        val encryptedData = scryptWriter.write(originalData, password.toByteArray())
        println("Encrypted Data: ${encryptedData.contentToString()}")

        // Decrypt data
        val decryptedData = scryptReader.read(encryptedData, password.toByteArray())
        println("Decrypted Data: ${String(decryptedData)}")

        // Verify decrypted data matches original
        assertEquals(originalData.contentToString(), decryptedData.contentToString())
    }

    @Test
    fun testFullWorkflow() = runBlocking {
        // 1. Generate key pair
        val keyPair = keyPairGenerator.generateKeyPair()
        println("Generated new key pair")

        // 2. Prepare test data
        val testData = "Test message for encryption".toByteArray()
        val password = "securePassword123"

        // 3. Encrypt data
        val encryptedData = scryptWriter.write(testData, password.toByteArray())
        println("Data encrypted successfully")

        // 4. Decrypt data
        val decryptedData = scryptReader.read(encryptedData, password.toByteArray())
        println("Data decrypted successfully")

        // 5. Verify results
        assertEquals(testData.contentToString(), decryptedData.contentToString())
        println("Verification successful: Original and decrypted data match")
    }
} 