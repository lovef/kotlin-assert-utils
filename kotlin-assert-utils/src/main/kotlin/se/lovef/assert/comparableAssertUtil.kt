package se.lovef.assert

import org.junit.Assert

/*
 * Date: 2017-11-25
 * @author Love
 */

/** `a isLessThan b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is less than `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a isLessThan b isLessThan c`
 *
 * Compare with mathematical expression `a < b < c` */
infix fun <T : Comparable<T>> T?.isLessThan(other: T?): T {
    try {
        Assert.assertTrue(this.shouldNotBeNull() < other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this < $other", error)
    }
    return other!!
}

/** `a isLessOrEqualTo b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is less than or equal to `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a isLessOrEqualTo b isLessOrEqualTo c`
 *
 * Compare with mathematical expression `a ≤ b ≤ c` */
infix fun <T : Comparable<T>> T?.isLessOrEqualTo(other: T?): T {
    try {
        Assert.assertTrue(this.shouldNotBeNull() <= other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this <= $other", error)
    }
    return other!!
}

/** `a isGreaterThan b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is greater than `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a isGreaterThan b isGreaterThan c`
 *
 * Compare with mathematical expression `a > b > c` */
infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): T {
    try {
        Assert.assertTrue("$this > $other", this.shouldNotBeNull() > other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this > $other", error)
    }
    return other!!
}


/** `a isGreaterOrEqualTo b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is greater than or equal to `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a isGreaterOrEqualTo b isGreaterOrEqualTo c`
 *
 * Compare with mathematical expression `a ≥ b ≥ c` */
infix fun <T : Comparable<T>> T?.isGreaterOrEqualTo(other: T?): T {
    try {
        Assert.assertTrue("$this >= $other", this.shouldNotBeNull() >= other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this >= $other", error)
    }
    return other!!
}
