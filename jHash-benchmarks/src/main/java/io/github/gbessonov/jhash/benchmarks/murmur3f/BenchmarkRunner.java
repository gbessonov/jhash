package io.github.gbessonov.jhash.benchmarks.murmur3f;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*Benchmark.*")
                .resultFormat(ResultFormatType.JSON)
                .result("benchmark-results.json")
                .build();

        new Runner(opt).run();
    }
}