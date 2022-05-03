package net.codinux.log.appender

import net.codinux.log.LogLevel


open class ConsoleAppender : Appender {

  override fun append(level: LogLevel, loggerName: String, message: String, exception: Throwable?, vararg arguments: Any) {
    println(createOutputString(level, loggerName, message, exception, *arguments))
  }


  protected open fun createOutputString(level: LogLevel, loggerName: String, message: String, exception: Throwable?, vararg arguments: Any): String {
    // really, there's not String.format() ?! // TODO: add arguments
    val formattedMessage = if (exception != null) {
      "$message: $exception\n${exception.stackTraceToString()}"
    } else message

    return "[$level] $loggerName - $formattedMessage"
  }

}