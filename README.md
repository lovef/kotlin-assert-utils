# Kotlin assert utils

A set of assert utilities for JUnit testing with Kotlin

``` Kotlin
this typeIs Any::class

1 isEqualTo 1
{ 1 isEqualTo 2 } throws Error::class

1 isLessThan 2 isLessThan 3
1 isCloseTo 1.1 tolerance 0.2

"my awesome string" doesContain "my" doesContain "awesome" doesContain "string"
"my awesome string" doesNotContain "shit"

null.isNull()
"123".isNotNull().length isEqualTo 3

{ "1 is 2" proof { 1 isEqualTo 2 } }
        .throws(Error::class)
        .message isEqualTo "1 is 2"
```