package io.github.gbessonov.jhash;

import io.github.gbessonov.jhash.implementations.murmur3f.Murmur3fFactory;

public final class JHash {
    private static final int DEFAULT_SEED = 0;

    private JHash() {
    }

    public static HashFunction newMurmur3_128() {
        return Murmur3fFactory.create(DEFAULT_SEED);
    }

    /**
     * Fast implementation of MurmurHash3 (x64 128-bit variant),
     * optimized for streaming usage and performance.
     *
     *  <p><strong>Thread Safety:</strong> This class is <strong>NOT thread-safe</strong>.
     *  Each thread must use its own instance, or external synchronization must be provided
     *  if sharing instances across threads.
     */
    public static HashFunction newMurmur3_128(int seed) {
        return Murmur3fFactory.create(seed);
    }

    public static HashCode murmur3_128(byte[] data) {
        return Murmur3fFactory.create(DEFAULT_SEED).include(data).hash();
    }

    public static HashCode murmur3_128(byte[] data, int seed) {
        return Murmur3fFactory.create(seed).include(data).hash();
    }
}
