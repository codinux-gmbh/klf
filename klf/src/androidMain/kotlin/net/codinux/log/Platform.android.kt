package net.codinux.log

import android.content.pm.ApplicationInfo
import android.os.Build
import net.codinux.log.android.AndroidContext
import net.codinux.log.appender.Appender
import net.codinux.log.appender.LogcatAppender
import kotlin.reflect.KClass

internal actual object Platform {

    const val MaxAndroidLogTagSizeBeforeApi26 = 23


    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { LogcatAppender.Default }

    actual fun <T : Any> getLoggerName(forClass: KClass<T>): String = getAndroidLogTag(forClass)

    actual fun getLoggerNameFromCallingMethod(): String? =
        JvmDefaults.getLoggerNameFromCallingMethod()

    actual val appName: String? by lazy {
      try {
          AndroidContext.applicationContext?.applicationContext?.let { context ->
              context.applicationInfo?.let { applicationInfo ->
                  applicationInfo.loadLabel(context.packageManager)?.let { label ->
                      "${applicationInfo.packageName ?: applicationInfo.processName}.$label"
                  }
              }
          }
      } catch (e: Throwable) {
          Log.error<Platform>(e) { "Could not get app name" }

          null
      }
    }


    /**
     * Before API 26 there was a max log tag length of 23:
     * "An IllegalArgumentException is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25),
     * there is no tag limit of concern after this API level."
     * [https://developer.android.com/reference/android/util/Log#isLoggable(java.lang.String,%20int)](https://developer.android.com/reference/android/util/Log#isLoggable(java.lang.String,%20int))
     */
    private fun <T : Any> getAndroidLogTag(forClass: KClass<T>): String {
      val defaultLogTag = JvmDefaults.getLoggerName(forClass)

      // >= 1 as for tests SDK_INT is 0
      return if (Build.VERSION.SDK_INT in 1 .. 25 && defaultLogTag.length > MaxAndroidLogTagSizeBeforeApi26) {
        getAndroidLogTagOfLogTagMaxLength(forClass)
      } else {
        defaultLogTag
      }
    }

    // visible for testing
    internal fun <T : Any> getAndroidLogTagOfLogTagMaxLength(forClass: KClass<T>): String {
      val loggerClass = JvmDefaults.unwrapCompanionClass(forClass)
      val className = loggerClass.simpleName!!
      val classNameLength = className.length

      if (classNameLength > MaxAndroidLogTagSizeBeforeApi26) {
        return "*" + className.substring(classNameLength - MaxAndroidLogTagSizeBeforeApi26  + 1)
      }

      return getClassNameWithShortenedPackageNameOfLogTagMaxLength(loggerClass, className)
        ?: className // even shortened package name is too long -> return class name only
    }

    private fun <T : Any> getClassNameWithShortenedPackageNameOfLogTagMaxLength(loggerClass: KClass<T>, className: String): String? {
      return try {
        val packageName = loggerClass.java.packageName

        getClassNameWithShortenedPackageNameOfLogTagMaxLength(packageName, className)
      } catch (e: Throwable) { // TODO: log to error logger
        Log.error<Platform>(e) { "Could not shorten class name to meet Android's max log tag length of $MaxAndroidLogTagSizeBeforeApi26 on older Androids before API 26" }
        null
      }
    }

    // visible for testing
    internal fun getClassNameWithShortenedPackageNameOfLogTagMaxLength(packageName: String, className: String): String? {
      val packagesShortName = packageName.split('.')
        .map { name ->
          if (name.length == 1) {
            name
          } else {
            name[0] + "*"
          }
        }
        .joinToString(".")

      return if (packagesShortName.length + className.length + 1 <= MaxAndroidLogTagSizeBeforeApi26) {
        packagesShortName + "." + className
      } else {
        null
      }
    }

}