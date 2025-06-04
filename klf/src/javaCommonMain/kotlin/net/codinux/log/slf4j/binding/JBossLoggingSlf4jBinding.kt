package net.codinux.log.slf4j.binding

import net.codinux.log.slf4j.Slf4jBinding

open class JBossLoggingSlf4jBinding : JavaUtilLogSlf4jBinding() {

    override val binding = Slf4jBinding.JBossLogging

    override val rootLoggerName = "ROOT"

    // of course there are differences to JavaUtilLog, but for our needs JavaUtilLog binding fulfills our needs

}