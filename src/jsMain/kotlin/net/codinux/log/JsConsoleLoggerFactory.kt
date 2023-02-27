package net.codinux.log

open class JsConsoleLoggerFactory : ILoggerFactory {

  override fun getLogger(name: String): Logger = JsConsoleLogger(name)

}