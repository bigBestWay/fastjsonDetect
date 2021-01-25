2020/3/23 version 0.1

适用性
使用oracle jdk 1.8.0_181 linux编译，适用于linux x64

使用说明：
java -jar jvmtiLoader.jar pid /root/fastjsonDetectAgent/fastjsonDetectAgent.jar
命令执行用户要与pid进程运行用户一致，并设置好JAVA_HOME和PATH环境变量。

jvmtiLoader.jar
第一个参数是JVM的PID
第二个参数是agent JAR，必须是全路径。如果存在fastjson包，会在/tmp/fastjson_detect.log目录写出版本
====================================================================================
2020/3/24 上午 version 0.2

+ 除了打印版本外，增加打印class所属的jar包路径

输出结果示例：
****fastjson VERSION = 1.2.24
com.alibaba.fastjson.JSON of /root/fastjson-1.2.24.jar
****fastjson VERSION = 1.2.24
com.alibaba.fastjson.JSON of /root/rsrc:fastjson-1.2.47.jar!
====================================================================================
2020/3/24 下午 version 0.3

+ 增加打印isAutoTypeSupport是否为true

输出结果示例：
com.alibaba.fastjson.JSON of /root/fastjson-1.2.37.jar
****fastjson VERSION = 1.2.37
com.alibaba.fastjson.parser.ParserConfig of /root/fastjson-1.2.37.jar
****fastjson isAutoTypeSupport = true
====================================================================================
2020/3/24 下午 version 0.4

+ BUG修复：fastjson 1.2.24等没有autotype版本会直接退出。
