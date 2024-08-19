package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass

@ThreadLocal // actually not needed anymore on Kotlin 1.7 and above but to make compiler happy
object LoggerFactory {

    private val loggerCache = Cache<KClass<*>, Logger>()

    private val loggerCacheForName = Cache<String, Logger>()

    private var customDefaultLoggerName: String? = null

    var defaultLoggerName: String
        get() = customDefaultLoggerName ?: Platform.appName ?: "net.codinux.log.klf"
        set(value) {
            customDefaultLoggerName = value
        }

    /**
     * The default log level of the logging system that will be used if no logger specific level is set with [LoggerBase.level].
     *
     * If slf4j is on the classpath setting this value has no effect. Configure log level via logging backend (logback, log4j, ...) then.
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
        val actualName = name ?: defaultLoggerName

        return loggerCacheForName.getOrPut(actualName) {
            factory.createLogger(actualName)
        }
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        loggerCache.getOrPut(forClass) {
            getLogger(Platform.getLoggerName(forClass))
        }

}