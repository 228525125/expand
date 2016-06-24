package org.cx.game.card.buff;

import org.cx.game.action.IAttack;
import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class DunpaifangyuAureoleBuff extends Buff {

	private Integer atkDownValue = 0;
	
	public DunpaifangyuAureoleBuff(Integer bout, Integer atkDownValue, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkDownValue = atkDownValue;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub		
		IIntercepter attackedIn = new Intercepter(){
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				IAttack attack = (IAttack) ((Object[]) args[0])[1];
				
				if(IAttack.Mode_Far.equals(attack.getMode())){					
					affect(attack);
				}
			}
		};
		
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		super.effect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		IAttack attack = (IAttack) objects[0];
		
		attack.addToAtk(atkDownValue);
		
		super.affect(objects);
	}
}
