package org.cx.game.rule;

import org.cx.game.action.UpgradeCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.IBuff;
import org.cx.game.magic.skill.IPassiveSkill;
import org.cx.game.magic.skill.ISkill;

public class CorpsPropertyUpdateUpgradeRule extends AbstractRule implements
		IRule {

	@Override
	public Class getInterceptable() {
		// TODO Auto-generated method stub
		return UpgradeCorps.class;
	}
	
	@Override
	public UpgradeCorps getOwner() {
		// TODO Auto-generated method stub
		return (UpgradeCorps) super.getOwner();
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
		for(IBuff buff : corps.getBuffList()){
			buffAtk += buff.getAtk();
		}
		Integer skillAtk = 0;
		for(ISkill skill : corps.getSkillList()){
			if (skill instanceof IPassiveSkill) {
				IPassiveSkill ps = (IPassiveSkill) skill;
				skillAtk += ps.getAtk();
			}
		}
		corps.getAttack().setExtraAtk(levelAtk + buffAtk + skillAtk);
	}
	
	static void updateExtraDef(Corps corps) {
		// TODO Auto-generated method stub
		Integer levelDef = corps.getUpgrade().getLevel();
		Integer buffDef = 0;
		for(IBuff buff : corps.getBuffList()){
			buffDef += buff.getDef();
		}
		Integer skillDef = 0;
		for(ISkill skill : corps.getSkillList()){
			if (skill instanceof IPassiveSkill) {
				IPassiveSkill ps = (IPassiveSkill) skill;
				skillDef += ps.getDef();
			}
		}
		corps.getAttacked().setExtraDef(levelDef + buffDef + skillDef);
	}

}
