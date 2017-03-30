package com.monadiccloud.applications.matchstats.service.model;

import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Connor Goulding
 */
public class Team
{
    private String         name;
    private TeamSourceType teamSourceType;
    private final Collection<Player> players = new ArrayList<>();

    public Team(String name, TeamSourceType teamSourceType)
    {
        this.name = name;
        this.teamSourceType = teamSourceType;
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }

    public String getName()
    {
        return name;
    }

    public TeamSourceType getTeamSourceType()
    {
        return teamSourceType;
    }

    public Collection<Player> getPlayers()
    {
        return players;
    }
}
