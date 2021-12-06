package week3

import kotlin.random.Random

object KMeansDoubleListSolver : KMeansSolver<List<Double>>(
    createInitialDictionary = ::createRandomPoint,
    calculateAveragePosition = ::calculateMean,
    distanceBetween = ::distanceBetweenPoints
)

private fun createRandomPoint(points: List<List<Double>>, meansNumber: Int): List<List<Double>> =
    List(meansNumber) {
        points.transpose().map { Random.nextDouble() * (it.maxOrNull()!! - it.minOrNull()!!) }
    }

private fun calculateMean(points: List<List<Double>>): List<Double> =
    points.transpose().map { it.average() }

private fun distanceBetweenPoints(p1: List<Double>, p2: List<Double>) =
    (p1 zip p2).sumOf { (c1, c2) -> Math.abs(c1 - c2) }