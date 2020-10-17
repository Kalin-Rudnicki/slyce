package slyce.tests.test

import scalaz.-\/
import scalaz.Scalaz.ToOptionIdOps
import scalaz.\/
import scalaz.\/-

import slyce.implementations.generation.lexer._
import Regex.CharClass._

object Generate extends App {

  val data: Data = Data(
    startMode = "General",
    modes = List(
      // General
      Data.Mode(
        name = "General",
        lines = List(
          Data.Mode.Line(
            lineNo = 1,
            regex = Inclusive('a'),
            yields = Yields(
              yields = Yields.Yield.Terminal.std("a").some,
              toMode = None
            )
          ),
          Data.Mode.Line(
            lineNo = 2,
            regex = Inclusive('b'),
            yields = Yields(
              yields = Yields.Yield.Terminal.std("b").some,
              toMode = None
            )
          ),
          Data.Mode.Line(
            lineNo = 3,
            regex = Regex.Sequence(
              Regex.Repeat(
                Inclusive('a'),
                2,
                None
              ),
              Regex.Repeat(
                Inclusive('b'),
                2,
                None
              )
            ),
            yields = Yields(
              yields = Yields.Yield.Terminal.std("ab").some,
              toMode = None
            )
          ),
          Data.Mode.Line(
            lineNo = 5,
            regex = Regex.Sequence(
              Regex.Repeat(
                Inclusive('/'),
                2,
                2.some
              ),
              Regex.Repeat * Exclusive('\n'),
              Inclusive('\n')
            ),
            yields = Yields(
              yields = None,
              toMode = None
            )
          )
        )
      )
    )
  )

  val dfa: Err \/ Dfa = Lexer(data)

  dfa match {
    case -\/(errs) =>
      println("Errors:")
      errs.foreach(println)
      System.exit(1)
    case \/-(dfa) =>
      implicit val idt: String = "  "

      println("Success:")
      println()
      println(dfa.toksStr.mkString("\n"))
      println()
      println(dfa.dfaStr.mkString("\n"))
  }

}