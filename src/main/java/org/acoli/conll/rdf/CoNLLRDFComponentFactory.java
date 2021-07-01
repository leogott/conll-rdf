package org.acoli.conll.rdf;

import java.io.IOException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.cli.ParseException;

public interface CoNLLRDFComponentFactory {
	public CoNLLRDFComponent buildFromCLI(String[] args) throws IOException, ParseException;

	public CoNLLRDFComponent buildFromJsonConfig(ObjectNode config) throws IOException, ParseException;
}
