package svk.lubsar.j2dgl.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;
import svk.lubsar.j2dgl.utils.debug.MessageHandler;

/**
 * Basic implementation of the {@link Core} class with game loop.
 * <p>
 * The game loop implementation uses {@link javax.swing.Timer}.
 * Update cycle is determined by constructor parameter updatesDelay.
 * Update and render is synchronized, so both tick() and render functions are called every game loop cycle (in such order).
 * Inherit this class to use its functionality.
 * <p>
 * Debug information is passed to {@link svk.lubsar.j2dgl.utils.debug.MessageHandler MessageHandler}.
 * 
 * @see Core
 * @see MultiThreadCore
 */
public abstract class BasicCore extends Core {
	/**
	 * Javax Timer used for timing of the update and render
	 */
	protected Timer timer;
	
	/**
	 * Controls whether game loop generates debug output
	 */
	private boolean debug;
	
	/**
	 * Delay in-between updates in milliseconds
	 */
	private int updatesDelayMs;
	
	/**
	 * Cumulated number of rendered frames per second.
	 */
	private int fps;
	
	/**
	 * Cumulated number of updates per second.
	 */
	private int ticks;
	
	/**
	 * @param updatesDelayMs delay between updates in milliseconds
	 * @param debug determines whether debug information is generated
	 * 
	 * @throws RuntimeException if updates delay is less than 1
	 * 
	 * @see BasicCore
	 */
	public BasicCore(int updatesDelayMs, boolean debug) {
		if(updatesDelayMs < 1) {
			throw new RuntimeException("Update delay must be higher than 0");
		}
		this.updatesDelayMs = updatesDelayMs;
		this.debug = debug;
	}

	/**
	 * @return delay between updates in milliseconds
	 */
	protected int getUpdatesDelayMs() {
		return updatesDelayMs;
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
	 * Starts game loop. {@link #init() init()} is called before the {@link javax.swing.Timer} is set up.
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
		init();
		
		timer = new Timer(updatesDelayMs, new ActionListener() {
			long lastTimeNano = System.nanoTime();
			long lastTimeDebugOutput = System.currentTimeMillis();
			
			int ticksCumulated = 0;
			int fpsCumulated = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long now = System.nanoTime();
				tick((now - lastTimeNano) / (updatesDelayMs * 1000000));
				
				ticksCumulated++;
				
				render();
				fpsCumulated++;
				
				if((System.currentTimeMillis() - lastTimeDebugOutput) >= 1000){
					BasicCore.this.ticks = ticksCumulated;
					BasicCore.this.fps = fpsCumulated;
					if(debug) {
						MessageHandler.print(MessageHandler.INFO, "ticks: " + ticks + " fps: " + fps + "delta: " + ((double)(now - lastTimeNano) / (updatesDelayMs * 1000000)));
					}
					
					lastTimeDebugOutput += 1000;
					ticksCumulated = 0;
					fpsCumulated = 0;
				}
				
				lastTimeNano = now;
			}
	
		});
		
		timer.setRepeats(true);
		timer.start();
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
	 timer.stop();
	 stopped();
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
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.appendInstanceInfo(getClass(), hashCode());
		ret.increaseOffset();
		ret.appendln(super.toString());
		ret.appendPrimitive("debug", debug);
		ret.appendPrimitive("updatesDelayMs", updatesDelayMs);
		ret.appendPrimitive("tps", ticks);
		ret.appendPrimitive("fps", fps);
		ret.append(timer, "Timer");
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
