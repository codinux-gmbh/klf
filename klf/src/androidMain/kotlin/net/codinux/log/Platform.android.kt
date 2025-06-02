package net.codinux.log

import net.codinux.kotlin.android.AndroidContext
import net.codinux.log.appender.Appender
import net.codinux.log.appender.LogcatAppender

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

                  // TODO: call ErrorLogger
              }

              null
          }
      } catch (e: Throwable) {
          Log.error<Platform>(e) { "Could not get app name" }

          null
      }
    }

}