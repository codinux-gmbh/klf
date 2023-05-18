package net.codinux.log

import net.codinux.log.appender.AppenderContainer
import kotlin.jvm.JvmOverloads


open class DelegateToAppenderLogger @JvmOverloads constructor(
  name: String,
  protected open val container: AppenderContainer, // or use ILoggerFactory implementation directly?
  level: LogLevel? = DefaultLevel
) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?) {
    container.getAppenders().forEach {
      it.append(level, name, message, exception)
    }
  }

}