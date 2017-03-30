package com.monadiccloud.applications.matchstats.service.repository.impl;

import com.monadiccloud.applications.matchstats.service.repository.ScoreStatisticsRepository;
import com.monadiccloud.applications.matchstats.service.statistic.OutcomeType;
import com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreSourceType;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreStatistic;
import com.monadiccloud.applications.matchstats.service.statistic.score.ScoreType;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.monadiccloud.applications.matchstats.service.statistic.OutcomeType.NEGATIVE;
import static com.monadiccloud.applications.matchstats.service.statistic.OutcomeType.POSITIVE;
import static com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType.THEM;
import static com.monadiccloud.applications.matchstats.service.statistic.TeamSourceType.US;
import static com.monadiccloud.applications.matchstats.service.statistic.score.ScoreSourceType.*;
import static com.monadiccloud.applications.matchstats.service.statistic.score.ScoreType.GOAL;
import static com.monadiccloud.applications.matchstats.service.statistic.score.ScoreType.POINT;

/**
 * @author Connor Goulding
 */
public class StaticScoreStatisticsRepository extends AbstractLuceneIndex<ScoreStatistic> implements ScoreStatisticsRepository
{

    public static final String UUID    = "uuid";
    public static final String NAME    = "name";
    public static final String OUTCOME = "outcome";
    public static final String TEAM    = "team";
    public static final String SCORE   = "score";
    public static final String SOURCE  = "source";

    public StaticScoreStatisticsRepository()
    {
    }

    public StaticScoreStatisticsRepository(Directory index)
    {
        super(index);
    }

    public StaticScoreStatisticsRepository(String path) throws IOException
    {
        super(path);
    }

    @Override
    public Collection<ScoreStatistic> searchScores(String term)
    {
        return search("_searchable", term);
    }

    @Override
    protected Document transform(ScoreStatistic object)
    {
        Document doc = new Document();
        doc.add(new StringField(UUID, object.getUuid(), Field.Store.YES));
        doc.add(new StringField(NAME, object.getName().toString(), Field.Store.YES));
        doc.add(new StringField(OUTCOME, object.getOutcome().toString(), Field.Store.YES));
        doc.add(new StringField(TEAM, object.getTeam().toString(), Field.Store.YES));
        doc.add(new StringField(SCORE, object.getScore().toString(), Field.Store.YES));
        doc.add(new StringField(SOURCE, object.getSource().toString(), Field.Store.YES));
        return asSearchable(doc, "_searchable", Arrays.asList(object.getName().toLowerCase(),
                object.getScore().toString().toLowerCase(), object.getSource().toString().toLowerCase()));
    }

    @Override
    protected ScoreStatistic transform(Document object)
    {
        ScoreStatistic scoreStatistic = new ScoreStatistic(object.getField(NAME).stringValue(),
                TeamSourceType.valueOf(object.getField(TEAM).stringValue()), OutcomeType.valueOf(object.getField(OUTCOME).stringValue()),
                ScoreType.valueOf(object.getField(SCORE).stringValue()), ScoreSourceType.valueOf(object.getField(SOURCE).stringValue()));
        return scoreStatistic;
    }

    @Override
    public Collection<ScoreStatistic> readAll()
    {
        return Arrays.asList(new ScoreStatistic("Goal Won - openplay", US, POSITIVE, GOAL, OPEN_PLAY),
                new ScoreStatistic("Goal Won - free", US, POSITIVE, GOAL, FREE),
                new ScoreStatistic("Goal Won - penalty", US, POSITIVE, GOAL, PENALTY),

                new ScoreStatistic("Point Won - openplay", US, POSITIVE, POINT, OPEN_PLAY),
                new ScoreStatistic("Point Won - free", US, POSITIVE, POINT, FREE),
                new ScoreStatistic("Point Won - penalty", US, POSITIVE, POINT, PENALTY),

                new ScoreStatistic("Goal Lost - openplay", THEM, NEGATIVE, GOAL, OPEN_PLAY),
                new ScoreStatistic("Goal Lost - free", THEM, NEGATIVE, GOAL, FREE),
                new ScoreStatistic("Goal Lost - penalty", THEM, NEGATIVE, GOAL, PENALTY),

                new ScoreStatistic("Point Lost - openplay", THEM, NEGATIVE, POINT, OPEN_PLAY),
                new ScoreStatistic("Point Lost - free", THEM, NEGATIVE, POINT, FREE),
                new ScoreStatistic("Point Lost - penalty", THEM, NEGATIVE, POINT, PENALTY));
    }
}
