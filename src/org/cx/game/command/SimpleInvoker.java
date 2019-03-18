package org.cx.game.command;

import org.cx.game.command.expression.Calculator;
import org.cx.game.exception.SyntaxValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.out.AbstractResponse;

/**
 * 在没有创建主机时使用，主要用于创建主机的过程
 * @author admin
 *
 */
public class SimpleInvoker implements IInvoker {
	
	private String response = "";
	
	private Command command = null;
	
	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return response;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	private void action() throws ValidatorException {
		this.command.execute();
	}
	
	/*
	 * 把执行中观察的结果反馈给前台
	 */
	private void response() {
		response = AbstractResponse.process.get().toString();
		AbstractResponse.process.get().delete(0, AbstractResponse.process.get().length());
	}

	private void intergrityValidate(String cmd) throws SyntaxValidatorException {
		String[] cs = cmd.split(Calculator.SPACE);
		if(cs.length==0)
			throw new SyntaxValidatorException("org.cx.game.command.Invoker.intergrityValidate");
	}
	
	@Override
	public void receiveCommand(String cmd) throws ValidatorException {
		// TODO Auto-generated method stub
		try {
			intergrityValidate(cmd);    //验证命令完整性
			
			for(String c : cmd.split(";")){
				Command command = CommandFactory.getInstance(c);
				setCommand(command);
				action();
			}
		} catch (ValidatorException e) {
			// TODO: handle exception
			throw e;
		} finally {
			response();
		}
	}

}
