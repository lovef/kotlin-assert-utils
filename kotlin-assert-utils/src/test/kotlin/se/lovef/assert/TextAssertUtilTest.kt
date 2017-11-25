package se.lovef.assert

import org.junit.Test

/**
 * Date: 2017-11-25
 * @author Love
 */
class TextAssertUtilTest {

    @Test fun `does contain`() {
        "my awesome string" as CharSequence doesContain "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesContain "shit" } throws Error::class
        { "my awesome string" doesContain "we.+me" } throws Error::class

        "my awesome string" doesContain "my" doesContain "awesome" doesContain "string"

        { null doesContain "shit" } throws Error::class
    }

    @Test fun `does not contain`() {
        "my awesome string" as CharSequence doesNotContain "shit" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotContain "awesome" } throws Error::class

        null doesNotContain "awesome"
    }

    @Test fun `does match`() {
        "my awesome string" as CharSequence doesMatch "we.+me" as CharSequence referenceIsEqualTo "my awesome string"
        "my awesome string" as CharSequence doesMatch "we.+me".toRegex() referenceIsEqualTo "my awesome string"
        { "my awesome string" doesMatch "shit" } throws Error::class

        "my awesome string" doesMatch "^my" doesMatch "we.+me" doesMatch "string$"
        "my awesome string" doesMatch "^my".toRegex() doesMatch "we.+me".toRegex() doesMatch "string$".toRegex();

        { null doesMatch "shit" } throws Error::class
    }

    @Test fun `does not match`() {
        "my awesome string" as CharSequence doesNotMatch "shit" as CharSequence referenceIsEqualTo "my awesome string"
        "my awesome string" as CharSequence doesNotMatch "shit".toRegex() referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotMatch "we.+me" } throws Error::class
        { "my awesome string" doesNotMatch "we.+me".toRegex() } throws Error::class

        null doesNotMatch "shit"
        null doesNotMatch "shit".toRegex()
    }

    @Test fun `does start with`() {
        "my awesome string" as CharSequence doesStartWith "my" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesStartWith "awesome" } throws Error::class

        { null doesStartWith "shit" } throws Error::class
    }

    @Test fun `does not start with`() {
        "my awesome string" as CharSequence doesNotStartWith "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotStartWith "my" } throws Error::class

        null doesNotStartWith "shit"
    }

    @Test fun `does end with`() {
        "my awesome string" as CharSequence doesEndWith "string" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesEndWith "awesome" } throws Error::class

        { null doesEndWith "shit" } throws Error::class
    }

    @Test fun `does not end with`() {
        "my awesome string" as CharSequence doesNotEndWith "awesome" as CharSequence referenceIsEqualTo "my awesome string"
        { "my awesome string" doesNotEndWith "string" } throws Error::class

        null doesNotEndWith "shit"
    }
}