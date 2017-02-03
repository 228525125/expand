package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.skill.Aureole;
import org.cx.game.core.Context;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;

public class MishuzengjianengliAureoleBuff extends Buff {

	private final static Integer MifazengjianengliAureoleBuff_ID = 10300010;
	
	private LifeCard host = null;
	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	private Integer hpUpValue = 0;
	private Integer hpUpTotal = 0;
	
	public MishuzengjianengliAureoleBuff(Integer atkUpValue, Integer hpUpValue, LifeCard host, LifeCard life) {
		super(MifazengjianengliAureoleBuff_ID, Aureole.Default_AureoleBuff_Bout, life);
		// TODO Auto-generated constructor stub
		this.host = host;
		this.atkUpValue = atkUpValue;
		this.hpUpValue = hpUpValue;
	}

	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		this.host.getAttack().addToAtk(atkUpValue);
		atkUpTotal += atkUpValue;
		this.host.getDeath().addToHp(hpUpValue);
		hpUpTotal += hpUpValue;
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
				if(list.contains(IMagic.Func_Mystery)){
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
		this.host.getDeath().addToHp(-hpUpTotal);
	}
	
}
