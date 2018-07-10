package org.cx.game.rule;

import org.cx.game.action.CorpsUpgrade;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.AbstractBuff;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.magic.skill.PassiveSkill;

public class CorpsPropertyUpdateUpgradeRule extends AbstractRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return CorpsUpgrade.class;
	}
	
	@Override
	public CorpsUpgrade getOwner() {
		// TODO Auto-generated method stub
		return (CorpsUpgrade) super.getOwner();
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Corps corps = getOwner().getOwner();
		updateExtraAtk(corps);
		updateExtraDef(corps);
	}
	
	static void updateExtraAtk(Corps corps) {
		// TODO Auto-generated method stub
		Integer levelAtk = corps.getUpgrade().getLevel();
		Integer buffAtk = 0;
		for(AbstractBuff buff : corps.getBuffList()){
			buffAtk += buff.getAtk();
		}
		Integer skillAtk = 0;
		for(AbstractSkill skill : corps.getSkillList()){
			if (skill instanceof PassiveSkill) {
				PassiveSkill ps = (PassiveSkill) skill;
				skillAtk += ps.getAtk();
			}
		}
		corps.getAttack().setExtraAtk(levelAtk + buffAtk + skillAtk);
		
		corps.updateAtk();
	}
	
	static void updateExtraDef(Corps corps) {
		// TODO Auto-generated method stub
		Integer levelDef = corps.getUpgrade().getLevel();
		Integer buffDef = 0;
		for(AbstractBuff buff : corps.getBuffList()){
			buffDef += buff.getDef();
		}
		Integer skillDef = 0;
		for(AbstractSkill skill : corps.getSkillList()){
			if (skill instanceof PassiveSkill) {
				PassiveSkill ps = (PassiveSkill) skill;
				skillDef += ps.getDef();
			}
		}
		corps.getAttacked().setExtraDef(levelDef + buffDef + skillDef);
		
		corps.updateDef();
	}

}
