package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.classname.ClassNameResolver
import net.codinux.log.classname.ClassType
import net.codinux.log.config.EffectiveLoggerConfig
import net.codinux.log.config.LoggerConfig
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass

@ThreadLocal // actually not needed anymore on Kotlin 1.7 and above but to make compiler happy
object LoggerFactory {

    private val loggerNameService = LoggerNameService.Default


    /**
     * The logger name that will be applied if no logger tag has been provided, e.g. with
     * `Log.info { "Info without provided logger tag" }`.
     *
     * If [defaultLoggerName] has not been set, we try to find the app name.
     *
     * If this does not work, then `"net.codinux.log.klf"` will be used as logger name.
     */
    @JvmStatic
    @Deprecated(
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith("LoggerFactory.config.defaultLoggerName or LoggerFactory.debugConfig.defaultLoggerName"),
        message = "Simply passes call on to `LoggerFactory.config.defaultLoggerName`.\nUse `LoggerFactory.config.defaultLoggerName` or `LoggerFactory.debugConfig.defaultLoggerName` instead.\nThis will be removed in klf 2.0.\n"
    )
    var defaultLoggerName: String?
        get() = config.defaultLoggerName
        set(value) {
            config.defaultLoggerName = value
        }

    /**
     * Experimental: Sets the default log level for all loggers that will be used if no logger specific level is set with [Logger.level].
     *
     * Be aware, this does not work reliably for all logging backends, e.g. we don't have implementations for all slf4j
     * logging backends and the Android min log level is unchangeable.
     *
     * If slf4j is on the classpath, configure log level via logging backend (logback, log4j, ...).
     */
    @JvmStatic
    @Deprecated(
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith("LoggerFactory.config.rootLevel or LoggerFactory.debugConfig.rootLevel"),
        message = "Simply passes call on to `LoggerFactory.config.rootLevel`.\nUse `LoggerFactory.config.rootLevel` or `LoggerFactory.debugConfig.rootLevel` instead.\nThis will be removed in klf 2.0.\n"
    )
    var RootLevel: LogLevel
        get() = config.rootLevel
        set(value) {
            config.rootLevel = value
        }


    @JvmStatic
    val config: LoggerConfig = LoggerConfig()

    @JvmStatic
    val debugConfig: LoggerConfig = LoggerConfig(defaultRootLevel = LogLevel.Debug)

    internal val effectiveConfig: EffectiveLoggerConfig = EffectiveLoggerConfig(config, debugConfig, Defaults.isRunningInDebugMode)


    internal var factory: ILoggerFactory = Platform.createDefaultLoggerFactory()

    @JvmStatic
    fun setLoggerFactory(factory: ILoggerFactory) {
        this.factory = factory
    }

    @JvmStatic
    val rootLogger: Logger
        get() = factory.rootLogger

    @JvmStatic
    fun addAppender(appender: Appender) {
        this.factory.addAppender(appender)
    }


    @JvmStatic
    fun getLogger(name: String?): Logger = loggerNameService.getLogger(name)

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger = loggerNameService.getLogger(forClass)

}