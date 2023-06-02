package net.codinux.log

import net.codinux.log.appender.Appender
import net.codinux.log.collection.ConcurrentSet
import net.codinux.log.collection.ImmutableCollection
import net.codinux.log.collection.toImmutableCollection

open class DefaultLoggerFactory : ILoggerFactory {

  protected open val loggerCache = Cache<Logger>()

  protected open val appenders = ConcurrentSet<Appender>()

  protected open var immutableAppenders = ImmutableCollection<Appender>()

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
    immutableAppenders = appenders.toImmutableCollection() // make a copy, don't pass mutable state to the outside

    this.doesAnyAppenderLogThreadName = appenders.any { it.logsThreadName }
  }

  override fun getAppenders(): Collection<Appender> =
    immutableAppenders

}