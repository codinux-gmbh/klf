package net.codinux.log.loki

import net.codinux.log.LogEvent
import net.codinux.log.LogField
import net.codinux.log.appender.Appender
import net.codinux.log.loki.config.LokiLogAppenderConfig
import net.codinux.log.loki.web.KtorWebClient
import net.codinux.log.statelogger.AppenderStateLogger
import net.codinux.log.statelogger.StdOutStateLogger
import net.dankito.datetime.Instant

open class LokiAppender(
    config: LokiLogAppenderConfig = LokiLogAppenderConfig(),
    stateLogger: AppenderStateLogger = StdOutStateLogger()
) : Appender {

    override val loggedFields = buildSet {
        addAll(Appender.MinLoggedFields)

        if (config.enabled && config.fields.logsThreadName) {
            add(LogField.ThreadName)
        }
        if (config.enabled && config.fields.logsException) {
            add(LogField.Exception)
        }
    }


    protected open val isEnabled: Boolean = config.enabled

    protected open val writer = LokiLogWriter(config, stateLogger, KtorWebClient.of(config, stateLogger))


    override fun append(event: LogEvent) {
        if (isEnabled) {
            with (event) {
                writer.writeRecord(Instant.now(), level.name, message, loggerName, threadName, exception)
            }
        }
    }

}