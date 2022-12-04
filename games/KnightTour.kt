package games

data class Size(
    val size: Int,
) {
    val max = (size - 1) / 2
    val min = max - size + 1
    val range = min..max

    override fun toString() = size.toString()
}

val Int.size: Size
    get() = Size(this)

data class Location(
    val size: Size,
    val x: Int,
    val y: Int,
) {
    fun isValid(): Boolean = x in size.range && y in size.range

    fun move(m: Move, loopX: Boolean): Location {
        var nx = x + m.dx
        if (loopX && nx < size.min) {
            nx += size.size
        }
        if (loopX && nx > size.max) {
            nx -= size.size
        }
        return copy(x = nx, y = y + m.dy)
    }

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

fun KnightTour(size: Size, mustReturn: Boolean = false): KnightTour =
    KnightTour(
        size,
        mustReturn,
        listOf(Location(size, 0, 0)),
        size.range.map { x ->
            size.range.map { y ->
                val loc = Location(size, x, y)
                loc to movePatterns.map { m -> loc.move(m, mustReturn) }
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
    val done = history.size == size.size * size.size && (!mustReturn || movePatterns.find { m -> current.move(m, true) == Location(size, 0, 0) } != null)

    private val mirrors = mirrorDefs.filter { mir ->
        mir(current, current) && history.dropLastWhile { l1 -> history.find { l2 -> mir(l1, l2) } != null }.isEmpty()
    }

    private fun isValid(location: Location): Boolean = location.isValid() && location !in history

    private fun move(location: Location) = copy(
        history = history + location,
        availabilities = availabilities.toMutableMap().apply { remove(current) },
    )

    fun candidatesFor(location: Location): List<Location> {
        val avail = (availabilities[location] ?: movePatterns.map { location.move(it, mustReturn) }).filter { isValid(it) }
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
