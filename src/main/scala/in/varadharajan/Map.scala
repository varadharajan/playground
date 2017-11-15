package in.varadharajan
import scala.collection.immutable.Seq

object MapTest {

  def main(args: Array[String]): Unit = {
    val th = ichi.bench.Thyme.warmed(verbose = print)
    val map_ : Seq[Map[String, Any]] = (1 to 500).map(x => {
      Map[String, Any]("name" -> s"product${x}") ++ (if (x % 5 == 0) Map[String, Any]("avail" -> true) else Map())
    })

    def process(map1: Seq[Map[String, Any]]) = {
      map1 map { entity =>
        entity map { entry =>
          entry._1 match {
            case "avail" => ("stock", 1)
            case _ => entry
          }
        }
      }
    }

    def process2(map1: Seq[Map[String, Any]]) = {
      map1 map { entity =>
        if(entity.contains("avail"))((entity ++ Map[String, Any]("stock" -> 1)) - "avail")
        else entity
      }
    }

    val w2 = th.Warm(process2(map_))
    th.pbenchWarm(w2)

    val w1 = th.Warm(process(map_))
    th.pbenchWarm(w1)

    assert(process(map_) == process2(map_))
  }
}
