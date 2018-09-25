package svk.sglubos.oengine.lib.utils.log;

import java.time.LocalDateTime;

public class LoggingUtilities {
	public static String getTime() {
		return getTime("%d-%d-%d %d-%d-%d");
	}
	
	public static String getTime(String format) {
		LocalDateTime dt = LocalDateTime.now();
		return String.format(format, dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), dt.getMinute(), dt.getSecond());
	}
}
