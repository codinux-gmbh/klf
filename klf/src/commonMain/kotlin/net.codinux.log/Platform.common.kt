package net.codinux.log

import net.codinux.log.appender.Appender

internal expect object Platform {

    fun createDefaultLoggerFactory(): ILoggerFactory

    /**
     * The [Appender] that gets used by default if [DefaultLoggerFactory] is used as [ILoggerFactory].
     */
    val systemDefaultAppender: Appender

    fun getLoggerNameFromCallingMethod(): String?

    val appName: String?

}