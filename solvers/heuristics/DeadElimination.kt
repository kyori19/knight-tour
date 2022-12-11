package solvers.heuristics

import games.KnightTour
import games.utils.knightMoves
import solvers.Heuristic

class DeadElimination(
    private val mode: Mode,
) : Heuristic {
    enum class Mode {
        ZERO,
        ONE,
        SINGLE_ONE,
    }

    override fun cost(game: KnightTour): Int =
        if (game.isDead()) {
            -1
        } else {
            game.history.size
        }

    private fun KnightTour.isDead(): Boolean {
        when (mode) {
            Mode.ZERO -> {
                availabilities.keys.forEach { loc ->
                    if (candidatesFor(loc).isEmpty()) {
                        return true
                    }
                }
            }

            Mode.ONE -> {
                availabilities.keys.forEach { loc ->
                    if (loc != current && candidatesFor(loc).size < 2) {
                        return true
                    }
                }
            }

            Mode.SINGLE_ONE -> {
                if (availabilities.keys.count { loc -> candidatesFor(loc).size < 2 } > 1) {
                    return true
                }
            }
        }

        if (mustReturn) {
            if (knightMoves.find { m -> history.first().move(m, true) !in history } != null) {
                return true
            }
        }

        return false
    }
}
