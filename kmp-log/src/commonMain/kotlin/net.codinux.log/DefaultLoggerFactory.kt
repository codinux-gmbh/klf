package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override val rootLogger = DelegateToAppenderLogger("", this, LoggerFactory.DefaultLevel)

  override fun createLogger(name: String) =
    DelegateToAppenderLogger(name, this)

}