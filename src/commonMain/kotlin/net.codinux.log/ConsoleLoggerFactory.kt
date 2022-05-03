package net.codinux.log

open class ConsoleLoggerFactory : ILoggerFactory {

  override fun getLogger(name: String): Logger {
    return DelegateToAppenderLogger(name)
  }

}