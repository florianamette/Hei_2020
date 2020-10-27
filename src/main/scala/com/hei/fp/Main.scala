package com.hei.fp

import com.hei.fp.market.{Market, OHLCV, Statistics}

object Main {

  def main(args: Array[String]): Unit = {

    val marketList : List[String] = List("GLE.PA", "ACA.PA", "ERA.PA" , "VK.PA")
    val res = new Market(marketList)
    val value : Map[String, Array[OHLCV]] = res.marketValues

    //---> objectif récupérer le cours de clôture de la premier valeur de la Map Value
    val tickers: Seq[String] = value.keys.toList
    val firstVal : Array[OHLCV] = value.get(tickers.head) match { case Some(s) => s }
    val close : Array[Double] = firstVal.map(_.close)
    val mm20: Array[Double] = new Statistics(close).movingAverage(20)

    val indicators = close.zip(mm20)
      .zip(firstVal.map(_.date))

    indicators.foreach(println)

    println("debug")
  }


}
