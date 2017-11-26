package se.lovef.assert

import org.junit.Test
import java.util.*

/**
 * Date: 2017-03-31
 * @author Love
 */
class AssertUtilTest {

    @Test fun `type is`() {
        this typeIs AssertUtilTest::class referenceIsEqualTo this
        this typeIs Any::class
        { this typeIs Int::class } throws Error::class
    }

    @Test fun `type is returns correct type`() {
        val a: Any = "hello"
        a.typeIs(String::class).toUpperCase() isEqualTo "HELLO" // Returns a as String
    }

    @Test fun `type is can operate on nullable types`() {
        val a: String? = "hello"
        a.typeIs(String::class).length // Returns a as String

        val nullValue: String? = null
        { nullValue typeIs String::class } throws Error::class
    }

    private val OBJECT_A = Date(0)
    private val OBJECT_B = Date(0)

    @Test fun `reference is equal to`() {
        OBJECT_A referenceIsEqualTo OBJECT_A
        { OBJECT_A referenceIsEqualTo OBJECT_B } throws Error::class
        OBJECT_A referenceIsEqualTo OBJECT_A referenceIsEqualTo OBJECT_A
        { OBJECT_A referenceIsEqualTo OBJECT_B referenceIsEqualTo OBJECT_B } throws Error::class
        { OBJECT_A referenceIsEqualTo OBJECT_A referenceIsEqualTo OBJECT_B } throws Error::class
    }

    @Test fun `reference is not equal to`() {
        OBJECT_A referenceIsNotEqualTo OBJECT_B;
        { OBJECT_A referenceIsNotEqualTo OBJECT_A } throws Error::class
        (OBJECT_A referenceIsNotEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
    }

    @Test fun `is equal to`() {
        1 isEqualTo 1
        { 1 isEqualTo 2 } throws Error::class
        (OBJECT_A isEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isEqualTo 1 isEqualTo 1
        { 1 isEqualTo 2 isEqualTo 2 } throws Error::class
        { 1 isEqualTo 1 isEqualTo 2 } throws Error::class
        "123".isEqualTo("123").length isEqualTo 3
        null isEqualTo null
        { null isEqualTo 1 } throws Error::class
        { 1 isEqualTo null } throws Error::class
    }

    @Test fun `is not equal to`() {
        1 isNotEqualTo 2
        { 1 isNotEqualTo 1 } throws Error::class
        (Any() isNotEqualTo OBJECT_B) referenceIsEqualTo OBJECT_B
        1 isNotEqualTo 2 isNotEqualTo 1
        { 1 isNotEqualTo 1 isNotEqualTo 2 } throws Error::class
        { 1 isNotEqualTo 2 isNotEqualTo 2 } throws Error::class
        null isNotEqualTo 1
        1 isNotEqualTo null
        { null isNotEqualTo null } throws Error::class
    }

    @Test fun `array is equal to`() {
        val a = arrayOf(1, 2, 3)
        a isEqualTo a.clone()
        a.map { it.toByte() }.toByteArray().let { it isEqualTo it.clone() }
        a.map { it.toChar() }.toCharArray().let { it isEqualTo it.clone() }
        a.map { it.toShort() }.toShortArray().let { it isEqualTo it.clone() }
        a.map { it }.toIntArray().let { it isEqualTo it.clone() }
        a.map { it.toLong() }.toLongArray().let { it isEqualTo it.clone() }
        a.map { it.toFloat() }.toFloatArray().let { it isEqualTo it.clone() }
        a.map { it.toDouble() }.toDoubleArray().let { it isEqualTo it.clone() }
        booleanArrayOf(true, false).let { it isEqualTo it.clone() }
    }

    @Test fun `array is not equal to`() {
        val a = arrayOf(1, 2, 3)
        val b = arrayOf(3, 4, 5)
        a isNotEqualTo b
        a.map { it.toByte() }.toByteArray() isNotEqualTo b.map { it.toByte() }.toByteArray()
        a.map { it.toChar() }.toCharArray() isNotEqualTo b.map { it.toChar() }.toCharArray()
        a.map { it.toShort() }.toShortArray() isNotEqualTo b.map { it.toShort() }.toShortArray()
        a.map { it }.toIntArray() isNotEqualTo b.map { it }.toIntArray()
        a.map { it.toLong() }.toLongArray() isNotEqualTo b.map { it.toLong() }.toLongArray()
        a.map { it.toFloat() }.toFloatArray() isNotEqualTo b.map { it.toFloat() }.toFloatArray()
        a.map { it.toDouble() }.toDoubleArray() isNotEqualTo b.map { it.toDouble() }.toDoubleArray()
        booleanArrayOf(true, false) isNotEqualTo booleanArrayOf(false, true)
    }

    @Test fun `is null`() {
        null.isNull()
        null.isNull() referenceIsEqualTo null
        { OBJECT_A.isNull() } throws Error::class
    }

    @Test fun `is not null`() {
        OBJECT_A.isNotNull()
        OBJECT_A.isNotNull() referenceIsEqualTo OBJECT_A
        { null.isNotNull() } throws Error::class
        "123".isNotNull().length isEqualTo 3
    }

    @Test fun `is true`() {
        true.isTrue()
        true.isTrue() referenceIsEqualTo true
        { false.isTrue() } throws Error::class
        { null.isTrue() } throws Error::class
    }

    @Test fun `is false`() {
        false.isFalse()
        false.isFalse() referenceIsEqualTo false
        { true.isFalse() } throws Error::class
        { null.isFalse() } throws Error::class
    }

    @Test fun `is close to`() {
        1 isCloseTo 1 tolerance 0
        { 1 isCloseTo 2 tolerance 0 } throws Error::class
        1 isCloseTo 2 tolerance 1
        { 1 isCloseTo 2 tolerance 0 } throws Error::class
        1 isCloseTo 0 tolerance 1
        { 1 isCloseTo 0 tolerance 0 } throws Error::class

        1 isCloseTo 1.1 tolerance 0.2
        { 1 isCloseTo 1.1 tolerance 0.05 } throws Error::class
        1 isCloseTo 0.9 tolerance 0.2
        { 1 isCloseTo 0.9 tolerance 0.05 } throws Error::class
    }

    @Test fun `close to works on nullable types`() {
        (1 as Int?) isCloseTo (1 as Int?) tolerance (0 as Int?)

        { (null as Int?) isCloseTo 1 tolerance 0 } throws Error::class
        { 1 isCloseTo (null as Int?) tolerance 0 } throws Error::class
        { 1 isCloseTo 1 tolerance (null as Int?) } throws Error::class
    }

    @Test fun `proof`() {
        "We can describe our tests" proof {
            1 isEqualTo 1
        }
        {
            "Test description" proof {
                1 isEqualTo 2
            }
        }.throws(Error::class).message isEqualTo "Test description"
        {
            "Test description" proof {
                1 isEqualTo 1
                1 isEqualTo 2
            }
        }.throws(Error::class).message isEqualTo "Test description"
    }

    @Test fun `proof in a expression body`() = "A proof can be done in a expression body" proof {
        1 isEqualTo 1
    }
}
