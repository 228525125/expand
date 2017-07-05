package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ShieldBuff;
import org.cx.game.card.skill.PassiveSkill;

/**
 * 护盾
 * @author chenxian
 *
 */
public class Shield extends PassiveSkill {

	public static final Integer Shield_ID = 10200004;
	
	public Shield() {
		// TODO Auto-generated constructor stub
		super(Shield_ID);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new ShieldBuff(getOwner());
		buff.effect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		affect();
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
