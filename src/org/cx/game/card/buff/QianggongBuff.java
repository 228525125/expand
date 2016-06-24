package org.cx.game.card.buff;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;

public class QianggongBuff extends SimpleBuff {

	private Integer atkUpValue = 0;
	
	public QianggongBuff(Integer bout, Integer atkUpValue, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		addToKeepAtk(atkUpValue);
		
		affect();
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		List<String> funcList = Context.queryMagicFunction(IMagic.Func_Astrict);
		List<String> buffList = new ArrayList<String>();
		for(IBuff buff : getOwner().getBuffList()){
			String allName = buff.getClass().getName();
			buffList.add(allName);
		}
		
		buffList.retainAll(funcList);
		
		List<IBuff> buffs = new ArrayList<IBuff>();
		for(String className : buffList){
			buffs.addAll(getOwner().getBuff(className));
		}
		
		for(IBuff buff : buffs){
			buff.invalid();
		}
		
		super.affect(objects);
	}

}
