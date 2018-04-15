package se.lovef.assert

import org.junit.Assert

/*
 * Date: 2017-11-25
 * @author Love
 */

/** `a shouldBeLessThan b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is less than `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a shouldBeLessThan b shouldBeLessThan c`
 *
 * Compare with mathematical expression `a < b < c` */
infix fun <T : Comparable<T>> T?.shouldBeLessThan(other: T?): T {
    try {
        Assert.assertTrue(this.shouldNotBeNull() < other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this < $other", error)
    }
    return other!!
}

/** `a shouldBeLessOrEqualTo b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is less than or equal to `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a shouldBeLessOrEqualTo b shouldBeLessOrEqualTo c`
 *
 * Compare with mathematical expression `a ≤ b ≤ c` */
infix fun <T : Comparable<T>> T?.shouldBeLessOrEqualTo(other: T?): T {
    try {
        Assert.assertTrue(this.shouldNotBeNull() <= other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this <= $other", error)
    }
    return other!!
}

/** `a shouldBeGreaterThan b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is greater than `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a shouldBeGreaterThan b shouldBeGreaterThan c`
 *
 * Compare with mathematical expression `a > b > c` */
infix fun <T : Comparable<T>> T?.shouldBeGreaterThan(other: T?): T {
    try {
        Assert.assertTrue("$this > $other", this.shouldNotBeNull() > other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this > $other", error)
    }
    return other!!
}


/** `a shouldBeGreaterOrEqualTo b` asserts that
 *
 * - `a` is not null
 * - `b` is not null
 * - `a` is greater than or equal to `b`
 *
 * `b` is returned after successful assertion to allow chaining
 *
 * `a shouldBeGreaterOrEqualTo b shouldBeGreaterOrEqualTo c`
 *
 * Compare with mathematical expression `a ≥ b ≥ c` */
infix fun <T : Comparable<T>> T?.shouldBeGreaterOrEqualTo(other: T?): T {
    try {
        Assert.assertTrue("$this >= $other", this.shouldNotBeNull() >= other.shouldNotBeNull())
    } catch (error: AssertionError) {
        throw AssertionError("$this >= $other", error)
    }
    return other!!
}
