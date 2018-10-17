package svk.lubsar.j2dgl.utils.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

public abstract class Log extends PrintStream {
	String id;
	
	public Log(String id, File file) throws FileNotFoundException {
		super(file);
		this.id = id;
	}
	
	public Log(String id, File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		this.id = id;
	}

	public Log(String id, OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		this.id = id;
	}

	public Log(String id, OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		this.id = id;
	}

	public Log(String id, OutputStream out) {
		super(out);
		this.id = id;
	}

	public Log(String id, String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		this.id = id;
	}

	public Log(String id, String fileName) throws FileNotFoundException {
		super(fileName);
		this.id = id;
	}
	
	public void log(String... strings) {
		for(String string : strings) {
			super.print(string);
		}
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendInstanceInfo(this.getClass(), hashCode());
		ret.increaseOffset();
		ret.appendln(super.toString());
		ret.appendPrimitive("id", (Object)id);
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}