#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=zerogreen

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.war $REPOSITORY/"

cp $REPOSITORY/zip/*.war $REPOSITORY/
pwd
ls -al

echo "> 새 애플리케이션 배포"

WAR_NAME=$(ls -tr $REPOSITORY/*.war | tail -n 1)

echo "> WAR Name : $WAR_NAME"

echo "> $WAR_NAME 에 실행권한 추가"

chmod +x $WAR_NAME

echo "> $WAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $WAR_NAME 를 profile=$IDLE_PROFILE로 실행합니다."
nohup java -jar \
  -Dspring.config.location=/application.properties,/application-$IDLE_PROFILE.properties,/application-oauth.properties,/application-real-db.properties \
  -Dspring.profiles.active=$IDLE_PROFILE \
  $WAR_NAME > $REPOSITORY/nohup.out 2>&1 &