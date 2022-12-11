package solvers

import games.KnightTour

fun interface Heuristic {
    fun cost(game: KnightTour): Int?
}
