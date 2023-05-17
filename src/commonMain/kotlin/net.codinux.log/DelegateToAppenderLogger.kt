package net.codinux.log

import net.codinux.log.Logger.Companion.DefaultLevel
import net.codinux.log.appender.AppenderContainer


open class DelegateToAppenderLogger(
  name: String,
  protected open val container: AppenderContainer, // or use ILoggerFactory implementation directly?
  level: LogLevel = DefaultLevel
) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?) {
    container.getAppenders().forEach {
      it.append(level, name, message, exception)
    }
  }

}