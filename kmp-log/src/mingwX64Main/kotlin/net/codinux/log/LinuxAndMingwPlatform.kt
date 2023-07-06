package net.codinux.log

actual object LinuxAndMingwPlatform {

    private const val WindowsLineSeparator = "\r\n"

    actual fun lineSeparator() =
        WindowsLineSeparator

}