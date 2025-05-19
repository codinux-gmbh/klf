package net.codinux.log.config

import net.codinux.log.LogLevel
import net.codinux.log.formatter.LogEventFormatter

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

    val logEventFormatter: LogEventFormatter
        get() = if (isRunningInDebugMode && debugConfig.logEventFormatter != LoggerConfig.defaultLogEventFormatter) {
            debugConfig.logEventFormatter
        } else {
            config.logEventFormatter
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