package net.codinux.log.slf4j

import net.codinux.log.LogLevel
import net.codinux.log.slf4j.binding.*
import org.slf4j.Logger
import java.util.concurrent.ConcurrentHashMap

object Slf4jLogLevelHandler {

    private val bindingMap = ConcurrentHashMap<Slf4jBinding, Slf4jBindingImplementation?>()


    fun getLevel(logger: Logger, binding: Slf4jBinding): LogLevel? =
        getBindingImplementation(binding)?.getLevel(logger)

    fun setLevel(logger: Logger, binding: Slf4jBinding, level: LogLevel?): Boolean =
        getBindingImplementation(binding)?.setLevel(logger, level)
            ?: false

    private fun getBindingImplementation(binding: Slf4jBinding): Slf4jBindingImplementation? =
        bindingMap.getOrPut(binding) { createBindingImplementation(binding) }

    private fun createBindingImplementation(binding: Slf4jBinding): Slf4jBindingImplementation? = when (binding) {
        Slf4jBinding.Logback -> LogbackSlf4jBinding()
        Slf4jBinding.Log4j2 -> Log4j2Slf4jBinding()
        Slf4jBinding.Log4j1 -> Log4j1Slf4jBinding()
        Slf4jBinding.Reload4j -> Reload4jSlf4jBinding()
        Slf4jBinding.JUL -> JavaUtilLogSlf4jBinding()
        Slf4jBinding.Slf4jSimple -> Slf4jSimpleSlf4jBinding()
        else -> null
    }

}