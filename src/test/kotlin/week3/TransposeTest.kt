package week3
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransposeTest {

    private val list = listOf(listOf(1, 2, 3), listOf(4, 5, 6))

    @Test
    fun `Transposition of transposition is identity`() {
        assertEquals(list, list.transpose().transpose())
    }

    @Test
    fun `Simple transposition test`() {
        val transposed = listOf(listOf(1, 4), listOf(2, 5), listOf(3, 6))
        assertEquals(transposed, list.transpose())
    }
}