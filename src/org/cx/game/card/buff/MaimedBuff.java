package org.cx.game.card.buff;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.SimpleBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.I18n;

/**
 * 残废
 * @author chenxian
 *
 */
public class MaimedBuff extends SimpleBuff {
	
	private String name = null;
	private Integer downEnergyScale;
	
	public final static Integer DownEnergyScale_Max = 100;

	public MaimedBuff(Integer bout, Integer downEnergyScale,
			LifeCard life) {
		super(bout, life);
		// TODO Auto-generated constructor stub
		this.downEnergyScale = downEnergyScale;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer energy = getOwner().getMove().getEnergy();
		Integer downEnergyValue = energy*downEnergyScale/100;
		addToKeepEnergy(-downEnergyValue);
		
		super.affect(objects);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}

}
