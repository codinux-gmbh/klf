package net.codinux.log

import net.codinux.log.slf4j.Slf4jLoggerFactory
import net.codinux.log.slf4j.Slf4jUtil
import net.codinux.log.util.LoggerNameResolver
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

object JvmDefaults {

  fun createDefaultLoggerFactory(): ILoggerFactory {
    if (Slf4jUtil.useSlf4j) {
      return Slf4jLoggerFactory()
    }

    return DefaultLoggerFactory()
  }


  fun <T : Any> getLoggerName(forClass: KClass<T>): String = getClassName(unwrapCompanionClass(forClass))

  fun <T : Any> getLoggerName(forClass: Class<T>): String = getLoggerName(forClass.kotlin)

  // unwrap companion class to enclosing class given a Java Class
  internal fun <T : Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
    return if (ofClass.isCompanion) {
      ofClass.java.enclosingClass?.kotlin // enclosingClass should never be null, just in case
        ?: ofClass
    } else {
      ofClass
    }
  }

  private fun <T : Any> getClassName(forClass: KClass<T>): String =
    forClass.qualifiedName // os opposed to jvmName qualifiedName for inner classes already replaces '$' with '.'
      ?: forClass.jvmName.replace('$', '.')

  fun <T : Any> getClassNameWithUnwrappingCompanion(forClass: KClass<T>): String =
    LoggerNameResolver.removeCompanionAndInnerClassSeparatorFromName(forClass.qualifiedName ?: forClass.jvmName)


  fun getLoggerNameFromCallingMethod(): String? {
    val stackTrace = Thread.currentThread().stackTrace

    // on JVM the first element is "java.lang.Thread.getStackTrace", on Android "dalvik.system.VMStack.getThreadStackTrace" and then "java.lang.Thread.getStackTrace"
    val getStackTraceElement = stackTrace.indexOfFirst { it.className == "java.lang.Thread" && it.methodName == "getStackTrace" }

    // index 0 is Thread.getStackTrace() (or on Android dalvik.system.VMStack.getThreadStackTrace, which moves all indices at by one)
    // index 1 is this method
    // index 2 is Platform.getLoggerNameFromCallingMethod()
    // index 3 is LoggerFactory.resolveDefaultLoggerName()
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