package org.acoli.conll.rdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

class CoNLLStreamExtractorTest {

    /**
     * column label (with dash) in cli-args
     */
    @Test
    void simpleTest() throws ParseException, IOException
    {
        CoNLLStreamExtractor extractor = CoNLLStreamExtractorFactory.getStreamExtractor(new String[] {"url", "WORD", "POS", "PARSE", "NER", "COREF", "PRED", "PRED-ARGS"});

        assertEquals("url", extractor.getBaseURI());
        assertEquals(new LinkedList<String>(Arrays.asList("WORD", "POS", "PARSE", "NER", "COREF", "PRED", "PRED-ARGS")),
            extractor.getColumns());
    }
    /**
     * column label with dash in first line
     */
    @Test
    void simpleTest2() throws ParseException, IOException
    {
        CoNLLStreamExtractor extractor = CoNLLStreamExtractorFactory.getStreamExtractor(new String[] {"url"});
        extractor.setInputStream(new BufferedReader(new StringReader("# global.columns = WORD POS PARSE NER COREF PRED PRED-ARGS\n\n")));

        assertEquals("url", extractor.getBaseURI());
        assertEquals(new LinkedList<String>(Arrays.asList("WORD", "POS", "PARSE", "NER", "COREF", "PRED", "PRED-ARGS")),
            extractor.getColumns());
    }
}
