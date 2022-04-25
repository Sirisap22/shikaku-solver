package shikaku

import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer

class BillHeuristicSearchStrategy extends SolveStrategy {
  override def solve(numberOfRows: Int, numberOfCols: Int, _clues: Vector[Clue]): Vector[Square] = {      
      val stack = new Stack[(Space, Vector[Clue], Vector[Square])]()

      val initialState = Array.ofDim[Int](numberOfRows, numberOfCols)
      val initialClues = _clues
      val placedSquares = Vector[Square]()

      stack.push((initialState, initialClues, placedSquares))

      // var i = 0
      while (!stack.isEmpty) {
        val (currentState, currentClues, currentPlacedSquares) = stack.pop()
        // if (i == 2)
        //   return Vector[Square]()
        // i += 1
        // println(f" i $i")
        // println(currentClues)
        // println(currentPlacedSquares)
        // println(stack.size)

        if (this.isGoalState(currentState)) {
          // println(currentState)
          return currentPlacedSquares
        }

        // gen possible from every clue
        val childs= new ListBuffer[(Vector[Clue], Clue, Square)]()
        for (clue <- currentClues) {
          val generatedChildsFromClue = this.possibleSquaresFromClue(currentClues, clue, currentState)
          for (child <- generatedChildsFromClue) {
            childs += child          
          }
        }
        // println(f"${i}, child size ${childs.size}")
        // input to heurist surround estimatied value
        // get sort by desec high to low  (possiblesquare) then push to stack

        // childs.foreach(child => {
        //   println(f"clue ${child._2} heuristic val ${this.heuristic(child._1, child._2, child._3, numberOfCols * numberOfRows)} ")
        // })

        val sortedChilds = childs.sortBy((child) => {

        val heuristicVal = this.heuristic(child._1, child._2, child._3, numberOfCols * numberOfRows)
        // println(f"clue ${child._2} val ${heuristicVal}")
        heuristicVal
        })(Ordering[Float].reverse)
        // println(f"heuristic size ${sortedChilds.size}")

        for (child <- childs) {
          stack.push((this.placeSquare(currentState, child._3), child._1, currentPlacedSquares :+ child._3))
        }


      }

      return Vector[Square]()
    }

  def possibleSquaresFromClue(currentClues: Vector[Clue], currentClue: Clue, currentState: Array[Array[Int]]): Vector[(Vector[Clue], Clue, Square)] = {
    val currentCluesWithOutInputClue = currentClues.filter(clue => clue != currentClue);
    val shapes = this.squareShapeCombinations(currentClue.size)
    val possibleSquares = this.possibleSquareCombinations(currentState, currentClue.position, shapes, currentClues)
    val possibleSquaresWithNextClues = possibleSquares.map(square => (currentCluesWithOutInputClue, currentClue, square))
    return possibleSquaresWithNextClues
  }

  def heuristic(clues: Vector[Clue], clue: Clue, square: Square, boardSize: Int): Float = {
    val pointsInSquare = square.getAllPoints();
    val pointsInClue = clues.map(clues => clues.position)
    var sum = 0f
    for (point <- pointsInSquare) {
      val surroundingPoints = surroudingPoints(point);
      for (surroundingPoint <- surroundingPoints) {
        if (pointsInClue.contains(surroundingPoint)) {
          // println("IM IN")
          sum += 1f
        }
      }
    }

    // println(f"$sum / $pointsInSquare")
    val averageSurroundingPointThatContainClue = sum.toFloat/pointsInSquare.size.toFloat
    val emptyCoord = boardSize - clue.size
    val heuristicValue = emptyCoord + averageSurroundingPointThatContainClue
    // println(heuristicValue)
    return heuristicValue
  }

  def surroudingPoints(point: Coord): Vector[Coord] = {
    return for (
      x <- Vector.range(point.x - 1, point.x + 2);
      y <- Vector.range(point.y - 1, point.y + 2)
      ) yield Coord(x, y)
  }

  // def isPointOutOfBound(point: Coord, boundary: Boundary): Boolean = {

  // }
}

object BillHeuristicSearchStrategy {
  def main(args: Array[String]) {

val input = Vector(
(0, 0, 10),
(17, 0, 12),
(0, 21, 3),
(0, 10, 11),
(9, 21, 9),
(1, 10, 14),
(15, 10, 11),
(1, 16, 14),
(11, 0, 10),
(8, 21, 9),
(15, 0, 10),
(0, 27, 8),
(16, 10, 11),
(1, 11, 2),
(0, 28, 8),
(10, 21, 6),
(3, 0, 7),
(29, 0, 30),
(3, 21, 6),
(7, 21, 6),
(5, 21, 2),
(8, 0, 4),
(0, 29, 8),
(12, 0, 3),
(14, 21, 9),
(2, 21, 6),
(17, 5, 13),
(17, 18, 2),
(1, 17, 4),
(10, 28, 4),
(12, 21, 6),
(10, 27, 2),
(8, 4, 6),
(17, 20, 4),
(11, 21, 6),
(19, 18, 10),
(12, 8, 3),
(10, 29, 4),
(3, 7, 2),
(16, 0, 10),
(9, 4, 2),
(15, 21, 9),
(12, 27, 2),
(19, 5, 7),
(13, 21, 6),
(17, 4, 12),
(4, 21, 6),
(12, 3, 5),
(13, 3, 5),
(0, 24, 2),
(17, 19, 2),
(12, 9, 3),
(4, 11, 2),
(5, 24, 2),
(19, 19, 10),
(16, 21, 9),
(5, 26, 2),
(4, 13, 2),
(0, 26, 2),
(9, 5, 2),
(23, 5, 3),
(6, 24, 2),
(5, 22, 2),
(25, 20, 4),
(10, 0, 4),
(1, 0, 10),
(17, 2, 12),
(0, 25, 2),
(8, 13, 7),
(11, 11, 2),
(17, 1, 12),
(9, 7, 2),
(23, 8, 10),
(8, 14, 7),
(25, 26, 4),
(25, 22, 4),
(4, 15, 4),
(2, 0, 10),
(26, 22, 4),
(2, 17, 8),
(18, 5, 13),
(10, 17, 2),
(9, 8, 2),
(25, 21, 4),
(9, 6, 2),
(27, 22, 2),
(24, 8, 10),
(17, 3, 12),
(1, 21, 3),
(12, 1, 3),
(13, 11, 2),
(17, 27, 8),
(25, 8, 8),
(2, 18, 8),
(17, 29, 8),
(17, 28, 8),
(28, 26, 4),
(2, 19, 6),
(3, 9, 5),
(26, 26, 4),
(22, 20, 6),
(12, 2, 3),
(27, 26, 4),
(6, 11, 5),
(13, 12, 2),
(21, 20, 7),
(10, 8, 2),
(22, 26, 3),
(4, 0, 4),
(23, 7, 6),
(27, 5, 2),
(6, 12, 5),
(26, 5, 2),
(21, 5, 4),
(8, 15, 7),
(19, 14, 2),
(21, 9, 9),
(22, 5, 4),
(24, 20, 6),
(4, 6, 4),
(25, 16, 4),
(26, 8, 3),
(4, 12, 2),
(13, 17, 4),
(25, 17, 4),
(17, 22, 4),
(12, 11, 2),
(4, 1, 4),
(5, 7, 3),
(23, 20, 6),
(26, 11, 5),
(17, 21, 4),
(22, 9, 9),
(6, 22, 2),
(19, 17, 2),
(27, 25, 2),
(19, 12, 2),
(14, 17, 4),
(8, 19, 2),
(20, 5, 7),
(4, 5, 4),
(26, 9, 3),
(9, 0, 4),
(23, 6, 3),
(8, 20, 2),
(2, 20, 6),
(5, 1, 3),
(19, 13, 2),
(27, 11, 4),
(17, 23, 4),
(28, 5, 2),
(3, 11, 5),
(18, 23, 3),
(27, 15, 2),
(7, 13, 2),
(5, 13, 2),
(19, 16, 2),
(1, 15, 2),
(1, 12, 2),
(14, 3, 5),
(26, 10, 3),
(27, 24, 2),
(18, 24, 3),
(18, 26, 3),
(4, 7, 2),
(28, 11, 4),
(12, 17, 4),
(5, 14, 2),
(10, 20, 2),
(10, 19, 2),
(10, 18, 2),
(27, 23, 2),
(5, 3, 3),
(5, 8, 3),
(20, 14, 2),
(5, 2, 3),
(1, 14, 2),
(5, 4, 3),
(2, 12, 2),
(18, 25, 3)
)

    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val sh = new BillHeuristicSearchStrategy ()
    
    val time = Utils.averageRuntimeInNanoSec(1) {

      val ans = sh.solve(30, 30, clues)
      println(ans)
      var state = Array.ofDim[Int](30, 30)
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
