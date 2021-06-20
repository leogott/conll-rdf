package org.acoli.conll.rdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

import org.acoli.conll.rdf.CoNLLRDFFormatter.Mode;
import org.acoli.conll.rdf.CoNLLRDFFormatter.Module;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class CoNLLRDFFormatterImplementationTest {
	static Logger LOG = Logger.getLogger(CoNLLRDFFormatterImplementationTest.class);

	@Test
	void bugfix50_sortSnippetLexOrder() throws IOException {
		// TODO formatter = new CoNLLRDFFormatterFactory().buildFromCLI()
		final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		final CoNLLRDFFormatter formatter = new CoNLLRDFFormatter();

		final InputStream inputStream = classLoader.getResourceAsStream("snippet-lex-order.ttl");
		assumeFalse(inputStream.equals(null), "Failed to load resource");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true);

		formatter.setInputStream(new BufferedReader(new InputStreamReader(inputStream)));
		formatter.setOutputStream(ps);

		Module m = new Module();
		m.setMode(Mode.CONLL);
		m.setOutputStream(formatter.getOutputStream());
		m.setCols(Arrays.asList("ID", "WORD"));
		formatter.getModules().add(m);

		formatter.processSentenceStream();

		String data = baos.toString();
		String[] lines = data.trim().split("\\r?\\n");
		int index = 0;
		for (String string : lines) {
			if (index == 0) {
				index++;
			} else {
				LOG.debug(string);
				assertEquals(String.valueOf(index++), string.split("\\t")[0]);
			}
		}
	}

	@Test
	void bugfix50_sortSnippetNumOrder() throws IOException {
		// TODO formatter = new CoNLLRDFFormatterFactory().buildFromCLI()
		final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		final CoNLLRDFFormatter formatter = new CoNLLRDFFormatter();

		final InputStream inputStream = classLoader.getResourceAsStream("snippet-num-order.ttl");
		assumeFalse(inputStream.equals(null), "Failed to load resource");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos, true);

		formatter.setInputStream(new BufferedReader(new InputStreamReader(inputStream)));
		formatter.setOutputStream(ps);

		Module m = new Module();
		m.setMode(Mode.CONLL);
		m.setOutputStream(formatter.getOutputStream());
		m.setCols(Arrays.asList("ID", "WORD"));
		formatter.getModules().add(m);

		formatter.processSentenceStream();

		String data = baos.toString();
		String[] lines = data.trim().split("\\r?\\n");
		int index = 0;
		for (String string : lines) {
			if (index == 0) {
				index++;
			} else {
				LOG.debug(string);
				assertEquals(String.valueOf(index++), string.split("\\t")[0]);
			}
		}
	}
}
