package games.utils

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
