package solvers

import games.KnightTour

interface Solver {
    fun solve(initial: KnightTour): Pair<Int, KnightTour>
}
