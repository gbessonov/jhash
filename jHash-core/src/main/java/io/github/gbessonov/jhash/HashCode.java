package io.github.gbessonov.jhash;

import java.math.BigInteger;

/**
 * Represents a hash code of variable length produced by a {@link HashFunction}.
 * <p>
 * Implementations of this interface provide access to the hash in multiple formats
 * for serialization, comparison, or integration with other systems. The hash length
 * depends on the specific hash function used (e.g., 128-bit for MD5, 160-bit for SHA-1,
 * 256-bit for SHA-256, etc.).
 */
public interface HashCode {

    /**
     * Returns the hash code as a byte array in big-endian order.
     * <p>
     * The most significant byte comes first. This format is typically used for
     * interoperability with cryptographic or network-oriented protocols.
     *
     * @return a byte array representing the hash in big-endian format
     */
    byte[] getValueBytesBigEndian();

    /**
     * Returns the hash code as a byte array in little-endian order.
     * <p>
     * The least significant byte comes first. This is often more efficient on
     * little-endian platforms and is the native format of some hash functions
     * like MurmurHash3.
     *
     * @return a byte array representing the hash in little-endian format
     */
    byte[] getValueBytesLittleEndian();

    /**
     * Returns the hash code as a positive {@link BigInteger} in big-endian order.
     * <p>
     * This representation is useful for numeric comparisons or conversions to
     * other integer-based formats. The sign is always positive.
     *
     * @return a positive {@link BigInteger} representation of the hash
     */
    BigInteger getValueBigInteger();

    /**
     * Returns the hash code as a lowercase hexadecimal string.
     * <p>
     * The result length is twice the byte length (each byte becomes 2 hex characters)
     * with leading zero padding if necessary. For example, a 128-bit hash produces
     * a 32-character string, while a 256-bit hash produces a 64-character string.
     *
     * @return a lowercase hexadecimal string representation of the hash
     */
    String getValueHexString();
}