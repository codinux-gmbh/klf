package net.codinux.log

open class DefaultLoggerFactory : LoggerFactoryBase() {

  override fun createLogger(name: String) =
    DelegateToAppenderLogger(name, this)

}