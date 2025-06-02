package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory
import net.codinux.log.slf4j.Slf4jUtil
import net.codinux.log.util.LoggerNameResolver

object JvmDefaults {

  fun createDefaultLoggerFactory(): ILoggerFactory {
    if (Slf4jUtil.useSlf4j) {
      return Slf4jLoggerFactory()
    }

    return DefaultLoggerFactory()
  }


  fun getLoggerNameFromCallingMethod(): String? {
    val stackTrace = Thread.currentThread().stackTrace

    // the only entry point to klf to get here is when LoggerFactory.getLogger(String?) called with null as argument
    // so find that method in the stack trace. The method that called that one is the one we are searching for
    val getLoggerStackTraceElement = stackTrace.indexOfFirst { it.className == "net.codinux.log.LoggerFactory" && it.methodName == "getLogger" }

    return stackTrace.drop(getLoggerStackTraceElement + 1).firstOrNull()?.let { stackTraceElement ->
      LoggerNameResolver.getLoggerNameFromMethod(stackTraceElement.className, stackTraceElement.methodName)
    }
  }


  fun isClassAvailable(qualifiedClassName: String): Boolean =
    getClassOrNull(qualifiedClassName) != null

  fun getClassOrNull(qualifiedClassName: String): Class<*>? =
    try {
      Class.forName(qualifiedClassName)
    } catch (ignored: Exception) {
      null
    }

}