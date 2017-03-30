package com.monadiccloud.applications.matchstats.service.model;

import java.util.UUID;

/**
 * @author Connor Goulding
 */
public class Player
{
    private String uuid = UUID.randomUUID().toString();
    private String       name;
    private Team         team;
    private PositionType position;

    public Player(String name, Team team, PositionType position)
    {
        this.name = name;
        this.team = team;
        this.position = position;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public Team getTeam()
    {
        return team;
    }

    public PositionType getPosition()
    {
        return position;
    }
}
