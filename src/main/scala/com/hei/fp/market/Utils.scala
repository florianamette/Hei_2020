package com.hei.fp.market

trait Utils {

  def Try[A](block: => A): Either[Exception, A] =
    try Right(block)
    catch { case e: Exception =>
      Left(e) }

  def runOrCatchExceptions[A](block: => A ) = Try(block) match {
    case Right(block)  => block
    case Left(e)  => println("!!!! ERROR : " + e)
  }

}





