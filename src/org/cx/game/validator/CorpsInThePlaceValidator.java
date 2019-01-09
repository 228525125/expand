package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.AbstractGround;

/**
 * 验证Corps是否在指定的位置上
 * @author chenxian
 * 
 */
public class CorpsInThePlaceValidator extends Validator {

	private AbstractGround ground = null;
	private Integer position = null;
	
	public CorpsInThePlaceValidator(AbstractGround ground, Integer position) {
		// TODO Auto-generated constructor stub
		this.ground = ground;
		this.position = position;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(null!=ground.getCorps(position)){
			return true;
		}else{
			addMessage(I18n.getMessage(CorpsInThePlaceValidator.class.getName()));
			return false;
		}
	}
}
