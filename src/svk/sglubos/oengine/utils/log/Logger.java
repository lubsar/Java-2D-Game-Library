package svk.sglubos.oengine.utils.log;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.oengine.utils.debug.DebugStringBuilder;

public class Logger {
	private static final Map<String, PrintStream> globalLogs = new HashMap<String, PrintStream>();
	private static Log masterLog = null;
	
	public static void setMasterLog(Log log) {
		if(masterLog != null) {
			masterLog.close();
		}
		Logger.masterLog = log;
	}

	//TODO exception
	public static void log(String... strings) {
		if(masterLog != null) {
			for(String s : strings) {
				masterLog.print(s);							
			}
		}
	}
	
	//TODO exception
	public static void close() {
		if(masterLog != null) {
			masterLog.close();
		}
	}
	
	public static Log addGlobalLog(Log log) {
		globalLogs.put(log.id, log);
		return log;
	}
	
	public static Log getGlobalLog(String logID) {
		Log glob = (Log) globalLogs.get(logID);
		if(glob == null) {
			//TODO exception
		}
		
		return (Log) globalLogs.get(logID);
	}
	
	public static String toDebug() {
		DebugStringBuilder ret = new DebugStringBuilder();
			
		ret.append(Logger.class, DebugStringBuilder.STATIC_CONTENT);
		ret.increaseLayer();
		ret.append(globalLogs, "globalLogs");
		ret.append(masterLog, "masterLog");
		ret.decreaseLayer();
		ret.appendCloseBracket();
			
		return ret.getString();
	}
}