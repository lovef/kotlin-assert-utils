package se.lovef.assert

import junit.framework.AssertionFailedError
import kotlin.reflect.KClass

/*
 * Date: 2017-11-25
 * @author Love
 */

inline infix fun <reified T : Throwable> (() -> Any?).throws(throwableType: KClass<T>): T {
    var thrown: T? = null
    try {
        invoke()
    } catch (t: Throwable) {
        if (t is T) {
            thrown = t
        }
    }
    if (thrown == null) {
        throw NotThrownError("${throwableType.java.simpleName} was never thrown")
    }
    try {
        thrown typeIs throwableType
        return thrown
    } catch (error: Error) {
        throw NotThrownError("Expected ${throwableType.java.simpleName} to be thrown " +
                "but caught ${thrown.javaClass.simpleName}")
    }
}

class NotThrownError(message: String) : AssertionFailedError(message)
