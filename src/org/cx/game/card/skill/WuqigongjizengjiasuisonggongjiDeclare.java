package org.cx.game.card.skill;

import org.cx.game.card.effect.Declare;
import org.cx.game.widget.IWeapon;

/**
 * 战吼：获得等同于你武器攻击力的攻击力
 * @author chenxian
 *
 */
public class WuqigongjizengjiasuisonggongjiDeclare extends Declare {

	public final static Integer WuqigongjizengjiasuisonggongjiDeclare_ID = 10200020;
	
	private IWeapon weapon = null;
	private Integer atkUpTotal = 0;
	
	public WuqigongjizengjiasuisonggongjiDeclare() {
		super(WuqigongjizengjiasuisonggongjiDeclare_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Integer atkUpValue = weapon.getAtk();
		getOwner().getAttack().addToAtk(atkUpValue);
		
		this.atkUpTotal = atkUpValue;
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-this.atkUpTotal);
		this.atkUpTotal = 0;
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
