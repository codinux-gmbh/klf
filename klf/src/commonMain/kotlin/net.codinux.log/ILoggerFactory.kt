package net.codinux.log

import net.codinux.log.appender.AppenderCollection

interface ILoggerFactory : AppenderCollection {

    val rootLogger: RootLogger

    fun createLogger(name: String): Logger

}