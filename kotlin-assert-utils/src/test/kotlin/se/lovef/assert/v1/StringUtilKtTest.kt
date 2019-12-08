package se.lovef.assert.v1

import org.junit.Test

class StringUtilKtTest {

    @Test fun `multiline string`() {
        listOf(
            listOf("") to "",
            listOf("", "") to "\n",
            listOf("a", "b") to "a\nb"
        ) shouldAll { (input, output) ->
            String.multiline(*input.toTypedArray()) shouldEqual output
        }
    }

    @Test fun `string literal`() {
        listOf(
            "abc" to "\"abc\"",
            "\"abc\"" to "\"\\\"abc\\\"\"",
            " \n " to "\" \\n \"",
            " \r " to "\" \\r \"",
            " \t " to "\" \\t \"",
            " \$ " to "\" \\\$ \"",
            " \u00a0 " to "\" \\u00a0 \"",
            "\"\n\t\$\"\n\t\$" to "\"\\\"\\n\\t\\\$\\\"\\n\\t\\\$\""
        ) shouldAll { (input, output) ->
            input.toStringLiteral() shouldEqual output
        }
    }
}
