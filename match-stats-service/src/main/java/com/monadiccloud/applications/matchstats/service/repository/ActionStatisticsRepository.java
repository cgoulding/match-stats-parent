package com.monadiccloud.applications.matchstats.service.repository;

import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface ActionStatisticsRepository
{
    Collection<ActionStatistic> searchActions(String term);
}
