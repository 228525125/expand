package org.cx.game.card.buff;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

public class KuangquanbingBuff extends Buff {

	public KuangquanbingBuff(Integer bout, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		List<LifeCard> ls = new ArrayList<LifeCard>();
		
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), 1, IGround.Equal);
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life){
				ls.add(life);
			}
		}
		
		if(ls.isEmpty()){
			getOwner().getPlayer().getContext().done();       //结束该回合
		}else{
			try {
				getOwner().attack(ls.get(0));
				invalid();
			} catch (RuleValidatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		super.affect(objects);
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		/*
		 * 锁定的目标全部取消
		 */
		List<IBuff> buffs = getOwner().getNexusBuff(AttackLockBuff.class);
		for(IBuff buff : buffs)
			buff.invalid();
		
		IIntercepter activateIn = new Intercepter("setActivate") {    //当activate状态为true时
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				if((Boolean)args[0])
					affect();
			}
		};
		recordIntercepter(getOwner(), activateIn);
		
		affect();
	}

}
