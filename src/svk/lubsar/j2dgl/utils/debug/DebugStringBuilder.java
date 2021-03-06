package svk.lubsar.j2dgl.utils.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import svk.lubsar.j2dgl.utils.Constants;

//TODO build fancy string formatter
public class DebugStringBuilder implements Constants {
	public static final int STATIC_CONTENT = -1;
	
	private StringBuilder builder = new StringBuilder();
	public int offset;
	
	public void append(String string) {
		if(!string.contains("\r") && !string.contains("\n") && !string.contains(LINE_SEPARATOR)) {
			appendTabs();
			builder.append(string);
			return;
		}
		
		if(string.contains(LINE_SEPARATOR)) {
			string = string.replaceAll(LINE_SEPARATOR, "\n");
		}
		
		String[] strings = string.split("[\r\n]" );
		
		for(int i = 0; i < strings.length; i++) {
			appendTabs();
			builder.append(strings[i]);
			if(i < strings.length -1 )
				builder.append(LINE_SEPARATOR);
		}
	}
	
	public void append(String... strings) {
		for(String s : strings) {
			appendln(s);
		}
	}
	
	public void appendInstanceInfo(Class<?> clas, int hashcode) {
		appendTabs();
		builder.append(clas.getName());
		builder.append("@");
		if(hashcode == STATIC_CONTENT) {
			builder.append("STATIC CONTENT");	
		} else {
			builder.append(Integer.toHexString(hashcode));			
		}
		builder.append(" {");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(Object object, String name) {
		append("(");
		if(object == null ) {
			builder.append("null");
		} else {
			builder.append(object.getClass());
		}
		builder.append(')');
		builder.append(name);
		builder.append(" = [");
		builder.append(LINE_SEPARATOR);
		if(object == null) {
			offset++;
			appendln("null");
			offset--;
		} else {
			offset++;
			appendln(object.toString());
			offset--;
		}
		
		appendln("]");
	}
	
	public void append(Object[] objects, String name) {
		append("(");
		if(objects == null ) {
			builder.append("null)");
		} else {
			builder.append(objects.getClass());						
		}
		
		builder.append(')');
		builder.append(name);
		builder.append(" = [" + LINE_SEPARATOR);
		if(objects != null) {
			increaseOffset();
			for(int i = 0; i < objects.length; i++) {
				if(objects[i] == null) {
					appendln("null");
				} else {
					appendln(objects[i].toString());
				}
			}
			decreaseOffset();
		}
		
		appendln("]");
	}
	
	public void appendPrimitive(String name, Object primitive) {
		append(name);
		builder.append(" = ");
		if(primitive == null) {
			builder.append("null");
		}else {
			builder.append(primitive.toString());						
		}
		
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendPrimitive(String name, Object[] primitives) {
		append(name);
		builder.append(" = [");
		  for(int i = 0; i < primitives.length; i++) {
			  try{
				  appendln(primitives[i].toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(i < primitives.length - 1) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append("]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(Iterator<Object> iter, String name) {
		append(name);
		builder.append('<');
		builder.append(iter.getClass());
		builder.append('>');
		append(" = [");
		  while(iter.hasNext()){
			 Object o = iter.next();
			  try{
				  appendln(o.toString());			
			  } catch (NullPointerException e) {
				  builder.append("null");
			  }
			  
			  if(iter.hasNext()) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void append(Map<?, ?> map, String name) {
		append(name);
		Iterator<Object> keys = new ArrayList<Object>(map.keySet()).iterator();
		Iterator<Object> values = new ArrayList<Object>(map.values()).iterator();
		
		builder.append(" = [");
		while(keys.hasNext()){
			Object key = keys.next();
			Object value = values.next();
			builder.append('[');
			
			try{
				appendln(key.toString());
			} catch (NullPointerException e) {
				builder.append("null");
			}
			
			builder.append(',');
			builder.append(LINE_SEPARATOR);
			
			if(value == null) {
				builder.append("null");				
			} else {
				appendln(value.toString());				
			}

			builder.append(']');
			  
			if(keys.hasNext()) {
				  builder.append(',');
			  }
			  
			  builder.append(LINE_SEPARATOR);
		  }
		
		append(" ]");
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendTab() {
		appendTabs();
		builder.append(TABULATOR);
	}
	
	public void appendln(String string) {
		append(string);
		builder.append(LINE_SEPARATOR);
	}
	
	public void appendCloseBracket() {
		appendTabs();
		builder.append("}");
	}
	
	private void appendTabs() {
		if(offset == 0) {
			return;
		}
		
		for(int i = 0; i < offset; i++) {
			builder.append(TABULATOR);
		}
	}
	
	public void setOffset(int layer) {
		this.offset = layer;
	}
	
	public void increaseOffset() {
		offset++;
	}
	
	public void decreaseOffset() {
		offset--;
	}
	
	public String getString() {
		return builder.toString();
	}
}