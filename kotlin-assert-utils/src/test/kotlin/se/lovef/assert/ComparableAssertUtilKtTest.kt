package se.lovef.assert

import org.junit.Test
import java.util.*

/**
 * Date: 2017-11-25
 * @author Love
 */
class ComparableAssertUtilKtTest {

    companion object {
        private val OBJECT_0_A: Date? = Date(0)
        private val OBJECT_0_B: Date? = Date(0)
        private val OBJECT_1: Date? = Date(0)
    }

    @Test fun `is less than`() {
        1 isLessThan 2
        { 1 isLessThan 1 } throws Error::class
        (OBJECT_0_A isLessOrEqualTo OBJECT_1) referenceIsEqualTo OBJECT_1
        (1 isLessThan 2) isEqualTo 2
        1 isLessThan 2 isLessThan 3
        { 1 isLessThan 1 isLessThan 3 } throws Error::class
        { 1 isLessThan 2 isLessThan 2 } throws Error::class
    }

    @Test fun `is less or equal to`() {
        1 isLessOrEqualTo 1
        1 isLessOrEqualTo 2
        { 1 isLessOrEqualTo 0 } throws Error::class
        (OBJECT_0_A isLessOrEqualTo OBJECT_0_B) referenceIsEqualTo OBJECT_0_B
        1 isLessOrEqualTo 1 isLessOrEqualTo 1
        { 1 isLessOrEqualTo 0 isLessOrEqualTo 0 } throws Error::class
        { 1 isLessOrEqualTo 1 isLessOrEqualTo 0 } throws Error::class
    }

    @Test fun `is greater than`() {
        2 isGreaterThan 1
        { 1 isGreaterThan 1 } throws Error::class
        (OBJECT_1 isLessOrEqualTo OBJECT_0_A) referenceIsEqualTo OBJECT_0_A
        (2 isGreaterThan 1) isEqualTo 1
        3 isGreaterThan 2 isGreaterThan 1
        { 3 isGreaterThan 3 isGreaterThan 1 } throws Error::class
        { 3 isGreaterThan 2 isGreaterThan 2 } throws Error::class
    }

    @Test fun `is greater or equal to`() {
        1 isGreaterOrEqualTo 1
        2 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 } throws Error::class
        (OBJECT_0_A isGreaterOrEqualTo OBJECT_0_B) referenceIsEqualTo OBJECT_0_B
        1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 1
        { 1 isGreaterOrEqualTo 2 isGreaterOrEqualTo 2 } throws Error::class
        { 1 isGreaterOrEqualTo 1 isGreaterOrEqualTo 2 } throws Error::class
    }

    @Test fun `comparing null yields assertion error`() {
        val nullValue: Int? = null
        val a: Int? = 0

        { nullValue isLessThan a }
            .throws(Error::class)
            .message doesContain "null < $a"
        { nullValue isLessThan nullValue }
            .throws(Error::class)
            .message doesContain "null < null"
        { a isLessThan nullValue }
            .throws(Error::class)
            .message doesContain "$a < null"

        { nullValue isLessOrEqualTo a }
            .throws(Error::class)
            .message doesContain "null <= $a"
        { nullValue isLessOrEqualTo nullValue }
            .throws(Error::class)
            .message doesContain "null <= null"
        { a isLessOrEqualTo nullValue }
            .throws(Error::class)
            .message doesContain "$a <= null"

        { nullValue isGreaterThan a }
            .throws(Error::class)
            .message doesContain "null > $a"
        { nullValue isGreaterThan nullValue }
            .throws(Error::class)
            .message doesContain "null > null"
        { a isGreaterThan nullValue }
            .throws(Error::class)
            .message doesContain "$a > null"

        { nullValue isGreaterOrEqualTo a }
            .throws(Error::class)
            .message doesContain "null >= $a"
        { nullValue isGreaterOrEqualTo nullValue }
            .throws(Error::class)
            .message doesContain "null >= null"
        { a isGreaterOrEqualTo nullValue }
            .throws(Error::class)
            .message doesContain "$a >= null"
    }
}
