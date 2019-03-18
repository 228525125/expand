package org.cx.game.widget.treasure;

import org.cx.game.tools.CommonIdentifier;

public class SkillCount extends Resource {

	public SkillCount() {
		// TODO Auto-generated constructor stub
	}
	
	public SkillCount(Integer count) {
		// TODO Auto-generated constructor stub
		add(CommonIdentifier.SkillCount, count);
	}
	
	public Integer getCount(){
		return get(CommonIdentifier.SkillCount);
	}
}
