package org.cx.game.rule;

import org.cx.game.action.Call;
import org.cx.game.action.ICall;
import org.cx.game.core.IPlayer;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.widget.IGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.IResource;

/**
 * 处理补充兵源的情况
 * @author chenxian
 *
 */
public class CallRule extends AbstractRule implements IRule {
	
	private Boolean isInvoke = true;
	
	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return isInvoke;
	}
	
	private Place place = null;
	private Integer nop = 0;
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		this.place = (Place)((Object[]) args[0])[0];
		this.nop = (Integer)((Object[]) args[0])[1];
		
		ICall call = getOwner();

		Corps owner = call.getOwner();
		IGround ground = owner.getPlayer().getContext().getGround();
		
		/*
		 * 英雄复活要先把尸体回收
		 */
		if (owner instanceof Hero && null!= owner.getGround()) {
			ground.outCemetery(owner);
		}
		
		/*
		 * 招募分为创建一支部队和补充兵源，这里处理补充兵源的情况；
		 */
		Corps corps = place.getCorps();
		if(null!=corps){
			Integer n = corps.getCall().getNop();
			n += this.nop;
			corps.getCall().setNop(n);
			
			this.isInvoke = false;
		}
	}
	
	/**
	 * 这里与after区分开，如果是补充兵源after将不会被调用
	 */
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		ICall call = getOwner();
		Corps owner = call.getOwner();
		
		/*
		 * 扣减资源
		 */
		IPlayer player = owner.getPlayer();
		IResource consume = call.getConsume();		
		player.addToResource(consume);
		
		this.isInvoke = true;
	}
	
	@Override
	public Call getOwner() {
		// TODO Auto-generated method stub
		return (Call) super.getOwner();
	}
	
	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return Call.class;
	}
}
