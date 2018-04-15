package se.lovef.assert

import org.hamcrest.MatcherAssert
import org.intellij.lang.annotations.Language

/*
 * Date: 2017-11-25
 * @author Love
 */

infix fun CharSequence?.doesContain(other: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does contain '$other'", this.contains(other as? CharSequence ?: "$other"))
}

infix fun CharSequence?.doesNotContain(other: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' does not contain '$other'",
        this == null || !this.contains(other as? CharSequence ?: "$other")
    )
}

infix fun CharSequence?.doesMatch(@Language("RegExp") regex: CharSequence) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does match '$regex'", this.toString().contains(regex.toString().toRegex()))
}

infix fun CharSequence?.doesMatch(regex: Regex) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does match '$regex'", this.toString().contains(regex))
}

infix fun CharSequence?.doesNotMatch(@Language("RegExp") regex: CharSequence) = apply {
    MatcherAssert.assertThat(
        "'$this' does not match '$regex'",
        this == null || !this.contains(regex.toString().toRegex())
    )
}

infix fun CharSequence?.doesNotMatch(@Language("RegExp") regex: Regex) = apply {
    MatcherAssert.assertThat("'$this' does not match '$regex'", this == null || !this.contains(regex))
}

infix fun CharSequence?.doesStartWith(start: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does start with '$start'", this.startsWith(start as? CharSequence ?: "$start"))
}

infix fun CharSequence?.doesNotStartWith(start: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' does not start with '$start'",
        this == null || !this.startsWith(start as? CharSequence ?: "$start")
    )
}

infix fun CharSequence?.doesEndWith(end: Any?) = shouldNotBeNull().apply {
    MatcherAssert.assertThat("'$this' does end with '$end'", this.endsWith(end as? CharSequence ?: "$end"))
}

infix fun CharSequence?.doesNotEndWith(end: Any?) = apply {
    MatcherAssert.assertThat(
        "'$this' does not end with '$end'",
        this == null || !this.endsWith(end as? CharSequence ?: "$end")
    )
}
