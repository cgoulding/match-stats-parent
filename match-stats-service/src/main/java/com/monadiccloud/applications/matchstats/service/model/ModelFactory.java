/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service.model;

import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;

/**
 * @author Connor Goulding
 */
public class ModelFactory
{

    public Team createTeam(String name, TeamSourceType teamSource)
    {
        return new Team(name, teamSource);
    }

    public Player createPlayer(String name, Team team, PositionType position)
    {
        return new Player(name, team, position);
    }

    public Match createMatch(Team ourTeam, Team otherTeam)
    {
        return new Match(ourTeam, otherTeam);
    }
}
