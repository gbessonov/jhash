package io.github.gbessonov.jhash.implementations.murmur3f;

import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.JHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class Murmur3fOneShotSeededTest {

    @ParameterizedTest(name = "Case with input: {0}")
    @MethodSource("testData")
    public void caseTest(byte[] input, int[] seeds, String[] expectedResults) {
        for (var i = 0; i < seeds.length; i++) {
            var seed = seeds[i];
            var expectedResult = expectedResults[i];
            var hash = JHash.murmur3_128(input, seed);
            validateHash(expectedResult, hash, input, seed);
        }
    }

    private static void validateHash(String expectedHex, HashCode actualHash, byte[] input, int seed) {
        Assertions.assertEquals(
                expectedHex,
                actualHash.getValueHexString(),
                "Failed for input length " + input.length + " and seed " + seed
        );
        Assertions.assertEquals(
                "jHash.HashCode.Murmur3F: " + expectedHex,
                actualHash.toString(),
                "Failed for input length " + input.length + " and seed " + seed
        );
    }

    private static Stream<Arguments> testData() {

        var seeds = new int[]{0, 1, 15, 16, 17, 31, 32, 33, 63, 64, 65, 127, 128, 129, 255, Integer.MAX_VALUE, Integer.MIN_VALUE};
        return Stream.of(
                Arguments.of(
                        new byte[0],
                        seeds,
                        new String[]{
                                "00000000000000000000000000000000",
                                "b55cff6ee5ab10468335f878aa2d6251",
                                "7168a515b113e16cb28d0f23c2535919",
                                "90993207c2b3446ca39376ef74de730c",
                                "4894a8e97f703ebf16ad47ae03b4ec9a",
                                "0a8086199f0f7024edd00d5380c8fcb4",
                                "c73fbb942f35d282d0b0e07d6b94edb5",
                                "0a309aeb42052cfb719ba75d88ad2262",
                                "77c61e085e7c342158464730293a9261",
                                "4383d3005bde418fa06b0a76a76004ad",
                                "7c79b8a2b376a1235cb3db53f785731f",
                                "5f08dc15a68183354cfc82f31694d062",
                                "f55909778cc64011af2a776125cbc54c",
                                "0c8e117296729e19e2c55514185d8277",
                                "4606affc8db89fafa0adb5c2d1716f76",
                                "3f6c160e57c76a65408e46eda12c4cc3",
                                "1be3517815dfb2c8364351e4588144c8",
                        }
                ),
                Arguments.of(
                        new byte[]{0},
                        seeds,
                        new String[]{
                                "b55cff6ee5ab10468335f878aa2d6251",
                                "00000000000000000000000000000000", // <- Nice one!
                                "1eedbbfc08778b02ea46fe8a69f09383",
                                "4894a8e97f703ebf16ad47ae03b4ec9a",
                                "90993207c2b3446ca39376ef74de730c",
                                "15b8505761e1909e8386cb6a5a6b3eea",
                                "0a309aeb42052cfb719ba75d88ad2262",
                                "c73fbb942f35d282d0b0e07d6b94edb5",
                                "467ba9cc39937dd2f81a04dfd0c822ab",
                                "7c79b8a2b376a1235cb3db53f785731f",
                                "4383d3005bde418fa06b0a76a76004ad",
                                "dc099308e5eeab4f9f09e4587b6a67d0",
                                "0c8e117296729e19e2c55514185d8277",
                                "f55909778cc64011af2a776125cbc54c",
                                "0d5b2b2921c7922203bed659d9f5e96f",
                                "c7e09697157776fafb5236d6ef473391",
                                "7f857cb1e7e843e905bd9e62da6b93fa",
                        }
                ),
                Arguments.of(
                        new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF},
                        seeds,
                        new String[]{
                                "9738d04607c01d135f642f626b5d78ec",
                                "a4847dee3880833979fc89b1cedd4339",
                                "24e00601d48450ba79b39cd604e7dd3a",
                                "8e09685be8b19d344d065f8b2d41782f",
                                "f3a241e694f3bf0e173912a186c4bce3",
                                "8ae00186f0a8404345b492e03d2fbe4c",
                                "fc53691632299651b051dc3682a2ac56",
                                "cc852f3f55ab134450b56888c8a6a73b",
                                "b4346455e8d7cdf62013d2b4eeff1ba1",
                                "e1506ab82d20e75c1fac9c4b1b136760",
                                "82b6089a5c1c8e796177d90e790bb599",
                                "b64706fb9bc713f8d26a92ddd8340533",
                                "12d509e4f66d9703771064df7914d7df",
                                "abda905d8b9eb73aa91b72d2a275174e",
                                "28f06a353ad7535f94d3614be77133f4",
                                "e7f8dda1d95ce777d742380c0f202da8",
                                "8faeae905253d27b21f1a229b8169646",
                        }
                ),
                Arguments.of(
                        new byte[]{0x00, (byte) 0xFF, 0x00, (byte) 0xFF, 0x00, (byte) 0xFF},
                        seeds,
                        new String[]{
                                "8a9b09f972bb30c52ba98469a24fda0e",
                                "fe0c3bcf6f7beb54138ce7159ccf4f2e",
                                "4957879891abf98c18651c4d949f1823",
                                "734cc642cd1eb25f9b149e2802c1aee6",
                                "293c5b454b233ad307f4c72dfec9becd",
                                "3d719a8bf8915bff6b1741ad1976e3b2",
                                "f99f12918df512f309b29699d7c39e6a",
                                "e7525786382a3dfde51720842d2df37e",
                                "f518f7733a22af29db66fa7d9d968a07",
                                "f4a43dabd67e13bbc5e1368438a04ccc",
                                "85c12197e9bd7acde71aff5b5e1e1bf1",
                                "89c7417f4add6af972dd765f2a4310c2",
                                "9dfee323482919e3ca74794ba96794b6",
                                "7ca8802bc825743588c8b25aa9604a5b",
                                "eb367d2b404ba3983837c289940620bd",
                                "322c06c00464b3a8b818aa6c65302a9d",
                                "507de8fa624c2ee420b3953f224b5c6e",
                        }
                ),
                Arguments.of(
                        new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
                        seeds,
                        new String[]{
                                "303f9091b524494445e82f76566490ab",
                                "14a2bdebd36a15968a3c0331620ab1e2",
                                "c46fef37c2b240a9228982c687e02ca1",
                                "3513536e0973f60829468381450e39bd",
                                "054c93b774f262b140acc8dbfd28999c",
                                "610cc1f33e8d4fbef20c8480221b0b7f",
                                "a19f847f19d8aead15fb684c936a9e57",
                                "76b17602b306b5b6e887a2173bc11021",
                                "f9a39f2fca2cd0ff09a2deb910942a22",
                                "9dfce3bceb71a9056ea441656d2d6770",
                                "1e9e325ef2a31a8e6643140a02a6fa5b",
                                "529b2b03601f19edf72ef142ae63a731",
                                "73d989079a64cda1b22cc80175f12909",
                                "b212daf87894536b00ed82310d0b240f",
                                "4edea75d1cbc954653fa7080a6773251",
                                "fbe47397bf30f4d6876c35645cb48b03",
                                "1739c9ae387d2c370835dd542fe7022e",
                        }
                )
        );
    }
}