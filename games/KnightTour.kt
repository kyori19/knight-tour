package games

import games.utils.Location
import games.utils.Size
import games.utils.knightMoves
import games.utils.mirrorDefs

fun KnightTour(size: Size, mustReturn: Boolean = false): KnightTour =
    KnightTour(
        size,
        mustReturn,
        listOf(Location(size, 0, 0)),
        size.range.map { x ->
            size.range.map { y ->
                val loc = Location(size, x, y)
                loc to knightMoves.map { m -> loc.move(m, mustReturn) }
            }
        }.flatten().toMap().toMutableMap(),
    )

data class KnightTour(
    val size: Size,
    val mustReturn: Boolean,
    val history: List<Location>,
    val availabilities: MutableMap<Location, List<Location>>,
) {
    val current = history.last()
    val done = history.size == size.size * size.size && (!mustReturn || knightMoves.find { m -> current.move(m, true) == history.first() } != null)

    private val mirrors = mirrorDefs.filter { mir ->
        mir(current, current) && history.dropLastWhile { l1 -> history.find { l2 -> mir(l1, l2) } != null }.isEmpty()
    }

    private fun isValid(location: Location): Boolean = location.isValid() && location !in history

    private fun move(location: Location) = copy(
        history = history + location,
        availabilities = availabilities.toMutableMap().apply { remove(current) },
    )

    fun candidatesFor(location: Location): List<Location> {
        val avail = (availabilities[location] ?: knightMoves.map { location.move(it, mustReturn) }).filter { isValid(it) }
        availabilities[location] = avail
        return avail
    }

    fun candidates(): List<KnightTour> = candidatesFor(current)
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
}
