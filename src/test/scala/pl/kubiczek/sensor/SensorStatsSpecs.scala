package pl.kubiczek.sensor

import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

class SensorStatsSpecs extends SpecificationWithJUnit {

  "Sensor Stats" should {
    "add measurement" in new EmptySensorStatsScope {
      emptySensorStats.add(Measurement("s1", 10)) === SensorStats("s1", 10, 10, 10, 1, 0)
    }

    "throw exception when adding measurement with invalid identifier" in new EmptySensorStatsScope {
      emptySensorStats.add(Measurement("invalid", 10)) must throwA[AssertionError]
    }

    "add multiple measurements" in new EmptySensorStatsScope {
      emptySensorStats
        .add(Measurement("s1", 10))
        .add(Measurement("s1", 98)) ===
        SensorStats("s1", 10, 54, 98, 2, 0)
    }
  }

  trait EmptySensorStatsScope extends Scope {
    val emptySensorStats = SensorStats.emptySensorStats("s1")
  }

}
