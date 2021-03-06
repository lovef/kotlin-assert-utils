package se.lovef.assert

import junit.framework.AssertionFailedError
import org.junit.Assert
import se.lovef.assert.check.IterableCheck

/*
 * Date: 2017-09-02
 * @author Love
 */

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
            a.size isEqualTo  b.size
        }
    }
    val iteratorA = a.iterator()
    val iteratorB = b.iterator()
    while (iteratorA.hasNext()) {
        val elementA = iteratorA.next()
        if(!iteratorB.hasNext())
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
