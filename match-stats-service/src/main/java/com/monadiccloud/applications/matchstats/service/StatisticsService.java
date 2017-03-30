/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service;

import com.monadiccloud.applications.matchstats.service.model.Match;

/**
 * @author Connor Goulding
 */
public interface StatisticsService
{
    StatisticsContext start(Match match);
}
