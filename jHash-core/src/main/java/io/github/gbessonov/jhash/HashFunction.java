package io.github.gbessonov.jhash;

/**
 * A streaming hash function interface that supports incremental input,
 * finalization, and optional reseeding.
 *
 */
public interface HashFunction {

    /**
     * Feeds additional input into the hash function.
     * This method can be called multiple times to hash concatenated data.
     *
     * @param input the byte array to include in the hash computation
     * @return this instance for method chaining
     */
    HashFunction include(byte[] input);

    /**
     * Finalizes the hash computation and returns the result as a {@link HashCode}.
     * Once called, the hash function should be considered finalized for this input.
     *
     * @return a hash code representing the final digest
     */
    HashCode hash();

    /**
     * Resets the internal state of the hash function using the given seed.
     * This allows the instance to be reused for hashing different inputs.
     *
     * @param seed the seed value to reset the hash function to
     */
    void reset(int seed);
}