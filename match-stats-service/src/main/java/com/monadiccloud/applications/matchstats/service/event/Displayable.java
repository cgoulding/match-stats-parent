/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 */

package com.monadiccloud.applications.matchstats.service.event;

import javafx.util.Duration;

import java.util.Date;

/**
 * @author Connor Goulding
 */
public interface Displayable
{
    String getDisplay();

    Duration getDuration();

    Date getTime();
}
