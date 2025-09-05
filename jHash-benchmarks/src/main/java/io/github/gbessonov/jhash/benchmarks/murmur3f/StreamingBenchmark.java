package io.github.gbessonov.jhash.benchmarks.murmur3f;

import io.github.gbessonov.jhash.HashCode;
import io.github.gbessonov.jhash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import io.github.gbessonov.jhash.implementations.murmur3f.Murmur3fFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
public class StreamingBenchmark {

    @Param({"10", "100", "1000", "10000", "100000"})
    private int chunkCount;

    private byte[][] chunks;

    @Setup
    public void setup() {
        Random random = new Random(42);
        chunks = new byte[chunkCount][];

        for (int i = 0; i < chunkCount; i++) {
            chunks[i] = new byte[64]; // 64-byte chunks
            random.nextBytes(chunks[i]);
        }
    }

    @Benchmark
    public HashCode jHashStreaming(Blackhole bh) {
        HashFunction hasher = Murmur3fFactory.create(0);
        for (byte[] chunk : chunks) {
            hasher.include(chunk);
        }
        var hash = hasher.hash();
        bh.consume(hash);
        return hash;
    }

    @Benchmark
    public com.google.common.hash.HashCode guavaStreaming(Blackhole bh) {
        Hasher hasher = Hashing.murmur3_128().newHasher();
        for (byte[] chunk : chunks) {
            hasher.putBytes(chunk);
        }
        var hash = hasher.hash();
        bh.consume(hash);
        return hash;
    }

    @Benchmark
    public byte[] greenRobotStreaming(Blackhole bh) {
        var hasher = new org.greenrobot.essentials.hash.Murmur3F();
        for (byte[] chunk : chunks) {
            hasher.update(chunk);
        }
        var hash = hasher.getValueBytesLittleEndian();
        bh.consume(hash);
        return hash;
    }
}
