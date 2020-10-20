package com.hei.fp.market

class API {

  /**
   * @param ticker a ticker code
   * @return get historical values from one year
   */
  def getData(ticker: String): Array[Array[String]] = {

    val url = "https://in.finance.yahoo.com/quote/" + ticker + "/history?ltr=1"

    val response = requests.get(url)

    def findHistorical = response.text()
      .replace("]", "\n")
      .split("\n")
      .filter(x => x.contains("HistoricalPriceStore\":{\"prices\":"))
      .flatMap(x => x.split("}"))
      .map(_.replace(",\"HistoricalPriceStore\":{\"prices\":[{", ""))
      .map(_.replace(",{", ""))
      .map(_.replace(" ", ""))
      .map(_.replace("\"", ""))
      .drop(2)

    val kv: Array[Map[String, String]] =
      findHistorical.map(x => x.split(",")
        .map(x => {
          val y = x.split(":")
          (y(0), y(1))
        }).toMap)

    /*
     *  Deal with None type
     */
    def convert(s: Any): String = s match {
      case Some(value) => value.toString
      case None => "0"
    }

    val quote: Array[Array[String]] = kv.filter(_.contains("close"))
      .map(x => Array(convert(x.get("date")),
        convert(x.get("close")),
        convert(x.get("high")),
        convert(x.get("low")),
        convert(x.get("open")),
        convert(x.get("volume"))))
      .filter(!_.contains("null")) //  filter null values
      .sortBy(_ (0)) // ---- get time ordered

    println(s"--> $ticker")

    //quote.foreach(x => println(x.mkString("!")))

    quote
  }

}
