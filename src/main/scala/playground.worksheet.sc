val vec = Array(Array(1, 2), Array(3, 4))

def changeVec(v: Array[Array[Int]]) = {
  val vnew = v.map(_.clone)
  vnew(0)(0) = -1
  vnew
}
vec

val v1 = changeVec(vec)
vec

