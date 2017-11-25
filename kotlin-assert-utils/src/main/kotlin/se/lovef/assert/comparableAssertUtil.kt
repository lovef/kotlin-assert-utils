package se.lovef.assert

import org.junit.Assert

/*
 * Date: 2017-11-25
 * @author Love
 */

infix fun <T : Comparable<T>> T?.isLessThan(other: T?): T {
    Assert.assertTrue("$this < $other", this.isNotNull() < other.isNotNull())
    return other!!
}

infix fun <T : Comparable<T>> T?.isLessOrEqualTo(other: T?): T {
    Assert.assertTrue("$this <= $other", this.isNotNull() <= other.isNotNull())
    return other!!
}

infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): T {
    Assert.assertTrue("$this > $other", this.isNotNull() > other.isNotNull())
    return other!!
}

infix fun <T : Comparable<T>> T?.isGreaterOrEqualTo(other: T?): T {
    Assert.assertTrue("$this >= $other", this.isNotNull() >= other.isNotNull())
    return other!!
}
