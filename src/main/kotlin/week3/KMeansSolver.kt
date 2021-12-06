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
    ): List<P> = TODO()

    // Calculates the error of the dataset, that is the sum of distances to the closest dictionary points
    protected open fun calculateError(points: List<P>, means: List<P>): Double = TODO()

    // Calculates the next positions of the dictionary points
    protected open fun nextMeans(points: List<P>, means: List<P>): List<P> = TODO()

    // Group points by the dictionary points representing categories
    fun grouped(dictionary: List<P>, means: List<P>): Map<P, List<P>> = TODO()

    protected fun closestMean(point: P, means: List<P>): P =
        means.minByOrNull { mean -> distanceBetween(point, mean) }!!
}