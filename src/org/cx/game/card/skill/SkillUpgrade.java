package org.cx.game.card.skill;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.action.IUpgrade;
import org.cx.game.action.Upgrade;
import org.cx.game.core.ContextFactory;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;

public class SkillUpgrade extends Upgrade implements IUpgrade {

	private Integer consume = 1;
	
	@Override
	public ISkill getOwner() {
		// TODO Auto-generated method stub
		return (ISkill) super.getOwner();
	}
	
	@Override
	public void action(Object... objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Integer level = getLevel();
		level += 1;
		setLevel(level);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getOwner().getPlayer());
		map.put("container", getOwner().getOwner().getContainer());
		map.put("position", getOwner().getOwner().getContainerPosition());
		map.put("card", getOwner().getOwner());
		map.put("level", getLevel());
		NotifyInfo info = new NotifyInfo(NotifyInfo.Card_LifeCard_Skill_Upgrade,map);
		super.notifyObservers(info);
	}

	@Override
	public Integer getConsume() {
		// TODO Auto-generated method stub
		return this.consume;
	}

	@Override
	public void setConsume(Integer consume) {
		// TODO Auto-generated method stub
		this.consume = consume;
	}

}
