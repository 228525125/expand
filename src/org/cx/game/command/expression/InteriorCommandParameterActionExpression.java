package org.cx.game.command.expression;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.exception.SyntaxValidatorException;
import org.cx.game.validator.InteriorCommandParameterActionExpressionValidator;
import org.dom4j.Element;

public class InteriorCommandParameterActionExpression extends
		InteriorCommandParameterExpression {

	public InteriorCommandParameterActionExpression(AbstractPlayer own, String cmd,
			Element cmdEl) {
		super(own, cmd, cmdEl);
		// TODO Auto-generated constructor stub
		addValidator(new InteriorCommandParameterActionExpressionValidator(getParameter(cmd), cmdEl));
	}
	
	@Override
	public Object interpreter() throws SyntaxValidatorException {
		// TODO Auto-generated method stub
		super.interpreter();
		
		return getParameter();
	}

}
