package net.codinux.log.slf4j

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.codinux.log.LogEvent
import net.codinux.log.LogLevel
import net.codinux.log.LoggerFactory
import net.codinux.log.appender.Appender
import org.junit.jupiter.api.Test
import kotlin.reflect.jvm.jvmName

class Slf4jAppenderTest {

    companion object {
        private const val Message = "Info message"
        private val LoggerName = Slf4jAppenderTest::class.jvmName
    }


    init {
        LoggerFactory.setLoggerFactory(Slf4jLoggerFactory())
    }

    @Test
    fun append() {
        val mockAppender = mockk<Appender>()
        every { mockAppender.logsThreadName } returns false
        every { mockAppender.logsException } returns false
        every { mockAppender.append(any()) } returns Unit

        LoggerFactory.addAppender(mockAppender)
        val log = LoggerFactory.getLogger(LoggerName)

        log.info { Message }

        // in tests logback is the bound logging framework -> we want to test if log messages gets directed to our mock Appender
        verify { mockAppender.append(LogEvent(LogLevel.Info, Message, LoggerName, null, null)) }
    }

}