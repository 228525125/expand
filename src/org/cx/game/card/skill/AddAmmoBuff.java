package org.cx.game.card.skill;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.card.skill.IBuff;
import org.cx.game.card.skill.TiredAttackBuff;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

/**
 * 装弹
 * @author chenxian
 *
 */
public class AddAmmoBuff extends Buff {

	public AddAmmoBuff(Integer bout, Integer style, Integer type, Integer func, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		for(IBuff buff : getOwner().getBuffList()){
			if (buff instanceof TiredAttackBuff) {
				buff.invalid();
				break;
			}
		}
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter moveIn = new Intercepter(){

			@Override
			public void finish(Object[] args) {
				affect();
			}
			
			@Override
			public Integer getOrder() {
				// TODO Auto-generated method stub
				return IIntercepter.Order_Default + 10;    //相对与添加疲劳攻击Buff要晚执行
			}
		};
		recordIntercepter(getOwner().getMove(), moveIn);
		
		/*
		 * 只要攻击一次该buff失效 
		 */
		IIntercepter attackIn = new Intercepter(){
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
	}

}
