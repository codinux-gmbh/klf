package net.codinux.log

import net.codinux.log.appender.Appender

internal expect object Platform {

    fun createDefaultLoggerFactory(): ILoggerFactory

    val systemDefaultAppender: Appender

    fun getLoggerNameFromCallingMethod(): String?

    val appName: String?

}