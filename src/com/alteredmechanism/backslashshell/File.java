package com.alteredmechanism.backslashshell;

import java.net.URI;

public class File extends java.io.File {


	private static final long serialVersionUID = 1L;

	public File(java.io.File parent, String child) {
		super(parent, child);
	}

	public File(String parent, String child) {
		super(parent, child);
	}

	public File(String pathname) {
		super(pathname);
	}

	public File(URI uri) {
		super(uri);
	}

	@Override
	public String getPath() {
		return super.getPath().replaceAll("/", "\\\\");
	}
}
