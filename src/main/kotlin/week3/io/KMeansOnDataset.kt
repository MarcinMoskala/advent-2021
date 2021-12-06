package week3.io

import week3.KMeansPointsSolver
import week3.Point
import java.io.File

// Data from https://raw.githubusercontent.com/datascienceinc/learn-data-science/master/Introduction-to-K-means-Clustering/Data/data_1024.csv
fun main(args: Array<String>) {
    showGroupedDataSet()
}

fun showGroupedDataSet() {
    val points = readData()
    val maxX = points.maxOf { it.x }
    val maxY = points.maxOf { it.y }
    val solver = KMeansPointsSolver()
    val means = solver.solve(points, maxAvgError = 11.0)
    val pp: PointPanel = makePointMonitor(maxX, maxY, 1400.0, 800.0)
    val grouped = solver.grouped(points, means)
    pp.setPoints(grouped)
}

private fun readData(): List<Point> = File("data.csv")
    .readLines()
    .drop(1)
    .map { it.split("\t", limit = 3).drop(1).map { it.toDouble() } }
    .map { (f, s) -> Point(f, s) }