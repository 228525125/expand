package org.cx.game.widget.treasure;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class Resource implements IResource {

	private Map<Integer, Integer> resource = new HashMap<Integer, Integer>();
	
	public Integer get(Integer type) {
		// TODO Auto-generated method stub
		return this.resource.get(type);
	}
	
	public void add(Integer type, Integer value) {
		this.resource.put(type, value);
	}
	
	public Set<Entry<Integer, Integer>> entrySet(){
		return this.resource.entrySet();
	}
	
	public Boolean isEmpty() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		for(Entry<Integer,Integer> entry : entrySet()){
			if(!Integer.valueOf(0).equals(entry.getValue())){
				ret = false;
				break;
			}
		}
		return ret;
	}
}
