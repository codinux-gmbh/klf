package net.codinux.log.slf4j.binding

import net.codinux.log.LogLevel
import org.slf4j.Logger

object NopSlf4jBinding : Slf4jBindingImplementation {

    override val rootLoggerName = null

    override fun getLevel(logger: Logger): LogLevel? = null

    override fun getLevel(loggerName: String): LogLevel? = null

    override fun setLevel(logger: Logger, level: LogLevel?): Boolean = false

}