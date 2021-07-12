package org.acoli.conll.rdf;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;

public class CoNLLRDFFormatterIT {
	@Test
	void givenTwoSentences_thenOutputStreamIsFlushed()
			throws IOException, ParseException, InterruptedException, ExecutionException {
		final PipedOutputStream compOutput = new PipedOutputStream();
		final PrintStream inputStream = new PrintStream(compOutput);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		final String given = "@prefix : <#> .\n" + "@prefix powla: <http://purl.org/powla/powla.owl#> .\n"
				+ "@prefix conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> .\n"
				+ "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
				+ "@prefix terms: <http://purl.org/acoli/open-ie/> .\n"
				+ "@prefix x: <http://purl.org/acoli/conll-rdf/xml#> .\n"
				+ "@prefix nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> .\n"
				+ "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + ":s1_0 a nif:Sentence .\n"
				+ ":s1_1 a nif:Word; conll:WORD \"1\"; conll:HEAD :s1_0; conll:ID \"1\"; nif:nextWord :s1_2 .\n"
				+ ":s1_2 a nif:Word; conll:WORD \"2\"; conll:HEAD :s1_0; conll:ID \"2\"; nif:nextWord :s1_3 .\n"
				+ ":s1_3 a nif:Word; conll:WORD \"3\"; conll:HEAD :s1_0; conll:ID \"3\"; nif:nextWord :s1_4 .\n"
				+ ":s1_4 a nif:Word; conll:WORD \"4\"; conll:HEAD :s1_0; conll:ID \"4\" .\n";

		final String expected = "@prefix : <#> .\n" + "@prefix powla: <http://purl.org/powla/powla.owl#> .\n"
				+ "@prefix conll: <http://ufal.mff.cuni.cz/conll2009-st/task-description.html#> .\n"
				+ "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
				+ "@prefix terms: <http://purl.org/acoli/open-ie/> .\n"
				+ "@prefix x: <http://purl.org/acoli/conll-rdf/xml#> .\n"
				+ "@prefix nif: <http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#> .\n"
				+ "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" + ":s1_0 a nif:Sentence .\n"
				+ ":s1_1 a nif:Word .\n" + ":s1_2 a nif:Word .\n" + ":s1_3 a nif:Word .\n" + ":s1_4 a nif:Word .\n";

		final CoNLLRDFFormatter formatter = new CoNLLRDFFormatterFactory().buildFromCLI(new String[] {});
		formatter.setInputStream(new BufferedReader(new InputStreamReader(new PipedInputStream(compOutput))));
		// formatter.setOutputStream(new PrintStream(outputStream));
		// System.setOut(new PrintStream(outputStream));
		formatter.getModules().get(0).setOutputStream(new PrintStream(outputStream));
		final CompletableFuture<Void> processStreamFuture = CompletableFuture.runAsync(() -> {
			try {
				formatter.processSentenceStream();
			} catch (IOException e1) {
				throw new TestAbortedException("An Exception occured while the processing the Sentence Stream", e1);
			}
		});

		inputStream.append(given);
		inputStream.append(given);
		final String actual = outputStream.toString();

		// Can't use assertEquals(expected, actual) here
		// because the line endings don't (have to) match
		assertLinesMatch(Arrays.stream(expected.split("\\r?\\n")), Arrays.stream(actual.split("\\r?\\n")));
		inputStream.close();
		processStreamFuture.get();
	}
}
