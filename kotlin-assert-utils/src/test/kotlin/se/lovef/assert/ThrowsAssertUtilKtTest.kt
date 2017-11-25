package se.lovef.assert

import junit.framework.TestCase
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Test

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

    @Test fun `throws`() {
        val exception = SpecialException();
        { throw exception } throws SpecialException::class referenceIsEqualTo exception
        { throw exception }.throws(SpecialException::class).specialValue isEqualTo exception.specialValue
        { throw exception } throws Throwable::class

        { { } throws Error::class } throws NotThrownError::class

        { { 1 } throws Error::class } throws NotThrownError::class

        { { throw exception } throws Error::class } throws NotThrownError::class
        { { throw Error() } throws Exception::class } throws NotThrownError::class
    }
}
