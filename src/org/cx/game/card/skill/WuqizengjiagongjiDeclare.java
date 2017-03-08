package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IWeapon;

/**
 * 战吼：如果英雄装备有武器，则增加随从攻击力
 * @author chenxian
 *
 */
public class WuqizengjiagongjiDeclare extends Declare {

	public final static Integer WuqizengjiagongjiDeclare_ID = 10200018;
	
	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	
	private IWeapon weapon = null;
	
	public WuqizengjiagongjiDeclare(Integer atkUpValue) {
		super(WuqizengjiagongjiDeclare_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttack().addToAtk(atkUpValue);
		atkUpTotal += atkUpValue;
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-atkUpTotal);
		atkUpTotal = 0;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		this.weapon = getOwner().getPlayer().getHeroCard().getAttack().getWeapon();
		return null!=this.weapon;
	}

}
