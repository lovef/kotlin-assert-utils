package se.lovef.assert.v1

fun String.Companion.multiline(vararg strings: String) = strings.joinToString("\n")

fun String.toStringLiteral() = this
    .replace("\\", "\\\\")
    .replace("\"", "\\\"")
    .replace("\n", "\\n")
    .replace("\t", "\\t")
    .replace("\$", "\\\$")
    .let { "\"$it\"" }
