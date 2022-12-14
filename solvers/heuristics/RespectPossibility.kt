package solvers.heuristics

import games.KnightTour
import solvers.Heuristic

object RespectPossibility : Heuristic {
    override fun cost(game: KnightTour) =
        8 * game.history.size * game.size.count + game.availabilities.values.sumOf { it.locations.size }
}
