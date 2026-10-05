@rem SAP Hybris OOTB Demo - Gradle Wrapper using Gradle 8.14
@rem Fix: WindowsSelectorProvider to prevent JDK17+Win11 NIO Pipe failure in launcher JVM

@if "%DEBUG%"=="" @echo off
if "%OS%"=="Windows_NT" setlocal

set GRADLE_HOME=%USERPROFILE%\.gradle\wrapper\dists\gradle-8.14-bin\gradle-8.14

set JAVA_HOME=C:\Program Files\Java\jdk-17
set JAVA_EXE=%JAVA_HOME%\bin\java.exe

@rem Launcher JVM opts - include WindowsSelectorProvider fix so SocketConnection can open Selector
@rem The PipeImpl on JDK17+Windows11 fails with WEPoll, this forces the TCP-based selector
set DEFAULT_JVM_OPTS="-Xmx128m" "-Xms64m" "-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.WindowsSelectorProvider" "-javaagent:%GRADLE_HOME%\lib\agents\gradle-instrumentation-agent-8.14.jar"

@rem Also propagate fix to daemon JVM via org.gradle.jvmargs-compatible env
@rem (we use JAVA_OPTS which is part of the daemon startup command line chain)
set JAVA_OPTS="-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.WindowsSelectorProvider"

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% "-Dorg.gradle.appname=gradlew" -classpath "" -jar "%GRADLE_HOME%\lib\gradle-gradle-cli-main-8.14.jar" %*

if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%
if "%OS%"=="Windows_NT" endlocal
