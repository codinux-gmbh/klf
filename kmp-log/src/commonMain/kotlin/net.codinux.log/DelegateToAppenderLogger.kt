package net.codinux.log

import net.codinux.log.appender.AppenderContainer
import kotlin.jvm.JvmOverloads


open class DelegateToAppenderLogger @JvmOverloads constructor(
  name: String,
  protected open val container: AppenderContainer, // or use ILoggerFactory implementation directly?
  level: LogLevel? = DefaultLevel
) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?) {
    val threadName = if (container.doesAnyAppenderLogThreadName) Platform.getCurrentThreadName() else null

    container.getAppenders().forEach { appender ->
      appender.append(
        level,
        message,
        name,
        if (appender.logsThreadName) threadName else null,
        if (appender.logsException) exception else null
      )
    }
  }

}