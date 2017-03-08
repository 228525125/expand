package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.skill.Aureole;
import org.cx.game.core.Context;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class ZhiliaozengjiagongjiAureoleBuff extends Buff {

	public final static Integer ZhiliaozengjiagongjiAureoleBuff_ID = 10300009;
	
	private LifeCard host = null;
	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	
	public ZhiliaozengjiagongjiAureoleBuff(Integer atkUpValue, LifeCard host, LifeCard life) {
		super(ZhiliaozengjiagongjiAureoleBuff_ID, Aureole.Default_AureoleBuff_Bout, life);
		// TODO Auto-generated constructor stub
		this.host = host;
		this.atkUpValue = atkUpValue;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.host.getAttack().addToAtk(atkUpValue);
		this.atkUpTotal += atkUpValue;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter affectedIn =  new Intercepter() {
			
			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				IMagic magic = (IMagic) ((Object[]) args[0])[0];
				List<Integer> list = Context.queryForObject(magic.getId());
				if(list.contains(IMagic.Func_Cure)){
					affect();
				}
			}
		};
		
		recordIntercepter(getOwner().getAffected(), affectedIn);
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		this.host.getAttack().addToAtk(-atkUpTotal);
		this.atkUpTotal = 0;
	}

}
