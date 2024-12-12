package net.codinux.log.config

class EffectiveLoggerConfig(
    private val config: LoggerConfig,
    private val debugConfig: LoggerConfig,
    private val isRunningInDebugMode: Boolean
) {

    val useCallerMethodIfLoggerNameNotSet: Boolean
        get() = if (isRunningInDebugMode && debugConfig.useCallerMethodIfLoggerNameNotSet != LoggerConfig.useCallerMethodIfLoggerNameNotSetDefault) {
            debugConfig.useCallerMethodIfLoggerNameNotSet
        } else {
            config.useCallerMethodIfLoggerNameNotSet
        }

}