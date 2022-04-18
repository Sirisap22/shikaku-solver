package shikaku

object TestHeuristic {
  def main(args: Array[String]) = {
    val testMaps = Vector(firstMap(), secondMap(), thirdMap(), fourthMap(), fifthMap(), sixthMap())
    testMaps.foreach((mapMetaData) => {
      val (numberOfRow, numberOfCol, map) = mapMetaData
      val clues: Vector[Clue] = for (ele <- map) yield Clue(Coord(ele._1 , ele._2), ele._3)
      val depthSolver = new Solver(numberOfRow, numberOfCol, clues, new DepthFirstSearchStrategy())
      println("Depth First Search")
      depthSolver.timeIt(1)
      Utils.memory()
      println()

      val breathSolver = new Solver(numberOfRow, numberOfCol, clues, new BreathFirstSearchStrategy())
      println("Breath First Search")
      breathSolver.timeIt(1)
      Utils.memory()
      println()

      val heuristicSolver = new Solver(numberOfRow, numberOfCol, clues, new SimpleHeuristicSearchStrategy())
      println("Greedy Best-first Search")
      heuristicSolver.timeIt(1)
      Utils.memory()
      println()
    })
  }

  def firstMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
    (10, 10, Vector(
      (0, 0, 9),
      (4, 0, 12),
      (7, 0, 5),
      (9, 2, 6),
      (4, 3, 8),
      (0, 3, 8),
      (2, 3, 6),
      (0, 7, 4),
      (2, 9, 3),
      (5, 9, 9),
      (9, 9, 4),
      (9, 6, 12),
      (7, 6, 8),
      (5, 6, 6)
    ))
  }

  def secondMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
    (10, 10, Vector(
      (0, 0, 7),
      (9, 0, 8),
      (0, 9, 6),
      (9, 9, 8),
      (1, 2, 5),
      (1, 7, 3),
      (8, 2, 4),
      (8, 7, 6),
      (4, 8, 4),
      (5, 8, 2),
      (4, 1, 9),
      (5, 1, 5),
      (3, 3, 6),
      (6, 3, 8),
      (3, 6, 10),
      (6, 6, 9)
    ))
  }

  def thirdMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
    (10, 10, Vector(
      (3, 0, 4),
      (2, 1, 2),
      (4, 1, 2),
      (7, 1, 3),
      (6, 2, 3),
      (8, 2, 4),
      (1, 2, 4),
      (3, 2, 3),
      (2, 3, 4),
      (7, 3, 4),
      (9, 3, 10),
      (4, 4, 2),
      (5, 4, 5),
      (4, 5, 2),
      (5, 5, 4),
      (8, 4, 3),
      (1, 5, 4),
      (0, 6, 4),
      (2, 6, 3),
      (1, 7, 6),
      (3, 7, 3),
      (2, 8, 4),
      (7, 6, 2),
      (8, 7, 2),
      (6, 7, 3),
      (7, 8, 4),
      (5, 8, 3),
      (6, 9, 3)
    ))
  }

  def fourthMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
   (10, 18, Vector(
      (0, 4, 5),
      (0, 5, 3),
      (1, 4, 4),
      (1, 5, 6),
      (3, 1, 3),
      (3, 2, 3),
      (4, 1, 2),
      (4, 2, 8),
      (8, 0, 8),
      (9, 0, 9),
      (6, 2, 2),
      (7, 2, 4),
      (6, 3, 3),
      (7, 3, 9),
      (10, 2, 4),
      (11, 2, 2),
      (10, 3, 2),
      (11, 3, 2),
      (13, 1, 2),
      (13, 2, 6),
      (14, 1, 4),
      (14, 2, 3),
      (16, 4, 2),
      (17, 4, 3),
      (16, 5, 4),
      (17, 5, 5),
      (6, 6, 2),
      (7, 6, 3),
      (6, 7, 2),
      (7, 7, 2),
      (10, 6, 2),
      (11, 6, 2),
      (11, 7, 2),
      (10, 7, 4),
      (3, 7, 5),
      (4, 7, 2),
      (3, 8, 4),
      (4, 8, 5),
      (8, 9, 9),
      (9, 9, 8),
      (13, 7, 6),
      (14, 7, 10),
      (13, 8, 2),
      (14, 8, 2)
    ))
  }

  def fifthMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
    (10, 18, Vector(
      (1, 0, 5),
      (1, 1, 3),
      (3, 1, 2),
      (3, 2, 4),
      (5, 2, 9),
      (5, 3, 4),
      (7, 3, 2),
      (7, 4, 6),
      (1, 5, 3),
      (2, 5, 8),
      (1, 6, 4),
      (2, 6, 4),
      (0, 9, 8),
      (4, 7, 4),
      (4, 8, 4),
      (5, 7, 2),
      (5, 8, 2),
      (7, 9, 6),
      (8, 9, 8),
      (9, 0, 10),
      (10, 0, 4),
      (10, 5, 3),
      (10, 6, 4),
      (12, 6, 4),
      (12, 7, 6),
      (14, 7, 6),
      (14, 8, 6),
      (16, 8, 6),
      (16, 9, 2),
      (17, 0, 4),
      (12, 1, 2),
      (13, 1, 2),
      (12, 2, 9),
      (13, 2, 6),
      (15, 3, 6),
      (16, 3, 6),
      (15, 4, 2),
      (16, 4, 4)
    ))
  }

  def sixthMap(): (Int, Int, Vector[(Int, Int, Int)]) = {
    (10, 18, Vector(
      (0, 0, 4),
      (6, 0, 9),
      (8, 0, 6),
      (7, 1, 6),
      (9, 1, 9),
      (16, 0, 4),
      (17, 1, 4),
      (14, 1, 6),
      (15, 2, 2),
      (12, 2, 4),
      (13, 3, 8),
      (6, 3, 4),
      (3, 2, 4),
      (1, 2, 6),
      (2, 3, 4),
      (1, 4, 3),
      (7, 4, 8),
      (9, 4, 4),
      (8, 5, 6),
      (10, 5, 4),
      (11, 6, 6),
      (16, 5, 3),
      (15, 6, 4),
      (16, 7, 6),
      (14, 7, 6),
      (17, 9, 6),
      (10, 8, 4),
      (11, 9, 2),
      (8, 8, 4),
      (9, 9, 6),
      (0, 8, 6),
      (1, 9, 2),
      (2, 7, 3),
      (3, 8, 4),
      (5, 7, 4),
      (4, 6, 9)
    ))
  }
}