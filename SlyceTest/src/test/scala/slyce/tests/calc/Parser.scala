// DO NOT EDIT : Automatically generated by Slyce v0.1.0 @ 11/22/2020
package slyce.tests.calc

import scala.annotation.tailrec

import scalaz.\/
import scalaz.-\/
import scalaz.\/-
import scalaz.Scalaz.ToBooleanOpsFromBoolean
import scalaz.Scalaz.ToOptionIdOps

import slyce.common.helpers._
import slyce.parse._
import slyce.parse.{architecture => arch}

object Data {

  sealed trait Token extends Dfa.Token
  sealed trait HasSpanToken extends Token with Dfa.Token.HasSpan
  object Token {
    case object EOF extends Token
    object __ {

      def apply(str: String, span: Dfa.Token.Span): Token =
        str match {
          case "\n" =>
            `\n`.apply(span)
          case "=" =>
            `=`.apply(span)
          case "(" =>
            `(`.apply(span)
          case ")" =>
            `)`.apply(span)
          case _ =>
            ???
        }

      final case class `\n`(span: Dfa.Token.Span) extends HasSpanToken
      final case class `=`(span: Dfa.Token.Span) extends HasSpanToken
      final case class `(`(span: Dfa.Token.Span) extends HasSpanToken
      final case class `)`(span: Dfa.Token.Span) extends HasSpanToken
    }
    final case class _var(text: String, span: Dfa.Token.Span) extends HasSpanToken
    final case class addOp(text: String, span: Dfa.Token.Span) extends HasSpanToken with NonTerminal.Expr.Operator
    final case class comment(text: String, span: Dfa.Token.Span) extends HasSpanToken
    final case class float(text: String, span: Dfa.Token.Span) extends HasSpanToken
    final case class int(text: String, span: Dfa.Token.Span) extends HasSpanToken
    final case class multOp(text: String, span: Dfa.Token.Span) extends HasSpanToken with NonTerminal.Expr.Operator
    final case class powOp(text: String, span: Dfa.Token.Span) extends HasSpanToken with NonTerminal.Expr.Operator
  }
  object HasSpanToken {
    def unapply(arg: HasSpanToken): Option[Dfa.Token.Span] = arg.span.some
  }

  sealed trait NonTerminal
  object NonTerminal {

    sealed trait __Start extends NonTerminal
    object __Start {

      final case class _1(
        _1: NonTerminal.Lines,
        _2: Token.EOF.type,
      ) extends __Start

    }

    sealed trait AnonList1 extends NonTerminal {

      def toList: List[Token.__.`\n`] = {
        @tailrec
        def loop(unseen: AnonList1, seen: List[Token.__.`\n`]): List[Token.__.`\n`] =
          unseen match {
            case AnonList1._1(n, tail) =>
              loop(tail, n :: seen)
            case AnonList1._2 =>
              seen.reverse
          }

          loop(this, Nil)
      }

    }
    object AnonList1 {

      final case class _1(
        _1: Token.__.`\n`,
        _2: NonTerminal.AnonList1,
      ) extends AnonList1

      case object _2 extends AnonList1

    }

    sealed trait Assign extends NonTerminal
    object Assign {

      final case class _1(
        _1: Token._var,
        _2: Token.__.`=`,
        _3: NonTerminal.Expr,
      ) extends Assign

    }

    sealed trait Expr extends NonTerminal {

      def toExpr: Expression[Expr.Operand, Expr.Operator] =
        this match {
          case Expr._1(left, op, right) =>
            Expression(left.toExpr, op, right.toExpr)
          case Expr._2(child) =>
            child.toExpr
        }

    }
    object Expr {

      sealed trait Operator
      type Operand = Expr_4

      final case class _1(
        _1: NonTerminal.Expr,
        _2: Token.addOp,
        _3: NonTerminal.Expr_2,
      ) extends Expr

      final case class _2(
        _1: NonTerminal.Expr_2,
      ) extends Expr

    }

    sealed trait Expr_2 extends NonTerminal {

      def toExpr: Expression[Expr.Operand, Expr.Operator] =
        this match {
          case Expr_2._1(left, op, right) =>
            Expression(left.toExpr, op, right.toExpr)
          case Expr_2._2(child) =>
            child.toExpr
        }

    }
    object Expr_2 {

      final case class _1(
        _1: NonTerminal.Expr_2,
        _2: Token.multOp,
        _3: NonTerminal.Expr_3,
      ) extends Expr_2

      final case class _2(
        _1: NonTerminal.Expr_3,
      ) extends Expr_2

    }

    sealed trait Expr_3 extends NonTerminal {

      def toExpr: Expression[Expr.Operand, Expr.Operator] =
        this match {
          case Expr_3._1(left, op, right) =>
            Expression(left.toExpr, op, right.toExpr)
          case Expr_3._2(child) =>
            child.toExpr
        }

    }
    object Expr_3 {

      final case class _1(
        _1: NonTerminal.Expr_4,
        _2: Token.powOp,
        _3: NonTerminal.Expr_3,
      ) extends Expr_3

      final case class _2(
        _1: NonTerminal.Expr_4,
      ) extends Expr_3

    }

    sealed trait Expr_4 extends NonTerminal {

      def toExpr: Expression[Expr.Operand, Expr.Operator] =
        Expression(this)

    }
    object Expr_4 {

      final case class _1(
        _1: Token.__.`(`,
        _2: NonTerminal.Expr,
        _3: Token.__.`)`,
      ) extends Expr_4

      final case class _2(
        _1: Token.int,
      ) extends Expr_4

      final case class _3(
        _1: Token.float,
      ) extends Expr_4

      final case class _4(
        _1: Token._var,
      ) extends Expr_4

    }

    sealed trait Line extends NonTerminal
    object Line {

      final case class _1(
        _1: NonTerminal.Expr,
      ) extends Line

      final case class _2(
        _1: NonTerminal.Assign,
      ) extends Line

      final case class _3(
        _1: Token.comment,
      ) extends Line

    }

    sealed trait Lines extends NonTerminal {

      def toList: List[NonTerminal.Line] = {
        @tailrec
        def loop(unseen: Lines_2, seen: List[NonTerminal.Line]): List[NonTerminal.Line] =
          unseen match {
            case Lines_2._1(_, n, _, tail) =>
              loop(tail, n :: seen)
            case Lines_2._2 =>
              seen.reverse
        }

        this match {
          case Lines._1(_, n, tail) =>
            loop(tail, n :: Nil)
          case Lines._2 =>
            Nil
        }
      }

    }
    object Lines {

      final case class _1(
        _1: NonTerminal.AnonList1,
        _2: NonTerminal.Line,
        _3: NonTerminal.Lines_2,
      ) extends Lines

      case object _2 extends Lines

    }

    sealed trait Lines_2 extends NonTerminal
    object Lines_2 {

      final case class _1(
        _1: NonTerminal.AnonList1,
        _2: NonTerminal.Line,
        _3: NonTerminal.AnonList1,
        _4: NonTerminal.Lines_2,
      ) extends Lines_2

      case object _2 extends Lines_2

    }

  }

}

object Parser extends arch.Parser[String, List[String], Data.NonTerminal.Lines] {
  import Data._

  private val lexer: Dfa[Token] = {
    lazy val s0: Dfa.State[Token] =
      Dfa.State(
        id = 0,
        transitions = Map(
          0x9.toChar -> Some(Lazy(s11)), // '\t'
          0xA.toChar -> Some(Lazy(s7)), // '\n'
          0x20.toChar -> Some(Lazy(s11)), // ' '
          0x28.toChar -> Some(Lazy(s7)), // '('
          0x29.toChar -> Some(Lazy(s7)), // ')'
          0x2A.toChar -> Some(Lazy(s12)), // '*'
          0x2B.toChar -> Some(Lazy(s8)), // '+'
          0x2D.toChar -> Some(Lazy(s6)), // '-'
          0x2F.toChar -> Some(Lazy(s9)), // '/'
          0x30.toChar -> Some(Lazy(s4)), // '0'
          0x31.toChar -> Some(Lazy(s4)), // '1'
          0x32.toChar -> Some(Lazy(s4)), // '2'
          0x33.toChar -> Some(Lazy(s4)), // '3'
          0x34.toChar -> Some(Lazy(s4)), // '4'
          0x35.toChar -> Some(Lazy(s4)), // '5'
          0x36.toChar -> Some(Lazy(s4)), // '6'
          0x37.toChar -> Some(Lazy(s4)), // '7'
          0x38.toChar -> Some(Lazy(s4)), // '8'
          0x39.toChar -> Some(Lazy(s4)), // '9'
          0x3D.toChar -> Some(Lazy(s7)), // '='
          0x5E.toChar -> Some(Lazy(s15)), // '^'
          0x5F.toChar -> Some(Lazy(s3)), // '_'
          0x61.toChar -> Some(Lazy(s3)), // 'a'
          0x62.toChar -> Some(Lazy(s3)), // 'b'
          0x63.toChar -> Some(Lazy(s3)), // 'c'
          0x64.toChar -> Some(Lazy(s3)), // 'd'
          0x65.toChar -> Some(Lazy(s3)), // 'e'
          0x66.toChar -> Some(Lazy(s3)), // 'f'
          0x67.toChar -> Some(Lazy(s3)), // 'g'
          0x68.toChar -> Some(Lazy(s3)), // 'h'
          0x69.toChar -> Some(Lazy(s3)), // 'i'
          0x6A.toChar -> Some(Lazy(s3)), // 'j'
          0x6B.toChar -> Some(Lazy(s3)), // 'k'
          0x6C.toChar -> Some(Lazy(s3)), // 'l'
          0x6D.toChar -> Some(Lazy(s3)), // 'm'
          0x6E.toChar -> Some(Lazy(s3)), // 'n'
          0x6F.toChar -> Some(Lazy(s3)), // 'o'
          0x70.toChar -> Some(Lazy(s3)), // 'p'
          0x71.toChar -> Some(Lazy(s3)), // 'q'
          0x72.toChar -> Some(Lazy(s3)), // 'r'
          0x73.toChar -> Some(Lazy(s3)), // 's'
          0x74.toChar -> Some(Lazy(s3)), // 't'
          0x75.toChar -> Some(Lazy(s3)), // 'u'
          0x76.toChar -> Some(Lazy(s3)), // 'v'
          0x77.toChar -> Some(Lazy(s3)), // 'w'
          0x78.toChar -> Some(Lazy(s3)), // 'x'
          0x79.toChar -> Some(Lazy(s3)), // 'y'
          0x7A.toChar -> Some(Lazy(s3)), // 'z'
        ),
        elseTransition = None,
        yields = None,
      )
    lazy val s1: Dfa.State[Token] =
      Dfa.State(
        id = 1,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.comment.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s2: Dfa.State[Token] =
      Dfa.State(
        id = 2,
        transitions = Map(
          0x2F.toChar -> Some(Lazy(s1)), // '/'
        ),
        elseTransition = Some(Lazy(s13)),
        yields = None,
      )
    lazy val s3: Dfa.State[Token] =
      Dfa.State(
        id = 3,
        transitions = Map(
          0x30.toChar -> Some(Lazy(s3)), // '0'
          0x31.toChar -> Some(Lazy(s3)), // '1'
          0x32.toChar -> Some(Lazy(s3)), // '2'
          0x33.toChar -> Some(Lazy(s3)), // '3'
          0x34.toChar -> Some(Lazy(s3)), // '4'
          0x35.toChar -> Some(Lazy(s3)), // '5'
          0x36.toChar -> Some(Lazy(s3)), // '6'
          0x37.toChar -> Some(Lazy(s3)), // '7'
          0x38.toChar -> Some(Lazy(s3)), // '8'
          0x39.toChar -> Some(Lazy(s3)), // '9'
          0x41.toChar -> Some(Lazy(s3)), // 'A'
          0x42.toChar -> Some(Lazy(s3)), // 'B'
          0x43.toChar -> Some(Lazy(s3)), // 'C'
          0x44.toChar -> Some(Lazy(s3)), // 'D'
          0x45.toChar -> Some(Lazy(s3)), // 'E'
          0x46.toChar -> Some(Lazy(s3)), // 'F'
          0x47.toChar -> Some(Lazy(s3)), // 'G'
          0x48.toChar -> Some(Lazy(s3)), // 'H'
          0x49.toChar -> Some(Lazy(s3)), // 'I'
          0x4A.toChar -> Some(Lazy(s3)), // 'J'
          0x4B.toChar -> Some(Lazy(s3)), // 'K'
          0x4C.toChar -> Some(Lazy(s3)), // 'L'
          0x4D.toChar -> Some(Lazy(s3)), // 'M'
          0x4E.toChar -> Some(Lazy(s3)), // 'N'
          0x4F.toChar -> Some(Lazy(s3)), // 'O'
          0x50.toChar -> Some(Lazy(s3)), // 'P'
          0x51.toChar -> Some(Lazy(s3)), // 'Q'
          0x52.toChar -> Some(Lazy(s3)), // 'R'
          0x53.toChar -> Some(Lazy(s3)), // 'S'
          0x54.toChar -> Some(Lazy(s3)), // 'T'
          0x55.toChar -> Some(Lazy(s3)), // 'U'
          0x56.toChar -> Some(Lazy(s3)), // 'V'
          0x57.toChar -> Some(Lazy(s3)), // 'W'
          0x58.toChar -> Some(Lazy(s3)), // 'X'
          0x59.toChar -> Some(Lazy(s3)), // 'Y'
          0x5A.toChar -> Some(Lazy(s3)), // 'Z'
          0x5F.toChar -> Some(Lazy(s3)), // '_'
          0x61.toChar -> Some(Lazy(s3)), // 'a'
          0x62.toChar -> Some(Lazy(s3)), // 'b'
          0x63.toChar -> Some(Lazy(s3)), // 'c'
          0x64.toChar -> Some(Lazy(s3)), // 'd'
          0x65.toChar -> Some(Lazy(s3)), // 'e'
          0x66.toChar -> Some(Lazy(s3)), // 'f'
          0x67.toChar -> Some(Lazy(s3)), // 'g'
          0x68.toChar -> Some(Lazy(s3)), // 'h'
          0x69.toChar -> Some(Lazy(s3)), // 'i'
          0x6A.toChar -> Some(Lazy(s3)), // 'j'
          0x6B.toChar -> Some(Lazy(s3)), // 'k'
          0x6C.toChar -> Some(Lazy(s3)), // 'l'
          0x6D.toChar -> Some(Lazy(s3)), // 'm'
          0x6E.toChar -> Some(Lazy(s3)), // 'n'
          0x6F.toChar -> Some(Lazy(s3)), // 'o'
          0x70.toChar -> Some(Lazy(s3)), // 'p'
          0x71.toChar -> Some(Lazy(s3)), // 'q'
          0x72.toChar -> Some(Lazy(s3)), // 'r'
          0x73.toChar -> Some(Lazy(s3)), // 's'
          0x74.toChar -> Some(Lazy(s3)), // 't'
          0x75.toChar -> Some(Lazy(s3)), // 'u'
          0x76.toChar -> Some(Lazy(s3)), // 'v'
          0x77.toChar -> Some(Lazy(s3)), // 'w'
          0x78.toChar -> Some(Lazy(s3)), // 'x'
          0x79.toChar -> Some(Lazy(s3)), // 'y'
          0x7A.toChar -> Some(Lazy(s3)), // 'z'
        ),
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token._var.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s4: Dfa.State[Token] =
      Dfa.State(
        id = 4,
        transitions = Map(
          0x2E.toChar -> Some(Lazy(s5)), // '.'
          0x30.toChar -> Some(Lazy(s4)), // '0'
          0x31.toChar -> Some(Lazy(s4)), // '1'
          0x32.toChar -> Some(Lazy(s4)), // '2'
          0x33.toChar -> Some(Lazy(s4)), // '3'
          0x34.toChar -> Some(Lazy(s4)), // '4'
          0x35.toChar -> Some(Lazy(s4)), // '5'
          0x36.toChar -> Some(Lazy(s4)), // '6'
          0x37.toChar -> Some(Lazy(s4)), // '7'
          0x38.toChar -> Some(Lazy(s4)), // '8'
          0x39.toChar -> Some(Lazy(s4)), // '9'
        ),
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.int.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s5: Dfa.State[Token] =
      Dfa.State(
        id = 5,
        transitions = Map(
          0x30.toChar -> Some(Lazy(s10)), // '0'
          0x31.toChar -> Some(Lazy(s10)), // '1'
          0x32.toChar -> Some(Lazy(s10)), // '2'
          0x33.toChar -> Some(Lazy(s10)), // '3'
          0x34.toChar -> Some(Lazy(s10)), // '4'
          0x35.toChar -> Some(Lazy(s10)), // '5'
          0x36.toChar -> Some(Lazy(s10)), // '6'
          0x37.toChar -> Some(Lazy(s10)), // '7'
          0x38.toChar -> Some(Lazy(s10)), // '8'
          0x39.toChar -> Some(Lazy(s10)), // '9'
        ),
        elseTransition = None,
        yields = None,
      )
    lazy val s6: Dfa.State[Token] =
      Dfa.State(
        id = 6,
        transitions = Map(
          0x30.toChar -> Some(Lazy(s4)), // '0'
          0x31.toChar -> Some(Lazy(s4)), // '1'
          0x32.toChar -> Some(Lazy(s4)), // '2'
          0x33.toChar -> Some(Lazy(s4)), // '3'
          0x34.toChar -> Some(Lazy(s4)), // '4'
          0x35.toChar -> Some(Lazy(s4)), // '5'
          0x36.toChar -> Some(Lazy(s4)), // '6'
          0x37.toChar -> Some(Lazy(s4)), // '7'
          0x38.toChar -> Some(Lazy(s4)), // '8'
          0x39.toChar -> Some(Lazy(s4)), // '9'
        ),
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.addOp.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s7: Dfa.State[Token] =
      Dfa.State(
        id = 7,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.__.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s8: Dfa.State[Token] =
      Dfa.State(
        id = 8,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.addOp.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s9: Dfa.State[Token] =
      Dfa.State(
        id = 9,
        transitions = Map(
          0x2A.toChar -> Some(Lazy(s13)), // '*'
          0x2F.toChar -> Some(Lazy(s14)), // '/'
        ),
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.multOp.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s10: Dfa.State[Token] =
      Dfa.State(
        id = 10,
        transitions = Map(
          0x30.toChar -> Some(Lazy(s10)), // '0'
          0x31.toChar -> Some(Lazy(s10)), // '1'
          0x32.toChar -> Some(Lazy(s10)), // '2'
          0x33.toChar -> Some(Lazy(s10)), // '3'
          0x34.toChar -> Some(Lazy(s10)), // '4'
          0x35.toChar -> Some(Lazy(s10)), // '5'
          0x36.toChar -> Some(Lazy(s10)), // '6'
          0x37.toChar -> Some(Lazy(s10)), // '7'
          0x38.toChar -> Some(Lazy(s10)), // '8'
          0x39.toChar -> Some(Lazy(s10)), // '9'
        ),
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.float.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s11: Dfa.State[Token] =
      Dfa.State(
        id = 11,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(Dfa.State.Yields(s0)()),
      )
    lazy val s12: Dfa.State[Token] =
      Dfa.State(
        id = 12,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.multOp.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s13: Dfa.State[Token] =
      Dfa.State(
        id = 13,
        transitions = Map(
          0x2A.toChar -> Some(Lazy(s2)), // '*'
        ),
        elseTransition = Some(Lazy(s13)),
        yields = None,
      )
    lazy val s14: Dfa.State[Token] =
      Dfa.State(
        id = 14,
        transitions = Map(
          0xA.toChar -> None, // '\n'
        ),
        elseTransition = Some(Lazy(s14)),
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.comment.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )
    lazy val s15: Dfa.State[Token] =
      Dfa.State(
        id = 15,
        transitions = Map.empty,
        elseTransition = None,
        yields = Some(
          Dfa.State.Yields(s0)(
            Dfa.State.Yields.Yield(
              tokF = Token.powOp.apply,
              spanRange = (0,-1),
            ),
          ),
        ),
      )

    Dfa(s0, Token.EOF)
  }

  private val grammar: Builder[Token, NonTerminal, NonTerminal.Lines]#StateMachine =
    Builder.builder[Token, NonTerminal, NonTerminal.Lines].build { builder =>
      val elem: Matcher[builder.StackFrame.StackElement, builder.ElementT] = { element =>
        builder.StackFrame.StackElement.unapply(element).map(_._3)
      }
      val stateElem: Matcher[builder.StackFrame.StackElement, (builder.State, builder.ElementT)] = { element =>
        builder.StackFrame.StackElement.unapply(element).map { case (_1, _, _3) => (_1, _3) }
      }

      lazy val s0: builder.State =
        builder.State(
          id = 0,
          acceptF = Some {
            case \/-(_: NonTerminal.Lines) => s1
            case \/-(_: NonTerminal.AnonList1) => s2
            case -\/(_: Token.__.`\n`) => s6
          },
          returnFs = Nil,
          spontaneouslyGenerates = List(
            NonTerminal.AnonList1._2,
            NonTerminal.Lines._2,
          ),
          finalReturnF = None,
        )
      lazy val s1: builder.State =
        builder.State(
          id = 1,
          acceptF = Some {
            case -\/(Token.EOF) => s14
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s2: builder.State =
        builder.State(
          id = 2,
          acceptF = Some {
            case \/-(_: NonTerminal.Assign) => s20
            case \/-(_: NonTerminal.Expr_3) => s28
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case -\/(_: Token.comment) => s19
            case \/-(_: NonTerminal.Expr) => s7
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_2) => s4
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s22
            case \/-(_: NonTerminal.Line) => s15
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s3: builder.State =
        builder.State(
          id = 3,
          acceptF = Some {
            case -\/(_: Token.powOp) => s12
          },
          returnFs = List(
            {
              case stateElem(state, \/-(_1: NonTerminal.Expr_4)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_3._2(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s4: builder.State =
        builder.State(
          id = 4,
          acceptF = Some {
            case -\/(_: Token.multOp) => s5
          },
          returnFs = List(
            {
              case stateElem(state, \/-(_1: NonTerminal.Expr_2)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr._2(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s5: builder.State =
        builder.State(
          id = 5,
          acceptF = Some {
            case \/-(_: NonTerminal.Expr_3) => s9
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s17
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s6: builder.State =
        builder.State(
          id = 6,
          acceptF = Some {
            case \/-(_: NonTerminal.AnonList1) => s11
            case -\/(_: Token.__.`\n`) => s6
          },
          returnFs = Nil,
          spontaneouslyGenerates = List(
            NonTerminal.AnonList1._2,
          ),
          finalReturnF = None,
        )
      lazy val s7: builder.State =
        builder.State(
          id = 7,
          acceptF = Some {
            case -\/(_: Token.addOp) => s8
          },
          returnFs = List(
            {
              case stateElem(state, \/-(_1: NonTerminal.Expr)) :: stackT =>
                (
                  state,
                  NonTerminal.Line._1(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s8: builder.State =
        builder.State(
          id = 8,
          acceptF = Some {
            case \/-(_: NonTerminal.Expr_3) => s28
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_2) => s29
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s17
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s9: builder.State =
        builder.State(
          id = 9,
          acceptF = None,
          returnFs = List(
            {
              case elem(\/-(_3: NonTerminal.Expr_3)) :: elem(-\/(_2: Token.multOp)) :: stateElem(state, \/-(_1: NonTerminal.Expr_2)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_2._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s10: builder.State =
        builder.State(
          id = 10,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, -\/(_1: Token.int)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_4._2(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s11: builder.State =
        builder.State(
          id = 11,
          acceptF = None,
          returnFs = List(
            {
              case elem(\/-(_2: NonTerminal.AnonList1)) :: stateElem(state, -\/(_1: Token.__.`\n`)) :: stackT =>
                (
                  state,
                  NonTerminal.AnonList1._1(_1, _2),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s12: builder.State =
        builder.State(
          id = 12,
          acceptF = Some {
            case \/-(_: NonTerminal.Expr_3) => s13
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s17
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s13: builder.State =
        builder.State(
          id = 13,
          acceptF = None,
          returnFs = List(
            {
              case elem(\/-(_3: NonTerminal.Expr_3)) :: elem(-\/(_2: Token.powOp)) :: stateElem(state, \/-(_1: NonTerminal.Expr_4)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_3._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s14: builder.State =
        builder.State(
          id = 14,
          acceptF = None,
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = Some {
            case elem(-\/(Token.EOF)) :: elem(\/-(rawTree: NonTerminal.Lines)) :: Nil =>
              rawTree
          },
        )
      lazy val s15: builder.State =
        builder.State(
          id = 15,
          acceptF = Some {
            case \/-(_: NonTerminal.Lines_2) => s16
            case \/-(_: NonTerminal.AnonList1) => s18
            case -\/(_: Token.__.`\n`) => s6
          },
          returnFs = Nil,
          spontaneouslyGenerates = List(
            NonTerminal.Lines_2._2,
            NonTerminal.AnonList1._2,
          ),
          finalReturnF = None,
        )
      lazy val s16: builder.State =
        builder.State(
          id = 16,
          acceptF = None,
          returnFs = List(
            {
              case elem(\/-(_3: NonTerminal.Lines_2)) :: elem(\/-(_2: NonTerminal.Line)) :: stateElem(state, \/-(_1: NonTerminal.AnonList1)) :: stackT =>
                (
                  state,
                  NonTerminal.Lines._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s17: builder.State =
        builder.State(
          id = 17,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, -\/(_1: Token._var)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_4._4(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s18: builder.State =
        builder.State(
          id = 18,
          acceptF = Some {
            case \/-(_: NonTerminal.Assign) => s20
            case \/-(_: NonTerminal.Expr_3) => s28
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case -\/(_: Token.comment) => s19
            case \/-(_: NonTerminal.Expr) => s7
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_2) => s4
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s22
            case \/-(_: NonTerminal.Line) => s25
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s19: builder.State =
        builder.State(
          id = 19,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, -\/(_1: Token.comment)) :: stackT =>
                (
                  state,
                  NonTerminal.Line._3(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s20: builder.State =
        builder.State(
          id = 20,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, \/-(_1: NonTerminal.Assign)) :: stackT =>
                (
                  state,
                  NonTerminal.Line._2(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s21: builder.State =
        builder.State(
          id = 21,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, -\/(_1: Token.float)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_4._3(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s22: builder.State =
        builder.State(
          id = 22,
          acceptF = Some {
            case -\/(_: Token.__.`=`) => s23
          },
          returnFs = List(
            {
              case stateElem(state, -\/(_1: Token._var)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_4._4(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s23: builder.State =
        builder.State(
          id = 23,
          acceptF = Some {
            case \/-(_: NonTerminal.Expr_3) => s28
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case \/-(_: NonTerminal.Expr) => s24
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_2) => s4
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s17
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s24: builder.State =
        builder.State(
          id = 24,
          acceptF = Some {
            case -\/(_: Token.addOp) => s8
          },
          returnFs = List(
            {
              case elem(\/-(_3: NonTerminal.Expr)) :: elem(-\/(_2: Token.__.`=`)) :: stateElem(state, -\/(_1: Token._var)) :: stackT =>
                (
                  state,
                  NonTerminal.Assign._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s25: builder.State =
        builder.State(
          id = 25,
          acceptF = Some {
            case \/-(_: NonTerminal.AnonList1) => s26
            case -\/(_: Token.__.`\n`) => s6
          },
          returnFs = Nil,
          spontaneouslyGenerates = List(
            NonTerminal.AnonList1._2,
          ),
          finalReturnF = None,
        )
      lazy val s26: builder.State =
        builder.State(
          id = 26,
          acceptF = Some {
            case \/-(_: NonTerminal.Lines_2) => s27
            case \/-(_: NonTerminal.AnonList1) => s18
            case -\/(_: Token.__.`\n`) => s6
          },
          returnFs = Nil,
          spontaneouslyGenerates = List(
            NonTerminal.Lines_2._2,
            NonTerminal.AnonList1._2,
          ),
          finalReturnF = None,
        )
      lazy val s27: builder.State =
        builder.State(
          id = 27,
          acceptF = None,
          returnFs = List(
            {
              case elem(\/-(_4: NonTerminal.Lines_2)) :: elem(\/-(_3: NonTerminal.AnonList1)) :: elem(\/-(_2: NonTerminal.Line)) :: stateElem(state, \/-(_1: NonTerminal.AnonList1)) :: stackT =>
                (
                  state,
                  NonTerminal.Lines_2._1(_1, _2, _3, _4),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s28: builder.State =
        builder.State(
          id = 28,
          acceptF = None,
          returnFs = List(
            {
              case stateElem(state, \/-(_1: NonTerminal.Expr_3)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_2._2(_1),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s29: builder.State =
        builder.State(
          id = 29,
          acceptF = Some {
            case -\/(_: Token.multOp) => s5
          },
          returnFs = List(
            {
              case elem(\/-(_3: NonTerminal.Expr_2)) :: elem(-\/(_2: Token.addOp)) :: stateElem(state, \/-(_1: NonTerminal.Expr)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s30: builder.State =
        builder.State(
          id = 30,
          acceptF = Some {
            case \/-(_: NonTerminal.Expr_3) => s28
            case -\/(_: Token.int) => s10
            case -\/(_: Token.__.`(`) => s30
            case \/-(_: NonTerminal.Expr) => s31
            case -\/(_: Token.float) => s21
            case \/-(_: NonTerminal.Expr_2) => s4
            case \/-(_: NonTerminal.Expr_4) => s3
            case -\/(_: Token._var) => s17
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s31: builder.State =
        builder.State(
          id = 31,
          acceptF = Some {
            case -\/(_: Token.addOp) => s8
            case -\/(_: Token.__.`)`) => s32
          },
          returnFs = Nil,
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )
      lazy val s32: builder.State =
        builder.State(
          id = 32,
          acceptF = None,
          returnFs = List(
            {
              case elem(-\/(_3: Token.__.`)`)) :: elem(\/-(_2: NonTerminal.Expr)) :: stateElem(state, -\/(_1: Token.__.`(`)) :: stackT =>
                (
                  state,
                  NonTerminal.Expr_4._1(_1, _2, _3),
                  stackT,
                )
            },
          ),
          spontaneouslyGenerates = Nil,
          finalReturnF = None,
        )

      s0
    } {
      case (t1 @ HasSpanToken(s1), t2 @ HasSpanToken(s2)) =>
        (s2.start.abs > s1.start.abs).fold(t2, t1)
      case (eof @ Token.EOF, _) =>
        eof
      case (_, eof) =>
        eof
    }

  override def apply(input: String): List[String] \/ NonTerminal.Lines =
    arch.Parser(lexer, grammar)(input)

}
