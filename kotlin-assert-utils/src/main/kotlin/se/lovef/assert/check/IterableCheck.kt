package se.lovef.assert.check

import se.lovef.assert.isNotNull

/**
 * `a passes IterableCheck({ it shouldEqual a1}, { it shouldEqual a2})`
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
        for (it in toCheck) {
            if (!elementCheckIterator.hasNext()) {
                throw ElementCountMismatchError("To many elements in iterable,\n" +
                        "expected: ${elementChecks.count()}\n" +
                        "actual:   ${toCheck.count()}")
            }
            val check = elementCheckIterator.next()
            check(it)
        }
        if (elementCheckIterator.hasNext()) {
            throw ElementCountMismatchError("To few elements in iterable,\n" +
                    "expected: ${elementChecks.count()}\n" +
                    "actual:   ${toCheck.count()}")
        }
    }
}

/** Expected matching count on two collections but they did not match */
class ElementCountMismatchError(message: String): AssertionError(message)
