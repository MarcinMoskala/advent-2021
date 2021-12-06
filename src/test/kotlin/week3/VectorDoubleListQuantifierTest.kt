package week3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VectorDoubleListQuantifierTest {

    @Test
    fun `When number of thresholds is the same as number of points then points are equal to quantified`() {
        val points = listOf(1.0, 3.0, 5.0, 7.0, 10.0).map { listOf(it) }
        val quantified = VectorDoubleListQuantifier.quantify(points, points.size)
        assertEquals(points, quantified)
    }
}