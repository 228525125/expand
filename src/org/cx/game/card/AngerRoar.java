package org.cx.game.card;

import org.cx.game.action.IApply;
import org.cx.game.action.IChuck;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.AngerRoarBuff;
import org.cx.game.exception.RuleValidatorException;

/**
 * 怒吼
 * @author chenxian
 *
 */
public class AngerRoar extends CircleRangeMagicCard {
	
	private Integer bout;
	private Integer atkScale;
	
	public AngerRoar(Integer id, Integer consume, Integer style, Integer func, Integer radius, Integer bout,
			Integer atkScale) {
		super(id, consume, style, func, radius);
		// TODO Auto-generated constructor stub
		this.bout = bout;
		this.atkScale = atkScale;
	}

	@Override
	public void affect(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		
		LifeCard life = (LifeCard) objects[0];
		
		if(life.getPlayer().equals(getOwner()))
			new AngerRoarBuff(bout,getStyle(), IBuff.Type_Benefit, getFunc(), atkScale, life).effect();
	}
	
	@Override
	public void setApply(IApply apply) {
		// TODO Auto-generated method stub
		super.setApply(apply);
	}
	
	@Override
	public void setChuck(IChuck chuck) {
		// TODO Auto-generated method stub
		super.setChuck(chuck);
	}

	@Override
	public Boolean needConjurer() {
		// TODO Auto-generated method stub
		return false;
	}

}
