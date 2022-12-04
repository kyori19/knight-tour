package solvers

import games.KnightTour

object WFS : Solver {
    override fun solve(initial: KnightTour): Pair<Int, KnightTour> {
        var count = 0
        val queue = ArrayDeque<KnightTour>()
        queue += initial

        queue.forEach { game ->
            count += 1
            game.candidates().forEach { next ->
                if (next.done) {
                    count += 1
                    return count to next
                } else {
                    queue += next
                }
            }
        }

        error("solution not found")
    }
}
