package org.acoli.conll.rdf;

import static org.acoli.conll.rdf.CoNLLStreamExtractor.findFieldsFromComments;

import java.io.*;
import java.util.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.*;
import org.apache.log4j.Logger;

public class CoNLLStreamExtractorFactory {
    static Logger LOG = Logger.getLogger(CoNLLStreamExtractorFactory.class);
    public static CoNLLStreamExtractor getStreamExtractor(String[] args) throws IOException, ParseException {
        CoNLLStreamExtractor extractor = new CoNLLStreamExtractor();
		//FIXME
		List<Pair<String, String>> updates = new ArrayList<Pair<String, String>>();

		final CommandLine cmd = new CoNLLRDFCommandLine("synopsis: CoNLLStreamExtractor baseURI FIELD1[.. FIELDn] [-u SPARQL_UPDATE1..m] [-s SPARQL_SELECT]\n"
		+ "\tbaseURI       CoNLL base URI, cf. CoNLL2RDF\n"
		+ "\tFIELDi        CoNLL field label, cf. CoNLL2RDF",
		"reads CoNLL from stdin, splits sentences, creates CoNLL RDF, applies SPARQL queries",
		new Option[] {
			Option.builder("s").hasArg().hasArgs().desc("SPARQL SELECT statement to produce TSV output").build(),
			Option.builder("u").hasArgs().argName("sparql_update").desc("DEPRECATED - please use CoNLLRDFUpdater instead!").build()
			/* "SPARQL_UPDATE SPARQL UPDATE (DELETE/INSERT) query, either literally or its location (file/uri).
			Can be followed by an optional integer in {}-parentheses = number of repetitions" */
		}, LOG).parseArgs(args);

		List<String> argList = cmd.getArgList();
		if (argList.isEmpty()) {
			throw new ParseException("Missing required Argument baseURI");
		}
		extractor.setBaseURI(argList.remove(0));

		if (argList.isEmpty()) { // might be conllu plus, we check the first line for col names.
			extractor.setColumns(findFieldsFromComments(extractor.getInputStream(), 1));
			if (extractor.getColumns().isEmpty()) { // FIXME this should probably be a catch block
				throw new ParseException("Missing required Argument Fields/Columns not found as global.columns either");
			}
		} else {
			extractor.setColumns(argList);
		}

		if (cmd.hasOption("s")) {
			// setSelect(parseSparqlArg(String.join(" ", Arrays.asList(cmd.getOptionValues("s")))));
			extractor.setSelect(String.join(" ", Arrays.asList(cmd.getOptionValues("s")))); //FIXME
		}

		if (cmd.hasOption("u")) {
			LOG.warn("using -u to provide updates is deprecated");
			for (String arg : cmd.getOptionValues("u")) {
				Pair<String, String> update = extractor.parseUpdate(arg);
				updates.add(new ImmutablePair<String, String>(extractor.parseSparqlArg(update.getKey()), update.getValue()));
				// FIXME
			}
		}

		LOG.info("running CoNLLStreamExtractor");
		LOG.info("\tbaseURI:       " + extractor.getBaseURI());
		LOG.info("\tCoNLL columns: " + extractor.getColumns());

        return extractor;
	}
}
