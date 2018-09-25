package svk.sglubos.oengine.lib.event;

public interface Event {
	public Class<? extends Event> getType();
}
