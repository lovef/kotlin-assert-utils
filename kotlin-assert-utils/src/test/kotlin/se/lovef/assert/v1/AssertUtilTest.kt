package se.lovef.assert.v1

import org.junit.Test
import se.lovef.assert.throws
import java.util.*

/**
 * Date: 2017-03-31
 * @author Love
 */
class AssertUtilTest {

    @Test fun `simple fail`() {
        errorMessageOf { fail("message") } shouldEqual "message"
    }

    @Test fun `fail with objects`() {
        errorMessageOf { fail("message: {0} {1} {0}", 0, 1) } shouldEqual "message: 0 1 0"
        errorMessageOf { fail("message: {0}", "Hello") } shouldEqual "message: Hello"
    }

    @Test fun `fail with list in message`() {
        listOf('a', "abc", 1, null) shouldBeFormattedAs "[a, abc, 1, null]"
    }

    @Test fun `fail with array in message`() {
        arrayOf('a', "abc", 1, null) shouldBeFormattedAs "[a, abc, 1, null]"
        byteArrayOf(1, 2, 3) shouldBeFormattedAs "[1, 2, 3]"
        charArrayOf('a', 'b', 'c') shouldBeFormattedAs "[a, b, c]"
        shortArrayOf(2, 3, 4) shouldBeFormattedAs "[2, 3, 4]"
        intArrayOf(3, 4, 5) shouldBeFormattedAs "[3, 4, 5]"
        longArrayOf(4, 5, 6) shouldBeFormattedAs "[4, 5, 6]"
        floatArrayOf(5f, 6f, 7f) shouldBeFormattedAs "[5.0, 6.0, 7.0]"
        doubleArrayOf(6.0, 7.0, 8.0) shouldBeFormattedAs "[6.0, 7.0, 8.0]"
        booleanArrayOf(false, true) shouldBeFormattedAs "[false, true]"
    }

    object CustomObject {
        override fun toString() = "Custom toString()"
    }

    @Test fun `fail with custom object in message`() {
        CustomObject shouldBeFormattedAs CustomObject.toString()
    }

    @Test fun `fail with null in message`() {
        null shouldBeFormattedAs "null"
    }


    private infix fun Any?.shouldBeFormattedAs(formatted: String) {
        errorMessageOf { fail("message: {0}", this) } shouldEqual "message: $formatted"
    }

    enum class Foo {
        BAR;
        enum class Foo {
            BAR;
        }
    }

    @Test fun `fail error message includes type when necessary`() {
        errorMessageOf { fail("{0}, {1}", Foo.BAR, "BAR") } shouldEqual "<Foo> BAR, <String> BAR"
        errorMessageOf { fail("{0}, {1}", Foo.BAR, Foo.Foo.BAR) } shouldEqual "" +
                "<se.lovef.assert.v1.AssertUtilTest\$Foo> BAR, " +
                "<se.lovef.assert.v1.AssertUtilTest\$Foo\$Foo> BAR"
        errorMessageOf { fail("{0}, {1}", null, "null") } shouldEqual "null, <String> null"
        errorMessageOf { fail("{0}, {1}, {2}", null, Foo.BAR, Foo.Foo.BAR) } shouldEqual "" +
                "null, " +
                "<se.lovef.assert.v1.AssertUtilTest\$Foo> BAR, " +
                "<se.lovef.assert.v1.AssertUtilTest\$Foo\$Foo> BAR"
    }


    private fun errorMessageOf(function: () -> Unit) = function.throws(Error::class).message


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
        a.mapToByteArray().let { it shouldEqual it.clone() }
        a.mapToCharArray().let { it shouldEqual it.clone() }
        a.mapToShortArray().let { it shouldEqual it.clone() }
        a.mapToIntArray().let { it shouldEqual it.clone() }
        a.mapToLongArray().let { it shouldEqual it.clone() }
        a.mapToFloatArray().let { it shouldEqual it.clone() }
        a.mapToDoubleArray().let { it shouldEqual it.clone() }
        booleanArrayOf(true, false).let { it shouldEqual it.clone() }
    }

    @Test fun `array should NOT equal`() {
        val a = arrayOf(1, 2, 3)
        val b = arrayOf(3, 4, 5)
        a shouldNotEqual b
        a.mapToByteArray() shouldNotEqual b.mapToByteArray()
        a.mapToCharArray() shouldNotEqual b.mapToCharArray()
        a.mapToShortArray() shouldNotEqual b.mapToShortArray()
        a.mapToIntArray() shouldNotEqual b.mapToIntArray()
        a.mapToLongArray() shouldNotEqual b.mapToLongArray()
        a.mapToFloatArray() shouldNotEqual b.mapToFloatArray()
        a.mapToDoubleArray() shouldNotEqual b.mapToDoubleArray()
        booleanArrayOf(true, false) shouldNotEqual booleanArrayOf(false, true)
    }

    private fun Array<Int>.mapToByteArray() = map { it.toByte() }.toByteArray()
    private fun Array<Int>.mapToCharArray() = map { it.toChar() }.toCharArray()
    private fun Array<Int>.mapToShortArray() = map { it.toShort() }.toShortArray()
    private fun Array<Int>.mapToIntArray() = toIntArray()
    private fun Array<Int>.mapToLongArray() = map { it.toLong() }.toLongArray()
    private fun Array<Int>.mapToFloatArray() = map { it.toFloat() }.toFloatArray()
    private fun Array<Int>.mapToDoubleArray() = map { it.toDouble() }.toDoubleArray()

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
