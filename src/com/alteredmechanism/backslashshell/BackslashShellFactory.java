package com.alteredmechanism.backslashshell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class BackslashShellFactory implements ScriptEngineFactory {

	private Map<String,String> metaData = new HashMap<String,String>();
	private String[] names = new String[] {"backslash-shell", "cmd"};
	
	public BackslashShellFactory() {
		metaData.put(ScriptEngine.ENGINE, "Backslash Shell");
		metaData.put(ScriptEngine.ENGINE_VERSION, "1.0");
		metaData.put(ScriptEngine.LANGUAGE, "Batch File");
		metaData.put(ScriptEngine.LANGUAGE_VERSION, "10");
		metaData.put(ScriptEngine.NAME, names[0]);
	}
	
	public String getEngineName() {
		return metaData.get(ScriptEngine.ENGINE);
	}

	public String getEngineVersion() {
		return metaData.get(ScriptEngine.ENGINE_VERSION);
	}

	public List<String> getExtensions() {
		return Arrays.asList(new String[]{".bat", ".cmd"});
	}

	public String getLanguageName() {
		return metaData.get(ScriptEngine.LANGUAGE);
	}

	public String getLanguageVersion() {
		return metaData.get(ScriptEngine.LANGUAGE_VERSION);
	}

	public String getMethodCallSyntax(String obj, String m, String... args) {
		StringBuilder call = new StringBuilder();
		call.append("call :");
		call.append(obj);
		call.append('.');
		call.append(m);
		for (String arg : args) {
			call.append(' ');
			call.append(arg);
		}
		return call.toString();
	}

	public List<String> getMimeTypes() {
		return Arrays.asList(new String[] {"application/bat", "application/x-bat", "application/x-msdos-program"});
	}

	public List<String> getNames() {
		return Arrays.asList(names);
	}

	public String getOutputStatement(String toDisplay) {
		return "echo " + toDisplay;
	}

	public Object getParameter(String key) {
		return metaData.get(key);
	}

	public String getProgram(String... statements) {
		String sep = System.getProperty("line.separator");
		StringBuilder pgm = new StringBuilder();
		if (statements.length > 0) {
			pgm.append(statements[0]);
			for (int i = 1; i < statements.length; i++) {
				pgm.append(sep);
				pgm.append(statements[i]);
			}
		}
		return pgm.toString();
	}

	public ScriptEngine getScriptEngine() {
		return new BackslashShell();
	}

}
