package org.cx.game.card.skill;

import java.text.DecimalFormat;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.SimplePassiveSkill;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.Util;

/**
 * 瞄准射击
 * @author chenxian
 *
 */
public class AimShoot extends SimplePassiveSkill {
	
	private Integer upScale = 0;
	
	/**
	 * 
	 * @param style
	 * @param upScale 提升加成比例
	 */
	public AimShoot(Integer style, Integer upScale) {
		super(style);
		// TODO Auto-generated constructor stub
		this.upScale = upScale;
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		LifeCard life = (LifeCard) ((Object[]) args[0])[0];
		if(getOwner().getMove().getEnergy()>life.getMove().getEnergy()){
			Double d = 1-Util.format(getOwner().getMove().getEnergy().doubleValue()/life.getMove().getEnergy().doubleValue(), "0.00");
			Integer upAtkValue = Util.convertInteger(d * getOwner().getAttack().getAtk() * upScale/100);
			addToEruptAtk(upAtkValue);
			affect();
		}
	}

	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "attack";
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}

}
