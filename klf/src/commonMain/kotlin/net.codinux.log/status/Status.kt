package net.codinux.log.status

import net.codinux.log.LogLevel
import net.dankito.datetime.Instant

open class Status(
    open val level: LogLevel,
    open val origin: Any,
    open val message: String,
    open val exception: Throwable? = null,
    open val timestamp: Instant = Instant.now(),
) {
    companion object {
        fun error(origin: Any, message: String, exception: Throwable? = null) = Status(LogLevel.Error, origin, message, exception)

        fun warning(origin: Any, message: String, exception: Throwable? = null) = Status(LogLevel.Warn, origin, message, exception)

        fun info(origin: Any, message: String, exception: Throwable? = null) = Status(LogLevel.Info, origin, message, exception)
    }

    override fun toString() = "${timestamp.toLocalDateTimeAtSystemTimeZone().time.isoString} $level ${origin::class.simpleName} $message"
}