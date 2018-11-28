package org.cx.game.corps;

import java.util.List;

import org.cx.game.action.Execute;
import org.cx.game.action.IAction;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.validator.CorpsActivateValidator;
import org.cx.game.validator.CorpsMoveableBufferValidator;
import org.cx.game.validator.CorpsMoveableValidator;
import org.cx.game.validator.OperatePowerValidator;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.Place;

public class TeammateOption extends AbstractOption {
	
	public TeammateOption(AbstractCorps corps) {
		// TODO Auto-generated constructor stub
		setOwner(corps);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getOwner().getName();
	}
	
	@Override
	public List<Integer> getExecuteRange(AbstractGround ground) {
		// TODO Auto-generated method stub
		return ground.queryRange(getOwner(), CommonIdentifierE.Command_Query_Move);
	}

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	protected AbstractControlQueue getControlQueue() {
		// TODO Auto-generated method stub
		return getOwner().getGround().getQueue();
	}
	
	private Execute execute = null;
	
	@Override
	public Execute getExecute() {
		// TODO Auto-generated method stub
		if(null==this.execute){
			Execute execute = new LeaveOptionExecute();
			execute.setOwner(this);
			this.execute = execute;
		}
		return this.execute;
	}
	
	@Override
	public void execute(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		addValidator(new CorpsMoveableValidator(getOwner()));
		addValidator(new OperatePowerValidator(getOwner().getPlayer()));
		
		super.execute(objects);
	}
	
	class LeaveOptionExecute extends Execute implements IAction {
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			Place place = (Place) objects[0];
			getOwner().getOwner().leave(place);
		}
		
		@Override
		public TeammateOption getOwner() {
			// TODO Auto-generated method stub
			return (TeammateOption) super.getOwner();
		}
	}

}