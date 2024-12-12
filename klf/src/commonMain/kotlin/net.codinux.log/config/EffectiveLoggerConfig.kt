package net.codinux.log.config

class EffectiveLoggerConfig(
    private val config: LoggerConfig,
    private val debugConfig: LoggerConfig,
    private val isRunningInDebugMode: Boolean
) {

    val defaultLoggerName: String?
        get() = if (isRunningInDebugMode && debugConfig.defaultLoggerName != LoggerConfig.defaultLoggerNameDefault) {
            debugConfig.defaultLoggerName
        } else {
            config.defaultLoggerName
        }

    val useCallerMethodIfLoggerNameNotSet: Boolean
        get() = if (isRunningInDebugMode && debugConfig.useCallerMethodIfLoggerNameNotSet != LoggerConfig.useCallerMethodIfLoggerNameNotSetDefault) {
            debugConfig.useCallerMethodIfLoggerNameNotSet
        } else {
            config.useCallerMethodIfLoggerNameNotSet
        }

}