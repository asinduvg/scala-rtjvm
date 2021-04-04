package exercise

import lectures.part2oop.Generics.MyList

abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def add[B >: A](element: B): MyList[B]

  def printElements: String

  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): MyList[B]

  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

  def filter(predicate: MyPredicate[A]): MyList[A]

  def ++[B >: A](list: MyList[B]): MyList[B]

}

object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException

  override def tail: MyList[Nothing] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

  override def printElements: String = ""

  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty

  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h

  override def tail: MyList[A] = t

  override def isEmpty: Boolean = false

  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  /*
  * [1, 2] ++ [3, 4, 5]
  * = new Cons(1, [2] ++ [3, 4, 5])
  * = new Cons(1, new Cons(2, Empty ++ [3, 4, 5]))
  * = new Cons(1, new Cons(2, [3, 4, 5]))
  * = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5))))
  *
  * */
  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons[B](h, t ++ list)

  override def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(this.head)) new Cons[A](h, this.tail.filter(predicate))
    else this.tail.filter(predicate)
  }

  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons[B](transformer.transform(this.head), this.tail.map(transformer))
  }

  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(this.head) ++ this.tail.flatMap(transformer)
  }

}

trait MyPredicate[-T] {
  def test(value: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(elem: A): B
}

class EvenPredicate extends MyPredicate[Int] {
  override def test(n: Int): Boolean = n % 2 == 0
}

class StringToIntTransformer extends MyTransformer[String, Int] {
  override def transform(elem: String): Int = ???
}


object ListTest extends App {
  val listOfIntegers: MyList[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  val listOfStrings: MyList[String] = new Cons[String]("Asindu", new Cons[String]("Chamika", new Cons[String]("VG", Empty)))

  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }))

  println(listOfStrings.map(new MyTransformer[String, Int] {
    override def transform(elem: String): Int = 1
  }))

  println(listOfIntegers.filter(new EvenPredicate))

  println(listOfIntegers.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(elem: Int): MyList[Int] = new Cons[Int](elem, new Cons[Int](elem * 2, Empty))
  }))

}


