package net.codinux.log

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PlatformTest {

    class ClassWithoutPackageName

    class ClassNameLongerThanMaxTagNameLength


    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = Platform.getAndroidLogTagOfLogTagMaxLength(ClassWithoutPackageName::class)

        assertEquals("ClassWithoutPackageName", result)
    }

    @Test
    fun `getAndroidLogTagOfLogTagMaxLength - ClassNameLongerThanMaxTagNameLength`() {
        val result = Platform.getAndroidLogTagOfLogTagMaxLength(ClassNameLongerThanMaxTagNameLength::class)

        assertEquals("*erThanMaxTagNameLength", result)
        assertEquals(Platform.MaxAndroidLogTagSizeBeforeApi26, result.length)
    }


    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageAndClassNameLongerThanMaxTagNameLength`() {
        val result = Platform.getClassNameWithShortenedPackageNameOfLogTagMaxLength("net.codinux.log", "ShortClassName")

        assertEquals("n*.c*.l*.ShortClassName", result)
        assertEquals(Platform.MaxAndroidLogTagSizeBeforeApi26, result?.length)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameTooLong`() {
        val result = Platform.getClassNameWithShortenedPackageNameOfLogTagMaxLength("net.codinux.log.subpackage", "ShortClassName")

        assertNull(result)
    }

    @Test
    fun `getClassNameWithShortenedPackageNameOfLogTagMaxLength - PackageNameWithOneLetter`() {
        val result = Platform.getClassNameWithShortenedPackageNameOfLogTagMaxLength("n.codinux.l", "ShortClassName")

        assertEquals("n.c*.l.ShortClassName", result)
    }

}