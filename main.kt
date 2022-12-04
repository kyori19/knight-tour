import games.KnightTour
import games.Size
import games.size
import solvers.AStar
import solvers.DFS
import solvers.Solver
import solvers.WFS
import solvers.heuristics.DeadElimination
import solvers.heuristics.DeadElimination.Mode.*

fun test(size: Size, vararg solvers: Solver) {
    val game = KnightTour(size)
    solvers.forEach {
        println(it.solve(game))
    }
}

fun main() {
    println("1. N=5 with WFS, DFS, 3 A* heuristics")
    test(
        5.size,
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
        AStar(DeadElimination(SINGLE_ONE)),
        AStar(DeadElimination(ONE)),
    )
    println()

    println("3. Looped N=5")
    val game = KnightTour(5.size, true)
    println(AStar(DeadElimination(ONE)).solve(game))
}
