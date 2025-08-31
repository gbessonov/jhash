package io.github.gbessonov.jhash;

import io.github.gbessonov.jhash.implementations.murmur3f.Murmur3fFactory;

public final class JHash {
    private JHash() {
    }

    public static HashFunction murmur3_128() {
        return Murmur3fFactory.create();
    }

    public static HashFunction murmur3_128(int seed) {
        return Murmur3fFactory.create(seed);
    }
}
