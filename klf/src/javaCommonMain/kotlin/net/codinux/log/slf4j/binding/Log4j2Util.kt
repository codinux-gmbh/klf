package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.apache.logging.log4j.Level

object Log4j2Util {

    val Log4jToKlfLevelMapping = mapOf(
        Level.ERROR to LogLevel.Error,
        Level.INFO to LogLevel.Info,
        Level.DEBUG to LogLevel.Debug,
        Level.TRACE to LogLevel.Trace,
        Level.ALL to LogLevel.Trace,
        Level.OFF to LogLevel.Off
    )

    val KlfToLog4jLevelMapping = mapOf(
        LogLevel.Error to Level.ERROR,
        LogLevel.Info to Level.INFO,
        LogLevel.Debug to Level.DEBUG,
        LogLevel.Trace to Level.TRACE,
        LogLevel.Off to Level.OFF
    )

    fun mapLevel(level: LogLevel): Level =
        KlfToLog4jLevelMapping[level] ?: Level.OFF

    fun mapLevel(level: Level): LogLevel =
        Log4jToKlfLevelMapping[level] ?: LogLevel.Off

}