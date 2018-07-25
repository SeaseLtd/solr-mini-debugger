package sc;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public final class SystemOutTokenFilter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private String id;

    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */
    protected SystemOutTokenFilter(TokenStream input, String id) {
        super(input);
        this.id = id;
    }

    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            System.out.println(this + "/" + id +" => " + termAtt.toString());
            return true;
        } else {
            return false;
        }
    }
}
