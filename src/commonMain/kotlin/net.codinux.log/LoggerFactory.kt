package net.codinux.log

import kotlin.jvm.JvmStatic
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object LoggerFactory {

    private var factory: ILoggerFactory = SystemDefaults.createDefaultLoggerFactory()

    @JvmStatic
    fun setLoggerFactory(factory: ILoggerFactory) {
        this.factory = factory
    }


    @JvmStatic
    fun getLogger(name: String): Logger {
        return factory.getLogger(name)
    }

    @JvmStatic
    fun getLogger(forClass: KClass<*>): Logger =
        getLogger(getLoggerName(forClass))


    inline fun <reified R : Any> R.logger() = LoggerDelegate<R>()

    class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
        override fun getValue(thisRef: R, property: KProperty<*>) = LoggerFactory.getLogger(thisRef::class)
    }

}