package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.ActiveSkill;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 驱魔，清除与法术相关的负面buff
 * @author chenxian
 *
 */
public class DispelMagic extends ActiveSkill {

	private Integer dispelNumber;
	
	/**
	 * 
	 * @param consume
	 * @param dispelNumber 移除数量
	 * @param life
	 */
	public DispelMagic(Integer consume, Integer cooldown, Integer velocity, Integer style, Integer func, Integer dispelNumber) {
		super(consume, cooldown, velocity, style, func);
		// TODO Auto-generated constructor stub
		this.dispelNumber = dispelNumber;
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
		Integer i = 0;
		for(IBuff buff : life.getBuffList()){
			if(IBuff.Style_Magic==buff.getStyle()&&IBuff.Type_Harm==buff.getType()){
				buff.invalid();
				i += 1;
				if(i==dispelNumber)
					break;
			}
		}
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

}
