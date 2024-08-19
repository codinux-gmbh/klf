package net.codinux.log

import net.codinux.log.appender.AppenderContainer

interface ILoggerFactory : AppenderContainer {

    val rootLogger: Logger

    fun createLogger(name: String): Logger

}