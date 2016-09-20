package org.cx.game.card.buff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.action.IAttack;
import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.card.skill.ISkill;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.observer.NotifyInfo;

/**
 * 免疫
 * @author chenxian
 *
 */
public class ImmuneBuff extends Buff {

	private Boolean physical;
	private Boolean magic;
	private Boolean astrict;
	
	public ImmuneBuff(Integer bout, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}

	public Boolean getPhysical() {
		return physical;
	}

	public void setPhysical(Boolean physical) {
		this.physical = physical;
	}

	public Boolean getMagic() {
		return magic;
	}

	public void setMagic(Boolean magic) {
		this.magic = magic;
	}

	public Boolean getAstrict() {
		return astrict;
	}

	public void setAstrict(Boolean astrict) {
		this.astrict = astrict;
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
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		if(physical){
			/*
			 * 清除物理类buff
			 */
			for(IBuff buff : getOwner().getBuffList()){
				if(buff.getStyle().equals(IMagic.Style_physical))
					buff.invalid();
			}
		}
		
		if(magic){
			/*
			 * 清除魔法类buff
			 */
			for(IBuff buff : getOwner().getBuffList()){
				if(buff.getStyle().equals(IMagic.Style_Magic))
					buff.invalid();
			}
		}
		
		if(astrict){
			/*
			 * 清除自身移动限制类buff
			 */
			for(IBuff buff : getOwner().getBuffList()){
				if(buff.getFunc().equals(IMagic.Func_Astrict))
					buff.invalid();
			}
		}
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		affect();
		
		if(physical){
			getOwner().setImmunePhysical(true);
		}
		
		if(magic){
			getOwner().setImmuneMagic(true);
		}
		
		if(astrict){			
			
			/*
			 * 受到移动限制类法术影响时，免疫
			 */
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
					if(magic.getFunc().equals(ISkill.Func_Astrict))
						invoke = false;
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
}
