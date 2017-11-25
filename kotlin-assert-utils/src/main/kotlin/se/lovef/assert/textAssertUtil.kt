package se.lovef.assert

import org.hamcrest.MatcherAssert
import org.intellij.lang.annotations.Language

/*
 * Date: 2017-11-25
 * @author Love
 */

infix fun CharSequence?.doesContain(other: CharSequence) = isNotNull().apply {
    MatcherAssert.assertThat("'$this' does contain '$other'", this.contains(other))
}

infix fun CharSequence?.doesNotContain(other: CharSequence) = apply {
    MatcherAssert.assertThat("'$this' does not contain '$other'", this == null || !this.contains(other))
}

infix fun CharSequence?.doesMatch(@Language("RegExp") regex: CharSequence) = isNotNull().apply {
    MatcherAssert.assertThat("'$this' does match '$regex'", this.toString().contains(regex.toString().toRegex()))
}

infix fun CharSequence?.doesMatch(regex: Regex) = isNotNull().apply {
    MatcherAssert.assertThat("'$this' does match '$regex'", this.toString().contains(regex))
}

infix fun CharSequence?.doesNotMatch(@Language("RegExp") regex: CharSequence) = apply {
    MatcherAssert.assertThat("'$this' does not match '$regex'", this == null || !this.contains(regex.toString().toRegex()))
}

infix fun CharSequence?.doesNotMatch(@Language("RegExp") regex: Regex) = apply {
    MatcherAssert.assertThat("'$this' does not match '$regex'", this == null || !this.contains(regex))
}

infix fun CharSequence?.doesStartWith(start: CharSequence)  = isNotNull().apply {
    MatcherAssert.assertThat("'$this' does start with '$start'", this.startsWith(start))
}

infix fun CharSequence?.doesNotStartWith(start: CharSequence)  = apply {
    MatcherAssert.assertThat("'$this' does not start with '$start'", this == null || !this.startsWith(start))
}

infix fun CharSequence?.doesEndWith(end: CharSequence)  = isNotNull().apply {
    MatcherAssert.assertThat("'$this' does end with '$end'", this.endsWith(end))
}

infix fun CharSequence?.doesNotEndWith(end: CharSequence)  = apply {
    MatcherAssert.assertThat("'$this' does not end with '$end'", this == null || !this.endsWith(end))
}
