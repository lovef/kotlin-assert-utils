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

    @Test fun `should be less than`() {
        1 shouldBeLessThan 2
        { 1 shouldBeLessThan 1 } throws Error::class
        (OBJECT_0_A shouldBeLessOrEqualTo OBJECT_1) shouldBe OBJECT_1
        (1 shouldBeLessThan 2) shouldEqual 2
        1 shouldBeLessThan 2 shouldBeLessThan 3
        { 1 shouldBeLessThan 1 shouldBeLessThan 3 } throws Error::class
        { 1 shouldBeLessThan 2 shouldBeLessThan 2 } throws Error::class
    }

    @Test fun `should be less or equal to`() {
        1 shouldBeLessOrEqualTo 1
        1 shouldBeLessOrEqualTo 2
        { 1 shouldBeLessOrEqualTo 0 } throws Error::class
        (OBJECT_0_A shouldBeLessOrEqualTo OBJECT_0_B) shouldBe OBJECT_0_B
        1 shouldBeLessOrEqualTo 1 shouldBeLessOrEqualTo 1
        { 1 shouldBeLessOrEqualTo 0 shouldBeLessOrEqualTo 0 } throws Error::class
        { 1 shouldBeLessOrEqualTo 1 shouldBeLessOrEqualTo 0 } throws Error::class
    }

    @Test fun `should be greater than`() {
        2 shouldBeGreaterThan 1
        { 1 shouldBeGreaterThan 1 } throws Error::class
        (OBJECT_1 shouldBeLessOrEqualTo OBJECT_0_A) shouldBe OBJECT_0_A
        (2 shouldBeGreaterThan 1) shouldEqual 1
        3 shouldBeGreaterThan 2 shouldBeGreaterThan 1
        { 3 shouldBeGreaterThan 3 shouldBeGreaterThan 1 } throws Error::class
        { 3 shouldBeGreaterThan 2 shouldBeGreaterThan 2 } throws Error::class
    }

    @Test fun `should be greater or equal to`() {
        1 shouldBeGreaterOrEqualTo 1
        2 shouldBeGreaterOrEqualTo 1
        { 1 shouldBeGreaterOrEqualTo 2 } throws Error::class
        (OBJECT_0_A shouldBeGreaterOrEqualTo OBJECT_0_B) shouldBe OBJECT_0_B
        1 shouldBeGreaterOrEqualTo 1 shouldBeGreaterOrEqualTo 1
        { 1 shouldBeGreaterOrEqualTo 2 shouldBeGreaterOrEqualTo 2 } throws Error::class
        { 1 shouldBeGreaterOrEqualTo 1 shouldBeGreaterOrEqualTo 2 } throws Error::class
    }

    @Test fun `comparing null yields assertion error`() {
        val nullValue: Int? = null
        val a: Int? = 0

        { nullValue shouldBeLessThan a }
            .throws(Error::class)
            .message shouldContain "null < $a"
        { nullValue shouldBeLessThan nullValue }
            .throws(Error::class)
            .message shouldContain "null < null"
        { a shouldBeLessThan nullValue }
            .throws(Error::class)
            .message shouldContain "$a < null"

        { nullValue shouldBeLessOrEqualTo a }
            .throws(Error::class)
            .message shouldContain "null <= $a"
        { nullValue shouldBeLessOrEqualTo nullValue }
            .throws(Error::class)
            .message shouldContain "null <= null"
        { a shouldBeLessOrEqualTo nullValue }
            .throws(Error::class)
            .message shouldContain "$a <= null"

        { nullValue shouldBeGreaterThan a }
            .throws(Error::class)
            .message shouldContain "null > $a"
        { nullValue shouldBeGreaterThan nullValue }
            .throws(Error::class)
            .message shouldContain "null > null"
        { a shouldBeGreaterThan nullValue }
            .throws(Error::class)
            .message shouldContain "$a > null"

        { nullValue shouldBeGreaterOrEqualTo a }
            .throws(Error::class)
            .message shouldContain "null >= $a"
        { nullValue shouldBeGreaterOrEqualTo nullValue }
            .throws(Error::class)
            .message shouldContain "null >= null"
        { a shouldBeGreaterOrEqualTo nullValue }
            .throws(Error::class)
            .message shouldContain "$a >= null"
    }
}
