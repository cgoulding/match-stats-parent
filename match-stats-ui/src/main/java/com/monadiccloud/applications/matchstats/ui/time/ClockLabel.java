/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 */

package com.monadiccloud.applications.matchstats.ui.time;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * @author Connor Goulding
 */
public class ClockLabel
{
    private Label timerLabel = new Label();

    public ClockLabel(ClockTime clock)
    {
        timerLabel.textProperty().bind(clock.getDisplay());
        timerLabel.setTextFill(Color.RED);
    }

    public Label getTimerLabel()
    {
        return timerLabel;
    }
}
