package org.acoli.conll.rdf;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

public class SimpleLineBreakSplitter extends CoNLLRDFComponent {
	private static final Logger LOG = Logger.getLogger(SimpleLineBreakSplitter.class);

	@Override
	protected void processSentenceStream() throws IOException {
		String line;
		int empty = 0;
		while ((line = getInputStream().readLine()) != null) {
			if (line.trim().isEmpty()) {
				empty++;
			} else {
				if (empty > 0) {
					getOutputStream().print("\n#newsegment\n");
					empty = 0;
				}
				getOutputStream().print(line + "\n");
			}
		}
		getOutputStream().close();
	}

	@Override
	public void configureFromCommandLine(String[] args) throws IOException, ParseException {
		// Nothing to do
	}

	public static void main(String[] args) throws IOException {
		System.err.println("synopsis: SimpleLineBreakSplitter");
		SimpleLineBreakSplitter splitter = new SimpleLineBreakSplitter();

		long start = System.currentTimeMillis();

		// READ SENTENCES from System.in
		splitter.processSentenceStream();
		System.err.println(((System.currentTimeMillis() - start) / 1000 + " seconds"));
	}
}
