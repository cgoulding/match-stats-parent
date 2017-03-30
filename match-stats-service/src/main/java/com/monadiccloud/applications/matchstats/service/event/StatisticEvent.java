package com.monadiccloud.applications.matchstats.service.event;

import com.monadiccloud.applications.matchstats.service.model.Player;
import com.monadiccloud.applications.matchstats.service.model.Team;
import com.monadiccloud.applications.matchstats.service.statistic.AbstractStatistic;
import javafx.util.Duration;

import java.util.Date;

/**
 * @author Connor Goulding
 */
public class StatisticEvent<S extends AbstractStatistic> implements Displayable
{

    private Date   time;
    private Duration duration;
    private Team   team;
    private Player player;
    private S      statistic;

    public StatisticEvent(Date time, Duration duration, Team team, Player player, S statistic)
    {
        this.time = time;
        this.duration = duration;
        this.team = team;
        this.player = player;
        this.statistic = statistic;
    }

    @Override
    public Date getTime()
    {
        return time;
    }

    @Override
    public Duration getDuration()
    {
        return duration;
    }

    public Team getTeam()
    {
        return team;
    }

    public Player getPlayer()
    {
        return player;
    }

    public S getStatistic()
    {
        return statistic;
    }

    @Override
    public String getDisplay()
    {
        return statistic.getName();
    }
}
