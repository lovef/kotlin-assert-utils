package se.lovef.example

import org.junit.Test
import se.lovef.assert.v1.NotThrownError
import se.lovef.assert.v1.proof
import se.lovef.assert.v1.shouldAll
import se.lovef.assert.v1.shouldBeCloseTo
import se.lovef.assert.v1.shouldBeLessThan
import se.lovef.assert.v1.shouldBeNull
import se.lovef.assert.v1.shouldBeTrue
import se.lovef.assert.v1.shouldContain
import se.lovef.assert.v1.shouldEqual
import se.lovef.assert.v1.shouldNotBeEmpty
import se.lovef.assert.v1.shouldNotBeNull
import se.lovef.assert.v1.shouldNotContain
import se.lovef.assert.v1.throws

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
