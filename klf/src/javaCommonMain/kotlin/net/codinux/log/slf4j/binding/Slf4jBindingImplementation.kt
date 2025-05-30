package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.slf4j.Logger

interface Slf4jBindingImplementation {

    fun getLevel(logger: Logger): LogLevel?

    fun getLevel(loggerName: String): LogLevel?

    fun setLevel(logger: Logger, level: LogLevel?): Boolean

}