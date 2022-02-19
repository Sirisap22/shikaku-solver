package shikaku

object Main {
  def main(params: Array[String]): Unit = {
    val input = Vector(
                      (3, 0, 4),
                      (1, 1, 2),
                      (2, 1, 2),
                      (4, 1, 2),
                      (1, 2, 4),
                      (3, 2, 2),
                      (0, 3, 2),
                      (3, 3, 2),
                      (1, 4, 2),
                      (4, 4, 3)
                    )
    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val depthSolver = new Solver(5, 5, clues, new DepthFirstSearchStrategy())
    val breathSolver = new Solver(5, 5, clues, new BreathFirstSearchStrategy())

    depthSolver.timeIt(100)
    breathSolver.timeIt(100)

  }
}
