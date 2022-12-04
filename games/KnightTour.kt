package games

data class Location(
    val x: Int,
    val y: Int,
) {
    fun move(m: Move): Location = Location(x + m.dx, y + m.dy)

    override fun toString(): String = "($x, $y)"
}

data class Move(
    val dx: Int,
    val dy: Int,
)

private val movePatterns = listOf(
    Move(-2, -1),
    Move(-1, -2),
    Move(-2, 1),
    Move(-1, 2),
    Move(2, -1),
    Move(1, -2),
    Move(2, 1),
    Move(1, 2),
)

typealias Mirror = (Location, Location) -> Boolean

private val mirrorDefs: List<Mirror> = listOf(
    { l1, l2 -> l1.x == l2.x && l1.y == -l2.y },
    { l1, l2 -> l1.x == -l2.x && l1.y == l2.y },
    { l1, l2 -> l1.x == -l2.x && l1.y == -l2.y },
    { l1, l2 -> l1.x == l2.y && l1.y == l2.x },
    { l1, l2 -> l1.x == -l2.y && l1.y == l2.x },
    { l1, l2 -> l1.x == l2.y && l1.y == -l2.x },
    { l1, l2 -> l1.x == -l2.y && l1.y == -l2.x },
)

fun KnightTour(size: Int): KnightTour {
    val max = (size - 1) / 2
    val min = max - size + 1

    return KnightTour(
        size,
        listOf(Location(0, 0)),
        (min..max).map { x ->
            (min..max).map { y ->
                val loc = Location(x, y)
                loc to movePatterns.map { m -> loc.move(m) }
            }
        }.flatten().toMap().toMutableMap(),
    )
}

data class KnightTour(
    val size: Int,
    val history: List<Location>,
    val availabilities: MutableMap<Location, List<Location>>,
) {
    private val max = (size - 1) / 2
    private val min = max - size + 1

    val current = history.last()
    val done = history.size == size * size

    private val mirrors = mirrorDefs.filter { mir ->
        mir(current, current) && history.dropLastWhile { l1 -> history.find { l2 -> mir(l1, l2) } != null }.isEmpty()
    }

    private fun isValid(location: Location): Boolean =
        location.x in min..max && location.y in min..max && location !in history

    private fun move(location: Location) = copy(
        history = history + location,
        availabilities = availabilities.toMutableMap().apply { remove(current) },
    )

    fun candidatesFor(location: Location): List<Location> {
        val avail = (availabilities[location] ?: movePatterns.map { location.move(it) }).filter { isValid(it) }
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
