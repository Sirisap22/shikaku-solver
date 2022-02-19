import shikaku.Square
import shikaku.Coord
import shikaku.DepthLimitSearchStrategy
val dp = new DepthLimitSearchStrategy()
val state = Array.ofDim[Int](5, 5)
val square = new Square(Coord(2, 1), Coord(2, 2))

square.getAllPoints()
// square.getAllPoints()
dp.placeSquare(state, square)

val vec = Array(Array(1, 2), Array(3, 4))

val v1 = Vector(1, 1)

v1 :+ 2

// for (ve <- vec if ve.contains(0)) {
//   println(true)
// }

// def changeVec(v: Array[Array[Int]]) = {
//   val vnew = v.map(_.clone)
//   vnew(0)(0) = -1
//   vnew
// }
// vec

// val v1 = changeVec(vec)
// vec

