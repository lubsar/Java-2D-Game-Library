package svk.sglubos.oengine.lib.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;

public abstract class BasicCore extends Core {
	public static final int FPS_UNLIMITED = -1;
	
	protected Timer timer;
	
	private boolean debug;
	private int updatesDelay;
	private int fps;
	private int ticks;
	
	public BasicCore(int updatesDelay, boolean debug) {
		this.updatesDelay = updatesDelay;
		this.debug = debug;
	}

	protected int getUpdatesDelayMs() {
		return updatesDelay;
	}
	
	protected boolean isDebug() {
		return debug;
	}
	
	protected void setDebug(boolean debug) {
		this.debug = debug;
	}
		
	@Override
	protected void start() {
		running = true;
		init();
		
		timer = new Timer(updatesDelay, new ActionListener() {
			long lastTimeNano = System.nanoTime();
			long lastTimeDebugOutput = System.currentTimeMillis();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long now = System.nanoTime();
				tick((now - lastTimeNano) / (updatesDelay * 1000000));
				
				ticks++;
				
				render();
				fps++;
				
				if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
					if(debug) {
						MessageHandler.printMessage(MessageHandler.INFO, "ticks: " + ticks + " fps: " + fps + "delta: " + ((double)(now - lastTimeNano) / (updatesDelay * 1000000)));
					}
					
					lastTimeDebugOutput += 1000;
					fps = 0;
					ticks = 0;
				}
				
				lastTimeNano = now;
			}
	
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	
	@Override
	protected void stop() {
	 timer.stop();
	 stopped();
	 running = false;
	}
	
	@Override
	protected int getFPS() {
		return fps;
	}
	
	@Override
	protected int getTPS() {
		return ticks;
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("debug", debug);
		ret.append("updatesDelayMs", updatesDelay);
		ret.append("tps", ticks);
		ret.append("fps", fps);
		ret.append(timer, "Timer");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
