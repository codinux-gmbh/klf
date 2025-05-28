package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override val rootLogger = DelegateToAppendersLogger("", this, LoggerFactory.effectiveConfig.rootLevel)

  override fun createLogger(name: String) =
    DelegateToAppendersLogger(name, this)

}