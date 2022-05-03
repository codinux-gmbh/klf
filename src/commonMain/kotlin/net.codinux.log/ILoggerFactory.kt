package net.codinux.log

interface ILoggerFactory {

    fun getLogger(name: String): Logger

}