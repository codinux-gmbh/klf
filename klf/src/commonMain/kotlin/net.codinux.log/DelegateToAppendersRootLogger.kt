package net.codinux.log

import net.codinux.log.appender.AppenderCollection
import net.codinux.log.config.EffectiveLoggerConfig

/**
 * The root logger is basically a normal logger but with these guarantees:
 * - Its [name] is [RootLoggerName]
 * - Its [level] is never null
 * - Its [level] cannot be set but equals [EffectiveLoggerConfig.rootLevel]. Set it via
 * [LoggerFactory.config] or [LoggerFactory.debugConfig]
 */
class DelegateToAppendersRootLogger(
    private val effectiveConfig: EffectiveLoggerConfig,
    appenders: AppenderCollection
) : DelegateToAppendersLogger(RootLoggerName, appenders) {

    companion object {
        const val RootLoggerName = ""
    }

    override var level: LogLevel?
        get() = effectiveConfig.rootLevel
        set(value) {
            throw IllegalStateException("Root logger's level cannot be set directly. Set it with " +
                    "LoggerFactory.config.rootLevel or LoggerFactory.debugConfig.rootLevel")
        }
}