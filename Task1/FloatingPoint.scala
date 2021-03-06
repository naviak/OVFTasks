package Task1

import scala.math.Fractional.Implicits.infixFractionalOps
import scala.math.Ordered.orderingToOrdered
import scala.reflect.ClassTag

object FloatingPoint extends App{

  class FloatNumber[F:ClassTag](oneInSystem: F)(implicit ev: Fractional[F]) {
    private val one:F = oneInSystem
    private val two:F = ev.plus(one,one)
    def epsilon(): F = {
      lazy val s: LazyList[F] = one #:: s.map(f => ev.div(f, two))
      s.takeWhile(e => ev.plus(e, one) != one).last
    }

    def maxExponent(): Int = {
      lazy val s: LazyList[F] = one #:: s.map(f => ev.times(f, two))
      val ls = s.takeWhile(_ < ev.div(one,ev.minus(one,one))).toList
      ls.length - 1
    }

    def minExponent(): Int = {
      lazy val s: LazyList[F] = one #:: s.map(f => ev.div(f, two))
      val ls = s.takeWhile(_ != ev.minus(one,one)).toList
      -(ls.length - 1 - lazyNumMantiss)
    }

    def lazyNumMantiss(): Int = {
      var zeroTwo = ev.div(one,two)
      lazy val s: LazyList[F] = (one + zeroTwo) #:: s.map(f =>{
        zeroTwo = ev.div(zeroTwo,two)
        one + zeroTwo
      })
      val ls = s.takeWhile(_ != one).toList
      ls.length
    }

    def epsArray(): Array[F]  = {
      val eps = epsilon()
      Array(one,one + eps/two,one + eps,one + eps + eps/two)
    }
  }

  val floatNumber = new FloatNumber(1.0f)
  val doubleNumber = new FloatNumber(1.0)
  println(s"epsil on for Float:\t ${floatNumber.epsilon}")
  println(s"min exponent of Float:\t ${floatNumber.minExponent}")
  println(s"max exponent of Float:\t ${floatNumber.maxExponent}")
  println(s"mantissa of Float calculated by using lazy:\t ${floatNumber.lazyNumMantiss}")
  println(floatNumber.epsArray.mkString(" "))
  println(s"epsilon for Double:\t ${doubleNumber.epsilon}")
  println(s"min exponent of Double:\t ${doubleNumber.minExponent}")
  println(s"max exponent of Double:\t ${doubleNumber.maxExponent}")
  println(s"mantissa of Double calculated by using lazy:\t ${doubleNumber.lazyNumMantiss}")
  println(doubleNumber.epsArray.mkString(" "))
}
