package shikaku

class Square(val topLeft: Coord, val bottomRight: Coord) {
  type Space = Array[Array[Int]]

  override def toString(): String = {
    s"Square[(${this.topLeft.x}, ${this.topLeft.y}), (${this.bottomRight.x}, ${this.bottomRight.y})]"
  }

  def getAllPoints(): Vector[Coord] = {
    val points = for (
    x <- Vector.range(topLeft.x, bottomRight.x+1);
    y <- Vector.range(topLeft.y, bottomRight.y+1)
    ) yield Coord(x, y)
    points
  }

  def getSurroundingPonts(): Vector[Coord] = {
    val points = for (
    x <- Vector.range(topLeft.x - 1, bottomRight.x + 2);
    y <- Vector.range(topLeft.y - 1, bottomRight.y + 2);
    point = Coord(x, y);
    if (!this.isPointIntersect(point))
    ) yield point
    points
  }

  def getCentroid(): Centroid = {
    val midX = (this.topLeft.x + this.bottomRight.x).toDouble/2
    val midY = (this.topLeft.y + this.bottomRight.y).toDouble/2
    return Centroid(midX, midY)
  } 

  def isPointIntersect(point: Coord): Boolean = {
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
    for (point <- points if state(point.y)(point.x) != 0) return true 
    return false
  }
}
