#!/bin/bash


APP_NAME=security-1.0.0.jar

# 赋值脚本所在的文件夹目录給base_path
base_path=$(cd `dirname $0`;pwd)

usage(){
  echo "Usage: sh 脚本名.sh [start|stop|restart|status]"
  exit 1
}


#检查程序是否在运行 [ -z STRING ]  “STRING” 的长度为零则为真。 字符串为空即NULL时为真
is_exist(){
  pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi

}


#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo  "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -jar ${base_path}/$APP_NAME >> ${base_path}/nohup.out 2>&1 &
    echo "${APP_NAME} start success"
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}


#输出运行状态
status(){
 is_exist
 if [ $? -eq "0" ]; then
  echo "${APP_NAME} is running. Pid is ${pid}"
 else
  echo "${APP_NAME} is NOT running."
 fi
}


#重启
restart(){
 stop
 start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start;;
  "stop")
    stop;;
  "status")
    status;;
  "restart")
    restart;;
  *)
    usage;;
esac
