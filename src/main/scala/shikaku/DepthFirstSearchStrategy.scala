package shikaku

import scala.collection.mutable.Stack

class DepthFirstSearchStrategy extends SolveStrategy {
  def solve(numberOfRows: Int, numberOfCols: Int, clues: Vector[Clue]): Vector[Square] = {
    val limit = clues.size
    // TODO Change current state in search space
    // Space = board game, Clue = current clue, Square = block that place the round[state]
    val stack = new Stack[(Space, Int, Vector[Square])]()

    val initialState = Array.ofDim[Int](numberOfRows, numberOfCols)
    val initialClueIdx = 0
    val placedSquares = Vector[Square]()

    stack.push((initialState, initialClueIdx, placedSquares))

    while (!stack.isEmpty) {
      val (currentState, currentClueIdx, currentPlacedSquares) = stack.pop()
      // println("----------------------")
      // currentState.map(_.mkString(" ")).foreach(println)
      // println(s"Stack $currentState, $currentClueIdx, $currentPlacedSquares")

      // check if goalstate
      if (this.isGoalState(currentState)) {
        return currentPlacedSquares
      }

      // if (currentClueIdx < limit) {
      val currentClue = clues(currentClueIdx)

      // go next state
      // gen childs
      val shapes = this.squareShapeCombinations(currentClue.size)
      val possibleSquares = this.possibleSquareCombinations(currentState, currentClue.position, shapes, clues)
      possibleSquares.foreach((square) => {
        stack.push((this.placeSquare(currentState, square), currentClueIdx + 1, currentPlacedSquares :+ square))
      })
      // }
    }

    return Vector(new Square(Coord(-1, -1), Coord(-1,-1)))
  }


}

object DepthLimitSearchStrategy {
  def main(args: Array[String]) {
    val input = Vector(// x, y, size
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
    val dp = new DepthFirstSearchStrategy()

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
    // val shapes = dp.squareShapeCombinations(4)
    // // val possibleSquares = dp.possibleSquareCombinations(Array.ofDim[Int](5, 5), Coord(2, 2), shapes, clues) // clues & boundary check
    // val state = Array.ofDim[Int](5, 5)
    // state(1)(1) = 1
    // val possibleSquares = dp.possibleSquareCombinations(state, Coord(2, 2), shapes, clues) // placed check
    // possibleSquares.foreach(println)

    val ans = dp.solve(5, 5, clues)
    var state = Array.ofDim[Int](5, 5)
    var symbol = 1
    for (square <- ans){
      state = dp.placeSquare(state, square, symbol)
      symbol += 1
    }
    val s1 = for (row <- state) yield for (ele <- row) yield f"$ele%02d"
    s1.map(_.mkString(" ")).zipWithIndex.foreach{
      case (s, idx) => println(s"row $idx - $s")
    }

  }
}
