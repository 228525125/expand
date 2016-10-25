package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AddAmmoBuff;
import org.cx.game.card.buff.IBuff;

/**
 * 自动装弹
 * @author chenxian
 *
 */
public class AutomateAmmo extends PassiveSkill {

	private Integer bout = 3;
	
	public AutomateAmmo(Integer bout) {
		// TODO Auto-generated constructor stub
		this.bout = bout;
	}
	
	@Override
	public void affect(Object... objects){
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IBuff buff = new AddAmmoBuff(bout, getOwner());
		buff.effect();
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "setActivate";
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(getOwner().getMove().getMoveable())
			affect();
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.addIntercepter(this);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
