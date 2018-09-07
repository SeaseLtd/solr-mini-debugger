package sc;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A "dummy" integration test for debugging the RequestHandler directly in Solr. 
 * 
 * @author agazzarini
 */
public class Debugger extends BaseIntegrationTest {
    @Test
    public void mailingList() throws Exception {
        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.setField("id", "1");
        doc1.setField("body", "8. The method of claim 6 wherein said method inhibits at least one 5′-nucleotidase chosen from cytosolic 5′-nucleotidase II (cN-II), cytosolic 5′-nucleotidase IA (cN-IA), cytosolic 5′-nucleotidase IB (cN-IB), cytosolic 5′-nucleotidase IMA (cN-IIIA), cytosolic 5′-nucleotidase NIB (cN-IIIB), ecto-5′-nucleotidase (eN, CD73), cytosolic 5′(3′)-deoxynucleotidase (cdN) and mitochondrial 5′(3′)-deoxynucleotidase (mdN).");

        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.setField("id", "2");
        doc2.setField("body", "Trichomonosis caused by the flagellate protozoan Trichomonas vaginalis represents the most prevalent nonviral sexually transmitted disease worldwide (WHO-DRHR 2012). In women, the symptoms are cyclic and often worsen around the menstruation period. In men, trichomonosis is largely asymptomatic and these men are considered to be carriers of T. vaginalis (Petrin et al. 1998). This infection has been associated with birth outcomes (Klebanoff et al. 2001), infertility (Grodstein et al. 1993), cervical and prostate cancer (Viikki et al. 2000, Sutcliffe et al. 2012) and pelvic inflammatory disease (Cherpes et al. 2006). Importantly, T. vaginalis is a co-factor in human immunodeficiency virus transmission and acquisition (Sorvillo et al. 2001, Van Der Pol et al. 2008). Therefore, it is important to study the host-parasite relationship to understand T. vaginalis infection and pathogenesis. Colonisation of the mucosa by T. vaginalis is a complex multi-step process that involves distinct mechanisms (Alderete et al. 2004). The parasite interacts with mucin (Lehker & Sweeney 1999), adheres to vaginal epithelial cells (VECs) in a process mediated by adhesion proteins (AP120, AP65, AP51, AP33 and AP23) and undergoes dramatic morphological changes from a pyriform to an amoeboid form (Engbring & Alderete 1998, Kucknoor et al. 2005, Moreno-Brito et al. 2005). After adhesion to VECs, the synthesis and gene expression of adhesins are increased (Kucknoor et al. 2005). These mechanisms must be tightly regulated and iron plays a pivotal role in this regulation. Iron is an essential element for all living organisms, from the most primitive to the most complex, as a component of haeme, iron-sulphur clusters and a variety of proteins. Iron is known to contribute to biological functions such as DNA and RNA synthesis, oxygen transport and metabolic reactions. T. vaginalis has developed multiple iron uptake systems such as receptors for hololactoferrin, haemoglobin (HB), haemin (HM) and haeme binding as well as adhesins to erythrocytes and epithelial cells (Moreno-Brito et al. 2005, Ardalan et al. 2009). Iron plays a crucial role in the pathogenesis of trichomonosis by increasing cytoadherence and modulating resistance to complement lyses, ligation to the extracellular matrix and the expression of proteases (Figueroa-Angulo et al. 2012). In agreement with this role, the symptoms of trichomonosis worsen after menstruation. In addition, iron also influences nucleotide hydrolysis in T. vaginalis (Tasca et al. 2005, de Jesus et al. 2006). The extracellular concentrations of ATP and adenosine can markedly increase under several conditions such as inflammation and hypoxia as well as in the presence of pathogens (Robson et al. 2006, Sansom 2012). In the extracellular medium, these nucleotides can act as immunomodulators by triggering immunological effects. Extracellular ATP acts as a proinflammatory immune-mediator by triggering multiple immunological effects on cell types such as neutrophils, macrophages, dendritic cells and lymphocytes (Bours et al. 2006). In this sense, ATP and adenosine concentrations in the extracellular compartment are controlled by ectoenzymes, including those of the nucleoside triphosphate diphosphohydrolase (NTPDase) (EC: 3.1.4.1) family, which hydrolyze tri and diphosphates and ecto-5’-nucleotidase (EC: 3.1.3.5), which hydrolyses monophosphates (Zimmermann 2001). Considering that de novo nucleotide synthesis is absent in T. vaginalis (Heyworth et al. 1982, 1984), this enzyme cascade is important as a source of the precursor adenosine for purine synthesis in the parasite (Munagala & Wang 2003). Extracellular nucleotide metabolism has been characterised in several parasite species such as Toxoplasma gondii, Schistosoma mansoni, Leishmania spp, Trypanosoma cruzi, Acanthamoeba, Entamoeba histolytica, Giardia lamblia and fungi, Saccharomyces cerevisiae, Cryptococcus neoformans, Candida parapsilosis and Candida albicans (Sansom 2012). In T. vaginalis , NTPDase and ecto-5’-nucleotidase activities have been characterised and they are involved in host-parasite interactions by controlling ATP and adenosine levels (Matos et al. 2001, d, de Jesus et al. 2002, Tasca et al. 2003). Considering that (i) iron plays a crucial role in the pathogenesis of trichomonosis, (ii) ATP exerts a proinflammatory effect in inflammation, (iii) adenosine is important to T. vaginalis growth and acts as an antiinflammatory factor (Frasson et al. 2012) and (iv) ectonucleotidases modulate the nucleotide levels at infection sites (such as those observed in trichomonosis), the aim of this study was to investigate the effect of iron on the extracellular nucleotide hydrolysis and gene expression of T . vaginalis.");

        getSolrClient().add(doc1);
        getSolrClient().add(doc2);

        getSolrClient().commit();


        final SolrQuery q1 = new SolrQuery("(body:\"Cytosolic 5′-nucleotidase II\") ").setFields("id,score").setShowDebugInfo(true);
        final QueryResponse r1 = getSolrClient().query(q1);

        System.out.println(r1.getResults().getNumFound());
        System.out.println(r1.getDebugMap().get("parsedquery"));

        r1.getResults()
                .stream()
                .map(doc -> doc.getFieldValue("score") + " => " + doc.getFieldValue("id")).forEach(System.out::println);


        System.out.println("-------------------------");
    }

    /**
	 * Starts Solr and waits for the Enter key pressure.
	 * 
	 * @throws IOException in case of I/O failure.
	 */
	@Ignore
    public void doubleSynonymFilter() throws Exception {
        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.setField("id", "1");
        doc1.setField("title", "transfer phone number");

        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.setField("id", "1a");
        doc2.setField("title", "number which should be out of transfer");

        SolrInputDocument doc3 = new SolrInputDocument();
        doc3.setField("id", "3");
        doc3.setField("title", "Out of match. This is another doc which shouldn't match because of warranty");

        SolrInputDocument doc4 = new SolrInputDocument();
        doc4.setField("id", "4");
        doc4.setField("title", "how to test an existing code in java");

        SolrInputDocument doc5 = new SolrInputDocument();
        doc5.setField("id", "5");
        doc5.setField("title", "out of warranty process description");

        SolrInputDocument doc6 = new SolrInputDocument();
        doc6.setField("id", "6");
        doc6.setField("title", "oow means out of warranty");

        getSolrClient().add(doc1);
        getSolrClient().add(doc2);
        getSolrClient().add(doc3);
        getSolrClient().add(doc4);
        getSolrClient().add(doc5);
        getSolrClient().add(doc6);

        getSolrClient().commit();

        System.out.println("**************");



        final SolrQuery q1 = new SolrQuery("transfer my number").setFields("score,*");
        final QueryResponse r1 = getSolrClient().query(q1);

        //System.out.println(r1);
        System.out.println(r1.getDebugMap().get("parsedquery"));

        r1.getResults()
                .stream()
                .map(doc -> doc.getFieldValue("score") + " => " + doc.getFieldValue("title")).forEach(System.out::println);


        System.out.println("-------------------------");

        final SolrQuery q2 = new SolrQuery("out of my warranty").setFields("score,*");
        final QueryResponse r2 = getSolrClient().query(q2);

        //System.out.println(r2);
        System.out.println(r2.getDebugMap().get("parsedquery"));

        r2.getResults()
                .stream()
                .map(doc -> doc.getFieldValue("score") + " => " + doc.getFieldValue("title")).forEach(System.out::println);
    }

    @Ignore
    public void _doubleSynonymFilter() throws Exception {

        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.setField("id", "1");
        doc1.setField("title", "out of warranty process description");

        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.setField("id", "2");
        doc2.setField("title", "oow means out of warranty");

        SolrInputDocument doc3 = new SolrInputDocument();
        doc3.setField("id", "3");
        doc3.setField("title", "Out of match. This is another doc which shouldn't match because of warranty");

        SolrInputDocument doc4 = new SolrInputDocument();
        doc4.setField("id", "4");
        doc4.setField("title", "how to test an existing code in java");

        getSolrClient().add(doc1);
        getSolrClient().add(doc2);
        getSolrClient().add(doc3);
        getSolrClient().add(doc4);

        getSolrClient().commit();

        System.out.println("**************");

        //SolrQuery query = new SolrQuery("We are the +(usa \"united states of america\")");
        SolrQuery query = new SolrQuery("out of my warranty").setFields("score,*");
//        query.set("defType", "100");

        QueryResponse response = getSolrClient().query(query);

        System.out.println(response);
        System.out.println(response.getDebugMap().get("parsedquery"));

        System.out.println("**************");

        response.getResults()
                .stream()
                .map(doc -> doc.getFieldValue("score") + " => " + doc.getFieldValue("title")).forEach(System.out::println);
    }
}