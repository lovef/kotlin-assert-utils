package se.lovef.assert.v1.check

/**
 * Runs a check on an object.
 *
 * Date: 2017-12-03
 * @author Love
 */
interface Check<in T> {

    operator fun invoke(toCheck: T)
}
