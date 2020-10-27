package com.hei.fp.market

import java.sql.Date

class Market(marketList : List[String]) extends API  with Utils {

  /*   old
  /**
   * Turn Array String to OHLCV values
   * @param ticker : tricker market place code "GLE.PA" societe générale
   * @return Array of OHLCV values from API
   */
  def getOHLCVData (ticker : String): Array[OHLCV] = getData(ticker)
    .map( x => new OHLCV ( new Date(x(0).toLong  * 1000 ), x(4).toDouble, x(2).toDouble, x(3).toDouble, x(1).toDouble, x(5).toDouble))

  val marketValues = runOrCatchExceptions(marketList.map(x => Map(x -> getOHLCVData(x))))
                          // match { case a : Map[String , Array[OHLCV]] => a }

  lazy val mkt = marketList.map(x => runOrCatchExceptions(getData(x)))
                            //    .collect { case a : Array[OHLCV] => a }

   */

  /**
   * Turn Array String to OHLCV values
   * @param ticker : tricker market place code "GLE.PA" societe générale
   * @return Array of OHLCV values from API
   */
  def getOHLCVData (ticker : String): Array[OHLCV] = try( getData(ticker)
    .map( x => new OHLCV ( new Date(x(0).toLong  * 1000 ), x(4).toDouble, x(2).toDouble, x(3).toDouble, x(1).toDouble, x(5).toDouble)) )
  catch {case e :ArrayIndexOutOfBoundsException => println(s"No response from API : $e")
    null
  }

  val marketValues : Map[String, Array[OHLCV]] = marketList
    .flatMap(x => Map(x -> getOHLCVData(x)))
    .toMap
    .filter(x => x._2 != null)

}
