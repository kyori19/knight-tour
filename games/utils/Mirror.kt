package games.utils

typealias Mirror = (Location, Location) -> Boolean

val mirrorDefs: List<Mirror> = listOf(
    { l1, l2 -> l1.x == l2.x && l1.y == -l2.y },
    { l1, l2 -> l1.x == -l2.x && l1.y == l2.y },
    { l1, l2 -> l1.x == -l2.x && l1.y == -l2.y },
    { l1, l2 -> l1.x == l2.y && l1.y == l2.x },
    { l1, l2 -> l1.x == -l2.y && l1.y == l2.x },
    { l1, l2 -> l1.x == l2.y && l1.y == -l2.x },
    { l1, l2 -> l1.x == -l2.y && l1.y == -l2.x },
)
