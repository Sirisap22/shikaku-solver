package shikaku

class DepthLimitSearchStrategy extends SolveStrategy {
  def solve(numberOfRows: Int, numberOfCols: Int, clues: Vector[(Coord, Int)]): Vector[(Coord, Coord)] = {
    Vector((Coord(1, 2), Coord(1, 2)))
  }


}

object DepthLimitSearchStrategy {
  def main(args: Array[String]) {
    val input = Vector(
                      (0, 3, 2),
                      (1, 0, 3),
                      (2, 2, 4),
                      (2, 3, 4),
                      (3, 0, 2),
                      (3, 1, 4),
                      (3, 4, 2),
                      (4, 1, 4)
                    )
    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._2 , ele._1), ele._3)
    val dp = new DepthLimitSearchStrategy()

    // test squareShapeCombinations
    // println(dp.squareShapeCombinations(3)) 
    // println(dp.squareShapesCache(3))
    // println(dp.squareShapeCombinations(4))
    // println(dp.squareShapeCombinations(3))

    // test squareCombinations
    // val shapes = dp.squareShapeCombinations(4)
    // val square = dp.squareCombinations(Array.ofDim[Int](5, 5), Coord(2, 2), shapes)
    // println(square.size)
   
    // test possibleSquareCombinations
    val shapes = dp.squareShapeCombinations(4)
    // val possibleSquares = dp.possibleSquareCombinations(Array.ofDim[Int](5, 5), Coord(2, 2), shapes, clues) // clues & boundary check
    val state = Array.ofDim[Int](5, 5)
    state(1)(1) = 1
    val possibleSquares = dp.possibleSquareCombinations(state, Coord(2, 2), shapes, clues) // placed check
    possibleSquares.foreach(println)
  }
}
