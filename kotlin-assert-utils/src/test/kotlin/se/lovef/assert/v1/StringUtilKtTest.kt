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
            "abc\ndef" to "\"abc\\ndef\"",
            "abc\tdef" to "\"abc\\tdef\"",
            "abc\$def" to "\"abc\\\$def\"",
            "\"\n\t\$\"\n\t\$" to "\"\\\"\\n\\t\\\$\\\"\\n\\t\\\$\""
        ) shouldAll { (input, output) ->
            input.toStringLiteral() shouldEqual output
        }
    }
}
