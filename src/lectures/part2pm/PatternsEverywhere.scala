package lectures.part2pm

object PatternsEverywhere extends App {

  // big idea #1
  try {
    // code
  } catch {
    case e: RuntimeException => "Runtime exception"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // catches are actually matches
  /*
  try {
    // code
  } catch (e) {
    e match {
      case e: RuntimeException => "Runtime exception"
      case npe: NullPointerException => "npe"
      case _ => "something else"
    }
  }
   */

  // big idea #2
  val list = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list if x % 2 == 0
  } yield x * 10

  // generators are also based on PATTERN MATCHING
  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  // case classes, :: operators

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple
  // multiple value definitions based on pattern matching

  val head :: tail = list

  // big idea #4
  // partial functions based on PATTERN MATCHING

  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  } // partial function literal

  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }

  println(mappedList)
}
