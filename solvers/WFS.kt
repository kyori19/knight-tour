package solvers

object WFS : Solver by AStar({ game -> -game.history.size })
