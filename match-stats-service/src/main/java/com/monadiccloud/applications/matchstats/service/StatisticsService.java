package com.monadiccloud.applications.matchstats.service;

import com.monadiccloud.applications.matchstats.service.model.Match;

/**
 * @author Connor Goulding
 */
public interface StatisticsService
{
    StatisticsContext start(Match match);
}
