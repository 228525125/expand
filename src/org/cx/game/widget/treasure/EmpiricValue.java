package org.cx.game.widget.treasure;

import org.cx.game.tools.CommonIdentifier;

public class EmpiricValue extends Resource {
	
	public EmpiricValue(Integer empValue) {
		add(CommonIdentifier.EmpiricValue, empValue);
	}
	
	public Integer getValue(){
		return get(CommonIdentifier.EmpiricValue);
	}
}
