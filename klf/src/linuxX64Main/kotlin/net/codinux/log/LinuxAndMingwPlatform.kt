package net.codinux.log

actual object LinuxAndMingwPlatform {

    private const val LinuxLineSeparator = "\n"

    actual fun lineSeparator() =
        LinuxLineSeparator

}