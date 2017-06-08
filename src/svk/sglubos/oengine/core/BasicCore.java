package svk.sglubos.oengine.core;

import svk.sglubos.oengine.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.utils.debug.MessageHandler;

public abstract class BasicCore extends Core implements Runnable {
	public static final int FPS_UNLIMITED = -1;
	
	protected Thread thread;
	
	private boolean debug;
	private long sleep;
	private int ticksPerSecond;
	private int fpsLimit;
	private int fps;
	private int ticks;
	
	public BasicCore(int ticksPerSecond, int fpsLimit, boolean debug) {
		this.ticksPerSecond = ticksPerSecond;
		this.fpsLimit = fpsLimit;
		this.debug = debug;
		
		if(fpsLimit != FPS_UNLIMITED) {
			this.sleep = (long) (1000 / fpsLimit);
		}
	}
	
	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		long lastTimeDebugOutput = System.currentTimeMillis();
		double delta = 0;
		double nanoSecPerTick = Math.pow(10, 9) / ticksPerSecond;
		fps = 0;
		ticks = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) /nanoSecPerTick;
			lastTime = now;
				
			while(delta >= 1){
				delta--;
				tick();
				
				if (debug)
				 ticks++;
			}
			
			render();
			
			if (debug)
				fps++;
			
			if(fpsLimit != FPS_UNLIMITED) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
			if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
				if(debug) {
					MessageHandler.printMessage(MessageHandler.INFO, "ticks: " + ticks + " fps: " + fps);
				}
				lastTimeDebugOutput += 1000;
				fps = 0;
				ticks = 0;
			}
		}
		
		stopped();	
	}

	protected void setFPSLimit(int fpsLimit) {
		this.fpsLimit = fpsLimit;
		this.sleep = (long) (1000 / fpsLimit);
	}
	
	protected int getFPSLimit() {
		return fpsLimit;
	}
	
	protected int getTPSLimit() {
		return ticksPerSecond;
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
		thread = new Thread(this,"core");
		thread.start();
	}
	
	@Override
	protected void stop() {
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
		ret.append("sleep", sleep);
		ret.append("ticksPerSecond", ticksPerSecond);
		ret.append("fpsLimit", fpsLimit);
		ret.append("tps", ticks);
		ret.append("fps", fps);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
