import games.KnightTour
import solvers.AStar
import solvers.DFS
import solvers.WFS
import solvers.heuristics.DeadElimination

fun main() {
    val game = KnightTour(5)
    val solvers = listOf(WFS, DFS, AStar(DeadElimination))
    solvers.forEach {
        println(it.solve(game))
    }
}
