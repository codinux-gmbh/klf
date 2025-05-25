package net.codinux.log

import net.codinux.log.android.AndroidContext
import net.codinux.log.appender.Appender
import net.codinux.log.appender.LogcatAppender

internal actual object Platform {

    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { LogcatAppender.Default }

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

}