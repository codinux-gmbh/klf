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

    // on JVM the first element is "java.lang.Thread.getStackTrace", on Android "dalvik.system.VMStack.getThreadStackTrace" and then "java.lang.Thread.getStackTrace"
    val getStackTraceElement = stackTrace.indexOfFirst { it.className == "java.lang.Thread" && it.methodName == "getStackTrace" }

    // index 0 is Thread.getStackTrace() (or on Android dalvik.system.VMStack.getThreadStackTrace, which moves all indices at by one)
    // index 1 is this method
    // index 2 is Platform.getLoggerNameFromCallingMethod()
    // index 3 is LoggerNameService.resolveDefaultLoggerName()
    // index 4 is LoggerFactory.getLogger(String?)
    return stackTrace.drop(getStackTraceElement + 5).firstOrNull()?.let { stackTraceElement ->
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