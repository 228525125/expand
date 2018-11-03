package org.cx.game.action;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.corps.Corps;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.Util;

/**
 * 受到攻击
 * @author chenxian
 *
 */
public class Defend extends AbstractAction implements IAction {

	private Boolean canFightBack = true;
	private Integer def = 0;         //真实防御力
	private Integer armourDef = 0;   //装备防御力
	private Integer extraDef = 0;    //额外防御力
	private Integer landformDef = 0; //地形防御力
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	public Integer getDef() {
		// TODO Auto-generated method stub
		return this.def;
	}
	
	public void setDef(Integer def) {
		// TODO Auto-generated method stub
		this.def = def;
	}
	
	public Integer getArmourDef() {
		// TODO Auto-generated method stub
		return this.armourDef;
	}
	
	public void setArmourDef(Integer armourDef) {
		// TODO Auto-generated method stub
		if(!armourDef.equals(this.armourDef)){
			this.armourDef = armourDef;
		}
	}
	
	public Integer getLandformDef() {
		return landformDef;
	}

	public void setLandformDef(Integer landformDef) {
		if(!landformDef.equals(this.landformDef)){
			this.landformDef = landformDef;
		}
	}

	public Integer getExtraDef() {
		return extraDef;
	}

	public void setExtraDef(Integer extraDef) {
		if(!extraDef.equals(this.extraDef)){
			this.extraDef = extraDef;
		}
	}

	public Boolean isCanFightBack() {
		// TODO Auto-generated method stub
		return this.canFightBack;
	}
	
	public void setCanFightBack(Boolean canFightBack) {
		// TODO Auto-generated method stub
		this.canFightBack = canFightBack;
	}

	@Override
	public void action(Object...objects) {
		// TODO Auto-generated method stub
		super.action(objects);
		
		Corps attack = (Corps) objects[0];
		Integer atk = (Integer) objects[1];
		Integer dmg = (Integer) objects[2];
		
		/*
		 * 伤害 使用H3计算规则
		 */
		Integer def = getDef();
		Integer temp = atk - def;
		Integer ratio = temp==0 ? 0 : temp<0 ? temp*25 : temp*50;
		ratio += 1000;
		Integer[] dmgs = Attack.IntegerToDamage(dmg);
		Integer d = Util.nextInt((dmgs[1]-dmgs[0])+1);
		Integer damage =  dmgs[0]+d;
		damage = damage*ratio/1000;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("attack", attack);
		map.put("attacked", getOwner());
		map.put("position", getOwner().getPosition());
		map.put("damage", damage);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Corps_Attacked,map);
		super.notifyObservers(info);
		
		addActionResult("damageValue", damage);
		addActionResult("isCanFightBack", isCanFightBack());
	}
}
