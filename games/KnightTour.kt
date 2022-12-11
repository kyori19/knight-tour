package games

import games.utils.*

open class KnightTour(
    val size: Size,
    val history: List<Location> = listOf(Location(size, 0, 0)),
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
) {
    val depth = history.size
    val current = history.last()
    open val done = depth == size.count

    val availabilities = makeAvailabilities().toMap()

    open fun Location.move(m: Move) = move(m, false)

    private val mirrors = mirrorDefs.filter { mir ->
        mir(current, current) && history.dropLastWhile { l1 -> history.find { l2 -> mir(l1, l2) } != null }.isEmpty()
    }

    private fun isValid(location: Location): Boolean = location.isValid() && location !in history

    open fun move(location: Location) = KnightTour(
        size = size,
        history = history + location,
        makeAvailabilities = make@{
            this@KnightTour.availabilities.filterKeys { it != this@KnightTour.current }
                .map { (loc, a) -> loc to this@make.Availabilities(a.cacheDepth, a._locations) }
        },
    )

    fun candidates(): List<KnightTour> = availabilities[current]!!.locations
        .fold(listOf<Location>()) { cur, l1 ->
            mirrors.forEach { mir ->
                cur.forEach { l2 ->
                    if (mir(l1, l2)) {
                        return@fold cur
                    }
                }
            }

            return@fold cur + l1
        }
        .map { move(it) }

    override fun toString(): String = "KnightTour[$size]($history)"

    inner class Availabilities(
        var cacheDepth: Int,
        var _locations: List<Location>,
    ) {
        val locations: List<Location>
            get() {
                assert(cacheDepth <= depth)

                if (cacheDepth < depth) {
                    update()
                }

                return _locations
            }

        private fun update() {
            cacheDepth = depth
            _locations = _locations.filter { isValid(it) }
        }

        override fun toString(): String = "${
            if (cacheDepth < depth) {
                "outdated "
            } else {
                ""
            }
        }(${_locations.size}) $_locations"
    }
}
