package org.cx.game.validator;

import java.util.Iterator;

import org.cx.game.command.expression.Calculator;
import org.cx.game.tools.I18n;
import org.dom4j.Element;

/**
 * 验证参数类型是否正确，这里验证的对象是String
 * @author chenxian
 *
 */
public class InteriorCommandParameterExpressionStringTypeValidator extends Validator {

	private String parameter = null;
	private Element cmdEl = null;
	
	public InteriorCommandParameterExpressionStringTypeValidator(String parameter, Element cmdEl) {
		// TODO Auto-generated constructor stub
		this.parameter = parameter;
		this.cmdEl = cmdEl;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = false;
		
		String type = Calculator.itemToType(parameter);
		
		if(null==type){
			addMessage(I18n.getMessage(InteriorCommandParameterExpressionStringTypeValidator.class.getName()));
			return false;
		}
		
		Element paramEl = cmdEl.element("parameter");
		
		for(Iterator it = paramEl.elementIterator("option");it.hasNext();){
			Element option = (Element) it.next();
			if(option.getText().equals(type)){
				ret = true;
				break;
			}
		}
		
		if(!ret){
			addMessage(I18n.getMessage(InteriorCommandParameterExpressionStringTypeValidator.class.getName()));
		}
		
		return ret;
	}
}
