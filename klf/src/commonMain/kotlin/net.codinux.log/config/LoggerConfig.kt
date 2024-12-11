package net.codinux.log.config

class LoggerConfig(
    var useCallerMethodIfLoggerNameNotSet: Boolean = false
) {
    override fun toString() = "useCallerMethodIfLoggerNameNotSet = $useCallerMethodIfLoggerNameNotSet"
}