package io.github.gbessonov.jhash.implementations.murmur3f;

import io.github.gbessonov.jhash.HashFunction;

public class Murmur3fFactory {
    private Murmur3fFactory() {
    }

    public static HashFunction create(int seed) {
        return new Murmur3f(seed);
    }

    public static HashFunction create() {
        return new Murmur3f();
    }
}
