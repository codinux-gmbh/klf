package net.codinux.log.mdc

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase
import net.codinux.log.logger
import net.codinux.log.withMdc
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.MDC

class LoggerWithMDCTest {

    private val appender = CollectLogEventsAppender()

    private val log by logger()

    init {
        (org.slf4j.LoggerFactory.getILoggerFactory() as? LoggerContext)?.let { context ->
            context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(appender)
        }
    }


    @Test
    fun withMdc() {
        val message = "Test message"
        val mdc = mapOf("MDC Key" to "MDC Value")


        log.withMdc(mdc).info { message }


        val infoEvents = appender.collectedLogEvents.filter { it.level == ch.qos.logback.classic.Level.INFO }
        assertThat(infoEvents).hasSize(1)

        val event = infoEvents.first()
        assertThat(event.mdcPropertyMap).containsExactlyEntriesOf(mdc)
        assertThat(event.message).isEqualTo(message)
        assertThat(event.loggerName).isEqualTo(LoggerWithMDCTest::class.qualifiedName)

        // assert MDC is cleared afterwards
        assertThat(MDC.getCopyOfContextMap()).isEmpty()
    }

    @Test
    fun runWithMdc() {
        val message = "Test message"
        val mdcKey = "MDC Key"
        val mdcValue = "MDC Value"


        runWithMdc(mdcKey to mdcValue) {
            log.info { message }
        }


        val infoEvents = appender.collectedLogEvents.filter { it.level == ch.qos.logback.classic.Level.INFO }
        assertThat(infoEvents).hasSize(1)

        val event = infoEvents.first()
        assertThat(event.mdcPropertyMap).containsExactlyEntriesOf(mapOf(mdcKey to mdcValue))
        assertThat(event.message).isEqualTo(message)
        assertThat(event.loggerName).isEqualTo(LoggerWithMDCTest::class.qualifiedName)

        // assert MDC is cleared afterwards
        assertThat(MDC.getCopyOfContextMap()).isEmpty()
    }


    class CollectLogEventsAppender(val collectedLogEvents: List<ILoggingEvent> = mutableListOf()) : UnsynchronizedAppenderBase<ILoggingEvent>() {

        init {
            start()
        }

        override fun append(eventObject: ILoggingEvent) {
            (collectedLogEvents as? MutableList<ILoggingEvent>)?.add(eventObject)
        }

    }
}