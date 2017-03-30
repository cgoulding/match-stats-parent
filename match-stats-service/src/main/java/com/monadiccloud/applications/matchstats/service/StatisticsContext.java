/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service;

import com.monadiccloud.applications.matchstats.service.event.ClockEvent;
import com.monadiccloud.applications.matchstats.service.event.StatisticEvent;
import com.monadiccloud.applications.matchstats.service.model.Match;
import com.monadiccloud.applications.matchstats.service.model.Player;
import com.monadiccloud.applications.matchstats.service.model.Team;
import com.monadiccloud.applications.matchstats.service.statistic.AbstractStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;
import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.util.Duration;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Connor Goulding
 */
public class StatisticsContext
{
    private Match match;
    private ObservableList<ClockEvent>                      clocks  = FXCollections.observableArrayList();
    private ObservableList<StatisticEvent<ActionStatistic>> actions = FXCollections.observableArrayList();
    private ObservableList<StatisticEvent<ScoreStatistic>>  scores  = FXCollections.observableArrayList();

    public StatisticsContext(Match match)
    {
        this.match = match;
    }

    public void start(Duration duration)
    {
        clocks.add(new ClockEvent(time(), duration, ClockEvent.Clock.FIRST_HALF_START));
    }

    public void endFirstHalf(Duration duration)
    {
        clocks.add(new ClockEvent(time(), duration, ClockEvent.Clock.FIRST_HALF_STOP));
    }

    public void startSecondHalf(Duration duration)
    {
        clocks.add(new ClockEvent(time(), duration, ClockEvent.Clock.SECOND_HALF_START));
    }

    public void endSecondHalf(Duration duration)
    {
        clocks.add(new ClockEvent(time(), duration, ClockEvent.Clock.SECOND_HALF_STOP));
    }

    public void addAction(Duration duration, ActionStatistic action, Player player)
    {
        actions.add(new StatisticEvent<>(time(), duration, team(action, player), player, action));
    }

    public void addScore(Duration duration, ScoreStatistic score, Player player)
    {
        scores.add(new StatisticEvent<>(time(), duration, team(score, player), player, score));
    }

    private Date time()
    {
        return Calendar.getInstance().getTime();
    }

    private Team team(AbstractStatistic statistic, Player player)
    {
        if (player != null)
        {
            return player.getTeam();
        }
        return team(statistic.getTeam());
    }

    public ObservableList<ClockEvent> getClocks()
    {
        return clocks;
    }

    public ObservableList<StatisticEvent<ActionStatistic>> getActions()
    {
        return actions;
    }

    public ObservableList<StatisticEvent<ScoreStatistic>> getScores()
    {
        return scores;
    }

    public Team team(TeamSourceType teamSource)
    {
        switch(teamSource) {
            case US: return match.getOurTeam();
            case THEM: return match.getTheirTeam();
            default: return null;
        }
    }
}
