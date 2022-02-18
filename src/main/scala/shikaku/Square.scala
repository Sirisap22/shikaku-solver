package shikaku

class Square(val topLeft: Coord, val bottomRight: Coord) {
  type Space = Array[Array[Int]]

  override def toString(): String = {
    s"Square[(${this.topLeft.x}, ${this.topLeft.y}), (${this.bottomRight.x}, ${this.bottomRight.y})]"
  }

  def getAllPoints(): Vector[Coord] = {
    val points: Vector[Coord] = for (
      x <- Vector.range(topLeft.x, bottomRight.x);
      y <- Vector.range(topLeft.y, bottomRight.y)
      ) yield Coord(x, y)
    
    points
  }

  def isPointIntersect(point: Coord): Boolean = {
    println(s"$point, $topLeft, $bottomRight")
    point.x >= this.topLeft.x &&
    point.y >= this.topLeft.y &&
    point.x <= this.bottomRight.x &&
    point.y <= this.bottomRight.y
  }

  def isSquareIntersect(otherSquare: Square): Boolean = {
    val pointsInOtherSquare = otherSquare.getAllPoints()
    for (point <- pointsInOtherSquare if this.isPointIntersect(point)) return true
    return false
  }

  def isSquareOutOfBound(boundary: Boundary): Boolean = {
    this.topLeft.x < boundary.left ||
    this.topLeft.y < boundary.top ||
    this.bottomRight.x > boundary.right ||
    this.bottomRight.y > boundary.bottom
  }

  def isSquareOccupied(state: Space): Boolean = {
    val points = this.getAllPoints()
    for (point <- points if state(point.x)(point.y) != 0) return true 
    return false
  }
}
