package se.lovef.assert

import org.junit.Test
import se.lovef.assert.check.ElementCountMismatchError
import se.lovef.assert.check.IterableCheck

/**
 * Date: 2017-03-31
 * @author Love
 */
class IterableAssertUtilTest {

    class TestIterable<out E>(vararg elements: E): Iterable<E> {
        private val elements = elements.asList()
        override fun iterator() = elements.iterator()
    }

    interface IterableCreator {
        fun <E> iterable(vararg elements: E): Iterable<E>
    }

    class SetCreator: IterableCreator {
        override fun <E> iterable(vararg elements: E) = setOf(*elements)
    }

    class ListCreator: IterableCreator {
        override fun <E>iterable(vararg elements: E) = listOf(*elements)
    }

    class TestIterableCreator: IterableCreator {
        override fun <E>iterable(vararg elements: E) = TestIterable(*elements)
    }

    @Test fun `pairwise assert on iterables`() {
        listOf(
                SetCreator() to SetCreator(),
                SetCreator() to ListCreator(),
                SetCreator() to TestIterableCreator(),
                ListCreator() to SetCreator(),
                ListCreator() to ListCreator(),
                ListCreator() to TestIterableCreator(),
                TestIterableCreator() to SetCreator(),
                TestIterableCreator() to ListCreator(),
                TestIterableCreator() to TestIterableCreator()
        ).forEach {
            val a = it.first
            val b = it.second
            "pairwise assertion works with $a and $b" proof {
                assertPairwise(a.iterable(1), b.iterable(1)) { _, _ -> };
                { assertPairwise(a.iterable(1), b.iterable(1, 2)) { _, _ -> } } throws Error::class
                { assertPairwise(a.iterable(1, 2), b.iterable(1)) { _, _ -> } } throws Error::class

                assertPairwise(a.iterable(1), b.iterable(1)) { a, b -> a isEqualTo b }
                assertPairwise(a.iterable(null), b.iterable(null)) { a, b -> a isEqualTo b }
                assertPairwise(null as Iterable<*>?, null as Iterable<*>?) { _, _ -> };
                { assertPairwise(a.iterable(1), b.iterable(2)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(1), b.iterable(this)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(this), b.iterable(1)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(1), b.iterable(null)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(null), b.iterable(1)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(1), null) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(null, b.iterable(1)) { a, b -> a isEqualTo b } } throws Error::class

                assertPairwise(a.iterable(1, 2), b.iterable(1, 2)) { a, b -> a isEqualTo b };
                { assertPairwise(a.iterable(1, 2), b.iterable(2, 2)) { a, b -> a isEqualTo b } } throws Error::class
                { assertPairwise(a.iterable(1, 2), b.iterable(1, 3)) { a, b -> a isEqualTo b } } throws Error::class
            }
        }
    }

    @Test fun `check iterable aliases`() {
        iterableCheck<Any>({}) typeIs IterableCheck::class
        listCheck<Any>({}) typeIs IterableCheck::class
    }
}
