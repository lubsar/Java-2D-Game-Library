package svk.sglubos.oengine.lib.gfx.animation;

import svk.sglubos.oengine.lib.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.lib.utils.debug.MessageHandler;
import svk.sglubos.oengine.lib.utils.timer.Timer;

public abstract class Animation {
	protected int frameDelayTicks;
	protected int frames;

	protected boolean loop;
	protected boolean running;
	protected boolean reverse;

	protected int currentFrame;
	protected int startFrame;
	protected int endFrame;

	private int ticksToSwitch;

	public Animation(int frameDelay, int frames) {
		this(frameDelay, 0, frames - 1, frames);
	}

	public Animation(int frameDelayTicks, int startFrame, int endFrame, int frames) {
		this.frameDelayTicks = frameDelayTicks;
		this.frames = frames;

		initStartAndEnd(startFrame, endFrame);
	}

	public abstract void render(AnimationRenderer renderer, int x, int y);

	public void update() {
		if(!running) {
			return;
		}
		
		if(ticksToSwitch <= 0) {
			switchFrame();
			ticksToSwitch = frameDelayTicks + 1;
		}
		
		ticksToSwitch--;
	}

	public void start(boolean loop) {
		if (running) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.WARNING, "Animation is still running !");
			return;
		}

		if (startFrame == endFrame) {
			currentFrame = startFrame;
			return;
		}

		currentFrame = startFrame;
		running = true;
		reverse = false;
		this.loop = loop;
	}

	public void startReverse(boolean loop) {
		if (running) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.WARNING, "Animation is still running !");
			return;
		}

		if (startFrame == endFrame) {
			currentFrame = endFrame;
			return;
		}

		if (!Timer.isInitialized()) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR, "Timer is not initialized !");
			throw new IllegalStateException("Timer is not initialized !");
		}

		currentFrame = endFrame;
		running = true;
		reverse = true;
		this.loop = loop;
	}

	public void stop() {
		running = false;
	}

	protected void switchFrame() {
		if (reverse) {
			currentFrame--;
		} else {
			currentFrame++;
		}

		if (currentFrame < startFrame) {
			if (loop) {
				currentFrame = endFrame;
			} else {
				stop();
			}
		} else if (currentFrame > endFrame) {
			if (loop) {
				currentFrame = startFrame;
			} else {
				stop();
			}
		}
	}

	protected void initStartAndEnd(int startFrame, int endFrame) {
		if (startFrame > endFrame || startFrame < 0 || endFrame < 0 || startFrame > frames - 1
				|| endFrame > frames - 1) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR,
					"Invalind animation starting or ending frame. Starting frame can not be higher than end frame: start:"
							+ startFrame + " end: " + endFrame);
			throw new IllegalArgumentException(
					"Invalid starting or ending frame: start: " + startFrame + " end:" + endFrame);
		}

		this.startFrame = startFrame;
		this.endFrame = endFrame;
	}

	public void setFrameDelay(int frameDelay) {
		if (frameDelay < 0) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR,
					"Invalind animation frame delay, delay can not be less than zero " + frameDelay);
			throw new IllegalArgumentException("Frame delay can not be less than 0: " + frameDelay);
		}
		this.frameDelayTicks = frameDelay;
	}

	public int getFrameDelay() {
		return frameDelayTicks;
	}

	public void setStartFrame(int startFrame) {
		if (startFrame < 0 || startFrame >= frames || startFrame > endFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR,
					"Illegal starting frame, frame cannot be less than zero and more than frames -1: " + startFrame);
			throw new IllegalArgumentException("Illegal starting frame: " + startFrame);
		}

		this.startFrame = startFrame;
	}

	public void setEndFrame(int endFrame) {
		if (endFrame < 0 || endFrame >= frames || endFrame < startFrame) {
			MessageHandler.printMessage("ANIMATION", MessageHandler.ERROR,
					"Illegal ending frame, frame cannot be less than zero and more than frames -1: " + endFrame);
			throw new IllegalArgumentException("Illegal ending frame: " + endFrame);
		}

		this.endFrame = endFrame;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public boolean isRunning() {
		return running;
	}

	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();

		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.append("frameDelay", frameDelayTicks);
		ret.append("ticksToSwitch", ticksToSwitch);
		ret.append("frames", frames);
		ret.append("loop", loop);
		ret.append("running", running);
		ret.append("reverse", reverse);
		ret.append("currentFrame", currentFrame);
		ret.append("startFrame", startFrame);
		ret.append("endFrame", endFrame);

		ret.decreaseLayer();
		ret.appendCloseBracket();

		return ret.getString();
	}
}
