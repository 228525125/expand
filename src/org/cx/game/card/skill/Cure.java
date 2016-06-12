package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.CureTiredBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 治疗，治疗量有下限，上限与攻击力相同
 * @author chenxian
 *
 */
public class Cure extends ActiveSkill {

	private Integer atkScaleForHp;
	private Integer tireBout;
	
	/**
	 * 
	 * @param consume
	 * @param cureValue 治疗量的下限，上限与攻击力相同
	 * @param life
	 */
	public Cure(Integer consume, Integer cooldown, Integer velocity, Integer atkScaleForHp, Integer tireBout) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.atkScaleForHp = atkScaleForHp;
		this.tireBout = tireBout;
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		
		Integer cureValue = getOwner().getAttack().getAtk()*atkScaleForHp/100;  //保持下限 
		life.getDeath().magicToHp(cureValue);
		new CureTiredBuff(tireBout,life).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

	public Integer getAtkScaleForHp() {
		return atkScaleForHp;
	}

	public void setAtkScaleForHp(Integer atkScaleForHp) {
		this.atkScaleForHp = atkScaleForHp;
	}
	
}
