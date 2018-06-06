[![Build Status](https://travis-ci.org/lovef/kotlin-assert-utils.svg?branch=master)](https://travis-ci.org/lovef/kotlin-assert-utils)
[![Download](https://api.bintray.com/packages/lovef/maven/kotlin-assert-utils/images/download.svg)](https://bintray.com/lovef/maven/kotlin-assert-utils/_latestVersion)

# Kotlin assert utils

A set of assert utilities for JUnit testing with Kotlin

## Example

```kotlin
import org.junit.Test
import se.lovef.assert.v1.*

class ExampleTest {

    @Test fun `example test`() {
        this typeIs Any::class

        1 shouldEqual 1
        { 1 shouldEqual 2 } throws Error::class

        1 shouldBeLessThan 2 shouldBeLessThan 3
        1 shouldBeCloseTo 1.1 tolerance 0.2

        "my 1337 string" shouldContain "my" shouldContain 1337 shouldContain "string"
        "my 1337 string" shouldNotContain "shit"

        null.shouldBeNull()
        ("123" as String?).shouldNotBeNull().length shouldEqual 3

        val exception = Exception()
        ; { throw exception } throws exception
        { { throw exception } throws Exception() } throws exception
        { {  } throws exception } throws NotThrownError::class

        { "1 is 2" proof { 1 shouldEqual 2 } }
            .throws(Error::class)
            .message shouldEqual "1 is 2"

        true.shouldBeTrue();
        { null.shouldBeTrue() } throws Error::class

        listOf(1, "2", null) shouldContain "2" shouldNotContain -1
    }
}
```

## Setup

```gradle
apply plugin: 'kotlin'

dependencies {
    testCompile 'se.lovef:kotlin-assert-utils:0.6.1'
}

repositories {
    mavenCentral()
}
```
