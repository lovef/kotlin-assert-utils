package se.lovef.assert

import org.junit.Test

/**
 * Date: 2017-12-03
 * @author Love
 */
class CheckUtilTest {

    @Test fun `basic check`() {
        1 passes check { it isEqualTo 1 };
        { 1 passes check { it isEqualTo 2 } } throws Error::class
    }

    @Test fun `'passes' returns checked object`() {
        val a = Any()
        a passes check {  } shouldBe a
    }

    @Test fun `check on nullable`() {
        null passes check { it isEqualTo null };
        { null passes check { it isEqualTo 1 } } throws Error::class
    }
}
