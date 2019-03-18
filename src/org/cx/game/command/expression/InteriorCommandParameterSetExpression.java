package org.cx.game.command.expression;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.command.CommandBuffer;
import org.cx.game.exception.SyntaxValidatorException;
import org.cx.game.validator.InteriorCommandParameterSetExpressionValidator;
import org.dom4j.Element;

public class InteriorCommandParameterSetExpression extends
	WithCacheCommandParameterExpression {

	private InteriorCommandParameterSetExpressionValidator validator = null;
	
	public InteriorCommandParameterSetExpression(String cmd, Element cmdEl,
			CommandBuffer buffer) {
		super(cmd, cmdEl, buffer);
		// TODO Auto-generated constructor stub
		this.validator = new InteriorCommandParameterSetExpressionValidator(getParameter(), buffer);
		addValidator(this.validator);
	}	

	@Override
	public Object interpreter() throws SyntaxValidatorException {
		// TODO Auto-generated method stub
		super.interpreter();
		
		Map<String, Object> ret = new HashMap<String, Object>();

		String declareName = this.validator.getDeclareName();
		String methodName = this.validator.getMethodName();
		Class [] clazs = this.validator.getParameterTypes();
		Object [] params = this.validator.getParameterObjects();
		
		ret.put("declareName", declareName);
		ret.put("methodName", methodName);
		ret.put("parameterTypes", clazs);
		ret.put("parameterObjects", params);
		
		return ret;
	}

}
