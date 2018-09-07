package sc;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilterFactory;

import java.util.Map;

public class SynonymAwareStopFilterFactory extends StopFilterFactory  {
    /**
     * Creates a new StopFilterFactory
     *
     * @param args
     */
    public SynonymAwareStopFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new SynonymAwareStopFilter(input, getStopWords());
    }
}