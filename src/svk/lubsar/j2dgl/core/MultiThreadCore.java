package svk.lubsar.j2dgl.core;

import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;
import svk.lubsar.j2dgl.utils.debug.MessageHandler;

/**
 * Implementation of the {@link Core} class with multithreaded game loop.
 * <p>
 * The game loop implementation uses 1 thread for update and 1 for render.
 * Update cycles are determined by {@link MultiThreadCore#MultiThreadCore(int, int, boolean) constructor} parameters.
 * Inherit this class to use its functionality.
 * <p>
 * Debug information is passed to {@link svk.lubsar.j2dgl.utils.debug.MessageHandler MessageHandler}.
 * 
 * @see Core
 * @see BasicCore
 */
public abstract class MultiThreadCore extends Core {
	/**
	 * Use this value as parameter for fps limit in constructor to disable fps limiting.
	 */
	public static final int FPS_UNLIMITED = -1;
	
	/**
	 * Update {@link Runnable} implementation, calls {@link #tick(double)}
	 */
	private Updater updater;
	
	/**
	 * Rendering {@link Runnable} implementation, calls {@link #render()}
	 */
	private Renderer renderer;
	
	/**
	 * {@link Thread} object for updating.
	 */
	protected Thread update;
	
	/**
	 * {@link Thread} object for rendering.
	 */
	protected Thread render;
	
	/**
	 * Controls whether game loop generates debug output
	 */
	private boolean debug;
	
	/**
	 * Determines how many updates should be performed per second.
	 */
	private int ticksPerSecond;
	
	/**
	 * Determines how many frames should be rendered per second.
	 */
	private int fpsLimit;
	
	/**
	 * Cumulated number of frames per second.
	 */
	private int fps;
	
	/**
	 * Cumulated number of updates per second.
	 */
	private int ticks;
	
	/**
	 * @param ticksPerSecond determines how many updates should be performed every second
	 * @param fpsLimit determines maximum number of frames rendered per second
	 * @param debug determines whether debug information is generated
	 * 
	 * @throws RuntimeException if number of updates is less than 1 or fps limit is less than 1 or not value of {@link #FPS_UNLIMITED FPS_UNLIMITED}
	 * 
	 * @see MultiThreadCore
	 */
	public MultiThreadCore(int ticksPerSecond, int fpsLimit, boolean debug) {
		if(ticksPerSecond < 1) {
			throw new RuntimeException("Number of updates per second must be more than 0");
		}
		
		if(fpsLimit < 1 && fpsLimit != FPS_UNLIMITED) {
			throw new RuntimeException("Fps limit must be higher than 0, or " + FPS_UNLIMITED + " for no limit");
		}
		
		this.ticksPerSecond = ticksPerSecond;
		this.fpsLimit = fpsLimit;
		this.debug = debug;
	}
	
	/**
	 * Sets FPS limit for rendering thread
	 * @param fpsLimit 
	 * 
	 * @throws RuntimeException if number of updates is less than 1 or fps limit is less than 1 or not value of {@link #FPS_UNLIMITED FPS_UNLIMITED}
	 * 
	 * @see Renderer
	 */
	protected void setFPSLimit(int fpsLimit) {
		if(fpsLimit < 1 && fpsLimit != FPS_UNLIMITED) {
			throw new RuntimeException("Fps limit must be higher than 0, or " + FPS_UNLIMITED + " for no limit");
		}
		
		this.fpsLimit = fpsLimit;
		renderer.setFPSLimit(fpsLimit);
	}
	
	/**
	 * @return maximum number of rendered frames per second or value of {@link #FPS_UNLIMITED FPS_UNLIMITED}
	 */
	protected int getFPSLimit() {
		return fpsLimit;
	}
	
	/**
	 * @return limit for number of updates per second
	 */
	protected int getTPSLimit() {
		return ticksPerSecond;
	}
	
	/**
	 * @return <code><b>true</b></code> if debug information is generated <code><b>false</b></code> otherwise
	 */
	protected boolean isDebug() {
		return debug;
	}
	
	/**
	 * Set flag whether debug information should be generated or not
	 * @param debug
	 */
	protected void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * Starts game loop. {@link #init() init()} is called before the update and render loop is started.
	 * 
	 * @throws RuntimeException if game loop is already running
	 * 
	 * @see #stop()
	 */
	@Override
	protected void start() {
		if(running) {
			throw new RuntimeException("Game loop is already running");
		}
		
		running = true;
		updater = new Updater((long) (1000 / ticksPerSecond));
		renderer = new Renderer();
		update = new Thread(updater, "Updater");
		render = new Thread(renderer, "Renderer");
		init();
		
		update.start();
		render.start();
	}
	
	/**
	 * Stops the game loop.
	 * 
	 * @throws RuntimeException if game loop is not running
	 * 
	 * @see #start()
	 */
	@Override
	protected void stop() {
		 if(!running) {
			 throw new RuntimeException("Game loop is not running");
		 }
		running = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getFPS() {
		return fps;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getTPS() {
		return ticks;
	}
	
	/**
	 * Implementation of {@link Runnable} interface for rendering.
	 *
	 */
	private final class Renderer implements Runnable {
		private long sleep;
		public volatile int cummulatedFPS;
		
		@Override
		public void run() {
			cummulatedFPS = 0;
			
			while(running){
				render();
				
				if (debug)
					cummulatedFPS++;
				
				if(cummulatedFPS != FPS_UNLIMITED) {
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
	
	/**
	 * Implementation of {@link Runnable} interface for updating of the logic.
	 *
	 */
	private final class Updater implements Runnable {
		long sleep;
		public volatile int cummulatedTPS;
		
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
					fps = renderer.cummulatedFPS;
					ticks = cummulatedTPS;
					
					renderer.cummulatedFPS = 0;
					cummulatedTPS = 0;
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

