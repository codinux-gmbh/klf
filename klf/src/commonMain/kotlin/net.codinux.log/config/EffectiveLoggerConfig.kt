package net.codinux.log.config

import net.codinux.log.LogLevel

class EffectiveLoggerConfig(
    private val config: LoggerConfig,
    private val debugConfig: LoggerConfig,
    private val isRunningInDebugMode: Boolean
) {

    val rootLevel: LogLevel
        get() = if (isRunningInDebugMode && debugConfig.rootLevel != LoggerConfig.rootLevelDefault) {
            debugConfig.rootLevel
        } else {
            config.rootLevel
        }

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