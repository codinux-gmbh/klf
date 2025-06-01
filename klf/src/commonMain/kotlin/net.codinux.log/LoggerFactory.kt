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

    private val loggerCache = Cache<KClass<*>, Logger>()

    private val loggerCacheForName = Cache<String, Logger>()

    private val loggerNameService = LoggerNameService.Default


    @JvmStatic
    val config: LoggerConfig = LoggerConfig()

    @JvmStatic
    val debugConfig: LoggerConfig = LoggerConfig(defaultRootLevel = LogLevel.Debug)

    internal val effectiveConfig: EffectiveLoggerConfig = EffectiveLoggerConfig(config, debugConfig, Defaults.isRunningInDebugMode)


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
        val actualName = name ?: loggerNameService.resolveDefaultLoggerName(effectiveConfig)

        return loggerCacheForName.getOrPut(actualName) {
            factory.createLogger(actualName)
        }
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        loggerCache.getOrPut(forClass) {
            getLogger(loggerNameService.getLoggerName(forClass))
        }

}