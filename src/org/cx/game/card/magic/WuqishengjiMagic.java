package org.cx.game.card.magic;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IGround;
import org.cx.game.widget.IWeapon;

public class WuqishengjiMagic extends MagicCard {

	public final static Integer WuqishengjiMagic_ID = 10150003;
	
	private Integer atkUpValue = 0;
	private Integer wearUpValue = 0;
	
	private IWeapon weapon = null;
	
	public WuqishengjiMagic(Integer atkUpValue, Integer wearUpValue, Integer consume) {
		super(WuqishengjiMagic_ID, consume);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.wearUpValue = wearUpValue;
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard hero = getOwner().getHeroCard();
		hero.affected(this);
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		weapon.addToAtk(this.atkUpValue);
		weapon.addToWear(this.wearUpValue);
	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		weapon = getOwner().getHeroCard().getAttack().getWeapon();
		return null!=weapon;
	}
	
	@Override
	public List<Integer> getApplyRange(IGround ground) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(getOwner().getHeroCard().getContainerPosition());
		return list;
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
