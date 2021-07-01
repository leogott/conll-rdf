package org.acoli.conll.rdf;

import java.io.IOException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.cli.ParseException;

public interface CoNLLRDFComponentFactory<C extends CoNLLRDFComponent> {
	public C buildFromCLI(String[] args) throws IOException, ParseException;

	public C buildFromJsonConfig(ObjectNode config) throws IOException, ParseException;
}
