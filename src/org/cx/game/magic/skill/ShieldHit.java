package org.cx.game.magic.skill;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.magic.buff.DizzyBuff;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

/**
 * 盾击
 * @author chenxian
 *
 */
public class ShieldHit extends ActiveSkill {

	private Integer atkScale = 0;
	private Integer bout = 0;
	public static final Integer ShieldHit_ID = 10200007;
	
	/**
	 * 
	 * @param cooldown
	 * @param atkScale 攻击比例
	 * @param bout 眩晕回合
	 */
	public ShieldHit(Integer id, Integer cooldown, Integer atkScale, Integer bout) {
		super(id, cooldown);
		// TODO Auto-generated constructor stub
		this.atkScale = atkScale;
		this.bout = bout;
	}
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		AbstractOption option = new ConjureToCorpsOption(this);
		addOption(option);
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = (Corps) objects[0];
		Integer atk = getOwner().getAtk();
		
		corps.getDeath().setHp(Util.Sub, atk*atkScale/100);
		new DizzyBuff(bout, corps).effect();
	}
	
	@Override
	public void useSkill(Object... objects) {
		// TODO Auto-generated method stub
		
		Place place = (Place) objects[0];
		Corps corps = place.getCorps();
		corps.affected(this);
	}
}
