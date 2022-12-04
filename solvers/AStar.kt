package solvers

import games.KnightTour
import java.util.PriorityQueue

class AStar(
    private val costFn: (KnightTour) -> Int,
) : Solver {
    override fun solve(initial: KnightTour): Pair<Int, KnightTour> {
        var count = 0
        val queue = PriorityQueue<KnightTour> { o1, o2 ->
            costFn(o2) - costFn(o1)
        }
        queue += initial

        while (queue.isNotEmpty()) {
            val game = queue.poll()
            count += 1
            game.candidates().forEach { next ->
                if (next.done) {
                    count += 1
                    return count to next
                }

                queue += next
            }
        }

        error("solution not found")
    }
}
