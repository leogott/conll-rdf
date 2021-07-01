package org.acoli.conll.rdf;

import java.io.IOException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.cli.ParseException;

public class SimpleLineBreakSplitterFactory implements CoNLLRDFComponentFactory {

	@Override
	public SimpleLineBreakSplitter buildFromCLI(String[] args) throws IOException, ParseException {
		return new SimpleLineBreakSplitter();
	}

	@Override
	public SimpleLineBreakSplitter buildFromJsonConfig(ObjectNode config) throws IOException, ParseException {
		return new SimpleLineBreakSplitter();
	}
	
}
