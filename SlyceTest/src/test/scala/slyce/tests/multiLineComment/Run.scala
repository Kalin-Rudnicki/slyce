package slyce.tests.multiLineComment

import scalaz.Scalaz.ToOptionIdOps

import slyce.Runner

/*
object Run extends App {
  import Data._

  Runner.run(Parser, "res-test/argList/samples/ex1.txt") { rawTree =>
    println("Success:")
    println()
    println(rawTree)
    println()

    def loop(
        unseen: NonTerminal.AnonList1_2,
        seen: List[Token._var],
    ): List[Token._var] =
      unseen match {
        case NonTerminal.AnonList1_2._1(_, _var, next) =>
          loop(
            next,
            _var :: seen,
          )
        case NonTerminal.AnonList1_2._2 =>
          seen.reverse
      }

    val vars: List[Token._var] =
      rawTree match {
        case NonTerminal.ArgList._1(_, list, _, _) =>
          list match {
            case NonTerminal.AnonList1._1(_var, next) =>
              loop(
                next,
                _var :: Nil,
              )
            case NonTerminal.AnonList1._2 =>
              Nil
          }
      }
    val optTail: Option[Token.__.`!`] =
      rawTree match {
        case NonTerminal.ArgList._1(_, _, _, tail) =>
          tail.toOption
      }

    println(s"(${vars.map(_.text).mkString(", ")})")
    println(optTail)
  }

}
 */
