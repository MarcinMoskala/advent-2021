package week3

import kotlin.random.Random

data class Point(val x: Double, val y: Double)

open class KMeansPointsSolver : KMeansSolver<Point>(
    createInitialDictionary = ::createRandomPoint,
    calculateAveragePosition = ::calculateMean,
    distanceBetween = ::distanceBetweenTwoPoints
)

private fun createRandomPoint(points: List<Point>, meansNumber: Int): List<Point> {
    val xs = points.map { it.x }
    val ys = points.map { it.y }
    return List(meansNumber) {
        Point(
            Random.nextDouble() * (xs.maxOrNull()!! - xs.minOrNull()!!),
            Random.nextDouble() * (ys.maxOrNull()!! - ys.minOrNull()!!)
        )
    }
}

private fun calculateMean(points: List<Point>): Point =
    Point(points.map { it.x }.average(), points.map { it.y }.average())

private fun distanceBetweenTwoPoints(p1: Point, p2: Point): Double =
    Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y))
