package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(value = 2)
@Warmup(iterations = 3)
@Measurement(iterations = 7)
public class AlgorithmBenchmark {
    private Graph graph;

    @Param({ "8", "16", "24", "32", "40", "48", "56", "64", "72", "80", "88", "96", "104", "112", "120", "128" })
    private int vertices;

    @Setup(Level.Trial)
    public void setup() {
        this.graph = GraphGenerator.generate(vertices, vertices * (vertices - 1) / 4);
    }

    @Benchmark
    public void test(Blackhole bh) {
        bh.consume(MinCut.minCut(graph));
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .resultFormat(ResultFormatType.JSON)
                .result("results" + System.currentTimeMillis() + ".json")
                .build();

        new Runner(options).run();
    }
}
