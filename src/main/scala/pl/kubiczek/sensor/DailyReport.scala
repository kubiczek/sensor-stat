package pl.kubiczek.sensor

object DailyReport {
  val emptyDailyReport = DailyReport(Map(), 0, 0, 0)
}

case class DailyReport(sensors: Map[String, SensorStats],
                       processedFiles: Int,
                       processedMeasurements: Int,
                       failedMeasurement: Int) {

  def add(m: Measurement): DailyReport = {
    val current = sensors.getOrElse(m.sensorId, SensorStats.emptySensorStats(m.sensorId))
    if (m.humidity < 0 || m.humidity > 100)
      copy(
        sensors = sensors + (m.sensorId -> current.incFailedMeasurement()),
        processedMeasurements = processedMeasurements + 1,
        failedMeasurement = failedMeasurement + 1
      )
    else
      copy(
        sensors = sensors + (m.sensorId -> current.add(m)),
        processedMeasurements = processedMeasurements + 1
      )
  }

  def incProcessedFiles() =
    copy(processedFiles = processedFiles + 1)

  override def toString() =
    s"""
       |Num of processed files: $processedFiles
       |Num of processed measurements: $processedMeasurements
       |Num of failed measurements: $failedMeasurement
       |
       |Sensors with highest avg humidity:
       |
       |sensor-id,min,avg,max
       |${sensors.values.toList.sortWith(_.avg > _.avg).map(_.toString).mkString("\n")}
    """.stripMargin
}
