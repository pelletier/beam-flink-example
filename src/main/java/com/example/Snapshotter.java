package com.example;

import org.apache.beam.runners.flink.FlinkPipelineOptions;
import org.apache.beam.runners.flink.FlinkRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class Snapshotter {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).as(FlinkPipelineOptions.class);
        options.setRunner(FlinkRunner.class);

        Pipeline pipeline = Pipeline.create(options);

        pipeline
        .apply("read-files", TextIO.read().from("gs://tpelletier-dev/export/*"));
//        .apply("write-files", TextIO.write().to("gs://tpelletier-dev/output/"));

        pipeline.run().waitUntilFinish();
    }
}
