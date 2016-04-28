package org.cx.game.card.buff;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;

/**
 * 求生本能
 * @author chenxian
 *
 */
public class QiushengbennengBuff extends SimpleBuff {

	private Integer energyUpScale = 0;
	
	public QiushengbennengBuff(Integer bout, Integer energyUpScale, LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		
		this.energyUpScale = energyUpScale;
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		Integer energyUpValue = getOwner().getEnergy()*this.energyUpScale/100;
		addToKeepEnergy(energyUpValue);
		
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
