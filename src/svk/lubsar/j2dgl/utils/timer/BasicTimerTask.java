package svk.lubsar.j2dgl.utils.timer;
//TODO toString
public class BasicTimerTask extends TimerTask {
	public BasicTimerTask(byte timeFormat, double delay, TimerCallback callback) {
		super(timeFormat, delay, callback);
		nextCallback = delay;
	}
	
	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			done = true;
		}
	}
}
