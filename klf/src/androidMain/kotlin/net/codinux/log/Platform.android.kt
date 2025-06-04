package net.codinux.log

import net.codinux.kotlin.android.AndroidContext
import net.codinux.log.appender.Appender
import net.codinux.log.appender.LogcatAppender
import net.codinux.log.status.StatusManager

internal actual object Platform {

    private var hasLoggedThatAppNameCannotBeRetrievedAsApplicationContextIsNotSet = false


    actual fun createDefaultLoggerFactory() = JvmDefaults.createDefaultLoggerFactory()

    actual val systemDefaultAppender: Appender by lazy { LogcatAppender.Default }

    actual fun getLoggerNameFromCallingMethod(): String? =
        JvmDefaults.getLoggerNameFromCallingMethod()

    actual val appName: String? by lazy {
      try {
          val context = AndroidContext.getApplicationContextIfInitialized()

          if (context != null) {
              context.applicationInfo?.let { applicationInfo ->
                  applicationInfo.loadLabel(context.packageManager)?.let { label ->
                      "${applicationInfo.packageName ?: applicationInfo.processName}.$label"
                  }
              }
          } else {
              if (hasLoggedThatAppNameCannotBeRetrievedAsApplicationContextIsNotSet == false) {
                  hasLoggedThatAppNameCannotBeRetrievedAsApplicationContextIsNotSet = true

                  StatusManager.newError(this, "Cannot determine Android app name as " +
                          "net.codinux.kotlin.android.AndroidContext.applicationContext is not set.\nTo be able to " +
                          "determine Android app name and use it as default logger name, please set AndroidContext.applicationContext.")
              }

              null
          }
      } catch (e: Throwable) {
          Log.error<Platform>(e) { "Could not get app name" }

          null
      }
    }

}