package se.lovef.assert.v1

fun String.Companion.multiline(vararg strings: String) = strings.joinToString("\n")

fun String.toStringLiteral() = this
    .replace("\\", "\\\\")
    .replace("\"", "\\\"")
    .replace("\n", "\\n")
    .replace("\r", "\\r")
    .replace("\t", "\\t")
    .replace("\$", "\\\$")
    .replace("\u00a0", "\\u00a0")
    .let { "\"$it\"" }
