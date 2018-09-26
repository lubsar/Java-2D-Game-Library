package svk.lubsar.j2dgl.gfx.animation;

import svk.lubsar.j2dgl.gfx.sprite.AbstractSpriteRenderer;
import svk.lubsar.j2dgl.gfx.sprite.Sprite;
import svk.lubsar.j2dgl.gfx.sprite.SpriteSheet;
import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;

public class SpriteAnimation extends Animation<AbstractSpriteRenderer> {
	protected Sprite[] sprites;
	//fix NullPointer
	public SpriteAnimation(Sprite[] sprites, int frameDelayTicks, byte timeFormat) {
		super(frameDelayTicks, sprites.length);			
		this.sprites = sprites;
	}
	
	public SpriteAnimation(SpriteSheet spriteSheet, int frameDelayTicks, int startFrame, int endFrame) {
		super(frameDelayTicks, startFrame, endFrame, spriteSheet.getSprites().length);
		this.sprites = spriteSheet.getSprites();
	}

	@Override
	public void render(AbstractSpriteRenderer renderer, int x, int y) {
		renderer.render(sprites[currentFrame], x, y);
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