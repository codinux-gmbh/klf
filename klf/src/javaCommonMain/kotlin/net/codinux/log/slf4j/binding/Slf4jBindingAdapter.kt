package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.Slf4jBinding
import org.slf4j.Logger

interface Slf4jBindingAdapter {

    val binding: Slf4jBinding

    val rootLoggerName: String?

    /**
     * For all but Log4j 2 and Slf4jSimple rootLoggerName equals slf4j's root logger name.
     */
    val slf4jRootLoggerName: String get() = rootLoggerName!!


    fun getLevel(logger: Logger): LogLevel?

    fun getLevel(loggerName: String): LogLevel?

    fun setLevel(logger: Logger, level: LogLevel?): Boolean

}