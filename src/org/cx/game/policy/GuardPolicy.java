package org.cx.game.policy;

import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.command.Invoker;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;

/**
 * 原地休整
 * @author chenxian
 *
 */
public class GuardPolicy extends AbstractPolicy {
	
	private String cmdStr = "";

	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();

		Corps owner = (Corps) getOwner().getOwner();
		
		this.cmdStr = "guard";
		
		setPri(IPolicy.PRI_Min);
		
		String cmd = "select ground place"+owner.getPosition()+" corps;";
		try {
			Command command= CommandFactory.getInstance(owner.getPlayer(),cmd);
			command.execute();
			
			super.command = CommandFactory.getInstance(owner.getPlayer(),this.cmdStr);
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
