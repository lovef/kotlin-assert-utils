package se.lovef.assert.v1

import org.hamcrest.MatcherAssert
import org.intellij.lang.annotations.Language

/*
 * Date: 2017-11-25
 * @author Love
 */

infix fun CharSequence?.shouldContain(other: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' should contain '$other'", this.contains(other as? CharSequence ?: "$other"))
}

infix fun CharSequence?.shouldNotContain(other: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' should not contain '$other'",
        this == null || !this.contains(other as? CharSequence ?: "$other")
    )
}

infix fun CharSequence?.shouldMatch(@Language("RegExp") regex: CharSequence) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' should match '$regex'", this.toString().contains(regex.toString().toRegex()))
}

infix fun CharSequence?.shouldMatch(regex: Regex) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' should match '$regex'", this.toString().contains(regex))
}

infix fun CharSequence?.shouldNotMatch(@Language("RegExp") regex: CharSequence) = apply {
    MatcherAssert.assertThat(
        "'$this' should not match '$regex'",
        this == null || !this.contains(regex.toString().toRegex())
    )
}

infix fun CharSequence?.shouldNotMatch(@Language("RegExp") regex: Regex) = apply {
    MatcherAssert.assertThat("'$this' should not match '$regex'", this == null || !this.contains(regex))
}

@Deprecated("Use shouldStartWith instead",
    ReplaceWith("this.shouldStartWith(start)", "se.lovef.assert.v1.TextAssertUtilKt.shouldStartWith"))
infix fun CharSequence?.doesStartWith(start: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does start with '$start'", this.startsWith(start as? CharSequence ?: "$start"))
}

infix fun CharSequence?.shouldStartWith(start: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' should start with '$start'", this.startsWith(start as? CharSequence ?: "$start"))
}

infix fun CharSequence?.shouldNotStartWith(start: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' should not start with '$start'",
        this == null || !this.startsWith(start as? CharSequence ?: "$start")
    )
}

infix fun CharSequence?.shouldEndWith(end: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' should end with '$end'", this.endsWith(end as? CharSequence ?: "$end"))
}

infix fun CharSequence?.shouldNotEndWith(end: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' should not end with '$end'",
        this == null || !this.endsWith(end as? CharSequence ?: "$end")
    )
}
