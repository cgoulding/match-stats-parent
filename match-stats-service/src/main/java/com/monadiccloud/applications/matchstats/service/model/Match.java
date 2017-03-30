package com.monadiccloud.applications.matchstats.service.model;

/**
 * @author Connor Goulding
 */
public class Match
{

    private Team ourTeam;
    private Team theirTeam;

    public Match(Team ourTeam, Team theirTeam)
    {
        this.ourTeam = ourTeam;
        this.theirTeam = theirTeam;
    }

    public Team getOurTeam()
    {
        return ourTeam;
    }

    public Team getTheirTeam()
    {
        return theirTeam;
    }
}
