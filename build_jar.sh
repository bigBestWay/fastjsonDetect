#!/bin/sh
rm FastjsonDetectAgent.class
rm fastjsonDetectAgent.jar

javac FastjsonDetectAgent.java
jar cvmf MANIFEST.MF fastjsonDetectAgent.jar FastjsonDetectAgent.class
