package svk.lubsar.j2dgl.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import svk.lubsar.j2dgl.utils.debug.DebugStringBuilder;
import svk.lubsar.j2dgl.utils.debug.MessageHandler;

public class Keyboard extends KeyAdapter {
	private static final Keyboard INSTANCE = new Keyboard();
	public static final byte MAX_BOUND_COMPONENTS = Byte.MAX_VALUE;
	
	private static Map<Integer,KeyEvent> pressedKeys = new HashMap<Integer,KeyEvent>();
	private static List<Integer> clickedKeys = new ArrayList<Integer>();
	private static String recordedKeyChars = "";
	private static boolean recordKeySequence;
	
	private static byte boundTo;
	
	private Keyboard() {}
	
	public static void initClickBufferSize(int clickedSize) {
		clickedKeys = new ArrayList<Integer>(clickedSize);
	}
	
	public static void bind(Component component) {
		if(boundTo < MAX_BOUND_COMPONENTS) {
			KeyListener[] listeners = component.getKeyListeners();
			for(KeyListener list : listeners ) {
				if(list.equals(INSTANCE)){
					MessageHandler.print("KEYBOARD", MessageHandler.INFO, "The mouse is already bound to this component " + component.toString());
					return;
				}
			}
			
			component.addKeyListener(INSTANCE);
			component.requestFocusInWindow();
			
			boundTo++;
		} else {
			MessageHandler.print("KEYBOARD", MessageHandler.ERROR, "Maximum number of bound components was reached !");
		}
	}
	
	public static void unbind(Component component) {
		if(boundTo > 0) {
			KeyListener[] listeners = component.getKeyListeners();
			for(KeyListener list : listeners ) {
				if(list.equals(INSTANCE)){
					component.removeKeyListener(INSTANCE);
					boundTo--;
					return;
				}
			}
			MessageHandler.print("KEYBOARD", MessageHandler.INFO, "MOUSE was not bound to this component" + component.toString());
			
		} else {
			MessageHandler.print("KEYBOARD", MessageHandler.INFO, "MOUSE was not bound to any component.");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = e.getKeyCode();
		
		if(!pressedKeys.containsKey(code)) {
			pressedKeys.put(code, e);
		}
		
		if(recordKeySequence) { 
			recordedKeyChars += e.getKeyChar();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove((Integer)e.getKeyCode());
		clickedKeys.remove((Integer) e.getKeyCode());
	}
	
	public static void recordKeyCharacters() {
		recordKeySequence = true;
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return pressedKeys.containsKey(keyCode);
	}
	
	public static KeyEvent getPressEvent(int keyCode) {
		return pressedKeys.get(keyCode);
	}
	
	public static boolean isClicked(int keyCode) {
		if(pressedKeys.containsKey(keyCode) && !clickedKeys.contains((Integer) keyCode)) {
			return clickedKeys.add((Integer) keyCode);
		} else {
			return false;
		}
	}
	
	public static KeyEvent getClickEvent(int keyCode) {
		if(clickedKeys.contains((Integer) keyCode)) {
			return pressedKeys.get(keyCode);			
		} else {
			return null;
		}
	}
	
	public static boolean isAnyKeyPressed() {
		return pressedKeys.size() > 0;
	}
	
	public static boolean isBoundToAnyComponent() {
		return boundTo > 0;
	}
	
	public static boolean isBound() {
		return boundTo > 0;
	}
	
	public static String getRecordedKeySequence() {
		recordKeySequence = false;
		String temp = recordedKeyChars;
		recordedKeyChars = "";
		
		return temp;
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		ret.appendInstanceInfo(getClass(), hashCode());
		ret.increaseOffset();
		ret.append(pressedKeys, "pressedKeys");
		ret.append((Object)recordedKeyChars, "recorderKeyChars");
		ret.appendPrimitive("recordKeySequence", recordKeySequence);
		ret.appendPrimitive("boundTo", boundTo);
		ret.decreaseOffset();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
	
	public static String toDebug() {
		return INSTANCE.toString();
	}
}