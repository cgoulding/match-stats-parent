package com.monadiccloud.applications.matchstats.service.statistic.score;

import com.monadiccloud.applications.matchstats.service.statistic.AbstractStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.OutcomeType;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;

/**
 * @author Connor Goulding
 */
public class ScoreStatistic extends AbstractStatistic
{
    private ScoreType       score;
    private ScoreSourceType source;

    public ScoreStatistic(String name, TeamSourceType team, OutcomeType outcome, ScoreType score, ScoreSourceType source)
    {
        super(name, team, outcome);
        this.score = score;
        this.source = source;
    }

    public ScoreType getScore()
    {
        return score;
    }

    public ScoreSourceType getSource()
    {
        return source;
    }
}
