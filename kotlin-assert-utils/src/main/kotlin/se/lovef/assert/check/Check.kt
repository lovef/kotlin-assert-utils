package se.lovef.assert.check

/**
 * Runs a check on an object.
 *
 * Date: 2017-12-03
 * @author Love
 */
interface Check<in T> {

    operator fun invoke(toCheck: T)
}
