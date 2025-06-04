package net.codinux.log

import net.codinux.log.appender.AppenderCollectionImpl

open class DefaultLoggerFactory : AppenderCollectionImpl(), ILoggerFactory {

  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override val rootLogger = DelegateToAppendersRootLogger(LoggerFactory.effectiveConfig, this)

  override fun createLogger(name: String) =
    DelegateToAppendersLogger(name, this)

}