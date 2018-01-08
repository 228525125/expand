package org.cx.game.magic.skill;

import org.cx.game.action.IAttack;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.magic.buff.DizzyBuff;
import org.cx.game.widget.IControlQueue;

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
	public ShieldHit(Integer cooldown, Integer atkScale, Integer bout) {
		super(ShieldHit_ID, cooldown);
		// TODO Auto-generated constructor stub
		this.atkScale = atkScale;
		this.bout = bout;
		setParameterTypeValidator(new Class[]{Corps.class});
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return getOwner().getAttack().getRange();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = (Corps) objects[0];
		Integer atk = getOwner().getAtk();
		try {
			corps.getDeath().addToHp(-atk*atkScale/100);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new DizzyBuff(bout, corps).effect();
	}
	
	@Override
	public void useSkill(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.useSkill(objects);
		
		Corps corps = (Corps) objects[0];
		corps.affected(this);
	}
	

}
