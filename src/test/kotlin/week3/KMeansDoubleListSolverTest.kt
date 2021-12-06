package week3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KMeansDoubleListSolverTest {

    val points = listOf(
        listOf(0.0, 0.0),
        listOf(0.0, 1.0),
        listOf(1.0, 0.0),
        listOf(1.0, 1.0)
    )

    @Test
    fun `When we have the same number of means as points, every mean equals to point`() = repeat(10) {
        assertEquals(points, KMeansDoubleListSolver.solve(points, 4))
    }

    @Test
    fun `Single mean should finish in the middle`() = repeat(10) {
        assertEquals(listOf(listOf(0.5, 0.5)), KMeansDoubleListSolver.solve(points, 1))
    }
}