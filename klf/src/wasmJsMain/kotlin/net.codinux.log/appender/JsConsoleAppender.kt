package net.codinux.log.appender

import net.codinux.log.LogLevel
import net.codinux.log.formatter.MessageFormatter


fun consoleError(message: String): Unit =
  js("console.error(message)")

fun consoleError(message: String, exception: String): Unit =
  js("console.error(message, exception)")

fun consoleWarn(message: String): Unit =
  js("console.warn(message)")

fun consoleWarn(message: String, exception: String): Unit =
  js("console.warn(message, exception)")

fun consoleInfo(message: String): Unit =
  js("console.info(message)")

fun consoleInfo(message: String, exception: String): Unit =
  js("console.info(message, exception)")

fun consoleLog(message: String): Unit =
  js("console.log(message)")

fun consoleLog(message: String, exception: String): Unit =
  js("console.log(message, exception)")


open class JsConsoleAppender : Appender {

  protected open val formatter = MessageFormatter()


  override val logsThreadName = true

  override val logsException = true

  override fun append(level: LogLevel, message: String, loggerName: String, threadName: String?, exception: Throwable?) {
    val formattedMessage = formatter.formatMessage(level, message, loggerName, threadName, exception)

    when (level) {
      LogLevel.Error -> {
        if (exception == null) {
          consoleError(formattedMessage)
        } else {
          consoleError(formattedMessage, exceptionAsString(exception))
        }
      }
      LogLevel.Warn -> {
        if (exception == null) {
          consoleWarn(formattedMessage)
        } else {

          consoleWarn(formattedMessage, exceptionAsString(exception))
        }
      }
      LogLevel.Info -> {
        if (exception == null) {
          consoleInfo(formattedMessage)
        } else {
          consoleInfo(formattedMessage, exceptionAsString(exception))
        }
      }
      LogLevel.Debug, LogLevel.Trace -> { // why is there no console.debug() method?
        if (exception == null) {
          consoleLog(formattedMessage)
        } else {
          consoleLog(formattedMessage, exceptionAsString(exception))
        }
      }
      LogLevel.Off -> { }
    }


  }

  private fun exceptionAsString(exception: Throwable): String =
    exception.stackTraceToString()

}