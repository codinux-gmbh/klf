package net.codinux.log

import net.codinux.log.appender.AppenderCollection
import net.codinux.log.config.EffectiveLoggerConfig

class DelegateToAppendersRootLogger(
    private val effectiveConfig: EffectiveLoggerConfig,
    appenders: AppenderCollection
) : DelegateToAppendersLogger(RootLogger.RootLoggerName, appenders), RootLogger {

    override var level: LogLevel?
        get() = effectiveConfig.rootLevel
        set(value) {
            throw IllegalStateException("Root logger's level cannot be set directly. Set it with " +
                    "LoggerFactory.config.rootLevel or LoggerFactory.debugConfig.rootLevel")
        }

}