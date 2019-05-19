package se.lovef.assert.v1

import junit.framework.AssertionFailedError
import org.junit.Assert
import se.lovef.assert.v1.check.IterableCheck

/*
 * Date: 2017-09-02
 * @author Love
 */

infix fun <E> Iterable<E>?.shouldContain(element: E): Iterable<E> {
    if (this == null || !this.contains(element)) {
        throw AssertionError("Expected to contain $element:\n$this")
    }
    return this
}

infix fun <E> Iterable<E>?.shouldNotContain(element: E): Iterable<E>? {
    if (this != null && this.contains(element)) {
        throw AssertionError("Expected to NOT contain $element:\n$this")
    }
    return this
}

fun <E : Any?, T : Iterable<E>> T.shouldBeEmpty() = apply {
    if (this is Collection<*> && this.isNotEmpty() || this.iterator().hasNext()) {
        throw AssertionError("Expected to be empty:\n$this")
    }
}

fun <E : Any?, T : Iterable<E>> T?.shouldNotBeEmpty() = apply {
    if (this == null || this is Collection<*> && this.isEmpty() || !this.iterator().hasNext()) {
        throw AssertionError("Expected to NOT be empty:\n$this")
    }
}!!

/** Executes [assertionBlock] on all elements from [a] and [b] in pairs.
 * If only one of [a] and [b] is null then [AssertionFailedError]
 *
 * @throws AssertionError
 *      if only one of [a] and [b] are null,
 *      or if [a] and [b] have different number of elements,
 *      or if [assertionBlock] fails for any element pair. */
fun <E> assertPairwise(a: Iterable<E>?, b: Iterable<E>?, assertionBlock: (E, E) -> Unit) {
    if (a == null && b == null) {
        return
    }
    if (a == null || b == null) {
        throw AssertionError("$a and $b are both null or both not null")
    }
    if (a is Collection<E> && b is Collection<E>) {
        "a.size == ${a.size} == b.size == ${b.size}" proof {
            a.size shouldEqual b.size
        }
    }
    val iteratorA = a.iterator()
    val iteratorB = b.iterator()
    while (iteratorA.hasNext()) {
        val elementA = iteratorA.next()
        if (!iteratorB.hasNext())
            Assert.fail("to few elements in $b")
        val elementB = iteratorB.next()
        try {
            assertionBlock(elementA, elementB)
        } catch (t: Throwable) {
            throw AssertionError("Assertion of $elementA and $elementB failed", t)
        }
    }
    if (iteratorB.hasNext()) {
        Assert.fail("to many elements in $b")
    }
}

/** Creates an [IterableCheck] */
fun <E> iterableCheck(vararg elementChecks: (E) -> Unit) = IterableCheck(*elementChecks)

/** Creates an [IterableCheck] */
fun <E> listCheck(vararg elementChecks: (E) -> Unit) = IterableCheck(*elementChecks)

inline infix fun <E, T : Iterable<E>> T.shouldAll(assertion: (E) -> Unit) = apply {
    var firstError: Throwable? = null
    val failed = ArrayList<E>()
    for (it in this) {
        try {
            assertion.invoke(it)
        } catch (t: Throwable) {
            if (firstError == null) {
                firstError = t
            }
            failed += it
            if (failed.size > 10) {
                break
            }
        }
    }
    if (firstError != null) {
        val failedString = failed.take(10).joinToString()
        val message = if (failed.size > 10) {
            "Failed for more than 10 elements, starting with $failedString"
        } else {
            "Failed for ${failed.size} elements: $failedString"
        }
        throw AssertionError(message, firstError)
    }
}
