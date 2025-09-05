# jHash 
**High-performance Murmur3 hashing for the JVM.**
jHash delivers blazing-fast Murmur3 (128-bit) hashing with minimal allocations, offering both one-shot and streaming APIs. Optimized for speed, especially on large or streaming data, jHash outpaces Guava’s Murmur3 implementation while remaining lightweight and easy to integrate.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
    - [Installation](#installation)
- [Usage Examples](#usage-examples)
- [Contributing](#contributing)
- [License](#license)

## Features
- Lightweight, zero/minimal-allocation implementation
- One-shot and streaming hash APIs
- Seedable functionality for repeatable results
- Faster than Guava Murmur3 across chunk sizes

## Getting Started
### Installation
#### via JitPack:
```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
<dependency>
  <groupId>com.github.gbessonov</groupId>
  <artifactId>jHash</artifactId>
  <version>main-SNAPSHOT</version>
</dependency>
```

## Usage Examples
### One-shot hashing
```java
import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.JHash;

byte[] data = "Hello, World!".getBytes(StandardCharsets.UTF_8);
HashCode hash = JHash.murmur3_128(data);
System.out.println(hash); // Prints the 128-bit hash
```
Output:
```
jHash.HashCode.Murmur3F: c0a1b86f7365bc93bfae71678c2843aa
```
### One-shot hashing with seed
```java
import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.JHash;

int seed = 1337;
byte[] data = "Hello, World!".getBytes(StandardCharsets.UTF_8);
HashCode hash = JHash.murmur3_128(data, seed);
System.out.println(hash); // Prints the 128-bit hash
```
Output:
```
jHash.HashCode.Murmur3F: 4afbedb4dfd83011c7e0a1f0baeadad7
```

### Streaming hashing
```java
import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.JHash;

// Prepare data chunks
Random random = new Random(42);
int chunkCount = 1000;
byte[][] chunks = new byte[chunkCount][];

for (int i = 0; i < chunkCount; i++) {
    chunks[i] = new byte[64]; // 64-byte chunks
    random.nextBytes(chunks[i]);
}
// Create a hasher with a specific seed
HashFunction hasher = JHash.newMurmur3_128(42);
// Feed data chunks into the hasher
for (byte[] chunk : chunks) {
    hasher.include(chunk);
}
// Compute the final hash
var hash = hasher.hash();
System.out.println(hash);
```
Output:
```
jHash.HashCode.Murmur3F: f103c566293376326c638cbc4db64b0e
```

## Contributing

Contributions welcome! To help us improve:

1. Report bugs or performance regressions via Issues (label bug, perf).
2. Propose enhancements—e.g., SIMD (Vector API), Kotlin wrappers, additional algorithms.
3. Send pull requests with sensible descriptions and tests.

All contributions must include unit tests (and benchmarks where appropriate). Please open an issue before working on large features.

## License
This project is licensed under the Apache-2.0 License. See the [LICENSE](LICENSE) file for details