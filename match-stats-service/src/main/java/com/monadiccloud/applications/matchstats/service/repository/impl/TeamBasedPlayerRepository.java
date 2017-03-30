/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 */

package com.monadiccloud.applications.matchstats.service.repository.impl;

import com.monadiccloud.applications.matchstats.service.model.Player;
import com.monadiccloud.applications.matchstats.service.model.Team;
import com.monadiccloud.applications.matchstats.service.repository.PlayerRepository;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Connor Goulding
 */
public class TeamBasedPlayerRepository extends AbstractLuceneIndex<Player> implements PlayerRepository
{
    private Map<String, Player> players = new HashMap<>();

    public TeamBasedPlayerRepository(Team... teams)
    {
        for (Team team : teams)
        {
            players.putAll(team.getPlayers().stream().collect(Collectors.toMap(Player::getUuid, Function.identity())));
        }
        super.initialise();
    }

    public TeamBasedPlayerRepository(Directory index, Team... teams)
    {
        super(index);
        for (Team team : teams)
        {
            players.putAll(team.getPlayers().stream().collect(Collectors.toMap(Player::getUuid, Function.identity())));
        }
    }

    public TeamBasedPlayerRepository(String path, Team... teams) throws IOException
    {
        super(path);
        for (Team team : teams)
        {
            players.putAll(team.getPlayers().stream().collect(Collectors.toMap(Player::getUuid, Function.identity())));
        }
    }

    @Override
    public Collection<Player> searchPlayer(String term)
    {
        return search("_searchable", term);
    }

    @Override
    protected Document transform(Player object)
    {
        Document doc = new Document();
        doc.add(new StringField("uuid", object.getUuid(), Field.Store.YES));
        return asSearchable(doc, "_searchable", Arrays.asList(object.getName(),
                object.getPosition().toString(), object.getTeam().getName()));
    }

    @Override
    protected Player transform(Document object)
    {
        return players.get(object.getField("uuid").stringValue());
    }

    @Override
    protected Collection<Player> readAll()
    {
        if (players == null)
        {
            return Collections.emptyList();
        }
        else
        {
            return players.values();
        }

    }
}
