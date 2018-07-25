package sc;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public class SystemOutTokenFilterFactory extends TokenFilterFactory {
    private String id;

    /**
     * Initialize this factory via a set of key-value pairs.
     *
     * @param args
     */
    public SystemOutTokenFilterFactory(Map<String, String> args) {
        super(args);
        this.id = args.get("gid");
    }

    public TokenStream create(final TokenStream input) {
        return new SystemOutTokenFilter(input, id);
    }
}
