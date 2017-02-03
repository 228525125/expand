package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.MishuzengjianengliAureoleBuff;

/**
 * 如果周围有玩家使用秘术，则增加攻击和生命
 * @author chenxian
 *
 */
public class MishuzengjianengliAureole extends Aureole {

	public final static Integer Mifazengjianengli_ID = 10200010;
	
	private Integer atkUpValue = 0;
	private Integer hpUpValue = 0;
	
	public MishuzengjianengliAureole(Integer atkUpValue, Integer hpUpValue) {
		super(Mifazengjianengli_ID, Default_Effect_Range);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.hpUpValue = hpUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new MishuzengjianengliAureoleBuff(atkUpValue, hpUpValue, getOwner(), life).effect();
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leave(LifeCard life) {
		// TODO Auto-generated method stub
		for(IBuff buff : life.getBuffList()){
			if (buff instanceof MishuzengjianengliAureoleBuff) {
				buff.invalid();
			}
		}
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return MishuzengjianengliAureoleBuff.class;
	}

}
