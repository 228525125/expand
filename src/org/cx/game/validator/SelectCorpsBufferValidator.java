package org.cx.game.validator;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.I18n;

/**
 * 验证缓存中是否存在Corps
 * @author chenxian
 *
 */
public class SelectCorpsBufferValidator extends Validator {
	
	protected CommandBuffer buffer = null;
	private Corps corps = null;
	
	public SelectCorpsBufferValidator(CommandBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		Boolean ret = true; 
		AbstractCorps corps = buffer.getCorps(); ;
		
		if(null!=corps){
			this.corps = (Corps) corps;
			ret = true;
		}else{
			addMessage(I18n.getMessage(SelectCorpsBufferValidator.class.getName()));
			ret = false;
		}
		
		return ret;
	}

	public Corps getCorps() {
		return corps;
	}
}