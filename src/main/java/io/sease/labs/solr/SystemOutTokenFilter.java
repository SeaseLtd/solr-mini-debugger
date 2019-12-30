package io.sease.labs.solr;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public final class SystemOutTokenFilter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected SystemOutTokenFilter(TokenStream input) {
        super(input);
    }

    public boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            // We could also simply print out "this"
            // this code is just for trimming out some unwanted things like ClassName@hashcode
            // from the output message.
            String text = String.valueOf(this);
            System.out.println(text.substring(text.indexOf("startOffset")) + "\t => " + termAtt.toString() );
            return true;
        } else {
            return false;
        }
    }
}
