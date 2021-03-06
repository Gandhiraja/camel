/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.itest.jmh;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.camel.util.CaseInsensitiveMap;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * Tests {@link CaseInsensitiveMap}
 */
public class CaseInsensitiveMapTest {

    @Test
    public void launchBenchmark() throws Exception {
        Options opt = new OptionsBuilder()
            // Specify which benchmarks to run.
            // You can be more specific if you'd like to run only one benchmark per test.
            .include(this.getClass().getName() + ".*")
            // Set the following options as needed
            .mode(Mode.All)
            .timeUnit(TimeUnit.MICROSECONDS)
            .warmupTime(TimeValue.seconds(1))
            .warmupIterations(2)
            .measurementTime(TimeValue.seconds(1))
            .measurementIterations(2)
            .threads(2)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .build();

        new Runner(opt).run();
    }

    // The JMH samples are the best documentation for how to use it
    // http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
    @State(Scope.Thread)
    public static class BenchmarkState {
        CaseInsensitiveMap camelMap;
        com.cedarsoftware.util.CaseInsensitiveMap cedarsoftMap;
        HashMap hashMap;

        @Setup(Level.Trial)
        public void initialize() {
            camelMap = new CaseInsensitiveMap();
            cedarsoftMap = new com.cedarsoftware.util.CaseInsensitiveMap();
            hashMap = new HashMap();
        }

    }

    @Benchmark
    @Measurement(batchSize = 1000000)
    public void camelMap(BenchmarkState state, Blackhole bh) {
        Map map = state.camelMap;

        map.put("foo", "Hello World");
        Object o1 = map.get("foo");
        bh.consume(o1);
        Object o2 = map.get("FOO");
        bh.consume(o2);

        map.put("BAR", "Bye World");
        Object o3 = map.get("bar");
        bh.consume(o3);
        Object o4 = map.get("BAR");
        bh.consume(o4);
    }

    @Benchmark
    @Measurement(batchSize = 1000000)
    public void cedarsoftMap(BenchmarkState state, Blackhole bh) {
        Map map = state.cedarsoftMap;

        map.put("foo", "Hello World");
        Object o1 = map.get("foo");
        bh.consume(o1);
        Object o2 = map.get("FOO");
        bh.consume(o2);

        map.put("BAR", "Bye World");
        Object o3 = map.get("bar");
        bh.consume(o3);
        Object o4 = map.get("BAR");
        bh.consume(o4);
    }

    @Benchmark
    @Measurement(batchSize = 1000000)
    public void hashMap(BenchmarkState state, Blackhole bh) {
        Map map = state.hashMap;

        map.put("foo", "Hello World");
        Object o1 = map.get("foo");
        bh.consume(o1);
        Object o2 = map.get("FOO");
        bh.consume(o2);

        map.put("BAR", "Bye World");
        Object o3 = map.get("bar");
        bh.consume(o3);
        Object o4 = map.get("BAR");
        bh.consume(o4);
    }

}
