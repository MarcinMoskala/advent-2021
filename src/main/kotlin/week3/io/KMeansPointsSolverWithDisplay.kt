package week3.io

import week3.KMeansPointsSolver
import week3.Point
import java.awt.Color
import java.awt.Color.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.random.Random

class KMeansPointsSolverWithDisplay : KMeansPointsSolver() {
    private val pp: PointPanel = makePointMonitor(20.0, 20.0, 800.0, 800.0)

    override fun nextMeans(points: List<Point>, means: List<Point>): List<Point> {
        pp.setPoints(grouped(points, means))
        return super.nextMeans(points, means)
    }

    override fun calculateError(points: List<Point>, means: List<Point>): Double {
        Thread.sleep(300)
        return super.calculateError(points, means)
    }
}

fun makePointMonitor(maxX: Double, maxY: Double, sizeX: Double, sizeY: Double): PointPanel {
    val pp = PointPanel(sizeX / maxX, sizeY / maxY)
    JFrame().apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.add(pp)
        setSize(sizeX.toInt(), sizeY.toInt())
        setLocation(100, 100)
        isVisible = true
    }
    return pp
}

class PointPanel(val scaleX: Double, val scaleY: Double) : JPanel() {
    private var points: Map<Point, Color> = mapOf()
    private var means: Map<Point, Color> = mapOf()
    private val colors = listOf(
        blue,
        green,
        red,
        yellow,
        magenta,
        black,
        cyan,
        lightGray,
        Color(30, 144, 255),
        Color(106, 90, 205),
        Color(238, 130, 238),
        Color(255, 235, 205),
        Color(210, 105, 30),
        Color(102, 205, 170),
        Color(0, 255, 0),
        Color(218, 165, 32),
        Color(255, 215, 0),
        Color(255, 160, 122),
        Color(240, 230, 140),
        Color(240, 230, 140)
    )

    init {
        background = white
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        for ((p, c) in points) {
            g2.paint = c
            g2.fill(Ellipse2D.Double(p.x * scaleX, p.y * scaleY, 6.0, 6.0))
        }
        for ((p, c) in means) {
            g2.paint = c
            g2.fill(Ellipse2D.Double(p.x * scaleX, p.y * scaleY, 12.0, 12.0))
        }
    }

    fun setPoints(pointsByMeans: Map<Point, List<Point>>) {
        means = (pointsByMeans.keys zip colors).toMap()
        points = (pointsByMeans.values zip colors).flatMap { (p, c) -> p.map { it to c } }.toMap()
        repaint()
    }
}


private val points = List(50) { Point(Random.nextDouble() * 20, Random.nextDouble() * 20) }

fun showPresentation() {
    KMeansPointsSolverWithDisplay().solve(points, maxAvgError = 2.0)
}

fun main(args: Array<String>) {
    showPresentation()
}

