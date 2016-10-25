package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;

/**
 * 精神专注，使治疗效果加倍
 * @author chenxian
 *
 */
public class Jingshenzhuanzhu extends PassiveSkill {
	
	private Integer cureUpScale;
	
	public Jingshenzhuanzhu(Integer cureUpScale) {
		// TODO Auto-generated constructor stub		
		this.cureUpScale = cureUpScale;
	}
	
	private Integer cureValue = 0;
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
		ISkill magic = (ISkill) ((Object[]) args[0])[0];
		if (magic instanceof Cure) {
			Cure cure = (Cure) magic;
			cureValue = cure.getCureValue();
			cure.setCureValue(cureValue*(100+this.cureUpScale)/100);
			affect();
		}
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

		ISkill magic = (ISkill) ((Object[]) args[0])[0];
		if (magic instanceof Cure) {
			Cure cure = (Cure) magic;
			cure.setCureValue(cureValue);
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAffected().addIntercepter(this);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
