package org.cx.game.validator;

import org.cx.game.core.AbstractHost;
import org.cx.game.core.HostManager;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Validator;

/**
 * 用户是否已创建了主机
 * @author chenxian
 *
 */
public class HostIsExistedValidator extends Validator {
	
	private String playNo = null;
	
	private AbstractHost host = null;

	public HostIsExistedValidator(String playNo) {
		// TODO Auto-generated constructor stub
		this.playNo = playNo;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		this.host = HostManager.getHost(playNo);
		Boolean ret = null!=host;
		
		if(!ret){
			addMessage(I18n.getMessage(HostIsExistedValidator.class.getName()));
			ret = false;
		}
		
		return ret;
	}
	
	public AbstractHost getHost() {
		return host;
	}
}
