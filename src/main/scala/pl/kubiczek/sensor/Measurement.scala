package pl.kubiczek.sensor

object Measurement {
  def fromCsvRow(row: String) = {
    val columns = row.split(",").map(_.trim)
    val humidity = try {
      columns(1).toInt
    } catch {
      case _: Exception => -1
    }
    Measurement(columns(0), humidity)
  }
}

case class Measurement(sensorId: String, humidity: Int)