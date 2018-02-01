### Apache Beam example failing to run on Flink

This example fails to run on Flink with the latest revision of Apache Beam.

Apache Beam: [ff37337](https://github.com/apache/beam/commit/ff37337d85aa5af23418f3be4611b913395ccc88).
Flink version: 1.3.2
Hadoop: 2.7.5
Scala: 2.11

Need to build and install said Beam revision to local maven, with the version `2.4.0-SNAPSHOT`.

#### Reproduction steps

##### Set up Flink cluster

`FLINK_DOCKER_IMAGE_NAME=flink:1.3.2-hadoop27-scala_2.11 docker-compose up`

##### Produce fat jar

`./gradlew shadowJar`

##### Submit the job

`http://localhost:8081/#/submit` 


#### Exception

```
org.apache.flink.client.program.ProgramInvocationException: The main method caused an error.
	at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:545)
	at org.apache.flink.client.program.PackagedProgram.invokeInteractiveModeForExecution(PackagedProgram.java:419)
	at org.apache.flink.client.program.OptimizerPlanEnvironment.getOptimizedPlan(OptimizerPlanEnvironment.java:80)
	at org.apache.flink.client.program.ClusterClient.getOptimizedPlan(ClusterClient.java:318)
	at org.apache.flink.runtime.webmonitor.handlers.JarActionHandler.getJobGraphAndClassLoader(JarActionHandler.java:72)
	at org.apache.flink.runtime.webmonitor.handlers.JarPlanHandler.handleJsonRequest(JarPlanHandler.java:50)
	at org.apache.flink.runtime.webmonitor.handlers.AbstractJsonRequestHandler.handleRequest(AbstractJsonRequestHandler.java:41)
	at org.apache.flink.runtime.webmonitor.RuntimeMonitorHandler.respondAsLeader(RuntimeMonitorHandler.java:109)
	at org.apache.flink.runtime.webmonitor.RuntimeMonitorHandlerBase.channelRead0(RuntimeMonitorHandlerBase.java:97)
	at org.apache.flink.runtime.webmonitor.RuntimeMonitorHandlerBase.channelRead0(RuntimeMonitorHandlerBase.java:44)
	at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:105)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
	at io.netty.handler.codec.http.router.Handler.routed(Handler.java:62)
	at io.netty.handler.codec.http.router.DualAbstractHandler.channelRead0(DualAbstractHandler.java:57)
	at io.netty.handler.codec.http.router.DualAbstractHandler.channelRead0(DualAbstractHandler.java:20)
	at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:105)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
	at org.apache.flink.runtime.webmonitor.HttpRequestHandler.channelRead0(HttpRequestHandler.java:105)
	at org.apache.flink.runtime.webmonitor.HttpRequestHandler.channelRead0(HttpRequestHandler.java:65)
	at io.netty.channel.SimpleChannelInboundHandler.channelRead(SimpleChannelInboundHandler.java:105)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
	at io.netty.channel.ChannelInboundHandlerAdapter.channelRead(ChannelInboundHandlerAdapter.java:86)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:242)
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:147)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:339)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:324)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:847)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:131)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:511)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:468)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:382)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:354)
	at io.netty.util.concurrent.SingleThreadEventExecutor$2.run(SingleThreadEventExecutor.java:111)
	at io.netty.util.concurrent.DefaultThreadFactory$DefaultRunnableDecorator.run(DefaultThreadFactory.java:137)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.UnsupportedOperationException: The transform  is currently not supported.
	at org.apache.beam.runners.flink.FlinkBatchPipelineTranslator.visitPrimitiveTransform(FlinkBatchPipelineTranslator.java:99)
	at org.apache.beam.sdk.runners.TransformHierarchy$Node.visit(TransformHierarchy.java:670)
	at org.apache.beam.sdk.runners.TransformHierarchy$Node.visit(TransformHierarchy.java:662)
	at org.apache.beam.sdk.runners.TransformHierarchy$Node.visit(TransformHierarchy.java:662)
	at org.apache.beam.sdk.runners.TransformHierarchy$Node.access$600(TransformHierarchy.java:311)
	at org.apache.beam.sdk.runners.TransformHierarchy.visit(TransformHierarchy.java:245)
	at org.apache.beam.sdk.Pipeline.traverseTopologically(Pipeline.java:458)
	at org.apache.beam.runners.flink.FlinkPipelineTranslator.translate(FlinkPipelineTranslator.java:38)
	at org.apache.beam.runners.flink.FlinkBatchPipelineTranslator.translate(FlinkBatchPipelineTranslator.java:54)
	at org.apache.beam.runners.flink.FlinkPipelineExecutionEnvironment.translate(FlinkPipelineExecutionEnvironment.java:113)
	at org.apache.beam.runners.flink.FlinkRunner.run(FlinkRunner.java:110)
	at org.apache.beam.sdk.Pipeline.run(Pipeline.java:311)
	at org.apache.beam.sdk.Pipeline.run(Pipeline.java:297)
	at com.example.Snapshotter.main(Snapshotter.java:21)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:528)
	... 39 more
```

##### Job manager logs

```
jobmanager_1   | 2018-02-01 01:28:49,280 INFO  org.apache.beam.runners.flink.FlinkRunner                     - PipelineOptions.filesToStage was not specified. Defaulting to files from the classpath: will stage 1 files. Enable logging at DEBUG level to see which files will be staged.
jobmanager_1   | 2018-02-01 01:28:49,468 INFO  org.apache.beam.runners.flink.FlinkRunner                     - Executing pipeline using FlinkRunner.
jobmanager_1   | 2018-02-01 01:28:49,471 INFO  org.apache.beam.runners.flink.FlinkRunner                     - Translating pipeline to Flink program.
jobmanager_1   | 2018-02-01 01:28:49,797 INFO  org.apache.beam.runners.flink.FlinkPipelineExecutionEnvironment  - Creating the required Batch Execution Environment.
jobmanager_1   | 2018-02-01 01:28:49,798 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: jobmanager.rpc.address, jobmanager
jobmanager_1   | 2018-02-01 01:28:49,798 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: jobmanager.rpc.port, 6123
jobmanager_1   | 2018-02-01 01:28:49,799 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: jobmanager.heap.mb, 1024
jobmanager_1   | 2018-02-01 01:28:49,799 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: taskmanager.heap.mb, 1024
jobmanager_1   | 2018-02-01 01:28:49,799 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: taskmanager.numberOfTaskSlots, 1
jobmanager_1   | 2018-02-01 01:28:49,800 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: taskmanager.memory.preallocate, false
jobmanager_1   | 2018-02-01 01:28:49,800 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: parallelism.default, 1
jobmanager_1   | 2018-02-01 01:28:49,800 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: jobmanager.web.port, 8081
jobmanager_1   | 2018-02-01 01:28:49,801 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: blob.server.port, 6124
jobmanager_1   | 2018-02-01 01:28:49,801 INFO  org.apache.flink.configuration.GlobalConfiguration            - Loading configuration property: query.server.port, 6125
jobmanager_1   | 2018-02-01 01:28:49,802 INFO  org.apache.beam.runners.flink.FlinkBatchPipelineTranslator    -  enterCompositeTransform-
jobmanager_1   | 2018-02-01 01:28:49,803 INFO  org.apache.beam.runners.flink.FlinkBatchPipelineTranslator    - |    enterCompositeTransform- read-files
jobmanager_1   | 2018-02-01 01:28:49,813 INFO  org.apache.beam.runners.flink.FlinkBatchPipelineTranslator    - |   |    visitPrimitiveTransform- read-files/Read
```