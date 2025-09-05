package io.github.gbessonov.jhash.implementations.murmur3f;

import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.HashFunction;
import io.github.gbessonov.jhash.JHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ReadmeTest {
    @Test
    public void Test1OneShot() {
        var expectedString = "jHash.HashCode.Murmur3F: c0a1b86f7365bc93bfae71678c2843aa";

        // Start of README example
        byte[] data = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        HashCode hash = JHash.murmur3_128(data);
        System.out.println(hash); // Prints the 128-bit hash
        // End of README example

        Assertions.assertEquals(expectedString, hash.toString());
    }

    @Test
    public void Test2OneShotSeeded() {
        var expectedString = "jHash.HashCode.Murmur3F: 4afbedb4dfd83011c7e0a1f0baeadad7";

        // Start of README example
        int seed = 1337;
        byte[] data = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        HashCode hash = JHash.murmur3_128(data, seed);
        System.out.println(hash); // Prints the 128-bit hash
        // End of README example

        Assertions.assertEquals(expectedString, hash.toString());
    }

    @Test
    public void Test3Streaming() {
        var expectedString = "jHash.HashCode.Murmur3F: f103c566293376326c638cbc4db64b0e";

        // Start of README example
        // Prepare data chunks
        Random random = new Random(42);
        int chunkCount = 1000;
        byte[][] chunks = new byte[chunkCount][];

        for (int i = 0; i < chunkCount; i++) {
            chunks[i] = new byte[64]; // 64-byte chunks
            random.nextBytes(chunks[i]);
        }
        // Create a hasher with a specific seed
        HashFunction hasher = JHash.newMurmur3_128(42);
        // Feed data chunks into the hasher
        for (byte[] chunk : chunks) {
            hasher.include(chunk);
        }
        // Compute the final hash
        var hash = hasher.hash();
        System.out.println(hash);
        // End of README example

        Assertions.assertEquals(expectedString, hash.toString());
    }
}
