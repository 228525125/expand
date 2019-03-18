package org.cx.game.validator;

import org.cx.game.tools.I18n;

/**
 * 验证主机是否还有空位
 * @author chenxian
 * 
 */
public class HostVacancyValidator extends HostIsExistedValidator {

	private String playNo = null;
	
	public HostVacancyValidator(String playNo) {
		// TODO Auto-generated constructor stub
		super(playNo);
		this.playNo = playNo;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = super.validate();
		
		if(ret){
			if(null==getHost().getUsableTroop()){
				ret = false;
				addMessage(I18n.getMessage(HostVacancyValidator.class.getName()));
			}
		}
		
		return ret;
	}
}
