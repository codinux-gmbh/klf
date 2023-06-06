package net.codinux.log

import net.codinux.log.appender.AppenderContainer

interface ILoggerFactory : AppenderContainer {

    val rootLogger: Logger

    fun getLogger(name: String): Logger

}