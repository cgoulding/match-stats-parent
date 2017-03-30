package com.monadiccloud.applications.matchstats.service;

import com.monadiccloud.applications.matchstats.service.model.Match;
import com.monadiccloud.applications.matchstats.service.model.Player;
import com.monadiccloud.applications.matchstats.service.repository.ActionStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.repository.PlayerRepository;
import com.monadiccloud.applications.matchstats.service.repository.ScoreStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.searchable.PlayerSearchable;
import com.monadiccloud.applications.matchstats.service.searchable.StatisticSearchable;
import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;
import javafx.util.Duration;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public class DefaultStatisticsService implements StatisticsService, StatisticSearchable, PlayerSearchable
{
    private ActionStatisticsRepository actionStatisticsRepository;
    private ScoreStatisticsRepository  scoreStatisticsRepository;
    private PlayerRepository playerRepository;

    public DefaultStatisticsService(ActionStatisticsRepository actionStatisticsRepository,
            ScoreStatisticsRepository scoreStatisticsRepository, PlayerRepository playerRepository)
    {
        this.actionStatisticsRepository = actionStatisticsRepository;
        this.scoreStatisticsRepository = scoreStatisticsRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public StatisticsContext start(Match match)
    {
        StatisticsContext context = new StatisticsContext(match);
        context.start(Duration.ZERO);
        return context;
    }

    @Override
    public Collection<ActionStatistic> searchActions(String input)
    {
        return actionStatisticsRepository.searchActions(input);
    }

    @Override
    public Collection<ScoreStatistic> searchScores(String input)
    {
        return scoreStatisticsRepository.searchScores(input);
    }

    @Override
    public Collection<Player> searchPlayer(String input)
    {
        return playerRepository.searchPlayer(input);
    }
}
