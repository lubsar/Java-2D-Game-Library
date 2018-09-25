package svk.sglubos.oengine.lib.core;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;

public abstract class MultiThreadCore extends Core {
	public static final int FPS_UNLIMITED = -1;
	
	private Updater updater;
	private Renderer renderer;
	
	protected Thread update;
	protected Thread render;
	
	private boolean debug;
	private int ticksPerSecond;
	private int fpsLimit;
	private int fps;
	private int ticks;
	
	public MultiThreadCore(int ticksPerSecond, int fpsLimit, boolean debug) {
		this.ticksPerSecond = ticksPerSecond;
		this.fpsLimit = fpsLimit;
		this.debug = debug;
	}
	
	protected void setFPSLimit(int fpsLimit) {
		this.fpsLimit = fpsLimit;
		renderer.setFPSLimit(fpsLimit);
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
		updater = new Updater((long) (1000 / ticksPerSecond));
		renderer = new Renderer();
		update = new Thread(updater, "Updater");
		render = new Thread(renderer, "Renderer");
		init();
		
		update.start();
		render.start();
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
	
	private final class Renderer implements Runnable {
		private long sleep;
		
		@Override
		public void run() {
			fps = 0;
			
			while(running){
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
			}
		}
		
		public synchronized void setFPSLimit(int fpsLimit) {
			sleep = (1000 / fpsLimit);
		}
	}
	
	private final class Updater implements Runnable {
		long sleep;
		
		Updater(long sleep) {
			this.sleep = sleep;
		}
		
		@Override
		public void run() {
			long lastTime = System.nanoTime();
			long lastTimeDebugOutput = System.currentTimeMillis();
			double delta = 0;
			double nanoSecPerTick = Math.pow(10, 9) / ticksPerSecond;
			ticks = 0;
			
			while(running){
				long now = System.nanoTime();
				delta += (now - lastTime) /nanoSecPerTick;
				lastTime = now;
					
				while(delta >= 1){
					delta--;
					tick(1.0);
					
					if (debug)
					 ticks++;
				}
				
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("debug", debug);
		ret.append("Updater sleep",(long) 1000 / ticksPerSecond);
		ret.append("Renderer sleep",(long) 1000 / fpsLimit);
		ret.append("ticksPerSecond", ticksPerSecond);
		ret.append("fpsLimit", fpsLimit);
		ret.append("tps", ticks);
		ret.append("fps", fps);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}

