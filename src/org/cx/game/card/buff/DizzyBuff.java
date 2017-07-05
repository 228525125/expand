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
 * 眩晕
 * @author chenxian
 *
 */
public class DizzyBuff extends Buff {

	public static final Integer DizzyBuff_ID = 10350001;
	
	public DizzyBuff(Integer bout, LifeCard life) {
		super(DizzyBuff_ID, bout, life);
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
		
		/*
		 * 晕迷中的单位无法反击
		 */
		getOwner().getAttacked().setFightBack(false);
		
		IIntercepter activateIn = new Intercepter() {    //当activate状态为true时，将激活状态改为false

			@Override
			public void after(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)((Object[])args[0])[0])
					affect();
			}
		};
		
		recordIntercepter(getOwner().getActivate(), activateIn);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		try {
			getOwner().getActivate().action(false);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getOwner().getAttacked().setFightBack(false);
	}
}
