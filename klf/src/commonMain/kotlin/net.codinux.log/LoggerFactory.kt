package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.config.EffectiveLoggerConfig
import net.codinux.log.config.LoggerConfig
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass

/**
 * The central entry point of klf.
 *
 * Used to:
 * - Configure klf like `LoggerFactory.config.rootLevelDefault = LogLevel.Warn`, `LoggerFactory.debugConfig.logEventFormatter = YourLogEventFormatter`.
 * - Retrieve logger instances like `by logger()` (indirectly), `LoggerFactory.getLogger(UserService::class)`, `LoggerFactory.getLogger("org.company.feature.service.UserService")`.
 * - Register or remove appenders that control where log output is written (e.g., console, file, remote): `LoggerFactory.addAppender(YourAppender)`.
 * - Set a custom [ILoggerFactory]: `LoggerFactory.init(YourLoggerFactory)`. This has to be called before `ILoggerFactory` is used for the first time.
 */
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


    // to not call directly, always use getFactory()
    private lateinit var factory: ILoggerFactory

    /**
     * To provide a custom [ILoggerFactory] call this method before `ILoggerFactory` is used
     * for the first time, that is before any of these methods are called:
     * - [LoggerFactory.getLogger]
     * - [LoggerFactory.addAppender]
     * - [LoggerFactory.rootLogger]
     *
     * Otherwise an [IllegalStateException] will be thrown.
     */
    fun init(factory: ILoggerFactory) {
        if (this::factory.isInitialized) {
            throw IllegalStateException("ILoggerFactory has already been created. Call init(ILoggerFactory) before " +
                    "ILoggerFactory gets used for the first time.")
        }

        this.factory = factory
    }

    private fun getFactory(): ILoggerFactory {
        if (this::factory.isInitialized) {
            return factory
        }

        factory = Platform.createDefaultLoggerFactory()

        return factory
    }


    @JvmStatic
    val rootLogger: Logger
        get() = getFactory().rootLogger

    @JvmStatic
    fun addAppender(appender: Appender) {
        this.getFactory().addAppender(appender)
    }


    @JvmStatic
    fun getLogger(name: String?): Logger {
        // name can only be null when using one of the static log methods of net.codinux.log.Log without a logger name or class
        val actualName = name ?: loggerNameService.resolveDefaultLoggerName(effectiveConfig)

        return loggerCacheForName.getOrPut(actualName) {
            getFactory().createLogger(actualName)
        }
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        loggerCache.getOrPut(forClass) {
            getLogger(loggerNameService.getLoggerName(forClass))
        }

    @JvmStatic
    inline fun <reified R : Any> getLogger(): Logger = getLogger(R::class)

}