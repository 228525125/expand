package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

/**
 * 施法一个主动技能
 * @author chenxian
 *
 */
public class Conjure extends AbstractAction implements IAction {
	
	private Boolean conjureable = false;
	
	public Boolean getConjureable() {
		return conjureable;
	}

	public void setConjureable(Boolean conjureable) {
		this.conjureable = conjureable;
	}

	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}

	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		ActiveSkill skill = (ActiveSkill) objects[0];
		
		/*
		 * 由ConjureOptionBeforeExecute负责发送通知
		 */
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("card", getOwner());
		map.put("position", getOwner().getPosition());
		map.put("skill", skill);
		String desc = getOwner().getName()+" 【使用技能】 "+skill.getName();
		map.put("description", desc);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Conjure,map);
		super.notifyObservers(info);*/
		
		Object [] parameter = (Object[]) objects[1];
		skill.useSkill(parameter);
		
		setConjureable(false);
	}
}
