package week3

open class KMeansSolver<P : Any>(
    val createInitialDictionary: (points: List<P>, meansNumber: Int) -> List<P>,
    val distanceBetween: (P, P) -> Double,
    val calculateAveragePosition: (points: List<P>) -> P
) {
    fun solve(
        points: List<P>,
        maxAvgError: Double
    ): List<P> {
        var meansNum = 2
        while (true) {
            val means = solve(points, meansNum)
            val error = calculateError(points, means)
            if (error / points.size < maxAvgError) return means
            meansNum++
        }
    }

    /**
     * Solves k-means problem.
     *
     * The implementation should :
     * 1. Find the initial dictionary with `createInitialMeans`.
     * 2. Group points by the current state of our dictionary.
     * 3. For each group, find the new mean position of the element in
     * dictionary representing them.
     * 4. If the new error is better then at the previous iteration
     * (by more than `threshold`), go back to the step 2.
     * Otherwise return the final dictionary.
     *
     * @param points the points in our dataset.
     * @param dictSize how many elements do we expect in our dictionary (the number of means).
     * @param threshold the expected minimal improvement with each iteration.
     */
    fun solve(
        points: List<P>,
        dictSize: Int,
        threshold: Double = 0.0001
    ): List<P> {
        var means = createInitialDictionary(points, dictSize)
        var prevError: Double = calculateError(points, means)
        while (true) {
            means = nextMeans(points, means)
            val error = calculateError(points, means)
            if (error + threshold >= prevError) return means
            prevError = error
        }
    }

    protected open fun calculateError(points: List<P>, means: List<P>): Double {
        val closestMean = points.map { p -> closestMean(p, means) }
        val error = (points zip closestMean).sumOf { (p, q) -> distanceBetween(p, q) }
        return error
    }

    protected open fun nextMeans(points: List<P>, means: List<P>): List<P> {
        val grouped = grouped(points, means)
        val newMeans = grouped.map { (_, group) -> calculateAveragePosition(group) }
        val meansWithoutPoints: List<P> = (means elementMinus grouped.keys)
        return newMeans + meansWithoutPoints.moveToClosestPointUntaken(points, newMeans)
    }

    fun grouped(points: List<P>, means: List<P>) =
        points.groupBy { point -> closestMean(point, means) }

    protected fun closestMean(point: P, means: List<P>): P =
        means.minByOrNull { mean -> distanceBetween(point, mean) }!!

    // Improvement: For mean without points, move to closest untaken point
    private fun List<P>.moveToClosestPointUntaken(points: List<P>, newMeans: List<P>): List<P> {
        val untakenPoints = points - newMeans
        return map { m -> untakenPoints.minByOrNull { p -> distanceBetween(p, m) }!! }
    }
}

private infix fun <P> List<P>.elementMinus(another: Iterable<P>): List<P> {
    val list = toMutableList()
    for (e in another) list.remove(e)
    return list
}