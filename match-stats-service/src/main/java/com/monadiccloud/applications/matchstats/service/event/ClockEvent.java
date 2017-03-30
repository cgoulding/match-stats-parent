package com.monadiccloud.applications.matchstats.service.event;

import javafx.util.Duration;

import java.util.Date;

/**
 * @author Connor Goulding
 */
public class ClockEvent implements Displayable
{
    private Date     time;
    private Duration duration;
    private Clock    clock;

    public ClockEvent(Date time, Duration duration, Clock clock)
    {
        this.time = time;
        this.duration = duration;
        this.clock = clock;
    }

    @Override
    public String getDisplay()
    {
        return clock.toString();
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

    public enum Clock
    {
        START,
        FIRST_HALF_START,
        FIRST_HALF_STOP,
        SECOND_HALF_START,
        SECOND_HALF_STOP,
        PAUSE,
        STOP;
    }
}
