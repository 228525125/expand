package org.cx.game.validator;

import org.cx.game.tools.I18n;
import org.cx.game.widget.IGround;

/**
 * 验证指定的位置是否存在对象
 * @author chenxian
 *
 */
public class NullPointValidator extends Validator {

	private IGround ground = null;
	private Integer position = null;
	
	public NullPointValidator(IGround ground, Integer position) {
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
			addMessage(I18n.getMessage(NullPointValidator.class.getName()));
			return false;
		}
	}
}
