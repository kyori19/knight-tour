package solvers

import games.KnightTour

fun interface Solver {
    fun solve(initial: KnightTour): Pair<Int, KnightTour>
}
