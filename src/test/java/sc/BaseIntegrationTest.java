package sc;

import org.apache.solr.SolrJettyTestBase;
import org.junit.BeforeClass;

/**
 * A supertype layer for running Solr based integration tests.
 * 
 * @author agazzarini
 * @since 1.0
 */
public abstract class BaseIntegrationTest extends SolrJettyTestBase {
	@BeforeClass
	public static void init() throws Exception {
		System.setProperty("solr.data.dir", initCoreDataDir.getAbsolutePath());
		System.setProperty("solr.core.name", "collection1");
		initCore("solrconfig.xml", "schema.xml", "src/solr");
	}
}