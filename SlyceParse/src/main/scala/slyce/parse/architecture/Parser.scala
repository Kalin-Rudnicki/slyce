package slyce.parse.architecture

import slyce.common.architecture.Stage

trait Parser[Src, Errs, SimpleTree] extends Stage[Src, Errs, SimpleTree]
object Parser {

  def apply[Errs, Src, Tok, RawTree, SimpleTree, Formatter](
      lexer: Lexer[Src, Errs, Tok],
      grammar: Grammar[Tok, Errs, RawTree],
      simplifier: Simplifier[RawTree, Errs, SimpleTree],
      formatter: ErrorFormatter[Src, Errs],
  ): Parser[Src, String, SimpleTree] =
    (lexer >+> grammar >+> simplifier <<+< formatter)(_)

}