package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;

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
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
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
		
		getOwner().getMove().setHide(true);
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
