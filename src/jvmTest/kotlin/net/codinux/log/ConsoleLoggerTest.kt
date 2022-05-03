package net.codinux.log

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class ConsoleLoggerTest {

  private val consoleOutputStream = ByteArrayOutputStream()

  private val underTest = ConsoleLogger(ConsoleLoggerTest::class.java.name)


  @BeforeEach
  fun setUp() {
    System.setOut(PrintStream(consoleOutputStream.buffered(), true, Charsets.UTF_8.name()))
  }


  @Test
  fun fatalEnabled() {
    val message = "Fatal Message"

    underTest.fatal { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Fatal] net.codinux.log.ConsoleLoggerTest - " + message + System.lineSeparator())
  }

  @Test
  fun errorEnabled() {
    val message = "Error Message"

    underTest.error { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Error] net.codinux.log.ConsoleLoggerTest - " + message + System.lineSeparator())
  }

  @Test
  fun warnEnabled() {
    val message = "Warn Message"

    underTest.warn { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Warn] net.codinux.log.ConsoleLoggerTest - " + message + System.lineSeparator())
  }

  @Test
  fun infoEnabled() {
    val message = "Info Message"

    underTest.info { message }

    val logOutput = getWrittenLogOutput()
    assertThat(logOutput).isEqualTo("[Info] net.codinux.log.ConsoleLoggerTest - " + message + System.lineSeparator())
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