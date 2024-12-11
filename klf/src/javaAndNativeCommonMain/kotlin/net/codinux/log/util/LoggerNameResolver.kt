package net.codinux.log.util

import kotlin.reflect.KClass

internal object LoggerNameResolver {

    fun getLoggerNameForKClassesWithQualifiedName(forClass: KClass<*>): String {
        forClass.qualifiedName?.let { qualifiedName ->
            removeCompanionAndInnerClassSeparatorFromName(qualifiedName)
        }

        forClass.simpleName?.let {
            return it
        }

        val asString = forClass.toString()
        return if (asString.startsWith("class ")) { // remove 'class ' from beginning to .toString() return value
            asString.substring("class ".length)
        } else {
            asString
        }
    }

    fun removeCompanionAndInnerClassSeparatorFromName(loggerName: String): String {
        // unwrap companion object
        return if (loggerName.endsWith(".Companion")) { // ok, someone could name a class 'Companion', but in this case i have no pity that his/her logger name is wrong then
            loggerName.substring(0, loggerName.length - ".Companion".length)
        } else {
            loggerName
        }
            .replace('$', '.') // os opposed to jvmName qualifiedName for inner classes already replaces '$' with '.'
    }


    fun getLoggerNameFromMethod(packageAndClassName: String, methodName: String): String {
        var className = packageAndClassName
        var method = methodName

        // remove inner class suffixes like "$2$1$2" from "App$2$1$2"
        while (className.length > 2 && className[className.length - 1].isDigit() && className[className.length - 2] == '$') {
            className = className.substring(0, className.length - 2)
        }

        if (method == "invoke" || method == "invokeSuspend") { // log statement has then been called from a coroutine function
            val indexOfDollarSign = className.lastIndexOfOrNull('$')
            val indexOfDot = className.lastIndexOfOrNull('.')
            if (indexOfDollarSign != null && (indexOfDot == null || indexOfDollarSign > indexOfDot)) {
                method = className.substring(indexOfDollarSign + 1) // -> className is something like <className>$<methodName>
                className = className.substring(0, indexOfDollarSign)
            }
        }

        if (className.endsWith("Kt")) {
            className = className.substring(0, className.length - "Kt".length)
        }

        val index = className.lastIndexOfOrNull('.')
        if (index != null && className.substring(index + 1) == method) {
            return className // class name equals method name, e.g. in Composables -> leave away redundant method name
        }

        return className + "." + method
    }

}