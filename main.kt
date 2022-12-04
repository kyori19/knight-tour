import games.KnightTour
import solvers.DFS
import solvers.WFS

fun main() {
    val game = KnightTour(5)
    val solvers = listOf(WFS, DFS)
    solvers.forEach {
        println(it.solve(game))
    }
}
