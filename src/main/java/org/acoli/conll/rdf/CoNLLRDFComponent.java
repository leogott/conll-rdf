package org.acoli.conll.rdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public abstract class CoNLLRDFComponent implements Runnable {

	private BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
	private PrintStream outputStream = System.out;

	public final BufferedReader getInputStream() {
		return inputStream;
	}
	public final void setInputStream(BufferedReader inputStream) {
		this.inputStream = inputStream;
	}
	public final PrintStream getOutputStream() {
		return outputStream;
	}
	public final void setOutputStream(PrintStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public final void run() {
		try {
			processSentenceStream();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// TODO is this method used anywhere?
	public final void start() {
		run();
	}

	protected abstract void processSentenceStream() throws IOException;
}
