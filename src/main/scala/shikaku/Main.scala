package shikaku

object Main {
  def main(params: Array[String]): Unit = {
    val input = Vector(
                      (1, 0, 7),
                      (1, 1, 4),
                      (5, 1, 4),
                      (0, 2, 5),
                      (4, 2, 3),
                      (6, 2, 4),
                      (3, 3, 3),
                      (5, 3, 2),
                      (2, 4, 3),
                      (5, 4, 2),
                      (2, 5, 2),
                      (5, 5, 2),
                      (6, 5, 2),
                      (1, 6, 2),
                      (2, 6, 2),
                      (5, 6, 2)
                    )
    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val depthSolver = new Solver(7, 7, clues, new DepthFirstSearchStrategy())
    val breathSolver = new Solver(7, 7, clues, new BreathFirstSearchStrategy())

    depthSolver.timeIt(100)
    breathSolver.timeIt(100)
    // Utils.memory()

  }
}
