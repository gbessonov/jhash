package io.github.gbessonov.jhash.implementations.murmur3f;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Murmur3fTest {

    private static Stream<Arguments> hashingTestDataProvider() {
        // Test cases are taken from guava implementation
        // https://github.com/google/guava/blob/master/guava-tests/test/com/google/common/hash/Murmur3Hash128Test.java
        return Stream.of(
                Arguments.of(0, 0x629942693e10f867L, 0x92db0b82baeb5347L, "hell"),
                Arguments.of(1, 0xa78ddff5adae8d10L, 0x128900ef20900135L, "hello"),
                Arguments.of(2, 0x8a486b23f422e826L, 0xf962a2c58947765fL, "hello "),
                Arguments.of(3, 0x2ea59f466f6bed8cL, 0xc610990acc428a17L, "hello w"),
                Arguments.of(4, 0x79f6305a386c572cL, 0x46305aed3483b94eL, "hello wo"),
                Arguments.of(5, 0xc2219d213ec1f1b5L, 0xa1d8e2e0a52785bdL, "hello wor"),
                Arguments.of(0, 0xe34bbc7bbc071b6cL, 0x7a433ca9c49a9347L, "The quick brown fox jumps over the lazy dog"),
                Arguments.of(0, 0x658ca970ff85269aL, 0x43fee3eaa68e5c3eL, "The quick brown fox jumps over the lazy cog")
        );
    }

    @ParameterizedTest
    @MethodSource("hashingTestDataProvider")
    public void hashingTest(int seed, long expectedHash1, long expectedHash2, String inputString) {
        var expectedHash = longsToBytes(new long[]{expectedHash2, expectedHash1});

        var actualHash = Murmur3fFactory.create(seed)
                .include(ascii(inputString))
                .hash();

        var actualBEValue = actualHash.getValueBytesBigEndian();
        Assertions.assertArrayEquals(expectedHash, actualBEValue);
    }

    private static Stream<Arguments> chunksTestDataProvider() {
        return Stream.of(
                Arguments.of(0, 0x629942693e10f867L, 0x92db0b82baeb5347L, List.of("he", "ll")),
                Arguments.of(1, 0xa78ddff5adae8d10L, 0x128900ef20900135L, List.of("hell", "o")),
                Arguments.of(2, 0x8a486b23f422e826L, 0xf962a2c58947765fL, List.of("hello", " ")),
                Arguments.of(3, 0x2ea59f466f6bed8cL, 0xc610990acc428a17L, List.of("hello w", "")),
                Arguments.of(4, 0x79f6305a386c572cL, 0x46305aed3483b94eL, List.of("", "hello wo")),
                Arguments.of(5, 0xc2219d213ec1f1b5L, 0xa1d8e2e0a52785bdL, List.of("hel", "lo wor")),
                Arguments.of(0, 0xe34bbc7bbc071b6cL, 0x7a433ca9c49a9347L,
                        Arrays.stream("The quick brown fox jumps over the lazy dog".split("")).toList()),
                Arguments.of(0, 0x658ca970ff85269aL, 0x43fee3eaa68e5c3eL,
                        List.of("The quick brown fox jumps over the lazy cog"))

        );
    }

    @ParameterizedTest
    @MethodSource("chunksTestDataProvider")
    public void chunksTest(int seed, long expectedHash1, long expectedHash2, List<String> inputStringChunks) {
        var expectedHash = longsToBytes(new long[]{expectedHash2, expectedHash1});

        var hasher = Murmur3fFactory.create(seed);
        for (var chunk: inputStringChunks) {
            hasher.include(ascii(chunk));
        }
        var actualHash = hasher.hash();

        var actualBEValue = actualHash.getValueBytesBigEndian();
        Assertions.assertArrayEquals(expectedHash, actualBEValue);
    }


    static byte[] ascii(String string) {
        byte[] bytes = new byte[string.length()];
        for (int i = 0; i < string.length(); i++) {
            bytes[i] = (byte) string.charAt(i);
        }
        return bytes;
    }

    static byte[] longsToBytes(long[] longs) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * longs.length);
        for (long l : longs) {
            buffer.putLong(l);
        }
        return buffer.array();
    }
}
