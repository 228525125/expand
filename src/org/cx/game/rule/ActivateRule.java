package org.cx.game.rule;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.cx.game.action.IActivate;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.AttackLockBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.observer.NotifyInfo;

public class ActivateRule implements IRule {

	private IActivate activate = null;
	
	public ActivateRule(IActivate activate) {
		// TODO Auto-generated constructor stub
		this.activate = activate;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			
			if(NotifyInfo.Card_LifeCard_Action_Activate.equals(info.getType())){
				Map bean = (Map) info.getInfo();
				
				Boolean activate = (Boolean) bean.get("activate");
				LifeCard owner = getOwner().getOwner();
				
				if(activate){
					owner.getMove().setMoveable(true);
					owner.getAttacked().setFightBack(true);
					List<IBuff> buffs = getOwner().getOwner().getNexusBuff(AttackLockBuff.class);  //清除锁定对象
					for(IBuff buff : buffs)
						owner.removeNexusBuff(buff);
				}else{
					owner.getMove().setMoveable(false);
				}
			}
		}
		
	}

	@Override
	public IActivate getOwner() {
		// TODO Auto-generated method stub
		return this.activate;
	}

}
