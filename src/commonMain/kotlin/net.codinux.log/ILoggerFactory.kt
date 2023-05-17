package net.codinux.log

import net.codinux.log.appender.AppenderContainer

interface ILoggerFactory : AppenderContainer {

    fun getLogger(name: String): Logger

}