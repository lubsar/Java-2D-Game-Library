package svk.lubsar.j2dgl.core;

import svk.lubsar.j2dgl.utils.debug.*;

/**
 * Abstract class intended for elemental logic of the application such as starting/stopping 
 * and the entry points for updating and rendering.
 * 
 * @see BasicCore
 * @see MultiThreadCore
 */
public abstract class Core {
	/**
	 * Keeps state whether the logic is running or not.
	 * 
	 * @see #isRunning()
	 * @see #start()
	 * @see #stop()
	 */
	protected volatile boolean running;
	
	/**
	 * Starts elemental logic. Call to {@link #init() init()} function should be included (first).
	 * @see #ini()
	 */
	protected abstract void start();
	
	/**
	 * Stops elemental logic. Call to {@link #stopped() stoped()} should be included (last). 
	 * Sets running to false.
	 * 
	 * @see #stopped()
	 */
	protected abstract void stop();
	
	/**
	 * Entry point for initialization.
	 * 
	 * @see #start()
	 */
	protected abstract void init();
	
	/**
	 * Entry point for updating.
	 * 
	 * @param delta coefficient of passed time and desired delay in-between ticks
	 */
	protected abstract void tick(double delta);
	
	/**
	 * Entry point for rendering.
	 */
	protected abstract void render();
	
	/**
	 * Callback after the logic was stopped.
	 * 
	 * @see #stop()
	 */
	protected abstract void stopped();
	
	/**
	 * Returns the number of rendered frames in the timespan of 1 second.
	 * 
	 * @return FPS
	 */
	protected abstract int getFPS();
	
	/**
	 * Returns the number of updates in the timespan of 1 second.
	 * 
	 * @return TPS 
	 */
	protected abstract int getTPS();
	
	/**
	 * @return <b>{@code true}</b> if logic is running, <b>{@code false}</b> otherwise
	 * 
	 * @see #start()
	 * @see #stop()
	 */
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.appendInstanceInfo(getClass(), hashCode());
		ret.increaseOffset();
		ret.appendPrimitive("running", running);
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.toString();
	}
}
