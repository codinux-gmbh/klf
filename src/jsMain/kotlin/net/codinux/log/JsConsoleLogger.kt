package net.codinux.log

open class JsConsoleLogger(name: String, level: LogLevel = Logger.DefaultLevel) : LoggerBase(name, level) {

  override fun log(level: LogLevel, message: String, exception: Throwable?) {
    when (level) {
      LogLevel.Fatal, LogLevel.Error -> console.error(message, exception)
      LogLevel.Warn -> console.warn(message, exception)
      LogLevel.Info -> console.info(message, exception)
      LogLevel.Debug, LogLevel.Trace -> console.log(message, exception)
      LogLevel.Off -> { }
    }
  }

}