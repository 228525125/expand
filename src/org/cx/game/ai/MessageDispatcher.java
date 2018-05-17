package org.cx.game.ai;

import java.util.Map;

/**
 * 智能体通讯模块，相当于一部手机；
 * @author chenxian
 *
 */
public class MessageDispatcher {
	
	private MessageDispatcher() {
		// TODO Auto-generated constructor stub
		
	}
	
	private static MessageDispatcher dispatcher = null;
	
	public static MessageDispatcher getInstance(){
		if(null==dispatcher)
			dispatcher = new MessageDispatcher();
		
		return dispatcher;
	}
	
	public void senderMessage(AbstractAgent sender, AbstractAgent receiver, Integer messageType, Map<String, Object> extraInfo){
		Telegram msg = new Telegram(sender, receiver, messageType, extraInfo);
		discharge(receiver, msg);
	}
	
	private void discharge(AbstractAgent receiver, Telegram msg){
		receiver.handleMessage(msg);
	}
}
