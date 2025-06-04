package net.codinux.log

import net.codinux.log.config.EffectiveLoggerConfig

/**
 * The root logger is basically a normal [Logger] but with these guarantees:
 * - Its [name] is [RootLoggerName]
 * - Its [level] is never null
 * - Its [level] cannot be set but equals [EffectiveLoggerConfig.rootLevel]. Set it via
 * [LoggerFactory.config] or [LoggerFactory.debugConfig]
 */
interface RootLogger : Logger {

    companion object {
        const val RootLoggerName = ""
    }

}