package org.cx.game.card.magic;

import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.RuleValidatorException;

public class ZhisidajiMagic extends MagicCard {
	
	public final static Integer ZhisidajiMagic_ID = 10150005;
	
	private Integer harm = 0;

	public ZhisidajiMagic(Integer harm, Integer consume) {
		super(ZhisidajiMagic_ID, consume);
		// TODO Auto-generated constructor stub
		
		this.harm = harm;
		
		setParameterTypeValidator(new Class[]{LifeCard.class});
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		Integer hp = getOwner().getHeroCard().getDeath().getHp();
		Integer hrm = hp<12 ? this.harm*150/100 : this.harm;
		life.getAffected().magicHarm(hrm);
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard life = (LifeCard) objects[0];
		life.affected(this);
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setApply(IApply apply) {
		// TODO Auto-generated method stub
		super.setApply(apply);
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setChuck(IChuck chuck) {
		// TODO Auto-generated method stub
		super.setChuck(chuck);
	}
	
	/**
	 * 这个方法必须覆盖，利用反射创建这个对象时会用到
	 */
	@Override
	public void setConjurerList(List<Integer> conjurerList) {
		// TODO Auto-generated method stub
		super.setConjurerList(conjurerList);
	}

}
