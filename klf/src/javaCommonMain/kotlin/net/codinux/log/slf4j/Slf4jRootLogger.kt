package net.codinux.log.slf4j

import net.codinux.log.DelegateToAppendersRootLogger
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import net.codinux.log.appender.AppenderCollection
import net.codinux.log.config.EffectiveLoggerConfig

/**
 * The root logger is basically a normal logger but with these guarantees:
 * - Its [name] is [RootLoggerName]
 * - Its [level] is never null
 * - Its [level] cannot be set but equals [EffectiveLoggerConfig.rootLevel]. Set it via
 * [LoggerFactory.config] or [LoggerFactory.debugConfig]
 */
class Slf4jRootLogger(
    private val effectiveConfig: EffectiveLoggerConfig,
    appenderCollection: AppenderCollection,
) : Slf4jLogger(org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME), appenderCollection) {

    override val name: String = DelegateToAppendersRootLogger.RootLoggerName

    override var level: LogLevel?
        get() = effectiveConfig.rootLevel
        set(value) {
            throw IllegalStateException("Root logger's level cannot be set directly. Set it with " +
                    "LoggerFactory.config.rootLevel or LoggerFactory.debugConfig.rootLevel")
        }

    init {
        super.level = effectiveConfig.rootLevel // TODO: doesn't react to changes in rootLevel
    }

}