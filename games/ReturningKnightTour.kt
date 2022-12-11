package games

import games.utils.Location
import games.utils.Move
import games.utils.Size
import games.utils.knightMoves

class ReturningKnightTour(
    size: Size,
    history: List<Location> = listOf(Location(size, 0, 0)),
    makeAvailabilities: (KnightTour).() -> List<Pair<Location, Availabilities>> = {
        size.range.map { x ->
            size.range.map { y ->
                val loc = Location(size, x, y)
                loc to Availabilities(
                    0,
                    knightMoves.map { m -> loc.move(m) },
                )
            }
        }.flatten()
    },
) : KnightTour(size, history, makeAvailabilities) {
    override val done = super.done && knightMoves.find { m ->
        current.move(m) == history.first()
    } != null

    override fun Location.move(m: Move) = move(m, true)

    override fun move(location: Location) = ReturningKnightTour(
        size = size,
        history = history + location,
        makeAvailabilities = make@{
            this@ReturningKnightTour.availabilities.filterKeys { it != this@ReturningKnightTour.current }
                .map { (loc, a) -> loc to this@make.Availabilities(a.cacheDepth, a._locations) }
        },
    )
}
