package se.lovef.assert.v1

import org.junit.Test
import se.lovef.assert.v1.check.IterableCheck

/**
 * Date: 2017-03-31
 * @author Love
 */
class IterableAssertUtilTest {

    object TestType {
        fun testFun() {}
    }

    class TestIterable<out E>(private val elements: Iterable<E>) : Iterable<E> {
        constructor(vararg elements: E) : this(elements.toList())

        override fun iterator() = elements.iterator()
        fun testIterableFunction() {}
    }

    interface IterableCreator {
        fun <E> iterable(vararg elements: E) = iterableFrom(elements.toList())
        fun <E> iterableFrom(iterable: Iterable<E>): Iterable<E>
    }

    class SetCreator : IterableCreator {
        override fun <E> iterableFrom(iterable: Iterable<E>) = iterable.toSet()
    }

    class ListCreator : IterableCreator {
        override fun <E> iterableFrom(iterable: Iterable<E>) = iterable.toList()
    }

    class TestIterableCreator : IterableCreator {
        override fun <E> iterableFrom(iterable: Iterable<E>) = TestIterable(iterable)
    }

    private val iterableCreators = listOf(
        SetCreator(),
        ListCreator(),
        TestIterableCreator()
    )

    private fun <E> iterators(vararg elements: E) = iterableCreators.map { it.iterable(*elements) }

    @Test fun `iterable should contain`() {
        iterators(1, "2", null).forEach {
            val returned = it shouldContain 1 shouldContain "2" shouldContain null
            returned shouldBe it
            { it shouldContain -1 } throws Error::class
            ((it as Iterable<*>?) shouldContain "2").contains("2") // Return type is non nullable
        }
        ; { (null as Iterable<*>?) shouldContain "2" } throws Error::class
    }

    @Test fun `iterable should NOT contain`() {
        iterators(1, "2", null).forEach {
            val returned = it shouldNotContain -1
            returned shouldBe it
            { it shouldNotContain "2" } throws Error::class
        }
        (null as Iterable<*>?) shouldNotContain -1
    }

    @Test fun `iterable should be empty`() {
        iterators<Any>().forEach { it.shouldBeEmpty() shouldBe it }
        iterators<Any>(1, 2, 3).forEach { { it.shouldBeEmpty() } throws Error::class }
        emptyList<TestType?>().shouldBeEmpty().typeIsListOfTestTypeObjects()
    }

    @Test fun `iterable should NOT be empty`() {
        iterators<Any>(1, 2, 3).forEach { it.shouldNotBeEmpty() shouldBe it }
        iterators<Any>().forEach { { it.shouldNotBeEmpty() } throws Error::class }
        (listOf<TestType?>(TestType) as List<TestType?>?).shouldNotBeEmpty().typeIsListOfTestTypeObjects()
        ; { (null as List<TestType?>?).shouldNotBeEmpty() } throws Error::class
        ; { (null as Iterable<TestType?>?).shouldNotBeEmpty() } throws Error::class
    }

    /** Utility to check compile time type */
    private fun List<TestType?>.typeIsListOfTestTypeObjects() = this

    @Test fun `pairwise assert on iterables`() {
        val iteratorCombinations = iterableCreators
            .flatMap { first -> iterableCreators.map { first to it } }
        iteratorCombinations.forEach { (a, b) ->
            "pairwise assertion works with $a and $b" proof {
                assertPairwise(a.iterable(1), b.iterable(1)) { _, _ -> };
                { assertPairwise(a.iterable(1), b.iterable(1, 2)) { _, _ -> } } throws Error::class
                { assertPairwise(a.iterable(1, 2), b.iterable(1)) { _, _ -> } } throws Error::class

                assertPairwise(a.iterable(1), b.iterable(1)) { a, b -> a shouldEqual b }
                assertPairwise(a.iterable(null), b.iterable(null)) { a, b -> a shouldEqual b }
                assertPairwise(null as Iterable<*>?, null as Iterable<*>?) { _, _ -> };
                { assertPairwise(a.iterable(1), b.iterable(2)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(1), b.iterable(this)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(this), b.iterable(1)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(1), b.iterable(null)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(null), b.iterable(1)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(1), null) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(null, b.iterable(1)) { a, b -> a shouldEqual b } } throws Error::class

                assertPairwise(a.iterable(1, 2), b.iterable(1, 2)) { a, b -> a shouldEqual b };
                { assertPairwise(a.iterable(1, 2), b.iterable(2, 2)) { a, b -> a shouldEqual b } } throws Error::class
                { assertPairwise(a.iterable(1, 2), b.iterable(1, 3)) { a, b -> a shouldEqual b } } throws Error::class
            }
        }
    }

    @Test fun `check iterable aliases`() {
        iterableCheck<Any>({}) typeIs IterableCheck::class
        listCheck<Any>({}) typeIs IterableCheck::class
    }

    @Test fun `should all happy case`() {
        iterableCreators.forEach { iterableCreator ->
            iterableCreator.iterable(1, 2, 3) shouldAll { it shouldEqual it }
        }
    }

    @Test fun `should all gives one error`() {
        iterableCreators.forEach { iterableCreator ->
            val toBeThrown = Exception("To throw");
            {
                iterableCreator.iterable('a', 'b', 'c', 'd') shouldAll { if (it == 'c') throw toBeThrown }
            }.throws(AssertionError::class).also {
                it.message shouldContain 1 shouldContain 'c'
                it.cause shouldBe toBeThrown
            }
        }
    }

    @Test fun `should all gives a few errors`() {
        iterableCreators.forEach { iterableCreator ->
            val firstThrown = Exception("first")
            val secondThrow = Exception("second");
            {
                iterableCreator.iterable('a', 'b', 'c') shouldAll {
                    if (it == 'b') throw firstThrown
                    if (it == 'c') throw secondThrow
                }
            }.throws(AssertionError::class).also {
                it.message shouldContain 2 shouldContain "b, c"
                it.cause shouldBe firstThrown
            }
        }
    }

    @Test fun `should all gives 10 errors`() {
        iterableCreators.forEach { iterableCreator ->
            val toBeThrown = Exception("To throw");
            {
                iterableCreator.iterableFrom(1..10) shouldAll { throw toBeThrown }
            }.throws(AssertionError::class).also {
                it.message shouldContain 10 shouldContain (1..10).joinToString()
                it.cause shouldBe toBeThrown
            }
        }
    }

    @Test fun `should all gives more than 10 errors`() {
        iterableCreators.forEach { iterableCreator ->
            val toBeThrown = Exception("To throw");
            {
                iterableCreator.iterableFrom(1..20) shouldAll { throw toBeThrown }
            }.throws(AssertionError::class).also {
                it.message shouldContain "more than 10" shouldContain (1..10).joinToString()
                it.cause shouldBe toBeThrown
            }
        }
    }

    @Test fun `should all returns the iterable`() {
        iterableCreators.map { it.iterable(1..10) }.shouldAll {
            it shouldAll { } shouldBe it
        }
    }

    @Test fun `should all returns the iterable as the correct type`() {
        TestIterable(1, 2, 3).shouldAll { }.testIterableFunction()
    }
}

