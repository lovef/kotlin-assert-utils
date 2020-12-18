package se.lovef.assert

import junit.framework.TestCase
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import se.lovef.assert.v1.shouldBe
import se.lovef.assert.v1.shouldEqual

/**
 * Date: 2017-11-25
 * @author Love
 */
class ThrowsAssertUtilKtTest {

    @Test fun `throws throw exception if nothing is thrown`() {
        try {
            { } throws Throwable::class
            TestCase.fail("No exception was thrown")
        } catch (thrown: NotThrownError) {
            MatcherAssert.assertThat(thrown, IsInstanceOf(NotThrownError::class.java))
        }
    }

    class SpecialException(val specialValue: String = "special value") : Exception()
    class OtherException : Exception()

    @Test fun `throws type`() {
        val exception = SpecialException();
        { throw exception } throws SpecialException::class shouldBe exception
        { throw exception }.throws(SpecialException::class).specialValue shouldEqual exception.specialValue
        { throw exception } throws Throwable::class

        { { } throws Error::class } throws NotThrownError::class

        { { 1 } throws Error::class } throws NotThrownError::class
    }

    @Test fun `throws throw throwable if type is wrong`() {
        val thrown = SpecialException();
        { { throw thrown } throws OtherException::class }
            .throws(SpecialException::class)
            .shouldEqual(thrown)
    }

    private fun throwException(exception: Exception) {
        throw exception
    }

    @Test fun `throws instance`() {
        val exception = Exception()
        ;{ throwException(exception) }
            .throws(exception)
            .shouldBe(exception)
    }

    @Test fun `throws wrong instance`() {
        val thrownException = Exception()
        val expectedException = Exception()
        ; { { throwException(thrownException) } throws expectedException }
            .throws(Exception::class)
            .shouldBe(thrownException)
    }

    @Test fun `throws instance throws exception if nothing is thrown`() {
        {
            { } throws Exception()
        } throws NotThrownError::class
    }
}
