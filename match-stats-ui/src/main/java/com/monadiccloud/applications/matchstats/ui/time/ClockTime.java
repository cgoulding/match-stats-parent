package com.monadiccloud.applications.matchstats.ui.time;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author Connor Goulding
 */
public class ClockTime
{
    private Timeline timeline;
    private Duration             time    = Duration.ZERO;
    private SimpleStringProperty display = new SimpleStringProperty("00:00:00");

    public void start()
    {
        if (timeline == null)
        {
            timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent t)
                {
                    Duration duration = ((KeyFrame) t.getSource()).getTime();
                    time = time.add(duration);
                    display.set(time((long) time.toMillis()));
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    public void pause()
    {
        if (timeline != null)
        {
            timeline.pause();
        }
    }

    public void unpause()
    {
        if (timeline != null)
        {
            timeline.play();
        }
    }

    public ObservableValue<String> getDisplay()
    {
        return display;
    }

    public Duration getDuration()
    {
        return this.time;
    }

    private String time(long millis)
    {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
