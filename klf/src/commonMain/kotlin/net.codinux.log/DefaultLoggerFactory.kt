package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  companion object {
    const val RootLoggerName = ""
  }


  init {
    addAppender(Platform.systemDefaultAppender)
  }


  override val rootLogger = DelegateToAppendersLogger(RootLoggerName, this, LoggerFactory.effectiveConfig.rootLevel)

  override fun createLogger(name: String) =
    DelegateToAppendersLogger(name, this)

}