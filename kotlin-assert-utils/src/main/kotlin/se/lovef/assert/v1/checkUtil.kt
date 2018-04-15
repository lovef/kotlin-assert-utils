package se.lovef.assert.v1

import se.lovef.assert.v1.check.Check

/*
 * Date: 2017-12-03
 * @author Love
 */

/** `a passes check` asserts that `a` passes the [Check] `check` */
infix fun <T> T.passes(check: Check<T>) = also { check(it) }

/** `a passes check { it equals b }` checks that `a` is equal to `b` */
fun <T> check(check: (T) -> Unit) = object : Check<T> {
    override fun invoke(toCheck: T) {
        check(toCheck)
    }
}
