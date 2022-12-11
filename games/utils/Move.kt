package games.utils

data class Move(
    val dx: Int,
    val dy: Int,
)

val knightMoves = listOf(
    Move(-2, -1),
    Move(-1, -2),
    Move(-2, 1),
    Move(-1, 2),
    Move(2, -1),
    Move(1, -2),
    Move(2, 1),
    Move(1, 2),
)
