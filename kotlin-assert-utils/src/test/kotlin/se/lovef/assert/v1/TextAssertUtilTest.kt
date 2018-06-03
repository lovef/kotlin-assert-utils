package se.lovef.assert.v1

import org.junit.Test

/**
 * Date: 2017-11-25
 * @author Love
 */
class TextAssertUtilTest {

    @Test fun `should contain`() {
        "my awesome string" as CharSequence shouldContain "awesome" as CharSequence shouldBe "my awesome string"
        { "my awesome string" shouldContain "shit" } throws Error::class
        { "my awesome string" shouldContain "we.+me" } throws Error::class

        "my 1337 null string" shouldContain 1337 shouldContain null
        { "my 1337 null string" shouldContain 666 } throws Error::class
        { "my 1337 string" shouldContain null } throws Error::class

        "my 1337 string" shouldContain "my" shouldContain 1337 shouldContain "string"

        { null shouldContain "shit" } throws Error::class
    }

    @Test fun `should not contain`() {
        "my awesome string" as CharSequence shouldNotContain "shit" as CharSequence shouldBe "my awesome string"
        "other string" shouldNotContain 1337 shouldNotContain null
        { "my awesome string" shouldNotContain "awesome" } throws Error::class
        { "my 1337 string" shouldNotContain 1337 } throws Error::class

        null shouldNotContain "awesome"
    }

    @Test fun `should match`() {
        "my awesome string" as CharSequence shouldMatch "we.+me" as CharSequence shouldBe "my awesome string"
        "my awesome string" as CharSequence shouldMatch "we.+me".toRegex() shouldBe "my awesome string"
        { "my awesome string" shouldMatch "shit" } throws Error::class

        "my awesome string" shouldMatch "^my" shouldMatch "we.+me" shouldMatch "string$"
        "my awesome string" shouldMatch "^my".toRegex() shouldMatch "we.+me".toRegex() shouldMatch "string$".toRegex();

        { null shouldMatch "shit" } throws Error::class
    }

    @Test fun `should not match`() {
        "my awesome string" as CharSequence shouldNotMatch "shit" as CharSequence shouldBe "my awesome string"
        "my awesome string" as CharSequence shouldNotMatch "shit".toRegex() shouldBe "my awesome string"
        { "my awesome string" shouldNotMatch "we.+me" } throws Error::class
        { "my awesome string" shouldNotMatch "we.+me".toRegex() } throws Error::class

        null shouldNotMatch "shit"
        null shouldNotMatch "shit".toRegex()
    }

    @Test fun `should start with`() {
        ("my awesome string" as CharSequence).shouldStartWith("my" as CharSequence) shouldBe "my awesome string"
        { "my awesome string" shouldStartWith "awesome" } throws Error::class

        "1337 string" shouldStartWith 1337
        { "string 1337" shouldStartWith 1337 } throws Error::class

        "null string" shouldStartWith null
        { "string null" shouldStartWith null } throws Error::class

        { null shouldStartWith "shit" } throws Error::class
    }

    @Test fun `should not start with`() {
        "my awesome string" as CharSequence shouldNotStartWith "awesome" as CharSequence shouldBe "my awesome string"
        { "my awesome string" shouldNotStartWith "my" } throws Error::class

        "string 1337" shouldNotStartWith 1337
        { "1337 string" shouldNotStartWith 1337 } throws Error::class

        "string null" shouldNotStartWith null
        { "null string" shouldNotStartWith null } throws Error::class

        null shouldNotStartWith "shit"
    }

    @Test fun `should end with`() {
        "my awesome string" as CharSequence shouldEndWith "string" as CharSequence shouldBe "my awesome string"
        { "my awesome string" shouldEndWith "awesome" } throws Error::class

        "string 1337" shouldEndWith 1337
        { "1337 string" shouldEndWith 1337 } throws Error::class

        "string null" shouldEndWith null
        { "null string" shouldEndWith null } throws Error::class

        { null shouldEndWith "shit" } throws Error::class
    }

    @Test fun `should not end with`() {
        "my awesome string" as CharSequence shouldNotEndWith "awesome" as CharSequence shouldBe "my awesome string"
        { "my awesome string" shouldNotEndWith "string" } throws Error::class

        "1337 string" shouldNotEndWith 1337
        { "string 1337" shouldNotEndWith 1337 } throws Error::class

        "null string" shouldNotEndWith null
        { "string null" shouldNotEndWith null } throws Error::class

        null shouldNotEndWith "shit"
    }
}
