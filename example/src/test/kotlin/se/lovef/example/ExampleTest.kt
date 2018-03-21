package se.lovef.example

import org.junit.Test
import se.lovef.assert.*

/**
 * Date: 2017-08-27
 * @author Love
 */
class ExampleTest {

    @Test fun `example test`() {
        this typeIs Any::class

        1 isEqualTo 1
        { 1 isEqualTo 2 } throws Error::class

        1 isLessThan 2 isLessThan 3
        1 isCloseTo 1.1 tolerance 0.2

        "my 1337 string" doesContain "my" doesContain 1337 doesContain "string"
        "my 1337 string" doesNotContain "shit"

        null.isNull()
        ("123" as String?).isNotNull().length isEqualTo 3

        val exception = Exception()
        ; { throw exception } throws exception
        { { throw exception } throws Exception() } throws exception
        { {  } throws exception } throws NotThrownError::class

        { "1 is 2" proof { 1 isEqualTo 2 } }
            .throws(Error::class)
            .message isEqualTo "1 is 2"

        true.isTrue();
        { null.isTrue() } throws Error::class
    }
}
