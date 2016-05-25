package org.cx.game.card.buff;

import org.cx.game.action.IAttack;
import org.cx.game.card.LifeCard;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class DunpaifangyuAureoleBuff extends Buff {

	private Integer atkDownScale = 0;
	
	public DunpaifangyuAureoleBuff(Integer bout, Integer atkDownScale, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkDownScale = atkDownScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub		
		IIntercepter attackedIn = new Intercepter(){
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				LifeCard attack = (LifeCard) ((Object[]) args[0])[0];
				
				if(IAttack.Mode_Far.equals(attack.getAttack().getMode())){					
					affect(attack);
				}
			}
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				LifeCard attack = (LifeCard) ((Object[]) args[0])[0];
				
				if(IAttack.Mode_Far.equals(attack.getAttack().getMode())){					
					attack.getAttack().setAtk(attack_atk);
				}
			}
		};
		
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		super.effect();
	}
	
	private Integer attack_atk = 0;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		LifeCard attack = (LifeCard) objects[0];
		
		attack_atk = attack.getAttack().getAtk();
		attack.getAttack().setAtk(attack_atk+(attack_atk*atkDownScale/100));
		
		super.affect(objects);
	}

}
