package shikaku
import scala.collection.mutable.Queue

class BreathFirstSearchStrategy extends SolveStrategy {
 def solve(numberOfRows: Int, numberOfCols: Int, clues: Vector[Clue]): Vector[Square] = {
    // Space = board game, Clue = current clue, Square = block that place the round[state]
    val queue = new Queue[(Space, Int, Vector[Square])]()

    val initialState = Array.ofDim[Int](numberOfRows, numberOfCols)
    val initialClueIdx = 0
    val placedSquares = Vector[Square]()

    queue.enqueue((initialState, initialClueIdx, placedSquares))

    while (!queue.isEmpty) {
      val (currentState, currentClueIdx, currentPlacedSquares) = queue.dequeue()

      // check if goalstate
      if (this.isGoalState(currentState)) {
        return currentPlacedSquares
      }

      val currentClue = clues(currentClueIdx)

      // go next state
      // gen childs
      val shapes = this.squareShapeCombinations(currentClue.size)
      val possibleSquares = this.possibleSquareCombinations(currentState, currentClue.position, shapes, clues)
      possibleSquares.foreach((square) => {
        queue.enqueue((this.placeSquare(currentState, square), currentClueIdx + 1, currentPlacedSquares :+ square))
      })
    }

    return Vector[Square]()
  } 
}

object BreathFirstSearchStrategy {
  def main(args: Array[String]) {
    val input = Vector((1, 0, 5),
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
)
    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val bt = new BreathFirstSearchStrategy()

    

    val time = Utils.averageRuntimeInNanoSec(1) {
      val ans = bt.solve(10, 18, clues)
      var state = Array.ofDim[Int](10, 18)
      var symbol = 1
      for (square <- ans){
        state = bt.placeSquare(state, square, symbol)
        symbol += 1
      }
      val s1 = for (row <- state) yield for (ele <- row) yield f"$ele%02d"
      s1.map(_.mkString(" ")).zipWithIndex.foreach{
        case (s, idx) => println(s"row $idx - $s")
      }

      Utils.memory()
    }

    println(s"Runtime: ${time/1000000} ms")
  

  }
}
