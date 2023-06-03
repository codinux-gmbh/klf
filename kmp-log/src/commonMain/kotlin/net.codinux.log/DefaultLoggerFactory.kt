package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override fun createLogger(name: String) =
    DelegateToAppenderLogger(name, this)

}