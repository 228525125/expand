package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.FreezeBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.card.skill.CircleRangeAcitveSkill;
import org.cx.game.exception.RuleValidatorException;

/**
 * 冰霜新星
 * @author chenxian
 *
 */
public class IceRing extends CircleRangeAcitveSkill {

	private Integer bout;
	private Integer atkScale;
	
	public IceRing(Integer consume, Integer cooldown, Integer velocity,
			Integer style, Integer func, Integer range, Integer bout, Integer atkScale) {
		super(consume, cooldown, velocity, style, func, range);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.atkScale = atkScale;
	}
	
	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		LifeCard life = (LifeCard) objects[0];
		new FreezeBuff(bout,getStyle(),IBuff.Type_Harm, getFunc(),life.getAttack().getAtk()*atkScale/100,100,0,life).effect();
	}

	@Override
	public Integer getRange() {
		// TODO Auto-generated method stub
		return 0;
	}

}
