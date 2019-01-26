package org.cx.game.out;

import java.util.Observable;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.cx.game.action.Activate;
import org.cx.game.action.Affected;
import org.cx.game.action.Attack;
import org.cx.game.action.Defend;
import org.cx.game.action.Call;
import org.cx.game.action.Conjure;
import org.cx.game.action.Death;
import org.cx.game.action.Grow;
import org.cx.game.action.Move;
import org.cx.game.action.HeroUpgrade;
import org.cx.game.action.CorpsUpgrade;
import org.cx.game.action.SkillUpgrade;
import org.cx.game.core.Player;
import org.cx.game.core.SceneHost;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Hero;
import org.cx.game.corps.Corps;
import org.cx.game.corps.PlacementOption;
import org.cx.game.corps.TeammateOption;
import org.cx.game.magic.buff.AntibodyBuff;
import org.cx.game.magic.buff.AttackLockBuff;
import org.cx.game.magic.buff.ConjureWaitBuff;
import org.cx.game.magic.buff.DizzyBuff;
import org.cx.game.magic.buff.SneakBuff;
import org.cx.game.magic.skill.Bump;
import org.cx.game.magic.skill.Bump.BumpOption;
import org.cx.game.magic.skill.ConjureToPlaceOption;
import org.cx.game.magic.skill.ConjureToCorpsOption;
import org.cx.game.magic.skill.Cure;
import org.cx.game.magic.skill.Dive;
import org.cx.game.magic.skill.Dive.DiveOption;
import org.cx.game.magic.skill.ShieldHit;
import org.cx.game.magic.skill.Sneak;
import org.cx.game.magic.trick.ITrick;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.JsonHelper;
import org.cx.game.widget.Cemetery;
import org.cx.game.widget.HoneycombGround;
import org.cx.game.widget.Place;
import org.cx.game.widget.Scene;
import org.cx.game.widget.TrickList;
import org.cx.game.widget.building.CallBuilding;
import org.cx.game.widget.building.MineralBuilding;
import org.cx.game.widget.building.SpatialBuilding;
import org.cx.game.widget.building.SpatialOption;
import org.cx.game.widget.building.TownBuilding;
import org.cx.game.widget.building.BuildOption;
import org.cx.game.widget.building.BuildingUpgradeOption;
import org.cx.game.widget.building.CallOption;
import org.cx.game.widget.building.ReviveOption;
import org.cx.game.widget.building.UpgradeBuilding;
import org.cx.game.widget.treasure.EmpiricValue;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.SkillCount;
import org.cx.game.widget.treasure.EquipmentTreasure;
import org.cx.game.widget.treasure.MineralTreasure;

public class JsonOut extends AbstractResponse {
	
	private final static String Empty = "empty";
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof NotifyInfo) {
			NotifyInfo info = (NotifyInfo) arg;
			setAction(info.getType());
			setInfo(info.getInfo());
			Object result = convert(this);
			super.process.get().append(result.toString()+";");
			//System.out.println(result);
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
							.element("grow", corps.getGrow(),getConfig())
							.element("defend", corps.getDefend(),getConfig())
							.element("buffList", corps.getBuffList(),getConfig())
							.element("call", corps.getCall(),getConfig())
							.element("conjure", corps.getConjure(), getConfig())
							.element("death", corps.getDeath(),getConfig())
							.element("hero", corps.isHero())
							.element("id", corps.getType())
							.element("move", corps.getMove(),getConfig())
							.element("name", corps.getName())
							.element("player", corps.getPlayer(),getConfig())
							.element("playId", corps.getId())
							.element("position", corps.getPosition())
							.element("skillList", corps.getSkillList(),getConfig())
							.element("teammateList", corps.getTeammateList(), getConfig())
							.element("star", corps.getStar())
							.element("upgrade", corps.getUpgrade(),getConfig())
							.element("optionList", corps.getOptionList(),getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Hero.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Hero hero = (Hero) arg0;
					return new JSONObject().element("activate", hero.getActivate(), getConfig())
							.element("attack", hero.getAttack(), getConfig())
							.element("grow", hero.getGrow(),getConfig())
							.element("defend", hero.getDefend(), getConfig())
							.element("affected", hero.getAffected(), getConfig())
							.element("buffList", hero.getBuffList(), getConfig())
							.element("call", hero.getCall(), getConfig())
							.element("conjure", hero.getConjure(), getConfig())
							.element("death", hero.getDeath(), getConfig())
							.element("hero", hero.isHero())
							.element("id", hero.getType())
							.element("move", hero.getMove(), getConfig())
							.element("name", hero.getName())
							.element("player", hero.getPlayer(), getConfig())
							.element("playId", hero.getId())
							.element("position", hero.getPosition())
							.element("skillList", hero.getSkillList(), getConfig())
							.element("star", hero.getStar())
							.element("upgrade", hero.getUpgrade(), getConfig())
							.element("treasures", hero.getTreasures(), getConfig())
							.element("teammateList", hero.getTeammateList(), getConfig())
							.element("optionList", hero.getOptionList(),getConfig());
				}
			});
			
			//---------------------- action ---------------------
			
			config.registerJsonBeanProcessor(Activate.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Activate obj = (Activate) arg0;
					return new JSONObject().element("activation", obj.getActivation())
							.element("speed", obj.getSpeed());
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
							.element("range", obj.getRange())
							.element("weaponAtk", obj.getWeaponAtk());
				}
			});
			
			config.registerJsonBeanProcessor(Grow.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Grow obj = (Grow) arg0;
					return new JSONObject().element("power", obj.getPower());
				}
			});
			
			config.registerJsonBeanProcessor(Defend.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Defend obj = (Defend) arg0;
					return new JSONObject().element("armourDef", obj.getArmourDef())
							.element("def", obj.getDef())
							.element("extraDef", obj.getExtraDef())
							.element("canFightBack", obj.isCanFightBack())
							.element("landformDef", obj.getLandformDef());
				}
			});
			
			config.registerJsonBeanProcessor(Affected.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Affected obj = (Affected) arg0;
					return new JSONObject();
				}
			});
			
			config.registerJsonBeanProcessor(Conjure.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Conjure obj = (Conjure) arg0;
					return new JSONObject().element("conjureable", obj.getConjureable());
				}
			});
			
			config.registerJsonBeanProcessor(Call.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Call obj = (Call) arg0;
					return new JSONObject().element("consume", obj.getConsume(), getConfig())
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
							.element("mobile", obj.getMobile())
							.element("moveable", obj.getMoveable())
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
			
			config.registerJsonBeanProcessor(HeroUpgrade.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					HeroUpgrade obj = (HeroUpgrade) arg0;
					return new JSONObject().element("empiricValue", obj.getEmpiricValue(), getConfig())
							.element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig())
							.element("skillCount", obj.getSkillCount(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(CorpsUpgrade.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					CorpsUpgrade obj = (CorpsUpgrade) arg0;
					return new JSONObject().element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig())
							.element("empiricValue", obj.getEmpiricValue(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(SkillUpgrade.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					SkillUpgrade obj = (SkillUpgrade) arg0;
					return new JSONObject().element("level", obj.getLevel())
							.element("levelLimit", obj.getLevelLimit())
							.element("requirement", obj.getRequirement(), getConfig());
				}
			});
			
			//--------------------- action end ----------------------
			
			config.registerJsonBeanProcessor(Player.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Player obj = (Player) arg0;
					return new JSONObject().element("computer", obj.isComputer())
							.element("id", obj.getTroop())
							.element("name", obj.getName())
							.element("ration", obj.getRation())
							.element("rationLimit", obj.getRationLimit())
							.element("mineral", obj.getMineral(), getConfig());
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
							.element("landform", JsonHelper.convertForGroundLandform(obj.getLandformMap()), getConfig())
							.element("treasureList", obj.getTreasureList(), getConfig())
							.element("corpsList", obj.getCorpsList(), getConfig())
							//.element("emptyList", obj.queryPositionList(true))
							.element("imagePath", obj.getImagePath())
							.element("name", obj.getName())
							.element("xBorder", obj.getXBorder())
							.element("yBorder", obj.getYBorder())
							.element("id", obj.getId());
				}
			});
			
			config.registerJsonBeanProcessor(Scene.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Scene obj = (Scene) arg0;
					return new JSONObject().element("buildingList", obj.getBuildingList(), getConfig())
							.element("landform", JsonHelper.convertForGroundLandform(obj.getLandformMap()), getConfig())
							.element("treasureList", obj.getTreasureList(), getConfig())
							.element("corpsList", obj.getCorpsList(), getConfig())
							//.element("emptyList", obj.queryPositionList(true))
							.element("imagePath", obj.getImagePath())
							.element("name", obj.getName())
							.element("xBorder", obj.getXBorder())
							.element("yBorder", obj.getYBorder())
							.element("id", obj.getId());
				}
			});
			
			config.registerJsonBeanProcessor(Place.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Place obj = (Place) arg0;
					return new JSONObject().element("building", obj.getBuilding(), getConfig())
							.element("cemetery", obj.getCemetery(), getConfig())
							.element("empty", obj.isEmpty())
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
			
			config.registerJsonBeanProcessor(CallBuilding.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					CallBuilding obj = (CallBuilding) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("needBuilding", obj.getNeedBuilding())
							.element("optionList", obj.getOptionList(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPlace().getPosition())
							.element("status", obj.getStatus())
							.element("category", obj.getCategory())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("cardID", obj.getCorpsType())
							.element("nop", obj.getNop())
							.element("yield", obj.getYield());
				}
			});
			
			config.registerJsonBeanProcessor(MineralBuilding.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					MineralBuilding obj = (MineralBuilding) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("needBuilding", obj.getNeedBuilding())
							.element("optionList", obj.getOptionList(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPlace().getPosition())
							.element("status", obj.getStatus())
							.element("category", obj.getCategory())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("mineral", obj.getMineral(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(TownBuilding.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					TownBuilding obj = (TownBuilding) arg0;
					return new JSONObject().element("buildWait", obj.getBuildWait())
							.element("consume", obj.getConsume(), getConfig())
							.element("name", obj.getName())
							.element("optionList", obj.getOptionList(), getConfig())
							.element("player", obj.getPlayer(), getConfig())
							.element("position", obj.getPlace().getPosition())
							.element("status", obj.getStatus())
							.element("category", obj.getCategory())
							.element("type", obj.getType())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("buildings", obj.getBuildings(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(SpatialBuilding.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					SpatialBuilding obj = (SpatialBuilding) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("consume", obj.getConsume(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig())
							.element("position", obj.getPlace().getPosition())
							.element("status", obj.getStatus())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("category", obj.getCategory())
							.element("type", obj.getType());
							
				}
			});
			
			//---------------------------option--------------------------------
			
			config.registerJsonBeanProcessor(BuildOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BuildOption obj = (BuildOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(BuildingUpgradeOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BuildingUpgradeOption obj = (BuildingUpgradeOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(CallOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					CallOption obj = (CallOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(ReviveOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					ReviveOption obj = (ReviveOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(SpatialOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					SpatialOption obj = (SpatialOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(TeammateOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					TeammateOption obj = (TeammateOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("name", obj.getName());
				}
			});
			
			config.registerJsonBeanProcessor(ConjureToPlaceOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					ConjureToPlaceOption obj = (ConjureToPlaceOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(ConjureToCorpsOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					ConjureToCorpsOption obj = (ConjureToCorpsOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(BumpOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					BumpOption obj = (BumpOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(DiveOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					DiveOption obj = (DiveOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(PlacementOption.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					PlacementOption obj = (PlacementOption) arg0;
					return new JSONObject().element("status", obj.getStatus())
							.element("executeRemainBout", obj.getExecuteRemainBout())
							.element("executeWait", obj.getExecuteWait())
							.element("name", obj.getName())
							.element("spacingRemainBout", obj.getSpacingRemainBout())
							.element("spacingWait", obj.getSpacingWait());
				}
			});
			
			config.registerJsonBeanProcessor(Mineral.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Mineral obj = (Mineral) arg0;
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
			
			config.registerJsonBeanProcessor(Mineral.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Mineral obj = (Mineral) arg0;
					return new JSONObject().element("gold", obj.getGold())
							.element("wood", obj.getWood())
							.element("stone", obj.getStone())
							.element("ore", obj.getOre());
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
			
			config.registerJsonBeanProcessor(EquipmentTreasure.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					EquipmentTreasure obj = (EquipmentTreasure) arg0;
					return new JSONObject().element("atk", obj.getAtk())
							.element("def", obj.getDef())
							.element("id", obj.getType())
							.element("name", obj.getName())
							.element("position", obj.getPosition());
				}
			});

			config.registerJsonBeanProcessor(MineralTreasure.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					MineralTreasure obj = (MineralTreasure) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("position", obj.getPosition())
							.element("mineral", obj.getMineral(), getConfig());
				}
			});
			
			
			
			//---------------------- skill ------------------------//
			
			config.registerJsonBeanProcessor(Sneak.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Sneak obj = (Sneak) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("cooldown", obj.getCooldown())
							.element("prepare", obj.getPrepare())
							.element("conjureWait", obj.getConjureWait())
							.element("range", obj.getRange())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(ShieldHit.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					ShieldHit obj = (ShieldHit) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("cooldown", obj.getCooldown())
							.element("prepare", obj.getPrepare())
							.element("conjureWait", obj.getConjureWait())
							.element("range", obj.getRange())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Cure.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Cure obj = (Cure) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("cooldown", obj.getCooldown())
							.element("prepare", obj.getPrepare())
							.element("conjureWait", obj.getConjureWait())
							.element("range", obj.getRange())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Bump.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Bump obj = (Bump) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("cooldown", obj.getCooldown())
							.element("prepare", obj.getPrepare())
							.element("conjureWait", obj.getConjureWait())
							.element("range", obj.getRange())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig());
				}
			});
			
			config.registerJsonBeanProcessor(Dive.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					Dive obj = (Dive) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("cooldown", obj.getCooldown())
							.element("prepare", obj.getPrepare())
							.element("conjureWait", obj.getConjureWait())
							.element("range", obj.getRange())
							.element("upgrade", obj.getUpgrade(), getConfig())
							.element("optionList", obj.getOptionList(), getConfig());
				}
			});
			
			//----------------------- buff ------------------------//
			config.registerJsonBeanProcessor(AttackLockBuff.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					AttackLockBuff obj = (AttackLockBuff) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("depiction", obj.getDepiction());
				}
			});
			
			config.registerJsonBeanProcessor(SneakBuff.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					SneakBuff obj = (SneakBuff) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("depiction", obj.getDepiction());
				}
			});
			
			config.registerJsonBeanProcessor(AntibodyBuff.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					AntibodyBuff obj = (AntibodyBuff) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("depiction", obj.getDepiction());
				}
			});
			
			config.registerJsonBeanProcessor(DizzyBuff.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					DizzyBuff obj = (DizzyBuff) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("depiction", obj.getDepiction());
				}
			});
			
			config.registerJsonBeanProcessor(ConjureWaitBuff.class, new JsonBeanProcessor() {
				
				@Override
				public JSONObject processBean(Object arg0, JsonConfig arg1) {
					// TODO Auto-generated method stub
					ConjureWaitBuff obj = (ConjureWaitBuff) arg0;
					return new JSONObject().element("name", obj.getName())
							.element("depiction", obj.getDepiction());
				}
			});
		}
		return config;
	}
	
	public static void main(String[] args) {
		
		Hero hero = new Hero(1);
		
		System.out.println(JSONObject.fromObject(hero,getConfig()));
	}
}
