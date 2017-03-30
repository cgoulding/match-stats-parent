package com.monadiccloud.applications.matchstats.service.searchable;

import com.monadiccloud.applications.matchstats.service.model.Team;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface TeamSearchable
{
    Collection<Team> searchTeam(String input);
}
