package sc;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Tests extends BaseIntegrationTest {
    /**
     * Here we should index the sample data
     *
     * node 1 annexes nodes 2 and 3
     * node 1 is deprecated by nodes 3,4,5
     */
    @Before
    public void loadData() throws Exception {
        SolrInputDocument _1 =  newDoc("1");
        _1.setField("DOCTYPE", "NODE");

        SolrInputDocument _1a = newDoc(id());
        _1a.setField("DOCTYPE", "ASSOC");
        _1a.setField("QNAME", "annexed");
        _1a.setField("SOURCE_ID", "1");
        _1a.setField("TARGET_ID", asList("2","3"));

        SolrInputDocument _1b = newDoc(id());
        _1b.setField("DOCTYPE", "ASSOC");
        _1b.setField("QNAME", "deprecated");
        _1b.setField("SOURCE_ID", "3,4,5");
        _1b.setField("TARGET_ID", singletonList("1"));

        _1.addChildDocuments(asList(_1a, _1b));

        // This is just an intruder node with a doctype we don't want to be included in results
        // We won't have ACL with associations, but again, this is just to make sure we won't get
        // this node among results.
        SolrInputDocument intruder =  newDoc("intruder");
        intruder.setField("DOCTYPE", "ACL");

        SolrInputDocument intruder_c1 = newDoc(id());
        intruder_c1.setField("DOCTYPE", "ASSOC");
        intruder_c1.setField("QNAME", "annexed");
        intruder_c1.setField("SOURCE_ID", "1");
        intruder_c1.setField("TARGET_ID", asList("2","3"));

        SolrInputDocument intruder_c2 = newDoc(id());
        intruder_c2.setField("DOCTYPE", "ASSOC");
        intruder_c2.setField("QNAME", "deprecated");
        intruder_c2.setField("SOURCE_ID", "4");
        intruder_c2.setField("TARGET_ID", asList("1","6"));

        intruder.addChildDocuments(asList(intruder_c1, intruder_c2));

        // Add the data and commits
        getSolrClient().add(_1);
//        getSolrClient().add(_2);
        getSolrClient().commit();
    }

    @Before
    public void deleteData() throws Exception {
        getSolrClient().deleteByQuery("*:*");
        getSolrClient().commit();
    }

    /**
     * What documents are annexed to 1? 2,3
     */
    @Test
    public void documentsAnnexedTo1() throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery("{!parent which=DOCTYPE:NODE} QNAME:annexed AND SOURCE_ID:1");
        QueryResponse response = getSolrClient().query(q);

        assertEquals(response.getResults().toString(),1, response.getResults().getNumFound());

        SolrDocument doc = response.getResults().get(0);

        doc.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });

        assertEquals(asList("2","3"), doc.getFieldValue("TARGET_ID"));
    }

    /**
     * Is Document 2 annexed to a document?
     */
    @Test
    public void isDocument2AnnexedToDocument1() throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery("q={!parent which=DOCTYPE:NODE} +QNAME:annexed +SOURCE_ID:1");
        QueryResponse response = getSolrClient().query(q);

        assertEquals(1, response.getResults().getNumFound());

        SolrDocument doc = response.getResults().get(0);

        doc.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });

        assertEquals(asList("2","3"), doc.getFieldValue("TARGET_ID"));
    }

    private String id() {
        return String.valueOf(System.currentTimeMillis());
    }

    private SolrInputDocument newDoc(final String id) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", id);

        return doc;
    }
}
