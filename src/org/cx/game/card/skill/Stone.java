package org.cx.game.card.skill;

import org.cx.game.action.Random;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.StoneBuff;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.widget.IControlQueue;

/**
 * 石化（被动）
 * @author chenxian
 *
 */
public class Stone extends PassiveSkill {

	private Integer bout = 0;
	private Integer chance = 0;
	private LifeCard attacked = null;
	
	public Stone(Integer bout, Integer chance) {
		super();
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.chance = chance;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer speed = this.attacked.getSpeedChance();
		this.attacked.setSpeedChance(speed-bout*IControlQueue.consume);
		
		this.attacked.getAttacked().setFightBack(false);
		
		new StoneBuff(bout,this.attacked).effect();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		this.attacked = (LifeCard) ((Object[]) args[0])[0];
		if (Random.isTrigger(chance)) {
			affect();
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
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
