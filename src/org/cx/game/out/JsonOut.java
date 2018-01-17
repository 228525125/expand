package org.cx.game.out;

import java.util.Observable;
import java.util.Observer;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.cx.game.action.Activate;
import org.cx.game.action.Attack;
import org.cx.game.action.Attacked;
import org.cx.game.action.Call;
import org.cx.game.action.Death;
import org.cx.game.action.Move;
import org.cx.game.action.UpgradeHero;
import org.cx.game.action.UpgradeCorps;
import org.cx.game.action.UpgradeSkill;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.magic.trick.ITrick;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.Cemetery;
import org.cx.game.widget.ControlQueue;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.TrickList;
import org.cx.game.widget.building.BuildingCall;
import org.cx.game.widget.building.BuildingResource;
import org.cx.game.widget.building.BuildingTown;
import org.cx.game.widget.building.OptionBuild;
import org.cx.game.widget.building.OptionBuildingUpgrade;
import org.cx.game.widget.building.OptionCall;
import org.cx.game.widget.building.OptionRevive;
import org.cx.game.widget.building.UpgradeBuilding;
import org.cx.game.widget.treasure.EmpiricValue;
import org.cx.game.widget.treasure.Resource;
import org.cx.game.widget.treasure.SkillCount;
import org.cx.game.widget.treasure.TreasureEquipment;
import org.cx.game.widget.treasure.TreasureResource;

public class JsonOut extends AbstractResponse {
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			setAction(info.getType());
			setInfo(info.getInfo());
			Object result = convert(this);
			super.process.get().append(result.toString()+";");
			System.out.println(result);
		}
	}
	
	@Override
	public Object convert(AbstractResponse resp) {
		// TODO Auto-generated method stub
		return JSONObject.fromObject(resp,getConfig());
	}
	
	private static JsonConfig config = null;
	
	private static JsonConfig getConfig(){
		if(null==config){
			config = new JsonConfig();
			
			config.registerJsonBeanProcessor(NotifyInfo.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					NotifyInfo obj = (NotifyInfo) arg0;
					return new JSONObject().element("info", obj.getInfo(), getConfig())
							.element("type", obj.getType());
				}
			});
			
			config.registerJsonBeanProcessor(Corps.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Corps corps = (Corps) arg0;
					return new JSONObject().element("activate", corps.getActivate(), getConfig())
							.element("attack", corps.getAttack(),getConfig())
							.element("attacked", corps.getAttacked(),getConfig())
							.element("buffList", corps.getBuffList(),getConfig())
							.element("call", corps.getCall(),getConfig())
							.element("death", corps.getDeath(),getConfig())
							.element("hero", corps.getHero())
							.element("id", corps.getType())
							.element("move", corps.getMove(),getConfig())
							.element("name", corps.getName())
							.element("player", corps.getPlayer(),getConfig())
							.element("playId", corps.getId())
							.element("position", corps.getPosition())
							.element("skillList", corps.getSkillList(),getConfig())
							.element("star", corps.getStar())
							.element("upgrade", corps.getUpgrade(),getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Hero.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Hero hero = (Hero) arg0;
					return new JSONObject().element("activate", hero.getActivate())
							.element("affected", hero.getAffected(), getConfig())
							.element("attack", hero.getAttack(), getConfig())
							.element("attacked", hero.getAttacked(), getConfig())
							.element("buffList", hero.getBuffList(), getConfig())
							.element("call", hero.getCall(), getConfig())
							.element("conjure", hero.getConjure(), getConfig())
							.element("death", hero.getDeath(), getConfig())
							.element("hero", hero.getHero())
							.element("id", hero.getType())
							.element("move", hero.getMove(), getConfig())
							.element("name", hero.getName())
							.element("player", hero.getPlayer(), getConfig())
							.element("playId", hero.getId())
							.element("position", hero.getPosition())
							.element("skillList", hero.getSkillList(), getConfig())
							.element("star", hero.getStar())
							.element("upgrade", hero.getUpgrade(), getConfig())
							.element("treasures", hero.getTreasures(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Activate.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Activate obj = (Activate) arg0;
					return new JSONObject().element("activation", obj.getActivation())
							.element("speed", obj.getSpeed())
							.element("vigour", obj.getVigour());
				}
			});
			
			config.registerJsonBeanProcessor(Attack.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Attack obj = (Attack) arg0;
					return new JSONObject().element("atk", obj.getAtk())
							.element("attackable", obj.getAttackable())
							.element("dmg", obj.getDmg())
							.element("extraAtk", obj.getExtraAtk())
							.element("landformAtk", obj.getLandformAtk())
							.element("lockChance", obj.getLockChance())
							.element("mode", obj.getMode())
							.element("mobile", obj.getMobile())
							.element("range", obj.getRange())
							.element("weaponAtk", obj.getWeaponAtk());
				}
			});
			
			config.registerJsonBeanProcessor(Attacked.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Attacked obj = (Attacked) arg0;
					return new JSONObject().element("armourDef", obj.getArmourDef())
							.element("def", obj.getDef())
							.element("extraDef", obj.getExtraDef())
							.element("fightBack", obj.getFightBack())
							.element("landformDef", obj.getLandformDef());
				}
			});
			
			config.registerJsonBeanProcessor(Call.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Call obj = (Call) arg0;
					return new JSONObject().element("consume", obj.getConsume(), getConfig())
							.element("nop", obj.getNop())
							.element("ration", obj.getRation());
				}
			});
			
			config.registerJsonBeanProcessor(Death.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Death obj = (Death) arg0;
					return new JSONObject().element("hp", obj.getHp())
							.element("hpLimit", obj.getHpLimit())
							.element("status", obj.getStatus());
				}
			});
			
			config.registerJsonBeanProcessor(Move.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Move obj = (Move) arg0;
					return new JSONObject().element("consume", obj.getConsume(), getConfig())
							.element("direction", obj.getDirection())
							.element("energy", obj.getEnergy())
							.element("flee", obj.getFlee())
							.element("hide", obj.getHide())
							.element("moveable", obj.getMoveable())
							.element("movePath", obj.getMovePath())
							.element("type", obj.getType());
				}
			});
			
			config.registerJsonBeanProcessor(UpgradeBuilding.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					UpgradeBuilding obj = (UpgradeBuilding) arg0;
					return new JSONObject().element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(UpgradeHero.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					UpgradeHero obj = (UpgradeHero) arg0;
					return new JSONObject().element("empiricValue", obj.getEmpiricValue(), getConfig())
							.element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig())
							.element("skillCount", obj.getSkillCount(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(UpgradeCorps.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					UpgradeCorps obj = (UpgradeCorps) arg0;
					return new JSONObject().element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig())
							.element("empiricValue", obj.getEmpiricValue(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(UpgradeSkill.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					UpgradeSkill obj = (UpgradeSkill) arg0;
					return new JSONObject().element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Player.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Player obj = (Player) arg0;
					return new JSONObject().element("bout", obj.getBout())
							.element("computer", obj.getComputer())
							.element("id", obj.getId())
							.element("name", obj.getName())
							.element("ration", obj.getRation())
							.element("rationLimit", obj.getRationLimit())
							.element("resource", obj.getResource(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Cemetery.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Cemetery obj = (Cemetery) arg0;
					String corpsList = "";
					for(AbstractCorps corps : obj.getList()){
						corpsList += corps.getName();
					}
					
					return new JSONObject().element("size", obj.getSize())
							.element("lifeList", corpsList);
				}
			});
			
			config.registerJsonBeanProcessor(HoneycombGround.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					HoneycombGround obj = (HoneycombGround) arg0;
					return new JSONObject().element("buildingList", obj.getBuildingList(), getConfig())
							.element("disableList", obj.getDisableList())
							.element("emptyList", obj.getEmptyList())
							.element("imagePath", obj.getImagePath())
							.element("name", obj.getName())
							.element("xBorder", obj.getXBorder())
							.element("yBorder", obj.getYBorder());
				}
			});
			
			config.registerJsonBeanProcessor(Place.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Place obj = (Place) arg0;
					return new JSONObject().element("building", obj.getBuilding(), getConfig())
							.element("cemetery", obj.getCemetery(), getConfig())
							.element("disable", obj.getDisable())
							.element("empty", obj.getEmpty())
							.element("landform", obj.getLandform())
							.element("life", obj.getCorps(), getConfig())
							.element("position", obj.getPosition())
							.element("treasure", obj.getTreasure(), getConfig())
							.element("trickList", obj.getTrickList(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(TrickList.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					TrickList obj = (TrickList) arg0;
					String trickList = "";
					for(ITrick trick : obj.getList()){
						trickList += trick.getName();
					}
					
					return new JSONObject().element("size", obj.getSize())
							.element("trickList", trickList);
				}
			});
			
			config.registerJsonBeanProcessor(BuildingCall.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BuildingCall obj = (BuildingCall) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("needBuilding", obj.getNeedBuilding())
							.element("options", obj.getOptions(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPosition())
							.element("status", obj.getStatus())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("cardID", obj.getCorpsID())
							.element("nop", obj.getNop())
							.element("yield", obj.getYield());
				}
			});
			
			config.registerJsonBeanProcessor(BuildingResource.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BuildingResource obj = (BuildingResource) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("needBuilding", obj.getNeedBuilding())
							.element("options", obj.getOptions(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPosition())
							.element("status", obj.getStatus())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("resource", obj.getResource(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(BuildingTown.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BuildingTown obj = (BuildingTown) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("options", obj.getOptions(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPosition())
							.element("status", obj.getStatus())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("buildings", obj.getBuildings(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(OptionBuild.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					OptionBuild obj = (OptionBuild) arg0;
					return new JSONObject().element("allow", obj.getAllow())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(OptionBuildingUpgrade.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					OptionBuildingUpgrade obj = (OptionBuildingUpgrade) arg0;
					return new JSONObject().element("allow", obj.getAllow())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(OptionCall.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					OptionCall obj = (OptionCall) arg0;
					return new JSONObject().element("allow", obj.getAllow())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(OptionRevive.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					OptionRevive obj = (OptionRevive) arg0;
					return new JSONObject().element("allow", obj.getAllow())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(Resource.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Resource obj = (Resource) arg0;
					return new JSONObject().element("gold", obj.getGold())
							.element("wood", obj.getWood())
							.element("stone", obj.getStone())
							.element("ore", obj.getOre());
				}
			});
			
			config.registerJsonBeanProcessor(EmpiricValue.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					EmpiricValue obj = (EmpiricValue) arg0;
					return new JSONObject().element("value", obj.getValue());
				}
			});
			
			config.registerJsonBeanProcessor(SkillCount.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					SkillCount obj = (SkillCount) arg0;
					return new JSONObject().element("count", obj.getCount());
				}
			});
			
			config.registerJsonBeanProcessor(TreasureEquipment.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					TreasureEquipment obj = (TreasureEquipment) arg0;
					return new JSONObject().element("atk", obj.getAtk())
							.element("def", obj.getDef())
							.element("id", obj.getType())
							.element("name", obj.getName())
							.element("position", obj.getPosition());
				}
			});

			config.registerJsonBeanProcessor(TreasureResource.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					TreasureResource obj = (TreasureResource) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("position", obj.getPosition())
							.element("resource", obj.getResource(), getConfig());
				}
			});
		}
		return config;
	}
	
	public static void main(String[] args) {
		
		Resource r = new Resource("1,2,3,4");
		
		TreasureResource tr = new TreasureResource(1, 2, 2, 2);
		System.out.println(JSONObject.fromObject(r,getConfig()));
	}
}
