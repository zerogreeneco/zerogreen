#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=zerogreen

echo "> Build 파일 복사"

cp $REPOSITORY/zip/build/libs/*.war $REPOSITORY/

mv $REPOSITORY/zip/eco-0.0.1-SNAPSHOT.war $REPOSITORY/zip/$PROJECT_NAME.war

mv $REPOSITORY/zip/$PROJECT_NAME.war /home/ec2-user/apache-tomcat-9.0.60/webapps

rm -r -f zerogreen

echo "> 현재 구동중인 애플리케이션 pid 확인"


CURRENT_PID=$(pgrep -fl zerogreen | grep war | awk '{print $1}')

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

WAR_NAME=$(ls -tr $REPOSITORY/*.war | tail -n 1)

echo "> JAR Name: $WAR_NAME"

echo "> $WAR_NAME 에 실행권한 추가"

chmod +x $WAR_NAME

echo "> $WAR_NAME 실행"

nohup java -jar \
    -spring.config.location=classpath:/home/ec2-user/app/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -spring.profiles.active=real \
    $WAR_NAME > $REPOSITORY/nohup.out 2>&1 &