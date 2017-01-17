package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.buff.ZhiliaozengjiagongjiAureoleBuff;

/**
 * 指定范围内随从获得治疗时，增加攻击
 * @author chenxian
 *
 */
public class ZhiliaozengjiagongjiAureole extends Aureole {

	public final static Integer Zhiliaozengjiagongji_ID = 10200009;
	
	private Integer atkUpValue = 0;
	
	public ZhiliaozengjiagongjiAureole(Integer atkUpValue) {
		super(Zhiliaozengjiagongji_ID, Default_Range);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		new ZhiliaozengjiagongjiAureoleBuff(atkUpValue, getOwner(), life).effect();
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
			if (buff instanceof ZhiliaozengjiagongjiAureoleBuff) {
				buff.invalid();
			}
		}
	}

	@Override
	public Class getBuffClass() {
		// TODO Auto-generated method stub
		return ZhiliaozengjiagongjiAureoleBuff.class;
	}

}
