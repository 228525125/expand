package org.cx.game.widget.treasure;

public class EmpiricValue extends Resource implements IResource {

	public EmpiricValue() {
		// TODO Auto-generated constructor stub
	}
	
	public EmpiricValue(Integer empValue) {
		add(EmpiricValue, empValue);
	}
	
	public void add(Integer value){
		add(EmpiricValue, value);
	}
	
	public Integer get(){
		return get(EmpiricValue);
	}
	
	public Integer getValue(){
		return get();
	}
}
