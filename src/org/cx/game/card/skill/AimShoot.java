package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;

/**
 * 瞄准射击(需重新设计)
 * @author chenxian
 *
 */
public class AimShoot extends PassiveSkill {
	
	private Integer upScale = 0;
	
	/**
	 * 
	 * @param style
	 * @param upScale 提升加成比例
	 */
	public AimShoot(Integer upScale) {
		super();
		// TODO Auto-generated constructor stub
		this.upScale = upScale;
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		LifeCard life = (LifeCard) ((Object[]) args[0])[0];
		
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

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
