package org.cx.game.widget.treasure;

public class SkillCount extends Resource implements IResource {

	public SkillCount() {
		// TODO Auto-generated constructor stub
	}
	
	public SkillCount(Integer count) {
		// TODO Auto-generated constructor stub
		add(SkillCount, count);
	}
	
	public void add(Integer count){
		add(SkillCount, count);
	}
	
	public Integer get(){
		return get(SkillCount);
	}
	
	public Integer getCount(){
		return get();
	}
}
