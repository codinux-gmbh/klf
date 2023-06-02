package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.concurrent.ConcurrentSet

open class DefaultLoggerFactory : ILoggerFactory {

  protected open val appenders = ConcurrentSet<Appender>()

  protected open val loggerCache = Cache<Logger>()

  override var doesAnyAppenderLogThreadName: Boolean = false
    protected set

  init {
      addAppender(Platform.systemDefaultAppender)
  }


  override fun getLogger(name: String): Logger {
    return loggerCache.getOrPut(name) {
      DelegateToAppenderLogger(name, this)
    }
  }

  override fun addAppender(appender: Appender) {
    appenders.add(appender)

    this.doesAnyAppenderLogThreadName = appenders.any { it.logsThreadName }
  }

  override fun getAppenders(): Collection<Appender> =
    appenders.toList() // make a copy, don't pass mutable state to the outside

}