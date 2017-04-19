/**
  * @author Connor Goulding
  */

import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic
import com.monadiccloud.applications.matchstats.service.statistic.{OutcomeType, TeamSourceType};

object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello, world!");

    val stat : ActionStatistic = new ActionStatistic("ballyboys", TeamSourceType.THEM, OutcomeType.POSITIVE);
    println(stat.getName());
  }
}