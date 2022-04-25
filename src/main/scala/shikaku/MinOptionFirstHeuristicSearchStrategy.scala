package shikaku

package shikaku

import scala.collection.mutable.Stack
import scala.collection.mutable.HashMap

class MinOptionFirstHeuristicSearchStrategy extends SolveStrategy {

    // caching
    //val memoize: HashMap[String, Vector[Square]] = new HashMap[String, Vector[Square]]();
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
    //val key = currentClue.toString+currentState.toString
    //if (this.memoize.contains(key))
    //  return this.memoize(key)
    // calcute number of possible squares that can be placed
    val shapes = this.squareShapeCombinations(currentClue.size)
    val possibleSquares = this.possibleSquareCombinations(currentState, currentClue.position, shapes, clues)
    //this.memoize += key -> possibleSquares
    return possibleSquares
  }
}

object MinOptionFirstHeuristicSearchStrategy {
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
    val sh = new MinOptionFirstHeuristicSearchStrategy ()
    
    val time = Utils.averageRuntimeInNanoSec(100) {

      val ans = sh.solve(10, 18, clues)
      // var state = Array.ofDim[Int](10, 18)
      // var symbol = 1
      // for (square <- ans){
      //   state = sh.placeSquare(state, square, symbol)
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