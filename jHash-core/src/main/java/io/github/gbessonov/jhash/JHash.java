package io.github.gbessonov.jhash;

import io.github.gbessonov.jhash.implementations.murmur3f.Murmur3fFactory;

public final class JHash {
    private static final int DEFAULT_SEED = 0;

    private JHash() {
    }

    public static HashFunction newMurmur3_128() {
        return Murmur3fFactory.create(DEFAULT_SEED);
    }

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
