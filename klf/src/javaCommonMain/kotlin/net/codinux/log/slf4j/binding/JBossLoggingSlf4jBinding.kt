package net.codinux.log.slf4j.binding

open class JBossLoggingSlf4jBinding : JavaUtilLogSlf4jBinding() {

    override val rootLoggerName = "ROOT"

    // of course there are differences to JavaUtilLog, but for our needs JavaUtilLog binding fulfills our needs

}