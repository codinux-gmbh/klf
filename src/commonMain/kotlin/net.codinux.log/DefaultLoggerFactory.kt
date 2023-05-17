package net.codinux.log

import net.codinux.log.appender.Appender

open class DefaultLoggerFactory : ILoggerFactory {

  // TODO: this structure is not thread safe. But should be ok in most cases as Appender only get added at start and afterwards there will be only read access
  protected open val appenders = linkedSetOf<Appender>()

  init {
      addAppender(Platform.getSystemDefaultAppender())
  }


  override fun getLogger(name: String): Logger {
    return DelegateToAppenderLogger(name, this)
  }

  override fun addAppender(appender: Appender) {
    appenders.add(appender)
  }

  override fun getAppenders(): Collection<Appender> =
    appenders.toList() // make a copy, don't pass mutable state to the outside

}