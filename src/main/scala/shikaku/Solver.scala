package shikaku

case class Coord(val x: Int = 0, val y: Int = 0)
case class SquareShape(val width: Int = 1, val height: Int = 1)
case class Boundary(val top: Int, val bottom: Int, val left: Int, val right: Int)
case class Clue(val position: Coord, val size: Int)

// case class Square(val topLeft: Coord, val bottomRight: Coord)

class Solver(val numberOfRows: Int, val numberOfCols: Int, val clues: Vector[Clue]) {
  private val state = Array.ofDim[Int](numberOfRows, numberOfCols)
  this.printClues()
  this.insertClues()
  this.printState()

  def insertClues(): Unit = {
    clues.foreach(e => {
      val x = e.position.x 
      val y = e.position.y
      this.state(x)(y) = e.size
    })
  }

  def printState(): Unit = {
    this.state.map(_.mkString(" ")).foreach(println)
  }

  def printClues(): Unit = {
    println(this.clues)
  }
}
