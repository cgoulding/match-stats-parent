/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

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
