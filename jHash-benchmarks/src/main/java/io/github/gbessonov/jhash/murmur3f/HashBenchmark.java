package io.github.gbessonov.jhash.murmur3f;

import io.github.gbessonov.jhash.HashCode;
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
public class HashBenchmark {
    @Param({"8", "64", "1024", "8192"})
    private int inputSize;

    private byte[] data;

    @Setup
    public void setup() {
        data = new byte[inputSize];
        new Random(42).nextBytes(data);
    }

    @Benchmark
    public HashCode jHashMurmur3(Blackhole bh) {
        var hash = Murmur3fFactory.create(0).include(data).hash();
        bh.consume(hash);
        return hash;
    }

    @Benchmark
    public com.google.common.hash.HashCode guavaMurmur3(Blackhole bh) {
        var hash = com.google.common.hash.Hashing.murmur3_128().hashBytes(data);
        bh.consume(hash);
        return hash;
    }
}
