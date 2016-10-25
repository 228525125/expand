package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 快速治疗，不会产生治疗疲劳
 * @author chenxian
 *
 */
public class QuickCure extends ActiveSkill {

	private Integer atkScaleForHp;
	private Integer remainHpScale;
	
	/**
	 * 
	 * @param consume
	 * @param cooldown
	 * @param velocity
	 * @param atkScaleForHp 攻击力转化为恢复量的比例
	 * @param remainHpScale 目标剩余HP转化为恢复量的比例
	 */
	public QuickCure(Integer consume, Integer cooldown, Integer velocity,
			Integer atkScaleForHp, Integer remainHpScale) {
		super(consume, cooldown, velocity);
		// TODO Auto-generated constructor stub
		this.atkScaleForHp = atkScaleForHp;
		this.remainHpScale = remainHpScale;
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
		
		Integer cureValue = getOwner().getAttack().getAtk()*atkScaleForHp/100 + life.getHp()*(15+(100-(life.getHp()-life.getDeath().getHp())*100/life.getHp())/remainHpScale);  //恢复总生命值=攻击力*2+目标生命值总量*（15+目标受伤比例/5） 
		life.getDeath().addToHp(cureValue);
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
