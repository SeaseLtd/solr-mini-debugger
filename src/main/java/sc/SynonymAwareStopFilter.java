package sc;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class SynonymAwareStopFilter extends StopFilter {

    private TypeAttribute tAtt =
            addAttribute(TypeAttribute.class);
    private PositionLengthAttribute plAtt =
            addAttribute(PositionLengthAttribute.class);

    private int synonymSpans;

    protected SynonymAwareStopFilter(
            TokenStream in, CharArraySet stopwords) {
        super(in, stopwords);
    }

    @Override
    protected boolean accept() {
        if (isSynonymToken()) {
            synonymSpans = plAtt.getPositionLength() > 1
                    ? plAtt.getPositionLength()
                    : 0;
            return true;
        }

        return (--synonymSpans > 0) || super.accept();
    }

    private boolean isSynonymToken() {
        return "SYNONYM".equals(tAtt.type());
    }
}