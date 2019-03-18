package org.cx.game.command.check;

import org.cx.game.core.Context;
import org.cx.game.core.IContext;
import org.cx.game.exception.ValidatorException;
import org.cx.game.validator.ContextStatusValidator;

public class StartCheck extends Check {

	@Override
	public void check(Event event) throws ValidatorException {
		// TODO Auto-generated method stub
		CheckEvent ce = (CheckEvent) event;
		Context context = (Context) ce.getSource();
		doValidator(new ContextStatusValidator(context, IContext.Status_Ready));
	}

	@Override
	public Class[] getParameterTypes() {
		// TODO Auto-generated method stub
		return new Class[]{};
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return "start";
	}

	@Override
	public Class getCallerType() {
		// TODO Auto-generated method stub
		return Context.class;
	}

}
