package com.monadiccloud.applications.matchstats.ui;

import com.monadiccloud.applications.matchstats.service.DefaultStatisticsService;
import com.monadiccloud.applications.matchstats.service.StatisticsContext;
import com.monadiccloud.applications.matchstats.service.event.ClockEvent;
import com.monadiccloud.applications.matchstats.service.event.Displayable;
import com.monadiccloud.applications.matchstats.service.event.StatisticEvent;
import com.monadiccloud.applications.matchstats.service.model.Match;
import com.monadiccloud.applications.matchstats.service.model.ModelFactory;
import com.monadiccloud.applications.matchstats.service.model.Player;
import com.monadiccloud.applications.matchstats.service.model.PositionType;
import com.monadiccloud.applications.matchstats.service.model.Team;
import com.monadiccloud.applications.matchstats.service.repository.ActionStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.repository.PlayerRepository;
import com.monadiccloud.applications.matchstats.service.repository.ScoreStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.repository.impl.StaticActionStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.repository.impl.StaticScoreStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.repository.impl.TeamBasedPlayerRepository;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;
import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;
import com.monadiccloud.applications.matchstats.ui.time.ClockLabel;
import com.monadiccloud.applications.matchstats.ui.time.ClockTime;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;

/**
 * @author Connor Goulding
 */
public class MatchStatsApplication extends Application
{
    private ClockTime clockTime = new ClockTime();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        ModelFactory modelFactory = new ModelFactory();

        Team them = new Team("Tallow", TeamSourceType.THEM);
        Team us = new Team("Shamrocks", TeamSourceType.US);

        them.addPlayer(modelFactory.createPlayer("<THEM>", them, PositionType.UNDEFINED));

        us.addPlayer(modelFactory.createPlayer("<US>", us, PositionType.UNDEFINED));
        us.addPlayer(modelFactory.createPlayer("John Donovan", us, PositionType.FULL_BACK));
        us.addPlayer(modelFactory.createPlayer("Eoin Murphy", us, PositionType.CENTRE_FORWARD));
        us.addPlayer(modelFactory.createPlayer("Joe Leahy", us, PositionType.MID_FIELD));

        Match match = new Match(us, them);

        ActionStatisticsRepository actionStatisticsRepository = new StaticActionStatisticsRepository();
        ScoreStatisticsRepository scoreStatisticsRepository = new StaticScoreStatisticsRepository();
        PlayerRepository playerRepository = new TeamBasedPlayerRepository(them, us);

        DefaultStatisticsService statisticsService = new DefaultStatisticsService(actionStatisticsRepository, scoreStatisticsRepository,
                playerRepository);

        StatisticsContext context = statisticsService.start(match);

        Pane inputPane = createInputPane(statisticsService, context);
        Pane buttonPane = createButtonPane(context);
        Pane tablePane = createTablePane(context);

        Scene scene = new Scene(new Group(), 450, 250);
        Group root = (Group) scene.getRoot();
        GridPane gridPane = new GridPane();
        gridPane.setVgap(4);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.add(inputPane, 0, 0);
        gridPane.add(buttonPane, 0, 1);
        gridPane.add(tablePane, 0, 2);

        root.getChildren().add(gridPane);

        stage.setScene(scene);
        stage.show();
    }

    private Pane createInputPane(DefaultStatisticsService statisticsService, StatisticsContext context)
    {
        AutoCompleteTextField<ScoreStatistic> score = new AutoCompleteTextField<>(statisticsService::searchScores, ScoreStatistic::getName);
        AutoCompleteTextField<ActionStatistic> action = new AutoCompleteTextField<>(statisticsService::searchActions, ActionStatistic::getName);
        AutoCompleteTextField<Player> player = new AutoCompleteTextField<>(statisticsService::searchPlayer, Player::getName);

        Button addScore = new Button("Add");
        addScore.setOnAction(event -> context.addScore(clockTime.getDuration(), score.getResolved(), player.getResolved()));

        Button addAction = new Button("Add");
        addAction.setOnAction(event -> context.addAction(clockTime.getDuration(), action.getResolved(), player.getResolved()));

        GridPane inputGrid = new GridPane();
        inputGrid.setVgap(4);
        inputGrid.setHgap(10);
        inputGrid.setPadding(new Insets(5, 5, 5, 5));

        inputGrid.add(new Label("Player:"), 0, 0);
        inputGrid.add(player, 1, 0);

        inputGrid.add(new Label("Score: "), 0, 1);
        inputGrid.add(score, 1, 1);
        inputGrid.add(addScore, 2, 1);

        inputGrid.add(new Label("Action: "), 0, 2);
        inputGrid.add(action, 1, 2);
        inputGrid.add(addAction, 2, 2);
        return inputGrid;
    }

    private Pane createButtonPane(StatisticsContext context)
    {
        ClockLabel clockLabel = new ClockLabel(clockTime);

        Button start = new Button("Start");
        start.setOnAction(event -> {
            context.start(clockTime.getDuration());
            clockTime.start();
        });

        Button endFirst = new Button("End First Half");
        endFirst.setOnAction(event -> {
            context.endFirstHalf(clockTime.getDuration());
            clockTime.pause();
        });

        Button startSecond = new Button("Start Second Half");
        startSecond.setOnAction(event -> {
            context.startSecondHalf(clockTime.getDuration());
            clockTime.unpause();
        });

        Button endSecond = new Button("End Second Half");
        endSecond.setOnAction(event -> {
            context.endSecondHalf(clockTime.getDuration());
            clockTime.pause();
        });

        GridPane buttonPane = new GridPane();
        buttonPane.setVgap(4);
        buttonPane.setHgap(10);
        buttonPane.setPadding(new Insets(5, 5, 5, 5));
        buttonPane.add(new Label("Clock: "), 0, 0);
        buttonPane.add(clockLabel.getTimerLabel(), 1, 0);
        buttonPane.add(start, 0, 1);
        buttonPane.add(endFirst, 1, 1);
        buttonPane.add(startSecond, 2, 1);
        buttonPane.add(endSecond, 3, 1);

        return buttonPane;
    }

    private Pane createTablePane(StatisticsContext context)
    {
        TableView<StatisticEvent<ActionStatistic>> actions = createStatisticEventTableView(context.getActions());
        TableView<StatisticEvent<ScoreStatistic>> scores = createStatisticEventTableView(context.getScores());
        TableView<ClockEvent> clocks = createStatisticEventTableView(context.getClocks());


        GridPane tablePane = new GridPane();
        tablePane.setVgap(4);
        tablePane.setHgap(10);
        tablePane.setPadding(new Insets(5, 5, 5, 5));
        tablePane.add(actions, 0, 0);
        tablePane.add(scores, 1, 0);
        tablePane.add(clocks, 0, 1);
        return tablePane;
    }

    private <E extends Displayable> TableView<E> createStatisticEventTableView(ObservableList<E> observables)
    {
        TableView<E> actions = new TableView();

        TableColumn<E, Date> time = new TableColumn<>("Time");
        time.setPrefWidth(120);
        time.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<E, Duration> duration = new TableColumn<>("Duration");
        duration.setPrefWidth(120);
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<E, String> display = new TableColumn<>("Name");
        display.setPrefWidth(300);
        display.setCellValueFactory(new PropertyValueFactory<>("display"));

        actions.getColumns().addAll(time, duration, display);
        actions.setItems(observables);
        return actions;
    }
}
