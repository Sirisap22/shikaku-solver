package shikaku

package shikaku

import scala.collection.mutable.Stack

class MinOptionFirstHeuristicSearchStrategy extends SolveStrategy {
    override def solve(numberOfRows: Int, numberOfCols: Int, _clues: Vector[Clue]): Vector[Square] = {      
      val stack = new Stack[(Space, Vector[Clue], Vector[Square])]()

      val initialState = Array.ofDim[Int](numberOfRows, numberOfCols)
      val initialClues = _clues
      val placedSquares = Vector[Square]()

      stack.push((initialState, initialClues, placedSquares))

      while (!stack.isEmpty) {
        val (currentState, currentClues, currentPlacedSquares) = stack.pop()

        if (this.isGoalState(currentState)) {
          return currentPlacedSquares
        }


        val currentCluesHeuristic = currentClues.map(clue => this.heuristic(currentClues, clue, currentState))
        val possibleSquaresWithIndex = currentCluesHeuristic.zipWithIndex.reduceLeft((psA, psB) => if (psA._1.size < psB._1.size) psA else psB)

        val nextClues = currentClues.zipWithIndex.filter(_._2 != possibleSquaresWithIndex._2).map(_._1)
        possibleSquaresWithIndex._1.foreach((square) => {
          stack.push((this.placeSquare(currentState, square), nextClues, currentPlacedSquares :+ square))
        })

        
      }

      return Vector[Square]()
  }

  def heuristic(clues: Vector[Clue], currentClue: Clue, currentState: Array[Array[Int]]): Vector[Square] = {
    // calcute number of possible squares that can be placed
    val shapes = this.squareShapeCombinations(currentClue.size)
    val possibleSquares = this.possibleSquareCombinations(currentState, currentClue.position, shapes, clues)
    return possibleSquares
  }
}

object MinOptionFirstHeuristicSearchStrategy {
  def main(args: Array[String]) {
    val input = Vector((0, 0, 4),
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
)

    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val sh = new MinOptionFirstHeuristicSearchStrategy ()
    
    val time = Utils.averageRuntimeInNanoSec(1) {

      val ans = sh.solve(10, 18, clues)
      var state = Array.ofDim[Int](10, 18)
      var symbol = 1
      for (square <- ans){
        state = sh.placeSquare(state, square, symbol)
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