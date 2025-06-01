package net.codinux.log

import net.codinux.log.classname.ClassNameResolver
import net.codinux.log.classname.ClassType
import kotlin.reflect.KClass

open class LoggerNameService {

    companion object {
        val Default = LoggerNameService()
    }


    protected val classNameResolver = ClassNameResolver()


    open fun getLoggerName(forClass: KClass<*>): String {
        val components = classNameResolver.getClassNameComponents(forClass)

        val className = if (components.enclosingClassName != null && components.type != ClassType.InnerClass && components.type != ClassType.LocalClass) {
            components.enclosingClassName!!
        } else {
            components.className
        }

        return components.packageNamePrefix + className
    }

    open fun resolveDefaultLoggerName(): String {
        if (LoggerFactory.effectiveConfig.useCallerMethodIfLoggerNameNotSet) {
            Platform.getLoggerNameFromCallingMethod()?.let { fromCallingMethod ->
                return fromCallingMethod
            }
        }

        return LoggerFactory.effectiveConfig.defaultLoggerName ?: Platform.appName ?: "net.codinux.log.klf"
    }

}