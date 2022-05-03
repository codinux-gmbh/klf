package net.codinux.log.slf4j

import net.codinux.log.ILoggerFactory
import net.codinux.log.Logger
import org.slf4j.LoggerFactory


open class Slf4jLoggerFactory : ILoggerFactory {

    override fun getLogger(name: String): Logger {
        return Slf4jLogger(LoggerFactory.getLogger(name))
    }

}