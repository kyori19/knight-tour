package solvers.heuristics

import games.KnightTour
import games.ReturningKnightTour
import games.utils.knightMoves
import solvers.Heuristic

object DeadElimination : Heuristic {
    override fun cost(game: KnightTour): Int? =
        if (game.isDead()) {
            null
        } else {
            game.history.size
        }

    private fun KnightTour.isDead(): Boolean {
        if (availabilities.size < 3) {
            return false
        }

        availabilities.forEach { (_, a) ->
            if (a.locations.isEmpty()) {
                return true
            }
        }

        if (this is ReturningKnightTour) {
            if (knightMoves.find { m -> history.first().move(m) !in history } == null) {
                return true
            }
        }

        return false
    }
}
