package net.codinux.log.loki

import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender
import net.codinux.log.loki.config.LokiLogAppenderConfig
import net.dankito.datetime.Instant

open class LokiAppender(config: LokiLogAppenderConfig = LokiLogAppenderConfig()) : Appender {

    override val logsThreadName = config.enabled && config.fields.logsThreadName

    override val logsException = config.enabled && config.fields.logsException


    protected open val isEnabled: Boolean = config.enabled

    protected open val writer = LokiLogWriter(config)


    override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
        if (isEnabled) {
            writer.writeRecord(Instant.now(), level.name, message, loggerName, threadName, exception)
        }
    }

}