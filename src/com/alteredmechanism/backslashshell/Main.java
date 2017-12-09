package com.alteredmechanism.backslashshell;

import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class Main {

	public static void main(String[] args) {
		try {
			ScriptEngineFactory factory = new BackslashShellFactory();
			ScriptEngine engine = factory.getScriptEngine();
			for (String arg : args) {
				engine.eval(new FileReader(arg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
