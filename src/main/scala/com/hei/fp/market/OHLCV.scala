package com.hei.fp.market

import java.sql.Date

case class OHLCV(date: Date, open: Double, high: Double, low: Double, close: Double, volume: Double)
