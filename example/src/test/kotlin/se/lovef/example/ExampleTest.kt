package se.lovef.example

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

        listOf(1, "2", null).shouldNotBeEmpty() shouldContain "2" shouldNotContain -1

        { listOf(1, 2, 3) shouldEqual intArrayOf(1, 2, 3) }
            .throws(Error::class)
            .message shouldContain "<ArrayList> [1, 2, 3]" shouldContain "<int[]> [1, 2, 3]"
    }
}
