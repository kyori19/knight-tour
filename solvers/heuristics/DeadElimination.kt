package solvers.heuristics

import games.KnightTour
import games.Location
import solvers.Heuristic

object DeadElimination : Heuristic {
    override fun cost(game: KnightTour): Int =
        if (game.isDead()) { -1 } else { game.history.size }

    private fun KnightTour.isDead(): Boolean {
        (min..max).forEach { x ->
            (min..max).forEach eachLoc@ { y ->
                val location = Location(x, y)

                if (history.contains(location)) {
                    return@eachLoc
                }

                if (candidatesFor(location).isEmpty()) {
                    return true
                }
            }
        }

        return false
    }
}
