package org.acoli.conll.rdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

/**
 * Unit test for conll-rdf formatter.
 */
public class CoNLLRDFFormatterTest {

    // TODO column label with dash in cli args
    @Test
    void simpleTest() throws ParseException, IOException
    {
        CoNLLRDFFormatter formatter = new CoNLLRDFFormatter();
        formatter.configureFromCommandLine(new String[] {"-conll", "WORD", "POS", "PARSE", "NER", "COREF", "PRED", "PRED-ARGS"});

        assertEquals(new LinkedList<String>(Arrays.asList("WORD", "POS", "PARSE", "NER", "COREF", "PRED", "PRED-ARGS")),
            formatter.getModules().get(0).getCols());
    }

    @Test
    public void validCliArguments()
    throws IOException, ParseException
    {
        new CoNLLRDFFormatter().configureFromCommandLine(new String[] {"-sparqltsv", "sparql/analyze/eval-POSsynt.sparql"});
    }
}
