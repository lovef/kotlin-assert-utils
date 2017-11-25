package se.lovef.assert

import org.junit.Test
import java.util.*

/**
 * Date: 2017-11-25
 * @author Love
 */
class ComparableAssertUtilKtTest {

    private val OBJECT_A = Date(0)
    private val OBJECT_B = Date(0)

    @Test fun `is less than`() {
        1 isLessThan 2
        { 1 isLessThan 1 } throws Error::class
        (1 isLessThan 2) isEqualTo 2
        1 isLessThan 2 isLessThan 3
        { 1 isLessThan 1 isLessThan 3 } throws Error::class
        { 1 isLessThan 2 isLessThan 2 } throws Error::class
    }

    @Test fun `is less or equal to`() {
        1 isLessOrEqualTo 1
        1 isLessOrEqualTo 2
        { 1 isLessOrEqualTo 0 } throws Error::class
        (OBJECT_A isLessOrEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isLessOrEqualTo 1 isLessOrEqualTo 1
        { 1 isLessOrEqualTo 0 isLessOrEqualTo 0 } throws Error::class
        { 1 isLessOrEqualTo 1 isLessOrEqualTo 0 } throws Error::class
    }

    @Test fun `is greater than`() {
        2 isGreaterThan 1
        { 1 isGreaterThan 1 } throws Error::class
        (2 isGreaterThan 1) isEqualTo 1
        3 isGreaterThan 2 isGreaterThan 1
        { 3 isGreaterThan 3 isGreaterThan 1 } throws Error::class
        { 3 isGreaterThan 2 isGreaterThan 2 } throws Error::class
    }

    @Test fun `is greater or equal to`() {
        1 isGreaterOrEqualTo 1
        2 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 } throws Error::class
        (OBJECT_A isGreaterOrEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 isGreaterOrEqualTo 2 } throws Error::class
        { 1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 2 } throws Error::class
    }
}
