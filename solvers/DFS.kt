package solvers

import games.KnightTour

object DFS : Solver {
    override fun solve(initial: KnightTour): Pair<Int, KnightTour> {
        return nullableSolve(initial).takeIf { it.second != null }?.run { first to second!! }
            ?: error("solution not found")
    }

    private fun nullableSolve(game: KnightTour): Pair<Int, KnightTour?> {
        var count = 0
        game.candidates().forEach {
            count += 1
            if (it.done) {
                return count to it
            }

            val (c, r) = nullableSolve(it)
            count += c
            if (r != null) {
                return count to r
            }
        }
        return count to null
    }
}