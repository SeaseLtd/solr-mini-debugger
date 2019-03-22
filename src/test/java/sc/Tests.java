package sc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

/**
 * A "dummy" integration test for debugging the RequestHandler directly in Solr. 
 * 
 * @author agazzarini
 */
public class Tests extends BaseIntegrationTest {

    @Before
    public void loadData() throws Exception {
        getSolrClient().add(newDoc(
                "1",
                "Apache Solr Essentials",
                "978-1784399641",
                "Andrea Gazzarini"));

        getSolrClient().add(newDoc(
                "2",
                "Designing Data-Intensive Applications",
                "978-13343889641",
                "Martin Keppmann"));

        getSolrClient().add(newDoc(
                "3",
                "Building Micro-services",
                "978-34342849241",
                "Saw Newman", "John Berry", "Martin White"));

        getSolrClient().commit();
    }

    @Before
    public void deleteData() throws Exception {
        getSolrClient().deleteByQuery("*:*");
        getSolrClient().commit();
    }

    @Test
    public void titleSearchShouldBeCaseInsensitive() throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery("solr");
        QueryResponse response = getSolrClient().query(q);

        assertEquals(1, response.getResults().getNumFound());

        SolrDocument doc = response.getResults().get(0);

        assertEquals("1", doc.getFieldValue("id"));
    }

    @Test
    public void titleSearchShouldSupportStemming() throws IOException, SolrServerException {
        SolrQuery q = new SolrQuery("apache solr essential");
        QueryResponse response = getSolrClient().query(q);

        assertEquals(1, response.getResults().getNumFound());

        SolrDocument doc = response.getResults().get(0);

        assertEquals("1", doc.getFieldValue("id"));
    }

    @Test
    public void isbnSearch() throws IOException, SolrServerException {

        List<String> testData =
                Arrays.asList("978 1784399641", "978/1784399641", "978   1784399641", "  978   1784399641  ", "978-1784  399641");

        for(String isbn : testData) {
            SolrQuery q = new SolrQuery(isbn);
            q.setShowDebugInfo(true);

            QueryResponse response = getSolrClient().query(q);

            System.out.println(response.getDebugMap().get("parsedquery"));
            assertEquals(1, response.getResults().getNumFound());

            SolrDocument doc = response.getResults().get(0);
            assertEquals("1", doc.getFieldValue("id"));
        }
    }

    private SolrInputDocument newDoc(final String id, String title, final String isbn, String ... author) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", id);
        doc.setField("title", title);
        doc.setField("isbn", isbn);
        if (author != null) {
            for (String s : author) {
                doc.addField("author", s);
            }
        }

        return doc;
    }
}
