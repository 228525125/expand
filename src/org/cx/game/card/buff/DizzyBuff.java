package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.I18n;
import org.cx.game.widget.IControlQueue;

/**
 * 眩晕，实现方式：增加speedChance值
 * @author chenxian
 *
 */
public class DizzyBuff extends Buff {

	private String name = null;
	
	public DizzyBuff(Integer bout, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		/*
		 * 被击晕后，锁定的目标全部取消
		 */
		List<IBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(IBuff buff : buffs)
			buff.invalid();
		
		IIntercepter activateIn = new Intercepter("setActivate") {    //当activate状态为true时，表示从眩晕中恢复
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)args[0])
					invalid();
			}
		};
		recordIntercepter(getOwner(), activateIn);
		
		affect();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}
}
