package org.cx.game.command.expression;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.SyntaxValidatorException;
import org.dom4j.Element;

public class InteriorCommandParameterBasicTypeExpression extends
		InteriorCommandParameterExpression {

	public InteriorCommandParameterBasicTypeExpression(IPlayer player,
			String cmd, Element cmdEl) {
		super(player, cmd, cmdEl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object interpreter() throws SyntaxValidatorException {
		// TODO Auto-generated method stub
		super.interpreter();
		
		return getParameter();
	}
}
