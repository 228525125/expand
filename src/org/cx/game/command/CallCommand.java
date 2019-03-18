package org.cx.game.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.command.check.CheckHelper;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommandUtil;
import org.cx.game.tools.I18n;

public class CallCommand extends WithCacheCommand {
	
	public CallCommand(CommandBuffer buffer) {
		super(buffer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Object obj = buffer.get();
		
		Map<String, Object> param = (Map<String, Object>) parameter;
		String methodName = param.get("methodName").toString();
		Class [] paramTypes = (Class[]) param.get("parameterTypes");
		Object [] params = (Object[]) param.get("parameterObjects");
		
		/*
		 * 检查命令是否符合规则
		 */
		CheckHelper helper = CheckHelper.getInstance();
		helper.check(obj, methodName, params);
		
		List<Method> methods = new ArrayList<Method>();
		Method method = null;
		
		for(Method m : obj.getClass().getMethods()){
			if(methodName.equals(m.getName())){
				methods.add(m);
			}
		}
		
		/*
		 * 判断parameter与缓存对象的方法名称是否匹配
		 */
		if(methods.isEmpty())
			throw new CommandValidatorException(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
		
		/*
		 * 判断参数个数是否匹配
		 */
		for(Method m : methods){
			if(CommandUtil.compareParameterClass(paramTypes, m.getParameterTypes())){
				method = m;
				break;
			}
		}
		
		if(null==method)
			throw new CommandValidatorException(I18n.getMessage("InteriorCommandParameterExpressionFormatValidator"));
		
		try {
			Object result = method.invoke(obj, params);
			
			String methodString = obj.getClass().getName()+"."+method.getName();
			String type = CommandUtil.conversionNotifyType(methodString);
			/*
			 * 判断是否有返回值
			 */
			if(null!=type){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("return", result);
				NotifyInfo info = new NotifyInfo(type,map);
				notifyObservers(info);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
