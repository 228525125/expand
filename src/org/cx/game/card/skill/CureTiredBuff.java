package org.cx.game.card.skill;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Buff;
import org.cx.game.card.skill.ISkill;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 治疗疲劳
 * @author chenxian
 *
 */
public class CureTiredBuff extends Buff {
	
	public CureTiredBuff(Integer bout, Integer style, Integer type, Integer func,
			LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
	}

	public void immune(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getContainer());
		map.put("card", getOwner());
		map.put("buff", this);
		map.put("position", getOwner().getContainerPosition());
		NotifyInfo info = new NotifyInfo(getAction()+Affect,map);
		notifyObservers(info);	
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter affectedIn = new Intercepter(){

			private boolean invoke = true;
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				immune();
			}

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				ISkill magic = (ISkill) ((Object[]) args[0])[0];
				if (magic instanceof Cure && magic instanceof WoundHealing) {
					invoke = false;
				}
			}

			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return invoke;
			}
			
		};
		recordIntercepter(getOwner().getAffected(), affectedIn);
	}

}
