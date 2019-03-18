package org.cx.game.ai.policy;

import org.cx.game.ai.PlayerAgent;
import org.cx.game.command.CommandFactory;
import org.cx.game.core.IPlayer;
import org.cx.game.exception.ValidatorException;

public class DonePolicy extends AbstractPolicy<PlayerAgent> {

	private String cmdStr = "";
	
	private static DonePolicy policy = null;
	
	private DonePolicy() {
		// TODO Auto-generated constructor stub
	}
	
	public static DonePolicy getInstance(){
		if(null==policy)
			policy = new DonePolicy();
		
		return policy;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		IPlayer player = getAgent().getOwner();
		this.cmdStr = "done";
		
		setPri(AbstractPolicy.PRI_Min);
		
		try {
			super.command = CommandFactory.getInstance(this.cmdStr, player.getCommandBuffer());
			super.command.doValidator();
			if(!super.command.hasError()){
				setPri(AbstractPolicy.PRI_Bottom);
			}
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}
