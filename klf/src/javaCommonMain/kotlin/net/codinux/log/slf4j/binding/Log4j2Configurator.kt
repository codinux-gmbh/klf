package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configuration

open class Log4j2Configurator {

    open fun setRootLevel(level: LogLevel) {
        setRootLevel(Log4j2Util.mapLevel(level))
    }

    open fun setRootLevel(level: Level) {
        updateConfig { config ->
            config.rootLogger.level = level
        }
    }

    open fun addAppender(appender: Appender) {
        updateConfig { config ->
            appender.start()
            config.addAppender(appender)
            config.rootLogger.addAppender(appender, null, null)
        }
    }

    protected open fun updateConfig(configureConfig: (Configuration) -> Unit) {
        val context = LogManager.getContext(false) as LoggerContext
        val config = context.configuration

        configureConfig(config)

        context.updateLoggers()
    }

}