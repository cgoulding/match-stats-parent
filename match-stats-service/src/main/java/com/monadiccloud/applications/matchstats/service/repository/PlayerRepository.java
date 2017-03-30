package com.monadiccloud.applications.matchstats.service.repository;

import com.monadiccloud.applications.matchstats.service.model.Player;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface PlayerRepository
{
    Collection<Player> searchPlayer(String term);
}
