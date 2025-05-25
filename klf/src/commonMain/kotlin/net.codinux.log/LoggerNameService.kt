package net.codinux.log

import net.codinux.log.classname.ClassNameResolver
import net.codinux.log.classname.ClassType
import kotlin.reflect.KClass

open class LoggerNameService {

    companion object {
        val Default = LoggerNameService()
    }


    protected val loggerCache = Cache<KClass<*>, Logger>()

    protected val loggerCacheForName = Cache<String, Logger>()

    protected val classNameResolver = ClassNameResolver()


    open fun getLogger(name: String?): Logger {
        // name can only be null when using one of the static log methods of net.codinux.log.Log without a logger name or class
        val actualName = name ?: resolveDefaultLoggerName()

        return loggerCacheForName.getOrPut(actualName) {
            LoggerFactory.factory.createLogger(actualName)
        }
    }

    open fun getLogger(forClass: KClass<*>): Logger =
        loggerCache.getOrPut(forClass) {
            getLogger(getLoggerName(forClass))
        }

    protected open fun getLoggerName(forClass: KClass<*>): String {
        val components = classNameResolver.getClassNameComponents(forClass)

        val className = if (components.enclosingClassName != null && components.type != ClassType.InnerClass && components.type != ClassType.LocalClass) {
            components.enclosingClassName!!
        } else {
            components.className
        }

        return components.packageNamePrefix + className
    }

    protected open fun resolveDefaultLoggerName(): String {
        if (LoggerFactory.effectiveConfig.useCallerMethodIfLoggerNameNotSet) {
            Platform.getLoggerNameFromCallingMethod()?.let { fromCallingMethod ->
                return fromCallingMethod
            }
        }

        return LoggerFactory.effectiveConfig.defaultLoggerName ?: Platform.appName ?: "net.codinux.log.klf"
    }

}