package se.lovef.assert

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

infix fun <T : Any, R : Any> T?.typeIs(type: KClass<R>) = apply {
    MatcherAssert.assertThat("$this type is ${type.java.simpleName}", this, IsInstanceOf(type.java))
}

private fun equals(a: Any?, b: Any?) = when {
    a is Array<*> && b is Array<*> -> Arrays.equals(a, b)
    a is ByteArray && b is ByteArray  -> Arrays.equals(a, b)
    a is CharArray && b is CharArray -> Arrays.equals(a, b)
    a is ShortArray && b is ShortArray -> Arrays.equals(a, b)
    a is IntArray && b is IntArray -> Arrays.equals(a, b)
    a is LongArray && b is LongArray -> Arrays.equals(a, b)
    a is FloatArray && b is FloatArray -> Arrays.equals(a, b)
    a is DoubleArray && b is DoubleArray -> Arrays.equals(a, b)
    a is BooleanArray && b is BooleanArray -> Arrays.equals(a, b)
    else -> a == b
}

infix fun <T : Any?> T.isEqualTo(other: T): T {
    assertTrue("\n" +
            "Expected: $other\n" +
            "Got:      $this", equals(this, other))
    return other
}

infix fun <T : Any> T?.isNotEqualTo(other: T?): T? {
    assertThat("\n" +
            "Expected not: $other\n" +
            "Got:          $this", !equals(this, other))
    return other
}

fun <T : Any> T?.isNull() = apply { this isEqualTo null }

fun <T : Any> T?.isNotNull() = apply { this isNotEqualTo null }!!

fun Boolean?.isTrue() = apply { this isEqualTo true }

fun Boolean?.isFalse() = apply { this isEqualTo false }

infix fun <T : Any> T?.referenceIsEqualTo(other: T?) = apply {
    assertTrue("\n" +
            "Expected:          $this\n" +
            "Same reference as: $other", this === other)
}

infix fun <T : Any> T?.referenceIsNotEqualTo(other: T?): T? {
    Assert.assertTrue("\n" +
            "Expected:              $this\n" +
            "Not same reference as: $other", this !== other)
    return other
}

infix fun <T : Number, R : Number> T.isCloseTo(other: R) = Difference(this, other)

class Difference<out T : Number, out R : Number>(val a: T, val b: R) {
    infix fun tolerance(t: Number) {
        val difference = Math.abs(a.toDouble() - b.toDouble())
        assertTrue("To large difference between $a and $b: $difference > $t", difference <= t.toDouble())
    }
}

infix fun String.proof(proof: () -> Unit) {
    try {
        proof()
    } catch (throwable: Throwable) {
        throw AssertionError(this, throwable)
    }
}
