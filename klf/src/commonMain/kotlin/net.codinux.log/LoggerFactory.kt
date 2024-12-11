package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.config.LoggerConfig
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass

@ThreadLocal // actually not needed anymore on Kotlin 1.7 and above but to make compiler happy
object LoggerFactory {

    private val loggerCache = Cache<KClass<*>, Logger>()

    private val loggerCacheForName = Cache<String, Logger>()

    private var customDefaultLoggerName: String? = null

    /**
     * The logger name that will be applied if no logger tag has been provided, e.g. with
     * `Log.info { "Info without provided logger tag" }`.
     *
     * If [defaultLoggerName] has not been set, we try to find the app name.
     *
     * If this does not work, then `"net.codinux.log.klf"` will be used as logger name.
     */
    var defaultLoggerName: String
        get() = customDefaultLoggerName ?: Platform.appName ?: "net.codinux.log.klf"
        set(value) {
            customDefaultLoggerName = value
        }

    @JvmStatic
    val config: LoggerConfig = LoggerConfig()

    /**
     * Experimental: Sets the default log level for all loggers that will be used if no logger specific level is set with [LoggerBase.level].
     *
     * Be aware, this does not work reliably for all logging backends, e.g. we don't have implementations for all slf4j
     * logging backends and the Android min log level is unchangeable.
     *
     * If slf4j is on the classpath, configure log level via logging backend (logback, log4j, ...).
     */
    @JvmStatic
    var RootLevel: LogLevel = LogLevel.Info

    private var factory: ILoggerFactory = Platform.createDefaultLoggerFactory()

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
    fun getLogger(name: String?): Logger {
        // name can only be null when using one of the static log methods of net.codinux.log.Log without a logger name or class
        val actualName = name ?: resolveDefaultLoggerName()

        return loggerCacheForName.getOrPut(actualName) {
            factory.createLogger(actualName)
        }
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        loggerCache.getOrPut(forClass) {
            getLogger(Platform.getLoggerName(forClass))
        }

    private fun resolveDefaultLoggerName(): String {
        if (config.useCallerMethodIfLoggerNameNotSet) {
            Platform.getLoggerNameFromCallingMethod()?.let { fromCallingMethod ->
                return fromCallingMethod
            }
        }

        return defaultLoggerName
    }

}