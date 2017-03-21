package org.cx.game.card.magic;

import java.util.List;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.MagicCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.IWeapon;
import org.cx.game.widget.Weapon;

public abstract class WeaponMagicCard extends MagicCard {

	private Integer atk = 0;
	private Integer wear = 0;
	
	public WeaponMagicCard(Integer id, Integer consume, Integer atk, Integer wear) {
		super(id, consume);
		// TODO Auto-generated constructor stub
		this.atk = atk;
		this.wear = wear;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IWeapon weapon = new Weapon(this.atk,this.wear, this,getOwner().getHeroCard());
		weapon.equip();
	}
	
	@Override
	public void apply(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.apply(objects);
		
		LifeCard hero = getOwner().getHeroCard();
		hero.affected(this);
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

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}

}
