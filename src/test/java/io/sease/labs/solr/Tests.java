package io.sease.labs.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static java.util.Arrays.asList;

public class Tests extends BaseIntegrationTest {
    /**
     * Creates and load data according with your datamodel.
     * The provided example uses a simple document with two fields:
     *
     * - id
     * - title
     *
     * You should change them (and also the {@link #newDoc(String, String)} method)
     * according with your needs.
     */
    @Before
    public void indexSomeDocumentsBeforeEachTest() throws Exception {
        getSolrClient().add(asList(
                newDoc("1", "Object Oriented Software Construction"),
                newDoc("2", "Design Patterns: Elements of Reusable Object-Oriented Software")));
        getSolrClient().commit();
    }

    /**
     * Makes sure everything is deleted after each test method.
     */
    @After
    public void deleteDataAfterEachTest() throws Exception {
        getSolrClient().deleteByQuery("*:*");
        getSolrClient().commit();
    }

    /**
     * Simple test which prints out the matching documents.
     * In the same way you could debug/print out and format the whole response.
     */
    @Test
    public void test() throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery("*:*")
                .setFields("*")
                .setRows(10);

       QueryResponse response = getSolrClient().query("collection1", q);
       response.getResults()
               .stream()
               .peek(doc -> System.out.println("***** DOC " + doc.getFieldValue("id") + " *****"))
               .forEach(doc -> doc.iterator().forEachRemaining(field -> System.out.println(field.getKey() + " = " + field.getValue())));
    }

    /**
     * Change this method according with your data model.
     */
    private SolrInputDocument newDoc(final String id, String value) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", id);
        doc.setField("title", value);
        return doc;
    }
}
