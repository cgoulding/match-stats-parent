package com.monadiccloud.applications.matchstats.service.repository;

import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface ScoreStatisticsRepository
{
    Collection<ScoreStatistic> searchScores(String term);
}
