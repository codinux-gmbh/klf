package net.codinux.log.slf4j

import net.codinux.log.ILoggerFactory
import net.codinux.log.Logger
import net.codinux.log.appender.Appender
import org.slf4j.LoggerFactory


open class Slf4jLoggerFactory : ILoggerFactory {

    override val doesAnyAppenderLogThreadName = false

    override fun getLogger(name: String): Logger {
        return Slf4jLogger(LoggerFactory.getLogger(name))
    }

    override fun addAppender(appender: Appender) {
        System.err.println("Logging is delegated to slf4j. Configure appenders via the logging backend (logback, log4j, ...), appenders added to KMP-Log will be ignored.")
    }

    override fun getAppenders(): Collection<Appender> {
        throw IllegalStateException("Logging is delegated to slf4j. So use the appenders of the logging backend (logback, log4j, ...), not KMP-Log appenders.")
    }

}