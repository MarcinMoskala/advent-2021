package week3

import kotlin.math.abs

object VectorDoubleListQuantifier : KMeansSolver<List<Double>>(
    distanceBetween = ::distanceBetween,
    calculateAveragePosition = ::calculateMean,
    createInitialDictionary = ::createInitialMeans
) {
    fun quantify(
        points: List<List<Double>>,
        dictSize: Int,
        threshold: Double = 0.0001
    ): List<List<Double>> {
        val dictionary = solve(points, dictSize, threshold)
        return points.map { p -> closestMean(p, dictionary) }
    }
}

private fun distanceBetween(p1: List<Double>, p2: List<Double>): Double {
    return (p1 zip p2).sumOf { (c1, c2) -> abs(c1 - c2) }
}

private fun calculateMean(points: List<List<Double>>): List<Double> {
    return points.transpose().map { it.average() }
}

private fun createInitialMeans(points: List<List<Double>>, meansNumber: Int): List<List<Double>> {
    val centroid = calculateMean(points)
    return (1..meansNumber).map { it.toDouble() * 2 / meansNumber }.map { times(centroid, it) }
}

fun times(point: List<Double>, n: Double): List<Double> {
    return point.map { it * n }
}