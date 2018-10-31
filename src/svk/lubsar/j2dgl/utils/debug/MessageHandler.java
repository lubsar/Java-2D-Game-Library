package svk.lubsar.j2dgl.utils.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import svk.lubsar.j2dgl.utils.log.Log;

public class MessageHandler {
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";
	
	private static MessagePrintStream printStream = new MessagePrintStream((OutputStream)System.out);
	private static MessagePrintStream errorStream = new MessagePrintStream((OutputStream)System.err);
	
	private static boolean enabled = true;
	
	public static void print(String tag, String message) throws RuntimeException {
		print(null, tag, message);
	}
	
	public static void print(String prefix, String tag, String message) throws RuntimeException {
		if(!enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		if(prefix == null) {
			if(tag.equals(ERROR)){
				errorStream.println(String.format("[%s] %s", tag, message));
			} else {
				printStream.println(String.format("[%s] %s", tag, message));
			}
		} else {
			if(tag.equals(ERROR)){
				errorStream.println(String.format("%s: [%s] %s", prefix, tag, message));
			} else {
				printStream.println(String.format("%s: [%s] %s", tag, message));
			}
		}
	}
	
	public static void printError(String tag, String message) {
		 printError(null, tag, message);
	}
	
	public static void printError(String prefix, String tag, String message) {
		if(!enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		if(prefix == null) {
			errorStream.println(String.format("[%s] %s", tag, message));
		} else {
			errorStream.println(String.format("%s: [%s] %s", prefix, tag, message));
		}
	}
	
	public static void setEnabled(boolean enabled) {
		if(enabled && !MessageHandler.enabled) {
			throw new RuntimeException("Cant enable MessageHandler until intialized");
		}
		
		MessageHandler.enabled = enabled;
	}
	
	public static boolean isEnabled() {
		return MessageHandler.enabled;
	}

	
	public static void init(PrintStream printStream, PrintStream errorStream) throws NullPointerException {
		MessageHandler.printStream = new MessagePrintStream((OutputStream)printStream);
		MessageHandler.printStream = new MessagePrintStream((OutputStream)printStream);
		
		enabled = true;
	}
	
	public static void destroy() {
		printStream = null;
		errorStream = null;
		
		enabled = false;
	}
	
	public static void setPrintStream(PrintStream stream) throws NullPointerException {
		if((printStream != null) && printStream.logging) {
			PrintStream log = printStream.log;
			printStream = new MessagePrintStream((OutputStream)stream);
			printStream.setLogging(log, true);
		} else {
			printStream = new MessagePrintStream((OutputStream)stream);
		}
	}
	
	public static void setErrorStream(PrintStream stream) throws NullPointerException {
		if((errorStream != null) && errorStream.logging) {
			PrintStream log = errorStream.log;
			errorStream = new MessagePrintStream((OutputStream)stream);
			errorStream.setLogging(log, true);
		} else {
			errorStream = new MessagePrintStream((OutputStream)stream);
		}
	}
	
	public static PrintStream getPrintStream() throws RuntimeException {
		if(!enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		return printStream;
	}
	
	public static PrintStream getErrorStream() throws RuntimeException {
		if(!enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		return errorStream;
	}
	
	public static void setPrintLogging(Log log, boolean enabled) throws NullPointerException, RuntimeException {
		if(enabled && !MessageHandler.enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		printStream.setLogging(log, enabled);
	}
	
	public static void setErrorLogging(Log log, boolean enabled) throws NullPointerException, RuntimeException {
		if(enabled && !MessageHandler.enabled) {
			throw new RuntimeException("MessageHandler is disabled");
		}
		
		errorStream.setLogging(log, enabled);
	}
	
	public static boolean isLoggingPrint() {
		if(printStream == null) {
			return false;
		}
		
		return printStream.logging;
	}
	
	public static boolean isLoggingError() {
		if(errorStream == null) {
			return false;
		}
		
		return errorStream.logging;
	}
	
	private static class MessagePrintStream extends PrintStream {
		public PrintStream log = null;
		public boolean logging = false;
		
		public MessagePrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
			super(file, csn);
		}

		public MessagePrintStream(File file) throws FileNotFoundException {
			super(file);
		}

		public MessagePrintStream(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
			super(out, autoFlush, encoding);
		}

		public MessagePrintStream(OutputStream out, boolean autoFlush) {
			super(out, autoFlush);
		}

		public MessagePrintStream(OutputStream out) {
			super(out);
		}

		public MessagePrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
			super(fileName, csn);
		}

		public MessagePrintStream(String fileName) throws FileNotFoundException {
			super(fileName);
		}

		public void setLogging(PrintStream log, boolean logging) throws NullPointerException {
			if((log == null) && logging) {
				throw new NullPointerException("Log object is null");
			}
			
			this.log = log;
			this.logging = logging;
		}

		@Override
		public void write(int b) {
			if(logging) {
				log.write(b);
			}
			
			super.write(b);
		}

		@Override
		public void write(byte[] buf, int off, int len) {
			if(logging) {
				log.write(buf, off, len);
			}
			
			super.write(buf, off, len);
		}

		@Override
		public void print(boolean b) {
			if(logging) {
				log.print(b);
			}
			
			super.print(b);
		}

		@Override
		public void print(char c) {
			if(logging) {
				log.print(c);
			}
			
			super.print(c);
		}

		@Override
		public void print(int i) {
			if(logging) {
				log.print(i);
			}
			
			super.print(i);
		}

		@Override
		public void print(long l) {
			if(logging) {
				log.print(l);
			}
			
			super.print(l);
		}

		@Override
		public void print(float f) {
			if(logging) {
				log.print(f);
			}
			
			super.print(f);
		}

		@Override
		public void print(double d) {
			if(logging) {
				log.print(d);
			}
			
			super.print(d);
		}

		@Override
		public void print(char[] s) {
			if(logging) {
				log.print(s);
			}
			
			super.print(s);
		}

		@Override
		public void print(String s) {
			if(logging) {
				log.print(s);
			}
			
			super.print(s);
		}

		@Override
		public void print(Object obj) {
			if(logging) {
				log.print(obj);
			}
			
			super.print(obj);
		}

		@Override
		public void println() {
			if(logging) {
				log.println();
			}
			
			super.println();
		}

		@Override
		public void println(boolean x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(char x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(int x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(long x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(float x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(double x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(char[] x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(String x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public void println(Object x) {
			if(logging) {
				log.println(x);
			}
			
			super.println(x);
		}

		@Override
		public PrintStream printf(String format, Object... args) {
			if(logging) {
				log.printf(format, args);
			}
			
			return super.printf(format, args);
		}

		@Override
		public PrintStream printf(Locale l, String format, Object... args) {
			if(logging) {
				log.printf(l, format, args);
			}
			
			return super.printf(l, format, args);
		}

		@Override
		public PrintStream format(String format, Object... args) {
			if(logging) {
				log.format(format, args);
			}
			
			return super.format(format, args);
		}

		@Override
		public PrintStream format(Locale l, String format, Object... args) {
			if(logging) {
				log.format(l, format, args);
			}
			
			return super.format(l, format, args);
		}

		@Override
		public PrintStream append(CharSequence csq) {
			if(logging) {
				log.append(csq);
			}
			
			return super.append(csq);
		}

		@Override
		public PrintStream append(CharSequence csq, int start, int end) {
			if(logging) {
				log.append(csq, start, end);
			}
			
			return super.append(csq, start, end);
		}

		@Override
		public PrintStream append(char c) {
			if(logging) {
				log.append(c);
			}
			
			return super.append(c);
		}

		@Override
		public void write(byte[] b) throws IOException {
			if(logging) {
				log.write(b);
			}
			
			super.write(b);
		}
	}
}
