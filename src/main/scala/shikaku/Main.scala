package shikaku

object Main {
  def main(params: Array[String]): Unit = {
    val input = Vector(
                      (0, 3, 2),
                      (1, 0, 3),
                      (2, 2, 4),
                      (2, 3, 4),
                      (3, 0, 2),
                      (3, 1, 4),
                      (3, 4, 2),
                      (4, 1, 4)
                    )
    val clues: Vector[Clue] = for (ele <- input) yield Clue(Coord(ele._1 , ele._2), ele._3)
    val solver = new Solver(5, 5, clues)
  }
}
