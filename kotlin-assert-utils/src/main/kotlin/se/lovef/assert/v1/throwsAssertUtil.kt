@file:Suppress("DEPRECATION")

package se.lovef.assert.v1

import junit.framework.AssertionFailedError
import kotlin.reflect.KClass

/*
 * Date: 2017-11-25
 * @author Love
 */

/** `{ foo() } throws T::class` asserts that invoking `foo()` throws a throwable of type `T`
 *
 * Rethrows throwable of wrong type. Throws [NotThrownError] if nothing was thrown. */
@Deprecated("Deprecated and planed to be removed in version 1.0",
    replaceWith = ReplaceWith("this.throws(throwableType)", "se.lovef.assert.throws"))
inline infix fun <reified T : Throwable> (() -> Any?).throws(throwableType: KClass<T>): T {
    try {
        invoke()
    } catch (thrown: Throwable) {
        if (thrown is T) {
            return thrown
        } else {
            throw thrown
        }
    }
    throw NotThrownError("${throwableType.java.simpleName} was never thrown")
}

/** `{ foo() } throws exception` asserts that invoking `foo()` throws the throwable instance `exception`
 *
 * Rethrows unexpectedly thrown throwable. Throws [NotThrownError] if nothing was thrown. */
@Deprecated("Deprecated and planed to be removed in version 1.0",
    replaceWith = ReplaceWith("this.throws(expected)", "se.lovef.assert.throws"))
infix fun <T : Throwable> (() -> Any?).throws(expected: T): T {
    try {
        invoke()
    } catch (thrown: Throwable) {
        if (thrown === expected) {
            return thrown
        } else {
            throw thrown
        }
    }
    throw NotThrownError("instance $expected was never thrown")
}

/** Indicates that expected throwable was never thrown */
@Deprecated("Deprecated and planed to be removed in version 1.0, use se.lovef.assert.NotThrownError instead",
    replaceWith = ReplaceWith("NotThrownError", "se.lovef.assert.NotThrownError"))
class NotThrownError(message: String) : AssertionFailedError(message)
