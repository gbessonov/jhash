package io.github.gbessonov.jhash.implementations.murmur3f;

import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.JHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class Murmur3fOneShotTest {

    @Test
    public void nullInputTest() {
        Assertions.assertThrows(NullPointerException.class, () -> JHash.murmur3_128(null));
    }

    @ParameterizedTest(name = "Test case {index}: {0}")
    @MethodSource("basicHashingTestData")
    public void basicHashingTest(String description, String input, String expectedResult) {
        byte[] data = input.getBytes(StandardCharsets.UTF_8);
        var hash = JHash.murmur3_128(data);
        validateHash(expectedResult, hash);
    }

    @ParameterizedTest(name = "Edge case {index}: {0}")
    @MethodSource("edgeCaseTestData")
    public void edgeCaseTest(String description, byte[] input, String expectedResult) {
        var hash = JHash.murmur3_128(input);
        validateHash(expectedResult, hash);
    }

    @ParameterizedTest(name = "Boundary test {index}: size {1}")
    @MethodSource("boundaryTestData")
    public void boundaryTest(String description, int size, String expectedResult) {
        byte[] data = new byte[size];
        // Fill with predictable pattern for deterministic results
        for (int i = 0; i < size; i++) {
            data[i] = (byte) (i % 256);
        }
        var hash = JHash.murmur3_128(data);
        validateHash(expectedResult, hash);
    }

    @ParameterizedTest(name = "Character encoding {index}: {0}")
    @MethodSource("encodingTestData")
    public void encodingTest(String description, String input, Charset encoding, String expectedResult) {
        byte[] data = input.getBytes(encoding);
        var hash = JHash.murmur3_128(data);
        validateHash(expectedResult, hash);
    }

    @ParameterizedTest(name = "Binary data {index}: {0}")
    @MethodSource("binaryDataTestData")
    public void binaryDataTest(String description, byte[] input, String expectedResult) {
        var hash = JHash.murmur3_128(input);
        validateHash(expectedResult, hash);
    }

    private static void validateHash(String expectedHex, HashCode actualHash) {
        Assertions.assertEquals(expectedHex, actualHash.getValueHexString());
        Assertions.assertEquals(
                "jHash.HashCode.Murmur3F: " + expectedHex,
                actualHash.toString()
        );
    }

    // Test data providers
    private static Stream<Arguments> basicHashingTestData() {
        return Stream.of(
                Arguments.of("Empty string", "", "00000000000000000000000000000000"),
                Arguments.of("Single character", "a", "897859f6655555855a890e51483ab5e6"),
                Arguments.of("Classic test string", "The quick brown fox jumps over the lazy dog", "6c1b07bc7bbc4be347939ac4a93c437a"),
                Arguments.of("Numbers only", "1234567890", "0a877980e64afaec2bd2eb20c817d0c1"),
                Arguments.of("Special characters", "!@#$%^&*()_+-=[]{}|;':\",./<>?", "085e2fe67421b53fce963eb82e053086"),
                Arguments.of("Mixed case", "MiXeD cAsE tExT", "8c5a724e4fd8532b397794a1d3c0ee4f"),
                Arguments.of("Repeated pattern", "abcabcabcabcabc", "beee760f46de5a80a9a72b90f49bca18"),
                Arguments.of("All same character", "aaaaaaaaaa", "dc677a3c119dc6b1f208de80b548185c"),
                Arguments.of("Whitespace variations", "  spaces  and\ttabs\nand\rnewlines  ", "b79ee99c5c1371475706f0ff065dbcd3"),
                Arguments.of("Common password", "password123", "0d6e32cdfc91c174947f060f671760fc")
        );
    }

    private static Stream<Arguments> edgeCaseTestData() {
        return Stream.of(
                Arguments.of("Null array", new byte[0], "00000000000000000000000000000000"),
                Arguments.of("Single zero byte", new byte[]{0}, "b55cff6ee5ab10468335f878aa2d6251"),
                Arguments.of("All 0xFF bytes", new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, "9738d04607c01d135f642f626b5d78ec"),
                Arguments.of("Alternating 0x00 and 0xFF",
                        new byte[]{0x00, (byte) 0xFF, 0x00, (byte) 0xFF, 0x00, (byte) 0xFF},
                        "8a9b09f972bb30c52ba98469a24fda0e"),
                Arguments.of("Sequential bytes 0-15",
                        new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                        "303f9091b524494445e82f76566490ab")
        );
    }

    private static Stream<Arguments> boundaryTestData() {
        return Stream.of(
                Arguments.of("1 byte", 1, "b55cff6ee5ab10468335f878aa2d6251"),
                Arguments.of("15 bytes (block boundary - 1)", 15, "e92549fd98152347e97dc688ee6d84cd"),
                Arguments.of("16 bytes (block boundary)", 16, "303f9091b524494445e82f76566490ab"),
                Arguments.of("17 bytes (block boundary + 1)", 17, "0ec2e79f0ff4765c24a8da9e6b025fc1"),
                Arguments.of("31 bytes", 31, "94d02ca3e1d33d05905400b4ef9ae59e"),
                Arguments.of("32 bytes", 32, "0f502fb622906dc65111c3346e0a051c"),
                Arguments.of("33 bytes", 33, "1246bafa1b28417d0ba3d6a77380ac55"),
                Arguments.of("63 bytes", 63, "94600734460da3996743c2d4a1c6a4ed"),
                Arguments.of("64 bytes", 64, "0123818d2d52d5ffa18e3356eb3822a2"),
                Arguments.of("65 bytes", 65, "e21e984d019136d08efc5b38b08258bd"),
                Arguments.of("127 bytes", 127, "4b43af66bdf09fba5e41dbb64227f511"),
                Arguments.of("128 bytes", 128, "537ef1a53b4dd7952e808db6afa7a0ab"),
                Arguments.of("1024 bytes (1KB)", 1024, "294c6c31cedcd969c645cc05166c87ed"),
                Arguments.of("4096 bytes (4KB)", 4096, "7a9564a503a8c4f4bb77bb0fc6986883")
        );
    }

    private static Stream<Arguments> encodingTestData() {
        return Stream.of(
                Arguments.of("UTF-8 English", "Hello World", StandardCharsets.UTF_8, "dbc2a0c1ab26631a27b4c09fcf1fe683"),
                Arguments.of("UTF-8 Unicode", "Hello 世界 🌍", StandardCharsets.UTF_8, "8de2f37e166449ba522d170b0c0a50a2"),
                Arguments.of("UTF-16 English", "Hello World", StandardCharsets.UTF_16, "5d46d88c5f25e144f32f747fb11f54bf"),
                Arguments.of("UTF-16 Unicode", "Hello 世界 🌍", StandardCharsets.UTF_16, "f1e0ad6237d2701a3d7e5de09a0d0167"),
                Arguments.of("ASCII", "Hello World", StandardCharsets.US_ASCII, "dbc2a0c1ab26631a27b4c09fcf1fe683"),
                Arguments.of("ISO-8859-1", "Hëllö Wörld", StandardCharsets.ISO_8859_1, "d099b97e47ea485140df871a834ac6e7"),
                Arguments.of("UTF-8 Emoji", "😀😃😄😁😆😅🤣😂🙂🙃", StandardCharsets.UTF_8, "a53d117749ba0d32130f19d44dc7e503"),
                Arguments.of("UTF-8 Chinese", "中文测试字符串", StandardCharsets.UTF_8, "d8a2bd581b6bc92ed42c3f8b21be1255"),
                Arguments.of("UTF-8 Arabic", "اختبار النص العربي", StandardCharsets.UTF_8, "46806c2ec7c57e9d7ee5885f06faa95e"),
                Arguments.of("UTF-8 Russian", "Тестовая строка на русском", StandardCharsets.UTF_8, "fdc7526a9a4060a77f75a6c8c9009097")
        );
    }

    private static Stream<Arguments> binaryDataTestData() {
        return Stream.of(
                Arguments.of("JPEG header",
                        new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0},
                        "2406290bdbdd4e609ae39a6d6c57c958"),
                Arguments.of("PNG signature",
                        new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A},
                        "619cafbac2d7dd237177b954ee262974"),
                Arguments.of("PDF header",
                        "%PDF-1.4".getBytes(StandardCharsets.US_ASCII),
                        "39cd9040a3593d3f9bbc9a3218b7e3e0"),
                Arguments.of("ZIP signature",
                        new byte[]{0x50, 0x4B, 0x03, 0x04},
                        "90c8d50900afee8b8832fb85e9cba1cc"),
                Arguments.of("Random binary",
                        new byte[]{(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF,
                                (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE},
                        "b06d37fd3db5b882fdb1344df4cdc48d"),
                Arguments.of("Crypto-like data",
                        new byte[]{0x1A, 0x2B, 0x3C, 0x4D, 0x5E, 0x6F, 0x70, (byte) 0x81,
                                (byte) 0x92, (byte) 0xA3, (byte) 0xB4, (byte) 0xC5, (byte) 0xD6, (byte) 0xE7, (byte) 0xF8, 0x09},
                        "3947773a3f863b6e224dfaff5e932ccb")
        );
    }
}