package org.cx.game.rule;

import org.cx.game.action.Call;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.tools.Util;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.Resource;

/**
 * 处理补充兵源的情况
 * @author chenxian
 *
 */
public class CallRule extends AbstractRule {

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
		
		Call call = getOwner();

		Corps owner = call.getOwner();
		AbstractGround ground = owner.getPlayer().getContext().getGround();
		
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
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		AbstractCorps corps = getOwner().getOwner();
		AbstractPlayer player = corps.getPlayer();
		player.getCorpsList().add(corps);
	}
	
	/**
	 * 这里与after区分开，如果是补充兵源after将不会被调用
	 */
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Call call = getOwner();
		Corps owner = call.getOwner();
		
		/*
		 * 扣减资源
		 */
		AbstractPlayer player = owner.getPlayer();
		Mineral consume = call.getConsume();		
		player.setMineral(Util.Sub, consume);
		
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
