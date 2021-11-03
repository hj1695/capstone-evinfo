#!/bin/bash

REPOSITORY=/home/ec2-user/app/deploy
PROJECT_NAME=capstone-evinfo

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl capstone-evinfo | grep java | awk '{print $1}')

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> Logback 실행을 위한 디렉토리 추가"

sudo mkdir logs
chmod +w logs

echo "> $JAR_NAME 실행"

nohup java -jar -Xms512m -Xmx512m \
    -Dspring.config.location=classpath:/config/application.yml,classpath:/config/application-prod.yml \
    -Dspring.profiles.active=prod \
    $JAR_NAME 2>&1 &
