#!/usr/bin/env sh

##############################################################################
## Gradle wrapper script for Unix
##############################################################################

DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLE_WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$GRADLE_WRAPPER_JAR" ]; then
  echo "ERROR: gradle-wrapper.jar missing"
  exit 1
fi

JAVA_CMD="java"
exec "$JAVA_CMD" -classpath "$GRADLE_WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
