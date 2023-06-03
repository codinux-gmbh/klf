package net.codinux.log.slf4j

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import net.codinux.log.LoggerFactoryBase
import net.codinux.log.appender.Appender
import net.codinux.log.slf4j.binding.JavaUtilLoggingAppender
import net.codinux.log.slf4j.binding.Log4j2Appender
import net.codinux.log.slf4j.binding.Log4j2Configurator
import net.codinux.log.slf4j.binding.LogbackAppender
import org.slf4j.LoggerFactory
import java.util.logging.LogManager

open class Slf4jLoggerFactory : LoggerFactoryBase() {

    protected open val log4j2Configurator: Log4j2Configurator by lazy { Log4j2Configurator() }


    init {
        if (Slf4jUtil.boundLoggingFramework == Slf4jBinding.Log4j2) {
            log4j2Configurator.setRootLevel(net.codinux.log.LoggerFactory.DefaultLevel)
        }
    }


    override fun createLogger(name: String) =
        Slf4jLogger(LoggerFactory.getLogger(name))

    override fun addAppender(appender: Appender) {
        when (Slf4jUtil.boundLoggingFramework) {
            Slf4jBinding.Logback -> (Slf4jUtil.slf4jLoggerFactory as LoggerContext).getLogger(Logger.ROOT_LOGGER_NAME).addAppender(LogbackAppender(appender))
            Slf4jBinding.Log4j2 -> log4j2Configurator.addAppender(Log4j2Appender(appender))
            Slf4jBinding.JUL -> LogManager.getLogManager().getLogger("").addHandler(JavaUtilLoggingAppender(appender)) // the root logger has name ""
            else -> System.err.println("Logging is delegated to slf4j. Configure appenders via the logging backend (Slf4jSimple, ...), appenders added to KMP-Log will be ignored.")
        }
    }

    override fun getAppenders(): Collection<Appender> {
        throw IllegalStateException("Logging is delegated to slf4j. So use the appenders of the logging backend (logback, log4j, Java Util Log, Slf4jSimple, ...), not KMP-Log appenders.")
    }

}