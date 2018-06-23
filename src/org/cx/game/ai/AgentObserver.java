package org.cx.game.ai;

import java.util.Observable;
import java.util.Observer;

import org.cx.game.action.Attacked;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class AgentObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			if(CommonIdentifierE.Corps_Attacked.equals(info.getType())){
				Attacked action = (Attacked) o;
				
			}
		}
		
		
	}

}
