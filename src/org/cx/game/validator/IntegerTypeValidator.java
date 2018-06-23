package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;

/**
 * 验证参数类型是否正确，这里验证的对象是不是简单类型
 * @author chenxian
 *
 */
public class IntegerTypeValidator extends
		Validator {

	private Object parameter = null;
	
	public IntegerTypeValidator(Object parameter) {
		// TODO Auto-generated constructor stub
		this.parameter = parameter;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true;
		
		String num = Util.filterNumber(this.parameter.toString());
		
		if("".equals(num)){
			ret = false;
			addMessage(I18n.getMessage(IntegerTypeValidator.class.getName()));
		}
		
		return ret;
	}
}
