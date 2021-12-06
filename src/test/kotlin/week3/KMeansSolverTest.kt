package week3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class KMeansSolverTests {

    @Test
    fun `For two separate groups of points, two means end up in the middle of them`() {
        assertEquals(listOf(1.0, 6.0), DoublesKMeansSolver.solve(listOf(0.0, 2.0, 5.0, 7.0), 2))
        assertEquals(listOf(2.0, 6.0), DoublesKMeansSolver.solve(listOf(1.0, 2.0, 3.0, 5.0, 6.0, 7.0), 2))
        assertEquals(listOf(2.0, 6.0), DoublesKMeansSolver.solve(listOf(1.0, 2.0, 3.0, 6.0), 2))
    }

    @Test
    fun `Single mean is equal to average`() {
        val doubles = listOf(0.0, 2.0, 5.0, 7.0, 1.0)
        assertEquals(doubles.average(), DoublesKMeansSolver.solve(doubles, 1).first())
    }

    @Test
    fun `For the same amount of averages and points, they are equal`() {
        val doubles = listOf(1.0, 3.0, 5.0, 7.0, 10.0)
        assertEquals(doubles, DoublesKMeansSolver.solve(doubles, doubles.size))
    }

    companion object {
        val distanceTo = { d1: Double, d2: Double -> (d1 - d2) * (d1 - d2) }

        object DoublesKMeansSolver : KMeansSolver<Double>(
            createInitialDictionary = { list, num -> List(num) { Random.nextDouble() * (list.maxOrNull()!! - list.minOrNull()!!) } },
            distanceBetween = distanceTo,
            calculateAveragePosition = { list -> list.average() }
        )
    }
}