package se.lovef.assert.check

import org.junit.Test
import se.lovef.assert.IterableAssertUtilTest.TestIterable
import se.lovef.assert.isEqualTo
import se.lovef.assert.passes
import se.lovef.assert.throws

/**
 * Date: 2017-12-03
 * @author Love
 */
class IterableCheckTest {

    @Test fun `check iterable`() {
        TestIterable(1) passes IterableCheck({ it isEqualTo 1 })
        TestIterable(1, 2) passes IterableCheck({ it isEqualTo 1 }, { it isEqualTo 2 });
        { TestIterable(1, 2) passes IterableCheck({ it isEqualTo 2 }, { it isEqualTo 2 }) } throws Error::class
        { TestIterable(1, 2) passes IterableCheck({ it isEqualTo 1 }, { it isEqualTo 1 }) } throws Error::class
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
