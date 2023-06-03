package net.codinux.log.slf4j

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import net.codinux.log.LoggerFactoryBase
import net.codinux.log.appender.Appender
import net.codinux.log.slf4j.binding.LogbackAppender
import org.slf4j.LoggerFactory


open class Slf4jLoggerFactory : LoggerFactoryBase() {

    override fun createLogger(name: String) =
        Slf4jLogger(LoggerFactory.getLogger(name))

    override fun addAppender(appender: Appender) {
        if (Slf4jUtil.boundLoggingFramework == Slf4jBinding.Logback) {
            (Slf4jUtil.slf4jLoggerFactory as LoggerContext).getLogger(Logger.ROOT_LOGGER_NAME).addAppender(LogbackAppender(appender))
            super.addAppender(appender)
        } else {
            System.err.println("Logging is delegated to slf4j. Configure appenders via the logging backend (log4j, Java Util Log, Slf4jSimple, ...), appenders added to KMP-Log will be ignored.")
        }
    }

    override fun getAppenders(): Collection<Appender> {
        if (Slf4jUtil.boundLoggingFramework == Slf4jBinding.Logback) {
            return super.getAppenders()
        }

        throw IllegalStateException("Logging is delegated to slf4j. So use the appenders of the logging backend (log4j, Java Util Log, Slf4jSimple, ...), not KMP-Log appenders.")
    }

}