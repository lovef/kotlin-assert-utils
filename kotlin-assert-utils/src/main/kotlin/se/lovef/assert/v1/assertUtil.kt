package se.lovef.assert.v1

import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertTrue
import java.text.MessageFormat
import java.util.*
import kotlin.reflect.KClass

/*
 * Date: 2017-03-31
 * @author Love
 */

/**
 * Fail with message formatted in [MessageFormat.format].
 * The [arguments] will be prettified before added to the message, eg arrays are formatted like a [List].
 */
fun fail(messagePattern: String, vararg arguments: Any?) {
    val prettifiedArguments = arguments.map { stringOf(it) }.toTypedArray()
    val message = MessageFormat.format(messagePattern, *prettifiedArguments)
    org.junit.Assert.fail(message)
}

private fun stringOf(a: Any?) = when (a) {
    is Array<*> -> a.toList().toString()
    is ByteArray -> a.toList().toString()
    is CharArray -> a.toList().toString()
    is ShortArray -> a.toList().toString()
    is IntArray -> a.toList().toString()
    is LongArray -> a.toList().toString()
    is FloatArray -> a.toList().toString()
    is DoubleArray -> a.toList().toString()
    is BooleanArray -> a.toList().toString()
    else -> a.toString()
}

/** `a typeIs T::class asserts that `a` is an instance of `T`.
 *
 * `a` is returned as `T` after successful assertion. */
@Suppress("UNCHECKED_CAST")
infix fun <T : Any, R : Any> T?.typeIs(type: KClass<R>): R {
    MatcherAssert.assertThat("$this type is ${type.java.simpleName}", this, IsInstanceOf(type.java))
    return this as R
}

infix fun <T : Any?> T.shouldEqual(other: T): T {
    if (!equals(this, other))
        fail("\n" +
                "Expected: {0}\n" +
                "Got:      {1}",
            other,
            this)
    return other
}

infix fun <T : Any> T?.shouldNotEqual(other: T?): T? {
    if (equals(this, other))
        fail("\n" +
                "Expected not: {0}\n" +
                "Got:          {1}",
            other,
            this)
    return other
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

fun <T : Any> T?.shouldBeNull() = apply { this shouldEqual null }

fun <T : Any> T?.shouldNotBeNull() = apply { this shouldNotEqual null }!!

fun Boolean?.shouldBeTrue() = apply { this shouldEqual true }

fun Boolean?.shouldBeFalse() = apply { this shouldEqual false }

infix fun <T : Any> T?.shouldBe(other: T?): T? {
    if (this !== other) {
        fail("\n" +
                "Expected:          {0}\n" +
                "Same reference as: {1}",
            other,
            this)
    }
    return other
}

infix fun <T : Any> T?.shouldNotBe(other: T?): T? {
    if(this === other) {
        fail("\n" +
                "Expected:              {0}\n" +
                "Not same reference as: {1}",
            other,
            this)
    }
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
