package org.cx.game.magic.skill;

import org.cx.game.action.Attack;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AntibodyBuff;
import org.cx.game.tools.Logger;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

/**
 * 治疗，治疗量有下限，上限与攻击力相同
 * @author chenxian
 *
 */
public class Cure extends ActiveSkill {

	public final static Integer Cure_ID = 10200008;
	
	private Integer cureValue = null;
	private Integer antibodyBout = null;
	private Integer range = 1;
	
	/**
	 * 
	 * @param cureValue 暂时固定值
	 * @param antibodyBout 产生抗体回合数
	 * @param range
	 */
	public Cure(Integer id, Integer cureValue, Integer antibodyBout, Integer range, Integer prepare) {
		// TODO Auto-generated constructor stub
		super(id, 0, prepare);
		this.cureValue = cureValue;
		this.antibodyBout = antibodyBout;
		this.range = range;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		AbstractOption option = new ConjureToCorpsOption(this);
		addOption(option);
	}
	
	@Override
	public void useSkill(Object... objects) {
		// TODO Auto-generated method stub
		
		Place place = (Place) objects[0];
		Corps corps = place.getCorps();
		corps.affected(this);
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
	public Integer getRange() {
		// TODO Auto-generated method stub
		return this.range;
	}

	public Integer getCureValue() {
		return cureValue;
	}

	public void setCureValue(Integer cureValue) {
		this.cureValue = cureValue;
	}
	
	@Override
	public void setPrepare(Integer prepare) {
		// TODO Auto-generated method stub
		super.setPrepare(prepare);
	}
}
