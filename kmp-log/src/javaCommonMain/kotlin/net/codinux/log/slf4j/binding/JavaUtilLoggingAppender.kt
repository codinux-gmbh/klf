package net.codinux.log.slf4j.binding

import net.codinux.log.JvmDefaults
import net.codinux.log.LogLevel
import net.codinux.log.appender.Appender
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord

open class JavaUtilLoggingAppender(protected open val wrappedAppender: Appender) : Handler() {

    protected open val levelMapping = mapOf(
        Level.SEVERE to LogLevel.Error,
        Level.WARNING to LogLevel.Warn,
        Level.INFO to LogLevel.Info,
        Level.CONFIG to LogLevel.Info,
        Level.FINE to LogLevel.Debug,
        Level.FINER to LogLevel.Trace,
        Level.FINEST to LogLevel.Trace,
        Level.ALL to LogLevel.Trace,
        Level.OFF to LogLevel.Off
    )


    override fun publish(record: LogRecord?) {
        if (record != null && isLoggable(record)) {
            var message = if (record.message == null) "" else record.message
            if (record.parameters != null) {
                message = String.format(record.message, *record.parameters)
            }

            wrappedAppender.append(
                levelMapping[record.level] ?: LogLevel.Off,
                message,
                record.loggerName,
                if (wrappedAppender.logsThreadName) JvmDefaults.getCurrentThreadName() else null, // TODO: why can't i call Platform.getCurrentThreadName() ?
                if (wrappedAppender.logsException) record.thrown else null
            )
        }
    }

    override fun flush() {
        // no-op
    }

    override fun close() {
        // no-op
    }

}