package net.codinux.log

import net.codinux.log.Logger.Companion.DefaultLevel
import net.codinux.log.appender.Appender
import net.codinux.log.appender.Appenders


open class DelegateToAppenderLogger(
  name: String,
  protected open var appenders: List<Appender> = Appenders.appenders,
  level: LogLevel = DefaultLevel
) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?, vararg arguments: Any) {
    appenders.forEach { it.append(level, name, message, exception, *arguments) }
  }

}