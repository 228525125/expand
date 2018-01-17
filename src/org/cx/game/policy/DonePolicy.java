package org.cx.game.policy;

import org.cx.game.command.CommandFactory;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;

/**
 * 结束回合-player
 * @author chenxian
 *
 */
public class DonePolicy extends AbstractPolicy {

	private String cmdStr = "";
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		IPlayer owner = (IPlayer) getOwner().getOwner();
		this.cmdStr = "done";
		
		setPri(IPolicy.PRI_Min);
		
		try {
			super.command = CommandFactory.getInstance(owner,this.cmdStr);
			super.command.doValidator();
			if(!super.command.hasError()){
				setPri(IPolicy.PRI_Bottom);
			}
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

}
