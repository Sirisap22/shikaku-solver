package shikaku

case class Coord(val x: Int = 0, val y: Int = 0)
case class SquareShape(val width: Int = 1, val height: Int = 1)
case class Boundary(val top: Int, val bottom: Int, val left: Int, val right: Int)
case class Clue(val position: Coord, val size: Int)

// case class Square(val topLeft: Coord, val bottomRight: Coord)

class Solver(val numberOfRows: Int, val numberOfCols: Int, val clues: Vector[Clue]) {
 
}
