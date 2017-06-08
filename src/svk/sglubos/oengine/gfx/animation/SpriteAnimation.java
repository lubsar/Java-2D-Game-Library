package svk.sglubos.oengine.gfx.animation;

import svk.sglubos.oengine.gfx.Screen;
import svk.sglubos.oengine.gfx.sprite.Sprite;
import svk.sglubos.oengine.gfx.sprite.SpriteSheet;
import svk.sglubos.oengine.utils.debug.DebugStringBuilder;

public class SpriteAnimation extends Animation {
	protected Sprite[] sprites;
	//fix NullPointer
	public SpriteAnimation(Sprite[] sprites, double frameDelay, byte timeFormat) {
		super(frameDelay, timeFormat, sprites.length);			
		this.sprites = sprites;
	}
	
	public SpriteAnimation(SpriteSheet spriteSheet, double frameDelay, int startFrame, int endFrame, byte timeFormat) {
		super(frameDelay, startFrame, endFrame, spriteSheet.getSprites().length, timeFormat);
		this.sprites = spriteSheet.getSprites();
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderSprite(sprites[currentFrame], x, y);
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append(sprites, "sprites");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}