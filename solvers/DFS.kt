package solvers

object DFS : Solver by AStar({ game -> game.history.size })
