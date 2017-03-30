package com.monadiccloud.applications.matchstats.service.repository.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Connor Goulding
 */
public abstract class AbstractLuceneIndex<D>
{
    private final Directory index;
    private final Analyzer  analyzer;

    public AbstractLuceneIndex()
    {
        this(new RAMDirectory());
    }

    public AbstractLuceneIndex(Directory index)
    {
        this.index = index;
        this.analyzer = new StandardAnalyzer();
        initialise();
    }

    public AbstractLuceneIndex(String path) throws IOException
    {
        this(FSDirectory.open(new File(path).toPath()));
        initialise();
    }

    protected abstract Document transform(D object);

    protected abstract D transform(Document object);

    protected List<D> search(String field, String value)
    {
        return read(field, value).stream().map(this::transform).collect(Collectors.toList());
    }

    protected abstract Collection<D> readAll();

    protected Document asSearchable(Document document, String field, List<String> searchables)
    {
        searchables.forEach(s -> document.add(new TextField(field, s, Field.Store.YES)));
        return document;
    }

    protected void initialise()
    {
        readAll().stream().map(this::transform).forEach(this::addDocument);
    }

    private void addDocument(Document doc)
    {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter writer = new IndexWriter(index, config))
        {
            writer.addDocument(doc);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private List<Document> read(String field, String value)
    {
        List<Document> documents = new ArrayList<>();
        try (IndexReader reader = DirectoryReader.open(index))
        {
            IndexSearcher searcher = new IndexSearcher(reader);

            Query q = new WildcardQuery(new Term(field, "*" + value + "*"));
            TopDocs docs = searcher.search(q, Integer.MAX_VALUE);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println("Found " + hits.length + " hits.");

            for (int i = 0; i < hits.length; i++)
            {
                documents.add(searcher.doc(hits[i].doc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return documents;
    }
}
