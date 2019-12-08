[![Build Status](https://travis-ci.org/lovef/kotlin-assert-utils.svg?branch=master)](https://travis-ci.org/lovef/kotlin-assert-utils)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.lovef/kotlin-assert-utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.lovef/kotlin-assert-utils)
[![Download](https://api.bintray.com/packages/lovef/maven/kotlin-assert-utils/images/download.svg)](https://bintray.com/lovef/maven/kotlin-assert-utils/_latestVersion)

# Kotlin assert utils

A set of assert utilities for JUnit testing with Kotlin

## Example

```kotlin
import org.junit.Test
import se.lovef.assert.v1.*

class ExampleTest {

    @Test fun `example test`() {
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
        { { } throws exception } throws NotThrownError::class

        { "1 is 2" proof { 1 shouldEqual 2 } }
            .throws(Error::class)
            .message shouldEqual "1 is 2"

        true.shouldBeTrue();
        { null.shouldBeTrue() } throws Error::class

        listOf(1, "2", null).shouldNotBeEmpty() shouldContain "2" shouldNotContain -1

        { listOf(1, 2, 3) shouldEqual intArrayOf(1, 2, 3) }
            .throws(Error::class)
            .message shouldEqual """|
                |Expected: <int[]> [1, 2, 3]
                |Got:      <ArrayList> [1, 2, 3]
                """.trimMargin();

        { 1337 shouldEqual "1337" }
            .throws(Error::class)
            .message shouldEqual """|
                |Expected: "1337"
                |Got:      1337
            """.trimMargin()

        listOf(1, 3, 5) shouldAll { it % 2 shouldEqual 1 }
    }
}
```

## Setup

```gradle
plugins {
    id "org.jetbrains.kotlin.jvm" version "1.3.61"
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib"
    testImplementation "se.lovef:kotlin-assert-utils:0.10.1"
}

repositories {
    mavenCentral()
}
```
