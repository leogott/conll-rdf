package org.acoli.conll.rdf;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.acoli.conll.rdf.CoNLLRDFFormatter.Mode;
import org.acoli.conll.rdf.CoNLLRDFFormatter.Module;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

public class CoNLLRDFFormatterFactory {
    static Logger LOG = Logger.getLogger(CoNLLRDFFormatterFactory.class);
    public static CoNLLRDFFormatter getFormatter(String[] argv) throws IOException, ParseException {
        CoNLLRDFFormatter formatter = new CoNLLRDFFormatter();
		LOG.info(
			"synopsis: CoNLLRDFFormatter [-rdf [COLS]] [-debug] [-grammar] [-semantics] [-conll COLS] [-sparqltsv SPARQL]\n"
					+ "\t-rdf  write formatted CoNLL-RDF to stdout (sorted by list of CoNLL COLS, if provided)\n"
					+ "\t-conll  write formatted CoNLL to stdout (only specified COLS)\n"
					+ "\t-debug     write formatted, color-highlighted full turtle to stderr\n"
					+ "\t-grammar   write CoNLL data structures to stdout\n"
					+ "\t-semantics write semantic graph to stdout\n"
					+ "\t-sparqltsv write TSV generated from SPARQL statement to stdout.\n"
					+ "\t           if with -grammar, then skip type assignments\n"
					+ "read TTL from stdin => format CoNLL-RDF or extract and highlight CoNLL (namespace conll:) and semantic (namespace terms:) subgraphs\n"
					+ "if no parameters are supplied, -conllrdf is inferred");
		final String args = Arrays.asList(argv).toString().replaceAll("[\\[\\], ]+", " ").trim().toLowerCase();

		boolean CONLLRDF = args.contains("-rdf");
		final boolean CONLL = args.contains("-conll");
		final boolean DEBUG = args.contains("-debug");
		final boolean SPARQLTSV = args.contains("-sparqltsv");
		final boolean GRAMMAR = args.contains("-grammar");
		final boolean SEMANTICS = args.contains("-semantics");

		Module module;

		if (!CONLLRDF && !CONLL && !SPARQLTSV && !GRAMMAR && !SEMANTICS && !DEBUG) { // default
			CONLLRDF = true;
		}

		if (GRAMMAR) {
			module = new Module();
			module.setMode(Mode.GRAMMAR);
			module.setOutputStream(formatter.getOutputStream());
			formatter.getModules().add(module);
		}
		if (SEMANTICS) {
			module = new Module();
			module.setMode(Mode.SEMANTICS);
			module.setOutputStream(formatter.getOutputStream());
			formatter.getModules().add(module);
		}
		if (DEBUG) {
			module = new Module();
			module.setMode(Mode.DEBUG);
			module.setOutputStream(System.err);
			formatter.getModules().add(module);
		}
		if (CONLL) {
			module = new Module();
			module.setMode(Mode.CONLL);
			module.setOutputStream(formatter.getOutputStream());
			module.getCols().clear();
			int i = 0;
			while (i < argv.length && argv[i].toLowerCase().matches("^-+conll$"))
				i++;
			while (i < argv.length && !argv[i].toLowerCase().matches("^-+.*$"))
				module.getCols().add(argv[i++]);
			formatter.getModules().add(module);
		}
		if (CONLLRDF) {
			module = new Module();
			module.setMode(Mode.CONLLRDF);
			module.setOutputStream(formatter.getOutputStream());
			module.getCols().clear();
			int i = 0;
			while (i < argv.length && argv[i].toLowerCase().matches("^-+rdf$"))
				i++;
			while (i < argv.length && !argv[i].toLowerCase().matches("^-+.*$"))
				module.getCols().add(argv[i++]);
			formatter.getModules().add(module);
		}
		if (SPARQLTSV) {
			module = new Module();
			module.setMode(Mode.SPARQLTSV);
			module.setOutputStream(formatter.getOutputStream());
			String select = "";
			int i = 1;
			while (i < argv.length && argv[i].toLowerCase().matches("^-+sparqltsv$"))
				i++;
			if (i < argv.length)
				select = argv[i++];
			while (i < argv.length)
				select = select + " " + argv[i++]; // because queries may be parsed by the shell (Cygwin)

			Reader sparqlreader = new StringReader(select);
			File file = new File(select);
			URL u = null;
			try {
				u = new URL(select);
			} catch (MalformedURLException e) {
			}

			if (file.exists()) { // can be read from a file
				sparqlreader = new FileReader(file);
				LOG.debug("f");
			} else if (u != null) {
				try {
					sparqlreader = new InputStreamReader(u.openStream());
					LOG.debug("u");
				} catch (Exception e) {
				}
			}

			BufferedReader in = new BufferedReader(sparqlreader);
			select = "";
			for (String line = in.readLine(); line != null; line = in.readLine())
				select = select + line + "\n";
			module.setSelect(select);
			formatter.getModules().add(module);
		}
        return formatter;
	}
}
