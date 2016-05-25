package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class MianyichongfengAureoleBuff extends Buff {

	public MianyichongfengAureoleBuff(Integer bout, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		IIntercepter affectedIn = new Intercepter(){
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				IMagic magic = (IMagic) ((Object[]) args[0])[0];
				
				Integer func = Context.getMagicFunction(magic.getClass().getName());
				if(IMagic.Func_Bump==func){					
					affect(magic);
				}
			}
		};
		
		recordIntercepter(getOwner().getAffected(), affectedIn);
		super.effect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IMagic magic = (IMagic) objects[0];
		
		List<IBuff> list = getOwner().getBuff(BumpDizzyBuff.class);
		for(IBuff buff : list)
			buff.invalid();
			
		LifeCard attack = (LifeCard) magic.getOwner();
		attack.getDeath().attackToDamage(-attack.getAttack().getAtk());
	}

}
