#!/bin/bash
service ssh start
$HADOOP_HOME/sbin/start-dfs.sh
$HADOOP_HOME/sbin/start-yarn.sh
$HADOOP_HOME/sbin/mr-jobhistory-daemon.sh --config $HADOOP_HOME/etc/hadoop/ start historyserver
$HADOOP_HOME/bin/hdfs dfs -mkdir /usr
$HADOOP_HOME/bin/hdfs dfs -mkdir /usr/local
$HADOOP_HOME/bin/hdfs dfs -mkdir /usr/local/input
#$HADOOP_HOME/bin/hdfs dfs -put $HADOOP_HOME/etc/hadoop/*.xml /usr/local/input
#$HADOOP_HOME/bin/hdfs dfs -ls /usr/local/input
#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar wordcount /usr/local/input/ /usr/local/output/
#$HADOOP_HOME/bin/hdfs dfs -cat /usr/local/output/*
#$HADOOP_HOME/bin/hdfs dfs -rmr /usr/local/output

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi