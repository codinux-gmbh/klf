package net.codinux.log

open class NSLogLoggerFactory : ILoggerFactory {

    override fun getLogger(name: String): Logger = NSLogLogger(name)

}