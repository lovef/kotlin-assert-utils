package se.lovef.assert.v1

import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Assert.assertTrue
import java.util.*
import kotlin.reflect.KClass

/*
 * Date: 2017-03-31
 * @author Love
 */

/** `a typeIs T::class asserts that `a` is an instance of `T`.
 *
 * `a` is returned as `T` after successful assertion. */
@Suppress("UNCHECKED_CAST")
infix fun <T : Any, R : Any> T?.typeIs(type: KClass<R>): R {
    MatcherAssert.assertThat("$this type is ${type.java.simpleName}", this, IsInstanceOf(type.java))
    return this as R
}

private fun equals(a: Any?, b: Any?) = when {
    a is Array<*> && b is Array<*> -> Arrays.equals(a, b)
    a is ByteArray && b is ByteArray -> Arrays.equals(a, b)
    a is CharArray && b is CharArray -> Arrays.equals(a, b)
    a is ShortArray && b is ShortArray -> Arrays.equals(a, b)
    a is IntArray && b is IntArray -> Arrays.equals(a, b)
    a is LongArray && b is LongArray -> Arrays.equals(a, b)
    a is FloatArray && b is FloatArray -> Arrays.equals(a, b)
    a is DoubleArray && b is DoubleArray -> Arrays.equals(a, b)
    a is BooleanArray && b is BooleanArray -> Arrays.equals(a, b)
    else -> a == b
}

infix fun <T : Any?> T.shouldEqual(other: T): T {
    assertTrue("\n" +
            "Expected: $other\n" +
            "Got:      $this", equals(this, other))
    return other
}

infix fun <T : Any> T?.shouldNotEqual(other: T?): T? {
    assertThat("\n" +
            "Expected not: $other\n" +
            "Got:          $this", !equals(this, other))
    return other
}

fun <T : Any> T?.shouldBeNull() = apply { this shouldEqual null }

fun <T : Any> T?.shouldNotBeNull() = apply { this shouldNotEqual null }!!

fun Boolean?.shouldBeTrue() = apply { this shouldEqual true }

fun Boolean?.shouldBeFalse() = apply { this shouldEqual false }

infix fun <T : Any> T?.shouldBe(other: T?) = apply {
    assertTrue("\n" +
            "Expected:          $this\n" +
            "Same reference as: $other", this === other)
}

infix fun <T : Any> T?.shouldNotBe(other: T?): T? {
    Assert.assertTrue("\n" +
            "Expected:              $this\n" +
            "Not same reference as: $other", this !== other)
    return other
}

/** `a shouldBeCloseTo b tolerance c` asserts that
 *
 * - `a`, `b` and `c` is not null
 * - The difference between `a` and `b` is less then or equal to `c` */
infix fun <T : Number, R : Number> T?.shouldBeCloseTo(other: R?) = Difference(this.shouldNotBeNull(), other.shouldNotBeNull())

class Difference<out T : Number, out R : Number>(private val a: T, private val b: R) {
    infix fun tolerance(t: Number?) {
        val difference = Math.abs(a.toDouble() - b.toDouble())
        assertTrue("To large difference between $a and $b: $difference > $t", difference <= t.shouldNotBeNull().toDouble())
    }
}

infix fun String.proof(proof: () -> Unit) {
    try {
        proof()
    } catch (throwable: Throwable) {
        throw AssertionError(this, throwable)
    }
}
