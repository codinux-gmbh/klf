package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override val rootLogger = DelegateToAppenderLogger("", this, LoggerFactory.effectiveConfig.rootLevel)

  override fun createLogger(name: String) =
    DelegateToAppenderLogger(name, this)

}