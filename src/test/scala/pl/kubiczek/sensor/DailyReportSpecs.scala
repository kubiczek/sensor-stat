package pl.kubiczek.sensor

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

class DailyReportSpecs extends SpecificationWithJUnit {

  "Daily Report" should {
    "add measurement" in {
      DailyReport.emptyDailyReport
        .add(Measurement("s1", 10))
        .incProcessedFiles() ===
        DailyReport(Map("s1" -> SensorStats("s1", 10, 10.0, 10, 1, 0)), 1, 1, 0)
    }

    "add failed and successful measurement" in {
      DailyReport.emptyDailyReport
        .add(Measurement("s1", -1))
        .add(Measurement("s1", 10))
        .incProcessedFiles() ===
        DailyReport(Map("s1" -> SensorStats("s1", 10, 10.0, 10, 2, 1)), 1, 2, 1)
    }

    "add multiple successful and failed measurements" in {
      DailyReport.emptyDailyReport
        .add(Measurement("s1", 10))
        .add(Measurement("s2", 88))
        .add(Measurement("s1", -1))
        .incProcessedFiles()
        .add(Measurement("s2", 80))
        .add(Measurement("s3", -1))
        .add(Measurement("s2", 78))
        .add(Measurement("s1", 98))
        .incProcessedFiles() ===
      DailyReport(Map(
          "s1" -> SensorStats("s1", 10, 54.0, 98, 3, 1),
          "s2" -> SensorStats("s2", 78, 82.0, 88, 3, 0),
          "s3" -> SensorStats("s3", 2147483647, -1.0, -2147483648, 1, 1)
        ), 2, 7, 2)
    }
  }
}
