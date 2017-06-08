package svk.sglubos.oengine.utils.debug;

import java.io.PrintStream;

import svk.sglubos.oengine.utils.log.Log;

public class MessageHandler {
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static final String ERROR = "ERROR";
	
	private static boolean logPrint = false;
	private static boolean logError = false;
	
	private static PrintStream printLog = null;
	private static PrintStream errorLog = null;
	
	public static PrintStream printStream = System.out;
	public static PrintStream errorStream = System.err;
	
	public static void printMessage(String tag, String message) {
		printMessage("ENGINE", tag, message);
	}
	
	public static void printMessage(String prefix, String tag, String message) {
		if(tag.equals(ERROR)){
			String msg = prefix + ": [" + ERROR + "] " + message;
			if(errorStream != null) {
				errorStream.println(msg);
			}
			if(logError) {
				errorLog.println(msg);
			}
			return;
		}
		
		String msg = prefix + ": [" +tag + "] " + message;
		if(printStream != null){
			printStream.println(msg);
		}
		if(logPrint) {
			printLog.println(msg);
		}
	}
	
	public static void setPrintLogging(Log log, boolean enabled) {
		if(log != null) {
			printLog = log;
		}
		if(enabled && printLog == null) {
			//TODO Exception
		}
		logPrint = enabled;
	}
	
	public static void setErrorLogging(Log log, boolean enabled) {
		if(log != null) {
			errorLog = log;
		}
		if(enabled && errorLog == null) {
			//TODO Exception
		}
		
		logError = enabled;
	}
}
