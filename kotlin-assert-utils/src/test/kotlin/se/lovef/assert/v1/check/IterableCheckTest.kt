package se.lovef.assert.v1.check

import org.junit.Test
import se.lovef.assert.v1.IterableAssertUtilTest.TestIterable
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.passes
import se.lovef.assert.v1.throws

/**
 * Date: 2017-12-03
 * @author Love
 */
class IterableCheckTest {

    @Test fun `check iterable`() {
        TestIterable(1) passes IterableCheck({ it shouldEqual 1 })
        TestIterable(1, 2) passes IterableCheck({ it shouldEqual 1 }, { it shouldEqual 2 });
        { TestIterable(1, 2) passes IterableCheck({ it shouldEqual 2 }, { it shouldEqual 2 }) } throws Error::class
        { TestIterable(1, 2) passes IterableCheck({ it shouldEqual 1 }, { it shouldEqual 1 }) } throws Error::class
    }

    @Test fun `iterable check element count must match`() {
        { TestIterable(1) passes IterableCheck({}, {}) } throws ElementCountMismatchError::class
        { TestIterable(1, 2) passes IterableCheck({}) } throws ElementCountMismatchError::class
    }

    @Test fun `works on nullable types`() {
        (listOf(1) as List<Int>?) passes IterableCheck({});
        { (null as List<Int>?) passes IterableCheck({}) } throws Error::class
    }
}
