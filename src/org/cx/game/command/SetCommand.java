package org.cx.game.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.Util;
import org.cx.game.validator.SelectOptionValidator;
import org.cx.game.widget.building.IOption;

/**
 * 设置选项参数
 * @author chenxian
 *
 */
public class SetCommand extends InteriorCommand {

	public SetCommand(IPlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
		addValidator(new SelectOptionValidator(buffer));
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		Object [] objs = (Object[]) parameter;
		String property = (String) objs[0];
		String item = property.split("\\.")[0];
		Object object = buffer.get(item);
		Object value = objs[1];
		
		/*
		 * 这里不会抛出异常，因为command.xml已经声明了property的范围
		 */
		String methodName = "set"+Util.toUpperCaseFirstOne(property.split("\\.")[1]);
		try {
			Method method = object.getClass().getDeclaredMethod(methodName, value.getClass());
			method.invoke(object, value);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
