package net.codinux.log

import net.codinux.log.appender.Appender
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

object LoggerFactory {

    private var factory: ILoggerFactory = Platform.createDefaultLoggerFactory()

    @JvmStatic
    fun setLoggerFactory(factory: ILoggerFactory) {
        this.factory = factory
    }

    @JvmStatic
    fun addAppender(appender: Appender) {
        this.factory.addAppender(appender)
    }


    @JvmStatic
    fun getLogger(name: String): Logger {
        return factory.getLogger(name)
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        getLogger(Platform.getLoggerName(forClass))

}