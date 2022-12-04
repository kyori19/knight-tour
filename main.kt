import games.KnightTour
import solvers.AStar
import solvers.DFS
import solvers.Solver
import solvers.WFS
import solvers.heuristics.DeadElimination
import solvers.heuristics.DeadElimination.Mode.*

fun test(size: Int, vararg solvers: Solver) {
    val game = KnightTour(size)
    solvers.forEach {
        println(it.solve(game))
    }
}

fun main() {
    test(
        5,
        WFS,
        DFS,
        AStar(DeadElimination(ZERO)),
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )

    test(
        6,
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )
}
