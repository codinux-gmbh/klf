package net.codinux.log

import kotlin.reflect.KClass

fun Platform.getLoggerNameForKClassesWithQualifiedName(forClass: KClass<*>): String {
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

fun Platform.removeCompanionAndInnerClassSeparatorFromName(loggerName: String): String {
    // unwrap companion object
    return if (loggerName.endsWith(".Companion")) { // ok, someone could name a class 'Companion', but in this case i have no pity that his/her logger name is wrong then
        loggerName.substring(0, loggerName.length - ".Companion".length)
    } else {
        loggerName
    }
        .replace('$', '.') // os opposed to jvmName qualifiedName for inner classes already replaces '$' with '.'
}