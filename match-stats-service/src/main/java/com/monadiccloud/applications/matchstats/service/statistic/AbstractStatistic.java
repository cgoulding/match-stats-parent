package com.monadiccloud.applications.matchstats.service.statistic;

import java.util.UUID;

/**
 * @author Connor Goulding
 */
public abstract class AbstractStatistic
{

    private String         uuid;
    private String         name;
    private TeamSourceType team;
    private OutcomeType    outcome;

    public AbstractStatistic(String name, TeamSourceType team, OutcomeType outcome)
    {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.team = team;
        this.outcome = outcome;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public TeamSourceType getTeam()
    {
        return team;
    }

    public OutcomeType getOutcome()
    {
        return outcome;
    }

}
