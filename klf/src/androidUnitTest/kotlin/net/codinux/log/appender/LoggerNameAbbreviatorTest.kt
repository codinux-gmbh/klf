package net.codinux.log.appender

import assertk.assertThat
import assertk.assertions.isLessThanOrEqualTo
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggerNameAbbreviatorTest {

    class ClassWithoutPackageName

    class ClassNameLongerThanMaxTagNameLength


    private val underTest = LoggerNameAbbreviator()


    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength(ClassWithoutPackageName::class.qualifiedName!!, LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertEquals("*lassWithoutPackageName", result) // TODO: bug in ClassNameAbbreviator, should be "ClassWithoutPackageName"
    }

    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - ClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength(ClassNameLongerThanMaxTagNameLength::class.qualifiedName!!, LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertEquals("*erThanMaxTagNameLength", result)
        assertEquals(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26, result.length)
    }


    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = underTest.getLoggerTagOfMaxLength("net.codinux.log.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertEquals("ne.co.lo.ShortClassName", result)
        assertEquals(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26, result.length)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameTooLong`() {
        val result = underTest.getLoggerTagOfMaxLength("net.codinux.log.subpackage.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertEquals("n.c.l.s.ShortClassName", result)
        assertThat(result.length).isLessThanOrEqualTo(LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameWithOneLetter`() {
        val result = underTest.getLoggerTagOfMaxLength("n.codinux.l.ShortClassName", LogcatAppender.MaxAndroidLogTagSizeBeforeApi26)

        assertEquals("n.co.l.ShortClassName", result)
    }

}