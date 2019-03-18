package org.cx.game.validator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.cx.game.command.CommandBuffer;
import org.cx.game.command.expression.Calculator;
import org.cx.game.tools.CommandUtil;
import org.cx.game.tools.I18n;
import org.cx.game.tools.Util;

public class InteriorCommandParameterSetExpressionValidator extends Validator {

private String parameter = null;
	
	private CommandBuffer buffer = null;
	private String declareName = null;
	private String methodName = null;
	private List<Class> paramTypes = null;
	private List<Object> paramValues = null;
	
	public InteriorCommandParameterSetExpressionValidator(String parameter, CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.parameter = parameter;
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Object bufferObject = buffer.get();
		List<Method> methods = new ArrayList<Method>();
		Method method = null;
		String [] params = parameter.split(Calculator.SPACE);
		
		/*
		 * 这里自少要包含两个字符串，格式：set [@param method arguments]
		 */
		if(params.length<2){
			addMessage(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
			return false;
		}
		
		/*
		 * 判断参数名是否带@
		 */
		if(0!=params[0].indexOf("@")){
			addMessage(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
			return false;
		}
		
		this.declareName = params[0];
		this.methodName = "get"+Util.toUpperCaseFirstOne(params[1]);
		this.paramTypes = new ArrayList<Class>();
		this.paramValues = new ArrayList<Object>();
		
		for(Method m : bufferObject.getClass().getMethods()){
			if(methodName.equals(m.getName())){
				methods.add(m);
			}
		}
		
		/*
		 * 判断parameter与缓存对象的方法名称是否匹配
		 */
		if(methods.isEmpty()){
			addMessage(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
			return false;
		}
		
		/*
		 * 判断参数个数是否匹配
		 */
		for(Method m : methods){
			if(m.getParameterTypes().length == (params.length-2)){
				method = m;
				break;
			}
		}
		
		if(null==method){
			addMessage(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
			return false;
		}
		
		/*
		 * 判断是否有参数
		 */
		if(params.length>2){
			for(int i=2;i<params.length;i++){
				this.paramTypes.add(CommandUtil.getParameterType(params[i]));
				this.paramValues.add(CommandUtil.getParameterObject(params[i]));
			}
			
			Class[] clzs = method.getParameterTypes();
			if(!CommandUtil.compareParameterClass(this.paramTypes.toArray(new Class[this.paramTypes.size()]), clzs)){
				addMessage(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
				return false;
			}
		}
		
		return true;
	}
	
	public Class[] getParameterTypes() {
		return this.paramTypes.toArray(new Class[this.paramTypes.size()]);
	}
	
	public Object [] getParameterObjects() {
		return this.paramValues.toArray();
	}
	
	public String getMethodName() {
		return this.methodName;
	}
	
	public String getDeclareName() {
		return this.declareName;
	}
}
