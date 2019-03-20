package pl.kubiczek.sensor

object SensorStats {
  def emptySensorStats(sensorId: String) = SensorStats(sensorId, Int.MaxValue, -1, Int.MinValue, 0, 0)
}

case class SensorStats(sensorId: String,
                       min: Int,
                       avg: Double,
                       max: Int,
                       processedMeasurements: Int,
                       failedMeasurements: Int) {

  def add(m: Measurement): SensorStats = {
    assert(sensorId.equals(m.sensorId), s"Measurement identifier mismatch: $sensorId != ${m.sensorId}")

    copy(
      min = Math.min(min, m.humidity),
      avg = (avg * (processedMeasurements - failedMeasurements) + m.humidity) / (processedMeasurements - failedMeasurements + 1),
      max = Math.max(max, m.humidity),
      processedMeasurements = processedMeasurements + 1
    )
  }

  def incFailedMeasurement(): SensorStats =
    copy(
      processedMeasurements = processedMeasurements + 1,
      failedMeasurements = failedMeasurements + 1
    )

  override def toString: String =
    if (processedMeasurements == failedMeasurements)
      s"$sensorId,NaN,NaN,NaN"
    else
      s"$sensorId,$min,$avg,$max"
}