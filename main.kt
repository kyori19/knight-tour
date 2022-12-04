import games.KnightTour
import games.Size
import games.size
import solvers.AStar
import solvers.DFS
import solvers.Solver
import solvers.WFS
import solvers.heuristics.DeadElimination
import solvers.heuristics.DeadElimination.Mode.*

fun test(size: Size, mustReturn: Boolean, vararg solvers: Solver) {
    val game = KnightTour(size, mustReturn)
    solvers.forEach {
        println(it.solve(game))
    }
}

fun main() {
    println("1. N=5 with WFS, DFS and 3 A* heuristics")
    test(
        5.size,
        false,
        WFS,
        DFS,
        AStar(DeadElimination(ZERO)),
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )
    println()

    println("2. N>5")
    test(
        6.size,
        false,
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )
    println()

    println("3. Looped N=5 with DFS and 3 A* heuristics")
    test(
        5.size,
        true,
        DFS,
        AStar(DeadElimination(ZERO)),
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )
    println()
}
