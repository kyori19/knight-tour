import games.KnightTour
import games.ReturningKnightTour
import games.utils.size
import solvers.AStar
import solvers.DFS
import solvers.Solver
import solvers.WFS
import solvers.heuristics.DeadElimination
import solvers.heuristics.RespectPossibility

fun test(vararg solvers: Solver, game: () -> KnightTour) {
    solvers.forEach {
        println(it.solve(game()))
    }
}

fun main() {
    println("1. N=5 with WFS, DFS and 2 A* heuristics")
    test(
        WFS,
        DFS,
        AStar(DeadElimination),
        AStar(RespectPossibility),
    ) { KnightTour(5.size) }
    println()

    println("2. N>5")
    test(
        AStar(DeadElimination),
        AStar(RespectPossibility),
    ) { KnightTour(6.size) }
    test(
        AStar(RespectPossibility),
    ) { KnightTour(7.size) }
    test(
        AStar(RespectPossibility),
    ) { KnightTour(8.size) }
    println()

    println("3. Looped N=5 with DFS and 2 A* heuristics")
    test(
        DFS,
        AStar(DeadElimination),
        AStar(RespectPossibility),
    ) { ReturningKnightTour(5.size) }
    println()
}
