package sc;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * A "dummy" integration test for debugging the RequestHandler directly in Solr. 
 * 
 * @author agazzarini
 */
public class Debugger extends BaseIntegrationTest {
	/**
	 * Starts Solr and waits for the Enter key pressure.
	 * 
	 * @throws IOException in case of I/O failure.
	 */
	@Test
    public void start() throws Exception {
        System.out.println("Press [Enter] to stop Solr");

        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.setField("id", "1");
        doc1.setField("title", "pippo pluto paperino");

        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.setField("id", "2");
        doc2.setField("title", "the first world power should be united states of america");

        SolrInputDocument doc3 = new SolrInputDocument();
        doc3.setField("id", "3");
        doc3.setField("title", "the first world power should be united states america of ");

        getSolrClient().add(doc1);
        getSolrClient().add(doc2);
        getSolrClient().add(doc3);
        getSolrClient().commit();

        System.out.println("**************");

        //SolrQuery query = new SolrQuery("We are the +(usa \"united states of america\")");
        SolrQuery query = new SolrQuery("multimedia messaging service");
        //query.set("mm", "100");

        QueryResponse response = getSolrClient().query(query);

        System.out.println(response);
        System.out.println(response.getDebugMap().get("parsedquery"));

        if(response.getResults().getNumFound() != 1) System.err.println("NOOOOOOOO!!!!");
    }
}