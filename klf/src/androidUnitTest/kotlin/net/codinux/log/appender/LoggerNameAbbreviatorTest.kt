package net.codinux.log.appender

import assertk.assertThat
import assertk.assertions.hasLength
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThanOrEqualTo
import kotlin.test.Test

class LoggerNameAbbreviatorTest {

    class ClassWithoutPackageName

    class ClassNameLongerThanMaxTagNameLength


    private val underTest = LoggerNameAbbreviator()


    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength(ClassWithoutPackageName::class.qualifiedName!!, LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertThat(result).isEqualTo("ClassWithoutPackageName")
    }

    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - ClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength(ClassNameLongerThanMaxTagNameLength::class.qualifiedName!!, LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertThat(result).isEqualTo("*erThanMaxTagNameLength")
        assertThat(result).hasLength(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)
    }


    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength("net.codinux.log.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertThat(result).isEqualTo("ne.co.lo.ShortClassName")
        assertThat(result).hasLength(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameTooLong`() {
        val result = underTest.getLoggerTagOfMaxLength("net.codinux.log.subpackage.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertThat(result).isEqualTo("n.c.l.s.ShortClassName")
        assertThat(result.length).isLessThanOrEqualTo(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameWithOneLetter`() {
        val result = underTest.getLoggerTagOfMaxLength("n.codinux.l.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertThat(result).isEqualTo("n.co.l.ShortClassName")
    }

}