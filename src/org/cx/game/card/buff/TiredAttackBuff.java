package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 疲劳攻击，只针对远程单元
 * @author chenxian
 *
 */
public class TiredAttackBuff extends SimpleBuff {

	private Integer atkDownScale;     //0-100
	
	/**
	 * 
	 * @param bout
	 * @param style
	 * @param type
	 * @param atkDownScale 攻击力下降比例，计算基准，取值范围0-100，计算时，atk*atkDownScale
	 * @param life
	 */
	public TiredAttackBuff(Integer bout,
			Integer atkDownScale, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkDownScale = atkDownScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer scale = getOwner().getMove().getEnergy()*100/getOwner().getEnergy();
		Integer atk = getOwner().getAttack().getAtk();
		Integer atkDownValue = atk - (atk*scale/100)*atkDownScale;
		
		addToKeepAtk(atkDownValue);
		
		super.affect(objects);
		
		
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();		
		
		IIntercepter attackIn = new Intercepter(){

			@Override
			public void before(Object[] args) {
				affect();
			}
			
			@Override
			public Integer getOrder() {
				// TODO Auto-generated method stub
				return IIntercepter.Order_Default+10;           //疲劳会对整个状态进行叠加
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
		
		IIntercepter doneIn = new Intercepter("done"){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getPlayer().getContext(), doneIn);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Rule;
	}
}
