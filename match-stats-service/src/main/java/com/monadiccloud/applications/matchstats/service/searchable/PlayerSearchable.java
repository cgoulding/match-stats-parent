package com.monadiccloud.applications.matchstats.service.searchable;

import com.monadiccloud.applications.matchstats.service.model.Player;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface PlayerSearchable
{
    Collection<Player> searchPlayer(String input);
}
