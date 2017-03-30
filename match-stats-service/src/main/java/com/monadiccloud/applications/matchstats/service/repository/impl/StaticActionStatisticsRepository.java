/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service.repository.impl;

import com.monadiccloud.applications.matchstats.service.repository.ActionStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.statistic.OutcomeType;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;
import com.monadiccloud.applications.matchstats.service.statistic.action.ActionStatistic;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.monadiccloud.applications.matchstats.service.statistic.OutcomeType.NEGATIVE;
import static com.monadiccloud.applications.matchstats.service.statistic.OutcomeType.POSITIVE;
import static com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType.THEM;
import static com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType.US;

/**
 * @author Connor Goulding
 */
public class StaticActionStatisticsRepository extends AbstractLuceneIndex<ActionStatistic> implements ActionStatisticsRepository
{

    public static final String NAME    = "name";
    public static final String TEAM    = "team";
    public static final String OUTCOME = "outcome";

    public StaticActionStatisticsRepository()
    {
    }

    public StaticActionStatisticsRepository(Directory index)
    {
        super(index);
    }

    public StaticActionStatisticsRepository(String path) throws IOException
    {
        super(path);
    }

    @Override
    protected Document transform(ActionStatistic object)
    {
        Document document = new Document();
        document.add(new StringField(NAME, object.getName(), Field.Store.YES));
        document.add(new StringField(TEAM, object.getTeam().toString(), Field.Store.YES));
        document.add(new StringField(OUTCOME, object.getOutcome().toString(), Field.Store.YES));
        return asSearchable(document, "_searchable", Arrays.asList(object.getName().toLowerCase()));
    }

    @Override
    protected ActionStatistic transform(Document object)
    {
        ActionStatistic statistic = new ActionStatistic(object.getField(NAME).stringValue(),
                TeamSourceType.valueOf(object.getField(TEAM).stringValue()), OutcomeType.valueOf(object.getField(OUTCOME).stringValue()));
        return statistic;
    }

    @Override
    public Collection<ActionStatistic> searchActions(String term)
    {
        return search("_searchable", term);
    }

    @Override
    public Collection<ActionStatistic> readAll()
    {
        return Arrays.asList(
                new ActionStatistic("Hook won", US, POSITIVE),
                new ActionStatistic("Block won", US, POSITIVE),
                new ActionStatistic("Free won", US, POSITIVE),
                new ActionStatistic("Overturn won", US, POSITIVE),

                new ActionStatistic("Hook lost", THEM, NEGATIVE),
                new ActionStatistic("Block lost", THEM, NEGATIVE),
                new ActionStatistic("Free lost", THEM, NEGATIVE),
                new ActionStatistic("Overturn lost", THEM, NEGATIVE),

                new ActionStatistic("Own puckout won", US, POSITIVE),
                new ActionStatistic("Own puckout lost", THEM, NEGATIVE),
                new ActionStatistic("Their puckout won", US, POSITIVE),
                new ActionStatistic("Their puckout lost", THEM, NEGATIVE),

                new ActionStatistic("Save", US, POSITIVE),
                new ActionStatistic("Wide - them", US, NEGATIVE),
                new ActionStatistic("Wide - us", THEM, POSITIVE));
    }
}
