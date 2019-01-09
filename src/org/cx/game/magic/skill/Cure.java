package org.cx.game.magic.skill;

import org.cx.game.action.Attack;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AntibodyBuff;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

/**
 * 治疗，治疗量 = 基础治疗量*（100+英雄法术*10）/100
 * @author chenxian
 *
 */
public class Cure extends ActiveSkill {

	public final static Integer Cure_ID = 10200008;
	
	private Integer cureValue = null;
	private Integer antibodyBout = null;
	
	/**
	 * 
	 * @param cureValue 暂时固定值
	 * @param antibodyBout 产生抗体回合数 
	 * @param range
	 */
	public Cure(Integer id, Integer cureValue, Integer antibodyBout, String range) {
		// TODO Auto-generated constructor stub
		super(id, 0, 0);
		this.cureValue = cureValue;
		this.antibodyBout = antibodyBout;
		setRange(range);
	}
	
	@Override
	public String getRange() {
		// TODO Auto-generated method stub
		return "1-1";
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = (Corps) objects[0];
		
		Logger.debug(this, getOwner().getName()+" "+"["+getName()+"]"+corps.getName()+"("+corps.getDeath().getHp()+"/"+corps.getDeath().getHpLimit()+")"+"; cv:"+cureValue);
		
		corps.getDeath().setHp(Util.Sum, this.cureValue);
		new AntibodyBuff(antibodyBout,corps).effect();
	}
	
	@Override
	public void setUseItOnFriendOrFoeOrAll(Integer useItOnFriendOrFoeOrAll) {
		// TODO Auto-generated method stub
		super.setUseItOnFriendOrFoeOrAll(useItOnFriendOrFoeOrAll);
	}
	
	@Override
	public void setUseItOnYouself(Boolean useItOnYouself) {
		// TODO Auto-generated method stub
		super.setUseItOnYouself(useItOnYouself);
	}
}
