package net.codinux.log

import net.codinux.log.appender.AppenderCollection

interface ILoggerFactory : AppenderCollection {

    val rootLogger: Logger

    fun createLogger(name: String): Logger

}