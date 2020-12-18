package se.lovef.assert.v1

import org.junit.Test
import se.lovef.assert.throws

/**
 * Date: 2017-12-03
 * @author Love
 */
class CheckUtilTest {

    @Test fun `basic check`() {
        1 passes check { it shouldEqual 1 };
        { 1 passes check { it shouldEqual 2 } } throws Error::class
    }

    @Test fun `'passes' returns checked object`() {
        val a = Any()
        a passes check { } shouldBe a
    }

    @Test fun `check on nullable`() {
        null passes check { it shouldEqual null };
        { null passes check { it shouldEqual 1 } } throws Error::class
    }
}
