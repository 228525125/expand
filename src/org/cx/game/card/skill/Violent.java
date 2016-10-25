package org.cx.game.card.skill;

import org.cx.game.tools.Util;

/**
 * 狂怒，生命值的比例转换为攻击速度的比例 (重新设计)
 * @author chenxian
 *
 */
public class Violent extends PassiveSkill {

	public Violent() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Integer hp1 = getOwner().getHp();
		Integer hp2 = getOwner().getDeath().getHp();
		Double d = Util.format(1d-hp2.doubleValue()/hp1.doubleValue(), "0.00");
		Integer chance = Util.convertInteger(d*100);
		
		//addToKeepSpeedChance(chance);
		
		affect();
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "setHp";
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
