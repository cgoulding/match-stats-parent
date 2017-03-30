package com.monadiccloud.applications.matchstats.service.searchable;

import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface StatisticSearchable
{
    Collection<ActionStatistic> searchActions(String input);

    Collection<ScoreStatistic> searchScores(String input);
}
