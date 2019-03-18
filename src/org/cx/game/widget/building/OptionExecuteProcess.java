package org.cx.game.widget.building;

import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.intercepter.AbstractIntercepter;
import org.cx.game.widget.AbstractControlQueue;
import org.cx.game.widget.AbstractOption;

public class OptionExecuteProcess extends AbstractProcess {

	private Object[] parameter = null; 
	
	public OptionExecuteProcess(AbstractControlQueue queue, AbstractOption option) {
		super(queue, option);
		// TODO Auto-generated constructor stub
		setWaitBout(option.getExecuteWait());
	}
	
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		super.begin();
		
		recordIntercepter(getOwner().getExecute(), new AbstractIntercepter() {
			
			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				OptionExecuteProcess.this.parameter = (Object[]) args[0];
			}
			
			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return Integer.valueOf(0).equals(getRemainBout());
			}
		});
	}
	
	@Override
	public AbstractOption getOwner() {
		// TODO Auto-generated method stub
		return (AbstractOption) super.getOwner();
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(Integer.valueOf(0).equals(getRemainBout())){
			stop();

			IAction action = new ActionProxyHelper(getOwner().getExecute());
			action.action(parameter);
		}
	}
}
