package net.codinux.log.slf4j

import net.codinux.log.LoggerFactory
import net.codinux.log.LoggerFactoryBase
import net.codinux.log.slf4j.binding.Log4j2Configurator

open class Slf4jLoggerFactory : LoggerFactoryBase() {

    protected open val log4j2Configurator: Log4j2Configurator by lazy { Log4j2Configurator() }


    init {
        if (Slf4jUtil.boundLoggingFramework == Slf4jBinding.Log4j2) {
            log4j2Configurator.setRootLevel(LoggerFactory.RootLevel)
        }
    }


    override val rootLogger = Slf4jLogger(org.slf4j.Logger.ROOT_LOGGER_NAME, this, LoggerFactory.RootLevel)

    override fun createLogger(name: String) =
        Slf4jLogger(name, this)

}