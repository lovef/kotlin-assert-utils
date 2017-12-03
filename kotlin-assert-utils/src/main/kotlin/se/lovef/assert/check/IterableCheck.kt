package se.lovef.assert.check

import se.lovef.assert.isNotNull

/**
 * `a passes IterableCheck({ it isEqualTo a1}, { it isEqualTo a2})`
 * checks that `a` contains two elements that equals `a1` and `a2` respectively
 *
 * Date: 2017-12-03
 * @author Love
 */
class IterableCheck<in E>(vararg elementChecks: (E) -> Unit) : Check<Iterable<E>?> {

    private val elementChecks = elementChecks.asIterable()

    override fun invoke(toCheck: Iterable<E>?) {
        toCheck.isNotNull()
        toCheck!!
        val elementCheckIterator = elementChecks.iterator()
        var toFewChecks = false
        for (it in toCheck) {
            if (!elementCheckIterator.hasNext()) {
                toFewChecks = true
                break
            }
            val check = elementCheckIterator.next()
            check(it)
        }
        if (toFewChecks || elementCheckIterator.hasNext()) {
            throw ElementCountMismatchError("Not enough elements in iterable,\n" +
                    "expected: ${elementChecks.count()}\n" +
                    "actual:   ${toCheck.count()}")
        }
    }
}

/** Expected matching count on two collections but they did not match */
class ElementCountMismatchError(message: String): AssertionError(message)
