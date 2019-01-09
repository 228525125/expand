package org.cx.game.validator;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.tools.I18n;

/**
 * 验证敌友关系
 * @author chenxian
 *
 */
public class FriendOrEnemyValidator extends Validator {

	private Boolean isFriend = null;
	
	private AbstractPlayer owner = null;
	private AbstractPlayer enemy = null;
	
	public FriendOrEnemyValidator(AbstractPlayer owner, AbstractPlayer enemy, Boolean isFriend) {
		// TODO Auto-generated constructor stub
		this.owner = owner;
		this.enemy = enemy;
		this.isFriend = isFriend;
	}
	
	public FriendOrEnemyValidator(AbstractCorps owner, AbstractCorps enemy, Boolean isFriend) {
		// TODO Auto-generated constructor stub
		this.owner = owner.getPlayer();
		this.enemy = enemy.getPlayer();
		this.isFriend = isFriend;
	}
	
	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		if(isFriend.equals(owner.equals(enemy)))
			return true;
		else{
			addMessage(I18n.getMessage(FriendOrEnemyValidator.class.getName()));
			return false;
		}
	}
}
