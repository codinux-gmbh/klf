package net.codinux.log.appender

import net.codinux.log.DefaultLoggerFactory
import net.codinux.log.LoggerFactory
import net.codinux.log.logger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class ConsoleAppenderTest {

  private val consoleOutputStream = ByteArrayOutputStream()

  private val underTest by logger()

  init {
    // otherwise on slf4j is used instead of ConsoleAppender
    LoggerFactory.setLoggerFactory(DefaultLoggerFactory())
  }


  @BeforeEach
  fun setUp() {
    System.setOut(PrintStream(consoleOutputStream.buffered(), true, Charsets.UTF_8.name()))
  }


  @Test
  fun errorEnabled() {
    val message = "Error Message"

    underTest.error { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Test worker] Error net.codinux.log.appender.ConsoleAppenderTest - " + message + System.lineSeparator())
  }

  @Test
  fun warnEnabled() {
    val message = "Warn Message"

    underTest.warn { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Test worker] Warn net.codinux.log.appender.ConsoleAppenderTest - " + message + System.lineSeparator())
  }

  @Test
  fun infoEnabled() {
    val message = "Info Message"

    underTest.info { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Test worker] Info net.codinux.log.appender.ConsoleAppenderTest - " + message + System.lineSeparator())
  }

  @Test
  fun debugDisabled() {
    val message = "Debug Message"

    underTest.debug { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEmpty()
  }

  @Test
  fun traceDisabled() {
    val message = "Trace Message"

    underTest.trace { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEmpty()
  }


  private fun getWrittenLogOutput(): String {
    return consoleOutputStream.toByteArray().toString(Charsets.UTF_8)
  }

}