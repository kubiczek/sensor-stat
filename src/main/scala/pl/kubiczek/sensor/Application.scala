package pl.kubiczek.sensor

import java.io.File

import scala.io.Source

object Application extends App {
  if (args.length == 0) {
    System.err.println("Missing program argument")
    System.exit(-1)
  }
  val inputDirectory = new File(args(0))
  if (!inputDirectory.exists() || !inputDirectory.isDirectory) {
    System.err.println(s"Invalid argument: $inputDirectory")
    System.exit(-2)
  }

  var report = DailyReport.emptyDailyReport
  inputDirectory.listFiles().foreach(processInputFile(_))
  println(report)

  def processInputFile(f: File) = {
    val bufferedSource = Source.fromFile(f)
    for (line <- bufferedSource.getLines().drop(1)) {
      report = report.add(Measurement.fromCsvRow(line))
    }
    bufferedSource.close()
    report = report.incProcessedFiles()
  }
}