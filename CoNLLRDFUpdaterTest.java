package org.acoli.conll.rdf;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

public class CoNLLRDFUpdaterTest {
    @Test
    public void simpleTest() throws IOException, ParseException {
        CoNLLRDFUpdater updater = new CoNLLRDFUpdater();
        String[] a = new String[] {
                "-custom",
                "-updates",
                "examples/sparql/remove-IGNORE.sparql",
                "examples/sparql/analyze/UPOS-to-POSsynt.sparql",
                "examples/sparql/analyze/EDGE-to-POSsynt.sparql",
                "examples/sparql/analyze/consolidate-POSsynt.sparql"
            };
        updater.configureFromCommandLine(a);
    }
    @Test
    public void validCliArguments()
    throws IOException, ParseException
    {
        String[][] args = new String[][] {
            {
                "-custom",
                "-updates",
                "examples/sparql/remove-IGNORE.sparql",
                "examples/sparql/analyze/UPOS-to-POSsynt.sparql",
                "examples/sparql/analyze/EDGE-to-POSsynt.sparql",
                "examples/sparql/analyze/consolidate-POSsynt.sparql"
            },
            {
                "-custom",
                "-model", "http://purl.org/olia/owl/experimental/univ_dep/all_from_rdfa/ud-pos-all.owl", "http://purl.org/olia/ud-pos-all.owl",
                "-model", "http://purl.org/olia/owl/experimental/univ_dep/all_from_rdfa/ud-pos-all-link.rdf", "http://purl.org/olia/ud-pos-all.owl",
                "-updates",
                "examples/sparql/link/link-UPOS-simple.sparql"
            },
            {
            "-custom",
            "-model", "http://purl.org/olia/penn.owl", "http://purl.org/olia/penn.owl",
            "-model", "http://purl.org/olia/penn-link.rdf", "http://purl.org/olia/penn.owl",
            "-model", "http://purl.org/olia/olia.owl", "http://purl.org/olia/olia.owl",
            "-graphsout", "data/ud/graphsout", "s2_0", "s3_0",
            "-updates", "examples/sparql/remove-ID.sparql",
            "examples/sparql/remove-IGNORE.sparql", "examples/sparql/link/link-penn-POS.sparql",
            "examples/sparql/link/remove-annotation-model.sparql", "examples/sparql/link/infer-olia-concepts.sparql",
            "examples/sparql/parse/initialize-SHIFT.sparql", "examples/sparql/parse/REDUCE-english-1.sparql{5}",
            "examples/sparql/parse/REDUCE-english-2.sparql{5}", "examples/sparql/parse/REDUCE-english-3.sparql{5}",
            "examples/sparql/parse/REDUCE-english-4.sparql{3}", "examples/sparql/parse/REDUCE-to-HEAD.sparql"
            },
            {
            "-custom",
            "-model", "http://purl.org/olia/penn.owl", "http://purl.org/olia/penn.owl",
            "-model", "http://purl.org/olia/penn-link.rdf", "http://purl.org/olia/penn.owl",
            "-model", "http://purl.org/olia/olia.owl", "http://purl.org/olia/olia.owl",
            "-updates", "examples/sparql/remove-ID.sparql", "examples/sparql/remove-IGNORE.sparql",
            "examples/sparql/link/link-penn-POS.sparql", "examples/sparql/link/remove-annotation-model.sparql",
            "examples/sparql/link/infer-olia-concepts.sparql", "examples/sparql/parse/initialize-SHIFT.sparql",
            "examples/sparql/parse/REDUCE-english-1.sparql{5}", "examples/sparql/parse/REDUCE-english-2.sparql{5}",
            "examples/sparql/parse/REDUCE-english-3.sparql{5}", "examples/sparql/parse/REDUCE-english-4.sparql{3}",
            "examples/sparql/parse/REDUCE-to-HEAD.sparql"
            },
            {
            "-custom",
            "-updates", "examples/sparql/trees/tree2bracket.sparql"
            },
            {
            "-custom",
            "-updates", "examples/sparql/trees/tree2bracket.sparql"
            },
            {
            "-custom",
            "-updates",
            "examples/sparql/trees/xAttributes2value.sparql", "examples/sparql/trees/emptyNode2Word.sparql",
            "examples/sparql/trees/tree2bracket.sparql"
            }
        };
        for (String[] a : args) {
            new CoNLLRDFUpdater().configureFromCommandLine(a);
        }
    }
}
