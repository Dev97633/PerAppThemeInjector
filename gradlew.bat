@echo off
set DIR=%~dp0
set WRAPPER_JAR=%DIR%\gradle\wrapper\gradle-wrapper.jar

if not exist "%WRAPPER_JAR%" (
  echo ERROR: gradle-wrapper.jar missing
  exit /b 1
)

java -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
