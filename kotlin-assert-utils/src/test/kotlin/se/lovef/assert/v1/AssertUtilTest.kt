package se.lovef.assert.v1

import org.junit.Test
import java.util.*

/**
 * Date: 2017-03-31
 * @author Love
 */
class AssertUtilTest {

    @Test fun `type is`() {
        this typeIs AssertUtilTest::class shouldBe this
        this typeIs Any::class
        { this typeIs Int::class } throws Error::class
    }

    @Test fun `type is returns correct type`() {
        val a: Any = "hello"
        a.typeIs(String::class).toUpperCase() shouldEqual "HELLO" // Returns a as String
    }

    @Test fun `type is can operate on nullable types`() {
        val a: String? = "hello"
        a.typeIs(String::class).length // Returns a as String

        val nullValue: String? = null
        { nullValue typeIs String::class } throws Error::class
    }

    private val OBJECT_A = Date(0)
    private val OBJECT_B = Date(0)

    @Test fun `should be`() {
        OBJECT_A shouldBe OBJECT_A
        { OBJECT_A shouldBe OBJECT_B } throws Error::class
        OBJECT_A shouldBe OBJECT_A shouldBe OBJECT_A
        { OBJECT_A shouldBe OBJECT_B shouldBe OBJECT_B } throws Error::class
        { OBJECT_A shouldBe OBJECT_A shouldBe OBJECT_B } throws Error::class
    }

    @Test fun `should not be`() {
        OBJECT_A shouldNotBe OBJECT_B;
        { OBJECT_A shouldNotBe OBJECT_A } throws Error::class
        (OBJECT_A shouldNotBe OBJECT_B) shouldBe OBJECT_B
    }

    @Test fun `should equal`() {
        1 shouldEqual 1
        { 1 shouldEqual 2 } throws Error::class
        (OBJECT_A shouldEqual OBJECT_B) shouldBe OBJECT_B
        1 shouldEqual 1 shouldEqual 1
        { 1 shouldEqual 2 shouldEqual 2 } throws Error::class
        { 1 shouldEqual 1 shouldEqual 2 } throws Error::class
        "123".shouldEqual("123").length shouldEqual 3
        null shouldEqual null
        { null shouldEqual 1 } throws Error::class
        { 1 shouldEqual null } throws Error::class
    }

    @Test fun `should not equal`() {
        1 shouldNotEqual 2
        { 1 shouldNotEqual 1 } throws Error::class
        (Any() shouldNotEqual OBJECT_B) shouldBe OBJECT_B
        1 shouldNotEqual 2 shouldNotEqual 1
        { 1 shouldNotEqual 1 shouldNotEqual 2 } throws Error::class
        { 1 shouldNotEqual 2 shouldNotEqual 2 } throws Error::class
        null shouldNotEqual 1
        1 shouldNotEqual null
        { null shouldNotEqual null } throws Error::class
    }

    @Test fun `array should equal`() {
        val a = arrayOf(1, 2, 3)
        a shouldEqual a.clone()
        a.map { it.toByte() }.toByteArray().let { it shouldEqual it.clone() }
        a.map { it.toChar() }.toCharArray().let { it shouldEqual it.clone() }
        a.map { it.toShort() }.toShortArray().let { it shouldEqual it.clone() }
        a.map { it }.toIntArray().let { it shouldEqual it.clone() }
        a.map { it.toLong() }.toLongArray().let { it shouldEqual it.clone() }
        a.map { it.toFloat() }.toFloatArray().let { it shouldEqual it.clone() }
        a.map { it.toDouble() }.toDoubleArray().let { it shouldEqual it.clone() }
        booleanArrayOf(true, false).let { it shouldEqual it.clone() }
    }

    @Test fun `array should not equal`() {
        val a = arrayOf(1, 2, 3)
        val b = arrayOf(3, 4, 5)
        a shouldNotEqual b
        a.map { it.toByte() }.toByteArray() shouldNotEqual b.map { it.toByte() }.toByteArray()
        a.map { it.toChar() }.toCharArray() shouldNotEqual b.map { it.toChar() }.toCharArray()
        a.map { it.toShort() }.toShortArray() shouldNotEqual b.map { it.toShort() }.toShortArray()
        a.map { it }.toIntArray() shouldNotEqual b.map { it }.toIntArray()
        a.map { it.toLong() }.toLongArray() shouldNotEqual b.map { it.toLong() }.toLongArray()
        a.map { it.toFloat() }.toFloatArray() shouldNotEqual b.map { it.toFloat() }.toFloatArray()
        a.map { it.toDouble() }.toDoubleArray() shouldNotEqual b.map { it.toDouble() }.toDoubleArray()
        booleanArrayOf(true, false) shouldNotEqual booleanArrayOf(false, true)
    }

    @Test fun `should be null`() {
        null.shouldBeNull()
        null.shouldBeNull() shouldBe null
        { OBJECT_A.shouldBeNull() } throws Error::class
    }

    @Test fun `should not be null`() {
        OBJECT_A.shouldNotBeNull()
        OBJECT_A.shouldNotBeNull() shouldBe OBJECT_A
        { null.shouldNotBeNull() } throws Error::class
        "123".shouldNotBeNull().length shouldEqual 3
    }

    @Test fun `should be true`() {
        true.shouldBeTrue()
        true.shouldBeTrue() shouldBe true
        { false.shouldBeTrue() } throws Error::class
        { null.shouldBeTrue() } throws Error::class
    }

    @Test fun `should be false`() {
        false.shouldBeFalse()
        false.shouldBeFalse() shouldBe false
        { true.shouldBeFalse() } throws Error::class
        { null.shouldBeFalse() } throws Error::class
    }

    @Test fun `should be close to`() {
        1 shouldBeCloseTo 1 tolerance 0
        { 1 shouldBeCloseTo 2 tolerance 0 } throws Error::class
        1 shouldBeCloseTo 2 tolerance 1
        { 1 shouldBeCloseTo 2 tolerance 0 } throws Error::class
        1 shouldBeCloseTo 0 tolerance 1
        { 1 shouldBeCloseTo 0 tolerance 0 } throws Error::class

        1 shouldBeCloseTo 1.1 tolerance 0.2
        { 1 shouldBeCloseTo 1.1 tolerance 0.05 } throws Error::class
        1 shouldBeCloseTo 0.9 tolerance 0.2
        { 1 shouldBeCloseTo 0.9 tolerance 0.05 } throws Error::class
    }

    @Test fun `close to works on nullable types`() {
        (1 as Int?) shouldBeCloseTo (1 as Int?) tolerance (0 as Int?)

        { (null as Int?) shouldBeCloseTo 1 tolerance 0 } throws Error::class
        { 1 shouldBeCloseTo (null as Int?) tolerance 0 } throws Error::class
        { 1 shouldBeCloseTo 1 tolerance (null as Int?) } throws Error::class
    }

    @Test fun `proof`() {
        "We can describe our tests" proof {
            1 shouldEqual 1
        }
        {
            "Test description" proof {
                1 shouldEqual 2
            }
        }.throws(Error::class).message shouldEqual "Test description"
        {
            "Test description" proof {
                1 shouldEqual 1
                1 shouldEqual 2
            }
        }.throws(Error::class).message shouldEqual "Test description"
    }

    @Test fun `proof in a expression body`() = "A proof can be done in a expression body" proof {
        1 shouldEqual 1
    }
}
