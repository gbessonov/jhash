package io.github.gbessonov.jhash.implementations.murmur3f;

import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.HashFunction;
import io.github.gbessonov.jhash.implementations.murmur3f.Murmur3fHashCode;

/**
 * Fast implementation of MurmurHash3 (x64 128-bit variant),
 * optimized for streaming usage and performance.
 */
class Murmur3f implements HashFunction {

    private static final long C1 = 0x87c37b91114253d5L;
    private static final long C2 = 0x4cf5ad432745937fL;

    private long h1;
    private long h2;
    private int length;

    // Leftover byte buffer
    private final byte[] tailBuffer = new byte[16]; // up to 15 + 1 extra
    private int tailLength = 0;

    public Murmur3f() {
        this(0);
    }

    public Murmur3f(int seed) {
        reset(seed);
    }

    @Override
    public HashFunction include(byte[] input) {
        int offset = 0;
        int inputLength = input.length;
        length += inputLength;

        // Fill leftover buffer if needed
        if (tailLength > 0) {
            int needed = 16 - tailLength;
            if (inputLength < needed) {
                System.arraycopy(input, 0, tailBuffer, tailLength, inputLength);
                tailLength += inputLength;
                return this;
            } else {
                System.arraycopy(input, 0, tailBuffer, tailLength, needed);
                bmix64(getLittleEndianLong(tailBuffer, 0), getLittleEndianLong(tailBuffer, 8));
                offset += needed;
                tailLength = 0;
            }
        }

        // Process full 16-byte blocks directly from input
        int limit = inputLength - ((inputLength - offset) % 16);
        while (offset < limit) {
            long k1 = getLittleEndianLong(input, offset);
            long k2 = getLittleEndianLong(input, offset + 8);
            bmix64(k1, k2);
            offset += 16;
        }

        // Store remaining tail bytes
        tailLength = inputLength - offset;
        if (tailLength > 0) {
            System.arraycopy(input, offset, tailBuffer, 0, tailLength);
        }

        return this;
    }

    @Override
    public HashCode hash() {
        processRemaining();
        return prepareHashCode(h1, h2, length);
    }

    @Override
    public void reset(int seed) {
        this.h1 = seed;
        this.h2 = seed;
        this.length = 0;
        this.tailLength = 0;
    }

    private void processRemaining() {
        long k1 = 0, k2 = 0;

        switch (tailLength) {
            case 15: k2 ^= (tailBuffer[14] & 0xffL) << 48;
            case 14: k2 ^= (tailBuffer[13] & 0xffL) << 40;
            case 13: k2 ^= (tailBuffer[12] & 0xffL) << 32;
            case 12: k2 ^= (tailBuffer[11] & 0xffL) << 24;
            case 11: k2 ^= (tailBuffer[10] & 0xffL) << 16;
            case 10: k2 ^= (tailBuffer[9]  & 0xffL) << 8;
            case 9:  k2 ^= (tailBuffer[8]  & 0xffL);
            case 8:  k1 ^= getLittleEndianLong(tailBuffer, 0); break;
            case 7:  k1 ^= (tailBuffer[6]  & 0xffL) << 48;
            case 6:  k1 ^= (tailBuffer[5]  & 0xffL) << 40;
            case 5:  k1 ^= (tailBuffer[4]  & 0xffL) << 32;
            case 4:  k1 ^= (tailBuffer[3]  & 0xffL) << 24;
            case 3:  k1 ^= (tailBuffer[2]  & 0xffL) << 16;
            case 2:  k1 ^= (tailBuffer[1]  & 0xffL) << 8;
            case 1:  k1 ^= (tailBuffer[0]  & 0xffL); break;
            case 0:  return;
        }

        h1 ^= mixK1(k1);
        h2 ^= mixK2(k2);
    }

    private static long getLittleEndianLong(byte[] data, int offset) {
        return ((long) data[offset] & 0xff) |
                (((long) data[offset + 1] & 0xff) << 8) |
                (((long) data[offset + 2] & 0xff) << 16) |
                (((long) data[offset + 3] & 0xff) << 24) |
                (((long) data[offset + 4] & 0xff) << 32) |
                (((long) data[offset + 5] & 0xff) << 40) |
                (((long) data[offset + 6] & 0xff) << 48) |
                (((long) data[offset + 7] & 0xff) << 56);
    }

    private void bmix64(long k1, long k2) {
        h1 ^= mixK1(k1);
        h1 = Long.rotateLeft(h1, 27);
        h1 += h2;
        h1 = h1 * 5 + 0x52dce729;

        h2 ^= mixK2(k2);
        h2 = Long.rotateLeft(h2, 31);
        h2 += h1;
        h2 = h2 * 5 + 0x38495ab5;
    }

    private static long mixK1(long k1) {
        k1 *= C1;
        k1 = Long.rotateLeft(k1, 31);
        k1 *= C2;
        return k1;
    }

    private static long mixK2(long k2) {
        k2 *= C2;
        k2 = Long.rotateLeft(k2, 33);
        k2 *= C1;
        return k2;
    }

    private static long fmix64(long k) {
        k ^= k >>> 33;
        k *= 0xff51afd7ed558ccdL;
        k ^= k >>> 33;
        k *= 0xc4ceb9fe1a85ec53L;
        k ^= k >>> 33;
        return k;
    }

    private static HashCode prepareHashCode(long h1, long h2, int length) {
        h1 ^= length;
        h2 ^= length;

        h1 += h2;
        h2 += h1;

        h1 = fmix64(h1);
        h2 = fmix64(h2);

        h1 += h2;
        h2 += h1;

        return new Murmur3fHashCode(h1, h2);
    }
}
