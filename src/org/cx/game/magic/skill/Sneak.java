package org.cx.game.magic.skill;

import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.PassiveSkill;

/**
 * 潜行
 * @author chenxian
 * 
 */
public class Sneak extends PassiveSkill {
	
	public static final Integer Sneak_ID = 10200002;
	
	public Sneak() {
		// TODO Auto-generated constructor stub
		super(Sneak_ID);
	}

	@Override
	public void setOwner(AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.setOwner(corps);
		
		Corps cs = (Corps) corps;
		
		cs.getCall().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		((Corps) getOwner()).getMove().setHide(true);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
