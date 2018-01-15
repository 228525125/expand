package org.cx.game.command.expression;

import org.cx.game.core.IPlayer;
import org.cx.game.exception.SyntaxValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.validator.InteriorCommandParameterExpressionPropertyValidator;
import org.dom4j.Element;

/**
 * SetCommand的参数，格式为：set 对象.属性名[值]
 * 解析后的参数为Object[] = [对象.属性名,属性值]
 * @author chenxian
 *
 */
public class InteriorCommandParameterPropertyExpression extends
		InteriorCommandParameterExpression {

	public InteriorCommandParameterPropertyExpression(IPlayer own, String cmd, Element cmdEl) {
		super(own, cmd, cmdEl);
		// TODO Auto-generated constructor stub
		addValidator(new InteriorCommandParameterExpressionPropertyValidator(getParameter(cmd), cmdEl));
	}
	
	@Override
	public Object interpreter() throws SyntaxValidatorException {
		// TODO Auto-generated method stub
		super.interpreter();
		
		String value = getParameter().substring(getParameter().indexOf("[")+1, getParameter().indexOf("]"));
		String property = getParameter().substring(0, getParameter().indexOf("["));
		Object v = Util.isInteger(value) ? Integer.valueOf(value) : value;
		
		Object [] ret = {property,v};
		
		return ret;
	}

}
