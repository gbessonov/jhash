# jHash

| Benchmark                          | (chunkCount) | (inputSize) | Mode  | Cnt |        Score      |      Error      | Units  |
|-------------------------------------|--------------|-------------|-------|-----|------------------|-----------------|--------|
| HashBenchmark.guavaMurmur3          |     N/A      |      8      | thrpt |  10 |  52396681.259    | 1642976.726     | ops/s  |
| HashBenchmark.guavaMurmur3          |     N/A      |     64      | thrpt |  10 |  24206436.622    |  147156.698     | ops/s  |
| HashBenchmark.guavaMurmur3          |     N/A      |    1024     | thrpt |  10 |   3912219.080    |   28344.427     | ops/s  |
| HashBenchmark.guavaMurmur3          |     N/A      |    8192     | thrpt |  10 |   1009044.029    |   13522.997     | ops/s  |
| HashBenchmark.jHashMurmur3          |     N/A      |      8      | thrpt |  10 | 103718448.127    | 1657431.480     | ops/s  |
| HashBenchmark.jHashMurmur3          |     N/A      |     64      | thrpt |  10 |  53814197.451    |  123098.377     | ops/s  |
| HashBenchmark.jHashMurmur3          |     N/A      |    1024     | thrpt |  10 |   5899198.635    |    6260.327     | ops/s  |
| HashBenchmark.jHashMurmur3          |     N/A      |    8192     | thrpt |  10 |    790801.870    |    7456.936     | ops/s  |
| StreamingBenchmark.guavaStreaming   |     10       |     N/A     | thrpt |  10 |   3286582.431    |   37162.780     | ops/s  |
| StreamingBenchmark.guavaStreaming   |    100       |     N/A     | thrpt |  10 |    439264.126    |  151666.336     | ops/s  |
| StreamingBenchmark.guavaStreaming   |   1000       |     N/A     | thrpt |  10 |     47044.256    |   17680.635     | ops/s  |
| StreamingBenchmark.guavaStreaming   |  10000       |     N/A     | thrpt |  10 |      3725.119    |      56.254     | ops/s  |
| StreamingBenchmark.guavaStreaming   | 100000       |     N/A     | thrpt |  10 |       377.006    |       1.299     | ops/s  |
| StreamingBenchmark.jHashStreaming   |     10       |     N/A     | thrpt |  10 |   6456489.277    |   51096.200     | ops/s  |
| StreamingBenchmark.jHashStreaming   |    100       |     N/A     | thrpt |  10 |    673689.697    |    5327.980     | ops/s  |
| StreamingBenchmark.jHashStreaming   |   1000       |     N/A     | thrpt |  10 |     65574.775    |     619.565     | ops/s  |
| StreamingBenchmark.jHashStreaming   |  10000       |     N/A     | thrpt |  10 |      7571.946    |      12.420     | ops/s  |
| StreamingBenchmark.jHashStreaming   | 100000       |     N/A     | thrpt |  10 |       755.758    |       2.879     | ops/s  |