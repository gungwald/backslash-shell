package com.alteredmechanism.backslashshell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackslashShell {

	public static final String ENV_VAR_REGEX = "%([a-zA-Z0-9_]+)%";

	private static final int EXIT_SUCCESS = 0;
	private static final int EXIT_FAILURE = 1;

	private BufferedReader in;
	private PrintWriter out;
	private String drive;
	private File directory;
	private Environment env;
	private Pattern envVarPattern;
	private boolean running;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BackslashShell bssh = new BackslashShell();
			bssh.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BackslashShell() {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		drive = "C:";
		directory = new File(System.getProperty("user.dir"));
		envVarPattern = Pattern.compile(ENV_VAR_REGEX);
		running = false;
	}

	public void run() throws IOException {
		running = true;

		while (running) {
			prompt();
			eval(parse(substitute(in.readLine())));
		}
	}

	protected void prompt() {
		out.print(drive + directory + ">");
		out.flush();
	}

	protected String substitute(String text) {
		Matcher m = envVarPattern.matcher(text);
		if (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				String name = m.group(i);
				String value = env.getValue(name);
				text = text.replace("%" + name + "%", value);
			}
		}
		return text;
	}

	protected List<String> parse(String line) {
		Tokenizer tok = new Tokenizer(line);
		List<String> args = new ArrayList<String>();
		String token = tok.getToken();
		while (token.length() > 0) {
			args.add(token);
			token = tok.getToken();
		}
		return args;
	}

	protected void eval(List<String> args) {
		if (args.size() == 0) {
			return;
		}
		String cmd = args.get(0).toLowerCase();
		if (cmd.equals("echo")) {
			echo(args);
		}
		else if (cmd.equals("cd") || cmd.equals("chdir")) {
			chdir(args);
		}
		else if (cmd.equals("dir")) {
			dir(args);
		}
		else if (cmd.equals("exit")) {
			System.exit(EXIT_SUCCESS);
		}
	}

	protected int echo(List<String> args) {
		if (args.size() > 0) {
			out.print(args.get(1));
			for (int i = 2; i < args.size(); i++) {
				out.print(' ');
				out.print(args.get(i));
			}
			out.println();
			out.flush();
		}
		return EXIT_SUCCESS;
	}
	
	protected int chdir(List<String> args) {
		int exitCode = EXIT_SUCCESS;
		if (args.size()>0) {
			if (args.size() == 1) {
				println(drive + directory.toString());
			}
			else {
				File destDir = new File(directory, args.get(1));
				if (destDir.isDirectory()) {
					directory = destDir;
				}
				else {
					println("Directory does not exist: " + args.get(1));
					exitCode = EXIT_FAILURE;
				}
			}
		}
		return exitCode;
	}

	protected int dir(List<String> args) {
		java.io.File[] files = directory.listFiles();
		for (java.io.File file : files) {
			println(file.getName());
		}
		return EXIT_SUCCESS;
	}
	
	private void println(String message) {
		out.println(message);
		out.flush();
	}
}
