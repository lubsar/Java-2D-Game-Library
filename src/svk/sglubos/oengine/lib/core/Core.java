package svk.sglubos.oengine.lib.core;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;

public abstract class Core {
	protected volatile boolean running;
	
	protected abstract void start();
	protected abstract void stop();
	protected abstract void init();
	protected abstract void tick();
	protected abstract void render();
	protected abstract void stopped();
	protected abstract int getFPS();
	protected abstract int getTPS();
	
	public boolean isRunning() {
		return running;
	}
	
	@Override
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("running", running);
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.toString();
	}
}
