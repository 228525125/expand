package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.card.skill.ISkill;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 精神治疗
 * @author chenxian
 *
 */
public class SpiritCureBuff extends Buff {

	private Integer elevateScale;
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param elevateScale 治疗效果提升比例
	 * @param life
	 */
	public SpiritCureBuff(Integer bout, Integer style, Integer type, Integer func,
			Integer elevateScale, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.elevateScale = elevateScale;
	}

	@Override
	public void before(Object[] args) {
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if((getOwner().getDeath().getHp()/getOwner().getHp()*100)>20){
			invalid();
		}
	}
	
	private Boolean isCure = false;
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter affectedIn = new Intercepter(){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				ISkill magic = (ISkill) ((Object[]) args[0])[0];
				if (magic instanceof Cure && /*magic instanceof WoundHealing &&*/ magic instanceof QuickCure) {
					isCure = true;
				}
			}
		};
		recordIntercepter(getOwner().getAffected(), affectedIn);
		
		IIntercepter deathIn = new Intercepter("magicToHp"){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if(isCure){
					Integer h = (Integer) args[0];
					h = h*(100+elevateScale)/100;
					args[0] = h;
					isCure = false;
				}
			}
		};
		recordIntercepter(getOwner().getDeath(), deathIn);
	}

}
