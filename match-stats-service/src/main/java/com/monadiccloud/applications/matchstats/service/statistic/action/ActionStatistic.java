/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service.statistic.action;

import com.monadiccloud.applications.matchstats.service.statistic.AbstractStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.OutcomeType;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;

/**
 * @author Connor Goulding
 */
public class ActionStatistic extends AbstractStatistic
{
    public ActionStatistic(String name, TeamSourceType team, OutcomeType outcome)
    {
        super(name, team, outcome);
    }
}
