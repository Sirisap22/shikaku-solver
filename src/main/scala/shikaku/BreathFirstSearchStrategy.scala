package shikaku
import scala.collection.mutable.Queue

class BreathFirstSearchStrategy extends SolveStrategy {
 def solve(numberOfRows: Int, numberOfCols: Int, clues: Vector[Clue]): Vector[Square] = {
    val limit = clues.size

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

    return Vector(new Square(Coord(-1, -1), Coord(-1,-1)))
  } 
}

object BreathFirstSearchStrategy {
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
    val bt = new BreathFirstSearchStrategy()

    

    val time = Utils.averageRuntimeInNanoSec(100) {
      val ans = bt.solve(5, 5, clues)
      // var state = Array.ofDim[Int](5, 5)
      // var symbol = 1
      // for (square <- ans){
      //   state = bt.placeSquare(state, square, symbol)
      //   symbol += 1
      // }
      // val s1 = for (row <- state) yield for (ele <- row) yield f"$ele%02d"
      // s1.map(_.mkString(" ")).zipWithIndex.foreach{
      //   case (s, idx) => println(s"row $idx - $s")
      // }

      // Utils.memory()
    }
    
    println(s"Runtime: ${time/1000000} ms")
  

  }
}
