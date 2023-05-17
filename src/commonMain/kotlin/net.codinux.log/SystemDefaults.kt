package net.codinux.log

import net.codinux.log.appender.Appender


expect class SystemDefaults {

    companion object {

        fun createDefaultLoggerFactory(): ILoggerFactory

        fun getDefaultAppender(): Appender

    }

}