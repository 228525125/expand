package org.cx.game.widget;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.cx.game.core.IPlayer;
import org.cx.game.core.Player;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.ISkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.policy.IPolicyGroup;
import org.cx.game.tools.CellularDistrict;
import org.cx.game.tools.Node;
import org.cx.game.tools.PathFinding;
import org.cx.game.widget.building.BuildingFactory;
import org.cx.game.widget.building.IBuilding;
import org.cx.game.widget.building.IOption;
import org.cx.game.widget.treasure.ITreasure;

public class HoneycombGround implements IGround {

	private Integer xBorder = 0;                                       //边界x轴长度
	private Integer yBorder = 0;                                       //边界y轴长度
	private String imagePath = "";                                     //背景图片
	
	private Map<Integer,Place> ground = new HashMap<Integer,Place>();
	private List<AbstractCorps> corpsList = new ArrayList<AbstractCorps>();
	private List<IBuilding> buildingList = new ArrayList<IBuilding>();                 //建筑
	private Map<Integer, IBuilding> buildingMap = new HashMap<Integer, IBuilding>();   //位置 - 建筑
	private List<Integer> disableList = new ArrayList<Integer>();       //不可用的单元格（暂时没用），当地图中间需要被镂空时，会用到
	private List<Integer> emptyList = new ArrayList<Integer>();         //空位
	private Map<Integer, Integer> landformMap = new HashMap<Integer, Integer>();
	private int [][] MAP = null;               //用于查询路线
	private int[] hit = new int []{-1};                                  //-1表示障碍物
	
	private CellularDistrict cellularDistrict = new CellularDistrict();
	private Map<Integer, Integer> coordinateMap = new HashMap<Integer, Integer>();       //序号与坐标系映射
	private Map<Integer, Integer> serialNumberMap = new HashMap<Integer, Integer>();     //坐标系与序号映射

	private Map<Integer, Integer> npcMap = new HashMap<Integer, Integer>();
	private Map<Integer, IPolicyGroup> policyMap = new HashMap<Integer, IPolicyGroup>();
	private List<String> npcData = new ArrayList<String>();
	private IPlayer neutral = null;
	
	private Map<Integer, ITreasure> treasureMap = new HashMap<Integer, ITreasure>();
	
	public HoneycombGround(Integer xBorder, Integer yBorder, String imagePath) {
		// TODO Auto-generated constructor stub
		super();
		
		this.xBorder = xBorder;
		CellularDistrict.xBorder = xBorder;
		this.yBorder = yBorder;
		CellularDistrict.yBorder = yBorder;
		
		this.imagePath = imagePath;
		
		/*
		 * 初始化寻路算法
		 */
		this.MAP = new int[xBorder+2][yBorder+2];
		for(int i=0;i<xBorder+2;i++)
			for(int j=0;j<yBorder+2;j++)
				MAP[i][j] = -1;
		
		/*
		 * 初始化六边形地图算法
		 */
		cellularDistrict.initCellularDistrict(getMaxSerial(Math.max(xBorder, yBorder)));
		initCoordinateSystem(getCentrePoint(xBorder, yBorder),Math.max(xBorder, yBorder));		
		
		for(int i=1;i<getXBorder()+1;i++){
			for(int j=1;j<getYBorder()+1;j++){
				Integer curPos = Integer.valueOf(""+i+IGround.space+j);
				Place place = new Place(this, curPos);
				place.setLandform(AbstractPlace.Landform_Sward);
				addPlace(place);
				
				getEmptyList().add(curPos);      //初始化空位置
			}
		}
		
		/*
		 * 创建中立部队
		 */
		this.neutral = new Player(9, "neutral");
		neutral.setComputer(true);
	}

	public void add(Integer position, AbstractCorps corps) throws RuleValidatorException {
		// TODO 自动生成方法存根
		this.corpsList.add(corps);
		
		Place place = getPlace(position);
		place.in(corps);
		
		corps.setGround(this);
	}
	
	@Override
	public Boolean remove(AbstractCorps corps) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Boolean ret = this.corpsList.remove(corps);
		
		if(ret){
			Corps sc = (Corps) corps;
			Integer status = sc.getDeath().getStatus();
			
			if(AbstractCorps.Death_Status_Live.equals(status)){
				Integer position = sc.getPosition();
				Place place = getPlace(position);
				place.out();
			}
			
			if(AbstractCorps.Death_Status_Death.equals(status)){
				Integer position = sc.getPosition();
				Place place = getPlace(position);
				place.outCemetery(sc);
			}
			
			corps.setGround(null);
		}
		
		return ret;
	}
	
	@Override
	public Boolean contains(AbstractCorps corps) {
		// TODO Auto-generated method stub
		return ground.containsValue(corps);
	}
	
	@Override
	public AbstractCorps getCorps(Integer position) {
		// TODO Auto-generated method stub
		if(null!=ground.get(position))
			return ground.get(position).getCorps();
		else
			return null;
	}
	
	@Override
	public Place getPlace(Integer position) {
		// TODO Auto-generated method stub
		return ground.get(position);
	}
	
	@Override
	public void addPlace(AbstractPlace place) {
		// TODO Auto-generated method stub
		ground.put(place.getPosition(), (Place) place);
	}
	
	//----------------------- Building ------------------------

	public List<IBuilding> getBuildingList() {
		return buildingList;
	}
	
	@Override
	public List<IBuilding> getBuildingList(IPlayer player) {
		// TODO Auto-generated method stub
		List<IBuilding> list = new ArrayList<IBuilding>();
		for(IBuilding building : buildingList){
			if(null!=building.getPlayer() && player.equals(building.getPlayer()))
				list.add(building);
		}
		return list;
	}
	
	@Override
	public List<IBuilding> getBuildingList(IPlayer player, Integer type) {
		// TODO Auto-generated method stub
		List<IBuilding> list = new ArrayList<IBuilding>();
		for(IBuilding building : getBuildingList(player)){
			if(type.equals(building.getType()))
				list.add(building);
		}
		return list;
	}
	
	@Override
	public IBuilding getBuilding(Integer position) {
		// TODO Auto-generated method stub
		return this.buildingMap.get(position);
	}

	public void setBuildings(Map<Integer, Integer> buildings) {
		for(Entry<Integer, Integer> entry : buildings.entrySet()){
			Integer position = entry.getKey();
			Integer buildingType = entry.getValue();
			
			IBuilding building = BuildingFactory.getInstance(buildingType);
			Place place = getPlace(position);
			place.setBuilding(building);
			
			this.buildingList.add(building);
			buildingMap.put(position, building);
		}
	}
	
	@Override
	public List<Integer> getBuildingPosition(IPlayer player) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(IBuilding building : getBuildingList(player)){
			list.add(building.getPosition());
		}
		
		return list;
	}
	
	@Override
	public List<Integer> getBuildingPosition(IPlayer player,
			Integer buildingType) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(IBuilding building : getBuildingList(player, buildingType)){
			list.add(building.getPosition());
		}
		
		return list;
	}
	
	@Override
	public List<Integer> getBuildingPosition(IPlayer player, Integer buildingType, Integer level) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(IBuilding building : getBuildingList(player, buildingType)){
			if(building.getUpgrade().getLevel()>=level)
				list.add(building.getPosition());
		}
		
		return list;
	}
	
	@Override
	public void addBuilding(IBuilding building) {
		// TODO Auto-generated method stub
		buildingList.add(building);
	}
	
	@Override
	public void captureBuilding(Integer position, IPlayer player) {
		// TODO Auto-generated method stub
		IBuilding building = getBuilding(position);
		building.setPlayer(player);
	}
	
	//----------------------- Building End------------------------

	public List<Integer> getDisableList() {
		return disableList;
	}

	public void setDisableList(List<Integer> disableList) {
		this.disableList = disableList;

		for(Integer disable : getDisableList()){
			getPlace(disable).setDisable(true);
		}
	}
	
	@Override
	public List<Integer> getEmptyList() {
		// TODO Auto-generated method stub
		return this.emptyList;
	}
	
	@Override
	public List<String> getNpcData() {
		// TODO Auto-generated method stub
		return this.npcData;
	}
	
	public void setNpcData(List<String> npcData){
		this.npcData = npcData;
	}
	
	@Override
	public void setLandformMap(Map<Integer, Integer> landformMap) {
		// TODO Auto-generated method stub
		this.landformMap = landformMap;
		
		for(Entry<Integer,Integer> entry : landformMap.entrySet()){
			Integer pos = entry.getKey();
			Place place = getPlace(pos);
			place.setLandform(entry.getValue());
		}
	}

	@Override
	public Map<Integer, Integer> getLandformMap() {
		// TODO Auto-generated method stub
		return this.landformMap;
	}
	
	public Map<Integer, ITreasure> getTreasureMap() {
		return treasureMap;
	}
	
	public void setTreasureMap(Map<Integer, ITreasure> treasureMap) {
		this.treasureMap = treasureMap;
		
		for(Entry<Integer, ITreasure> entry : treasureMap.entrySet()){
			Integer position = entry.getKey();
			Place place = getPlace(position);
			ITreasure treasure = entry.getValue();
			place.setTreasure(treasure);
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Ground;
	}
	
	public IPlayer getNeutral(){
		return this.neutral;
	}
	
	public List<Integer> queryRange(AbstractCorps corps, String action){
		List<Integer> positionList = new ArrayList<Integer>();
		Corps sc = (Corps) corps;
		if(NotifyInfo.Command_Query_Attack.equals(action)){
			Integer range = sc.getAttack().getRange();
			/*
			 * 这里要考虑远程单位射程切换为近战时的变化
			 * 当远程单位在战场上时，如果附近有敌方单位，则只能近身攻击
			 */
			if(AbstractCorps.Attack_Mode_Far.equals(sc.getAttack().getMode())){
				List<Integer> list = areaForDistance(corps.getPosition(), 1, IGround.Equal);
				for(Integer position : list){
					AbstractCorps c = getCorps(position);
					if(null!=c && !c.getPlayer().equals(c.getPlayer())){
						range = 1;
						break;
					}
				}
			}
			
			positionList = areaForDistance(corps.getPosition(), range, Contain);  //1：表示范围内的所有单元格，0：表示等于范围的单元格
			positionList.remove(corps.getPosition());
		}
		
		if(NotifyInfo.Command_Query_Move.equals(action)){
			Integer step = sc.getMove().getEnergy()/sc.getMove().getConsume();
			switch (sc.getMove().getType()) {
			case 141:    //步行
				positionList = areaForDistance(corps.getPosition(), step, Contain, AbstractCorps.Move_Type_Walk, corps.getPlayer());
				//distance(280083, 280083, IMove.Type_Walk);
				break;
			case 142:    //骑行
				positionList = areaForDistance(corps.getPosition(), step, Contain, AbstractCorps.Move_Type_Equitation, corps.getPlayer());
				break;
			case 143:    //驾驶
				positionList = areaForDistance(corps.getPosition(), step, Contain, AbstractCorps.Move_Type_Drive, corps.getPlayer());
				break;
			case 144:    //飞行
				positionList = areaForDistance(corps.getPosition(), step, Contain);
				break;
				
			default:
				break;
			}
			positionList.remove(corps.getPosition());
		}
		return positionList;
	}
	
	public List<Integer> queryRange(ISkill skill, String action){
		List<Integer> positionList = new ArrayList<Integer>();
		if(NotifyInfo.Command_Query_Conjure.equals(action) && skill.getOwner() instanceof AbstractCorps){
			positionList = skill.getConjureRange();
		}
		return positionList;
	}
	
	@Override
	public List<Integer> queryRange(IOption option, String action) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		if(NotifyInfo.Command_Query_Execute.equals(action)){
			positionList = option.getExecuteRange(this);
		}
		return positionList;
	}
	
	@Override
	public void picked(ITreasure treasure) {
		// TODO Auto-generated method stub
		Place place = getPlace(treasure.getPosition());
		place.setTreasure(null);
	}

	public Integer getXBorder() {
		return xBorder;
	}

	public Integer getYBorder() {
		return yBorder;
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	/**
	 * 只能查找战场上的生物位置，包含墓地
	 */
	public Integer getPosition(AbstractCorps corps) {
		// TODO Auto-generated method stub
		for(Entry<Integer, Place> entry : ground.entrySet()){
			if(corps.equals(entry.getValue().getCorps())){
				return entry.getKey();
			}else if(entry.getValue().getCemetery().contains(corps)){
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public List<Integer> move(AbstractCorps corps, Integer position, Integer type) throws RuleValidatorException {
		// TODO Auto-generated method stub
		List<Integer> route = new ArrayList<Integer>();
		Corps sc = (Corps) corps;
		List path = route(getPosition(sc), position, type, sc.getPlayer());
		path.remove(0);                         //路径包含起点
		
		for(int i=0;i<path.size();i++){
			
			/*
			 * moveable状态可能会在place.in中改变
			 */
			if(!sc.getMove().getMoveable())
				break;
			
			Integer original = sc.getPosition();
			Node node = (Node) path.get(i);
			
			Place place = getPlace(original);
			place.out();
			place = getPlace(pointToInteger(node._Pos.x, node._Pos.y));
			place.in(sc);
			
			/*
			 * 减少精力
			 */
			Integer energy = sc.getMove().getEnergy();
			energy -= node.consume;
			sc.getMove().setEnergy(energy);
			
			/*
			 * 生成朝向信息
			 */
			if(null!=original){
				IGround ground = sc.getPlayer().getContext().getGround();
				Integer direction = ground.getDirection(original, sc.getPosition());
				sc.getMove().setDirection(direction);
			}
			
			/*
			 * 添加路径
			 */
			route.add(sc.getPosition());
		}
		
		return route;
	}
	
	@Override
	public List toList() {
		// TODO Auto-generated method stub
		List<Place> list = new ArrayList<Place>();
		Set<Integer> keySet = ground.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Place place = ground.get(it.next());
			list.add(place);
		}
		return list;
	}
	
	@Override
	public void inCemetery(AbstractCorps corps) throws RuleValidatorException {
		// TODO Auto-generated method stub
		Place place = getPlace(corps.getPosition());
		place.out();
		place.inCemetery(corps);
	}
	
	@Override
	public void outCemetery(AbstractCorps corps) {
		// TODO Auto-generated method stub
		Place place = getPlace(corps.getPosition());
		place.outCemetery(corps);
	}
	
	@Override
	public List<AbstractCorps> list() {
		// TODO Auto-generated method stub
		return this.corpsList;
	}
	
	@Override
	public List<AbstractCorps> listForID(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		for(AbstractCorps corps : list()){
			if(ids.contains(corps.getType()))
				ret.add(corps);
		}
		return ret;
	}

	@Override
	public List<AbstractCorps> listForID(IPlayer player, List<Integer> ids) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		for(AbstractCorps corps : list()){
			if(ids.contains(corps.getType()) && player.equals(corps.getPlayer()))
				ret.add(corps);
		}
		return ret;
	}

	@Override
	public List<AbstractCorps> list(IPlayer player, Integer status) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		
		for(AbstractCorps corps : list()){
			Corps sc = (Corps) corps;
			if(player.equals(sc.getPlayer()) && status.equals(sc.getDeath().getStatus()))
				ret.add(sc);
		}
		
		return ret;
	}
	
	@Override
	public List<AbstractCorps> list(Integer status) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		
		for(AbstractCorps corps : list()){
			Corps sc = (Corps) corps;
			if(status.equals(sc.getDeath().getStatus()))
				ret.add(sc);
		}
		
		return ret;
	}
	
	@Override
	public List<AbstractCorps> list(Integer stand, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ls = new ArrayList<AbstractCorps>();
		
		List<Integer> list = this.areaForDistance(stand, step, type);
		for(Integer position : list){
			AbstractCorps corps = this.getCorps(position);
			if(null!=corps){
				ls.add(corps);
			}
		}
		
		return ls;
	}
	
	/**
	 * 与distance方法类似，区别在于dis方法会去调用updateMAP
	 * @param start
	 * @param stop
	 * @return
	
	private Integer interval(Integer start, Integer stop){
		Integer ret = 0;
		
		if(!start.equals(stop)){
			List path = route(start, stop); 
			path.remove(0);                     //因为path包含起始位置，因此这里要删除
			path.remove(path.size()-1);         //不计算终点地形，只+1；
			ret += 1;
			
			for(int i=0;i<path.size();i++){
				Node node = (Node) path.get(i);
				ret += node.consume;
			}
		}
		
		return ret;
	} */
	
	@Override
	public Integer distance(Integer start, Integer stop) {
		// TODO Auto-generated method stub
		return CellularDistrict.getShortPathLength(start, stop);
	}
	
	private static final Integer Distance_Max = 9999;
	
	@Override
	public Integer distance(Integer start, Integer stop, Integer moveType, IPlayer control) {
		// TODO Auto-generated method stub
		Integer ret = 0;
		
		if(!start.equals(stop)){
			List path = route(start, stop, moveType, control);
			if(null!=path){                        //如果stop不可到达，即MAP为-1，则path为null
				path.remove(0);                     //因为path包含起始位置，因此这里要删除
				
				for(int i=0;i<path.size();i++){
					Node node = (Node) path.get(i);
					ret += node.consume;
				}
			}else{
				ret = Distance_Max;             //地图MAP中为-1的，这里返回9999
			}
		}
		
		return ret;
	}

	public List<Integer> areaForDistance(Integer position, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=1;i<xBorder+1;i++){
			for(int j=1;j<yBorder+1;j++){
				Integer curPos = Integer.valueOf(""+i+space+j);
				switch (type) {
				case 0:
					if(step.equals(distance(position, curPos)))
						list.add(curPos);
					break;
					
				case 1:
					if(step>=distance(position, curPos))
						list.add(curPos);
					break;		
					
				default:
					break;
				}
				
			}
		}
		return list;
	}
	
	@Override
	public List<Integer> areaForDistance(Integer position, Integer step,
			Integer type, Integer moveType, IPlayer control) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		/*
		 * 缩小计算范围
		 */
		List<Integer> posList = areaForDistance(position, step, type);
		
		for(Integer curPos : posList){
			switch (type) {
			case 0:
				if(step.equals(distance(position, curPos, moveType, control)))
					if(getPlace(curPos).getEmpty())             //友方可以穿人，因此在计算路径时，不考虑友军站位，但这里就要判断
						list.add(curPos);
				break;
					
			case 1:
				if(step>=distance(position, curPos, moveType, control))
					if(getPlace(curPos).getEmpty())
						list.add(curPos);
				break;	
					
			default:
				break;
			}
		}

		return list;		
	}
		
	@Override
	public Integer getDirection(Integer stand, Integer target) {
		// TODO Auto-generated method stub
		Integer ret = null;
		
		Integer [] p1 = integerToPoint(stand);
		Integer [] p2 = integerToPoint(target);
		
		if(p1[1]%2==0){
			if(p1[0]-p2[0]==1 && p1[1]-p2[1]==1)
				ret = IGround.Relative_LeftTop;
			
			if(p1[0]-p2[0]==0 && p1[1]-p2[1]==1)
				ret = IGround.Relative_RightTop;
				
			if(p1[0]-p2[0]==1 && p1[1]-p2[1]==0)
				ret = IGround.Relative_Left;
					
			if(p1[0]-p2[0]==-1 && p1[1]-p2[1]==0)
				ret = IGround.Relative_Right;
						
			if(p1[0]-p2[0]==1 && p1[1]-p2[1]==-1)
				ret = IGround.Relative_LeftBottom;
							
			if(p1[0]-p2[0]==0 && p1[1]-p2[1]==-1)
				ret = IGround.Relative_RightBottom;
		}else{
			if(p1[0]-p2[0]==0 && p1[1]-p2[1]==1)
				ret = IGround.Relative_LeftTop;
			
			if(p1[0]-p2[0]==-1 && p1[1]-p2[1]==1)
				ret = IGround.Relative_RightTop;
				
			if(p1[0]-p2[0]==1 && p1[1]-p2[1]==0)
				ret = IGround.Relative_Left;
					
			if(p1[0]-p2[0]==-1 && p1[1]-p2[1]==0)
				ret = IGround.Relative_Right;
						
			if(p1[0]-p2[0]==0 && p1[1]-p2[1]==-1)
				ret = IGround.Relative_LeftBottom;
							
			if(p1[0]-p2[0]==-1 && p1[1]-p2[1]==-1)
				ret = IGround.Relative_RightBottom;
		}
		
		return ret;
	}
	
	@Override
	public Integer getPosition(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		Integer [] p1 = integerToPoint(stand);
		Integer ret = null;
		
		if(p1[1]%2==0){
			switch (direction) {
			case 10:
				ret = pointToInteger(p1[0]-1, p1[1]-1);
				break;
			case 2:
				ret = pointToInteger(p1[0], p1[1]-1);
				break;
			case 9:
				ret = pointToInteger(p1[0]-1, p1[1]);
				break;
			case 3:
				ret = pointToInteger(p1[0]+1, p1[1]);
				break;
			case 8:
				ret = pointToInteger(p1[0]-1, p1[1]+1);
				break;
			case 4:
				ret = pointToInteger(p1[0], p1[1]+1);
				break;

			default:
				break;
			}
		}else{
			switch (direction) {
			case 10:
				ret = pointToInteger(p1[0], p1[1]-1);
				break;
			case 2:
				ret = pointToInteger(p1[0]+1, p1[1]-1);
				break;
			case 9:
				ret = pointToInteger(p1[0]-1, p1[1]);
				break;
			case 3:
				ret = pointToInteger(p1[0]+1, p1[1]);
				break;
			case 8:
				ret = pointToInteger(p1[0], p1[1]+1);
				break;
			case 4:
				ret = pointToInteger(p1[0]+1, p1[1]+1);
				break;

			default:
				break;
			}
		}
		
		return ret ;
	}
	
	@Override
	public List<Integer> twoFlanks(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		Integer position  = null;
		
		switch (direction) {
		case 10:
			position = getPosition(stand, IGround.Relative_Left);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightTop);
			if(!isOver(position))
				list.add(position);
			
			break;
		case 2:
			position = getPosition(stand, IGround.Relative_LeftTop);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_Right);
			if(!isOver(position))
				list.add(position);
			
			break;
		case 9:
			position = getPosition(stand, IGround.Relative_LeftTop);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_LeftBottom);
			if(!isOver(position))
				list.add(position);
			
			break;
		case 3:
			position = getPosition(stand, IGround.Relative_RightTop);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightBottom);
			if(!isOver(position))
				list.add(position);
			
			break;
		case 8:
			position = getPosition(stand, IGround.Relative_Left);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightBottom);
			if(!isOver(position))
				list.add(position);
			
			break;
		case 4:
			position = getPosition(stand, IGround.Relative_Right);
			if(!isOver(position))
				list.add(position);
			
			position = getPosition(stand, IGround.Relative_LeftBottom);
			if(!isOver(position))
				list.add(position);
			
			break;

		default:
			break;
		}
		
		return list;
	}
	
	@Override
	public Integer getPointByWay(Integer stand, Integer dest, Integer step,
			Integer moveType, IPlayer control) {
		// TODO Auto-generated method stub
		List path = route(stand, dest, moveType, control);
		for(int i=0;i<path.size();i++){
			Node node = (Node) path.get(i);
			Integer pos = pointToInteger(node._Pos.x, node._Pos.y);
			if(dest.equals(pos)         //在移动范围内
			|| node.sourcePoint>step)   //在移动范围外
				return pos;
		}
		return null;
	}

	/**
	 * 根据圈数计算最大编号
	 * @param circleNumber 圈数，原点不算，即第一圈为7
	 * @return
	 */
	private Integer getMaxSerial(Integer circleNumber){
		Integer number = 1;
    	for (int i = 1; i < circleNumber+1; i++) {
			number += i*6;
		}
    	return number;
    }
	
	/**
	 * 根据边界计算中心点
	 * @param xBorder x边界
	 * @param yBorder y边界
	 * @return
	 */
	private Integer getCentrePoint(Integer xBorder, Integer yBorder){
		return pointToInteger(xBorder/2, yBorder/2);
    }
	
	public static final Integer base = 100;
	
	/**
	 * 判断坐标是否越界，只用于初始化坐标系与序号映射时使用
	 * @param point 坐标
	 * @return
	 */
	private Boolean isCoordinateOver(Integer point){
    	Integer [] ps = integerToPoint(point);
		if(ps[0]<base+1)
			return true;
		if(ps[1]<base+1)
			return true;
		return false;
    }
    
	/**
	 * 添加坐标系与序号的映射
	 * @param serialNumber 序号
	 * @param point 坐标
	 */
    private void addCoordinateMap(Integer serialNumber, Integer point){
    	if(!isCoordinateOver(point)){
			coordinateMap.put(serialNumber, point);
    		serialNumberMap.put(point, serialNumber);
		}
    }
    
    /**
     * 根据坐标查找序号
     * @param coordinate 坐标
     * @return
     */
    private Integer getSerialNumber(Integer coordinate){
    	Integer [] ps = integerToPoint(coordinate);
    	Integer point = pointToInteger(base+ps[0], base+ps[1]);
    	return serialNumberMap.get(point);
    }
    
    /**
     * 根据序号查找坐标
     * @param serialNumber 序号
     * @return
     */
    private Integer getCoordinate(Integer serialNumber){
    	Integer point = coordinateMap.get(serialNumber);
    	Integer [] ps = integerToPoint(point);
    	return pointToInteger(ps[0]%base, ps[1]%base);
    }
	
	/**
	 * 初始化坐标系与序号的映射
	 * @param centrePoint 原点/中心点
	 * @param circleNumber 圈数，原点不算，即第一圈为7
	 */
	private void initCoordinateSystem(Integer centrePoint, Integer circleNumber){
		Integer n = 0;
    	Integer [] point = integerToPoint(centrePoint);
    	
    	Integer starPoint = pointToInteger(base+point[0], base+point[1]);
    	n += 1;
    	addCoordinateMap(n, starPoint);
    	
    	starPoint = getPosition(starPoint, IGround.Relative_Left);
		n += 1;
		addCoordinateMap(n, starPoint);
    	
    	for (int i = 1; i < circleNumber+1; i++) {
			for (int j = 1; j < i; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_LeftTop);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
			
			for (int j = 0; j < i; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_RightTop);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
			
			for (int j = 0; j < i; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_Right);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
			
			for (int j = 0; j < i; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_RightBottom);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
			
			for (int j = 0; j < i; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_LeftBottom);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
			
			for (int j = 0; j < i+1; j++) {
				starPoint = getPosition(starPoint, IGround.Relative_Left);
				n += 1;
				addCoordinateMap(n, starPoint);
			}
		}
    }
	
	private Integer[] integerToPoint(Integer point){
		String [] points = point.toString().split(space);
		Integer x = Integer.valueOf(points[0]);
		Integer y = Integer.valueOf(points[1]);

		return new Integer[]{x,y}; 
	}
	
	/**
	 * 用于表示越界的点，isOver = true
	 */
	private static final Integer OverPoint = 110010;
	
	private Integer pointToInteger(Integer x,Integer y){
		if(x<1 || y<1)
			return OverPoint;
		else
			return Integer.valueOf(x+space+y);
	}
	
	/**
	 * 判断是否超出边界
	 * @param position
	 * @return
	 */
	private Boolean isOver(Integer position){
		Integer [] ps = integerToPoint(position);
		if(ps[0]<1||ps[0]>xBorder)
			return true;
		if(ps[1]<1||ps[1]>yBorder)
			return true;
		return false;
	}
	
	/**
	 * 两点之间的矩阵，包含起点和终点
	 * @param start 左上角
	 * @param stop  右下角
	 * @return
	 */
	private List<Integer> rectangle(Integer start, Integer stop){
		String [] starts = start.toString().split(space);
		Integer startx = Integer.valueOf(starts[0]);
		Integer starty = Integer.valueOf(starts[1]);
		
		String [] stops = stop.toString().split(space);
		Integer stopx = Integer.valueOf(stops[0]);
		Integer stopy = Integer.valueOf(stops[1]);
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i=startx;i<=stopx;i++)
			for(int j=starty;j<=stopy;j++)
				list.add(Integer.valueOf(i+space+j));
		return list;
	}
	
	/**
	 * 更新MAP加载地形；
	 */
	private void updateMAP_Landform(Integer moveType){
		
		List<Integer> m = rectangle(Integer.valueOf(1+space+1), Integer.valueOf(xBorder+space+yBorder));
		
		for(Integer i : m){
			String [] is = i.toString().split(space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			Place p = getPlace(i);
			MAP[ix][iy] = LandformEffect.getConsume(moveType, p.getLandform());
		}
	}
	
	/**
	 * 加载战场单位的站位情况；
	 * 为什么是独立为一个方法，主要考虑hide状态下系统不会去加载站位情况
	 */
	private void updateMAP_Stance(IPlayer control){
		
		List<Integer> pList = new ArrayList<Integer>();         //敌方单位的站位，友方允许穿过
		List<Integer> nList = new ArrayList<Integer>();         //敌方单位附近1个单元格
		
		List<AbstractCorps> cList = list(control, AbstractCorps.Death_Status_Live);
		List<AbstractCorps> eList = list(AbstractCorps.Death_Status_Live);            //非友方单位
		eList.removeAll(cList);
		
		for(AbstractCorps corps : eList){
			pList.add(corps.getPosition());
			
			List<Integer> list = areaForDistance(corps.getPosition(), 1, IGround.Contain);
			list.removeAll(nList);
			nList.addAll(list);
		}
		
		for(Integer p : nList){
			Integer [] point = integerToPoint(p);
			if(-1!=MAP[point[0]][point[1]])
				MAP[point[0]][point[1]] += 1;
		}
		
		for(Integer p : pList){
			Integer [] point = integerToPoint(p);
			MAP[point[0]][point[1]] = -1;
		}
	}
	
	/**
	 * 初始化MAP，不加载地形等信息
	 */
	private void updateMAP(){
		List<Integer> m = rectangle(Integer.valueOf(1+space+1), Integer.valueOf(xBorder+space+yBorder));
		
		for(Integer i : m){
			String [] is = i.toString().split(space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			MAP[ix][iy] = 1;
		}
	}
	
	/**
	 * 获得两点之间的最短路线，考虑障碍物，并且start<>stop
	 * 注意，在调用该方法之前，必须调用updateMAP，之所以将两个方法分开，也是为了提高
	 * 计算效率，例如在一次route方法调用，只更新一次MAP
	 * @param start
	 * @param stop
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达则返回null
	 */
	private List route(Integer start, Integer stop){
		updateMAP();
		
		PathFinding pathFinding = new PathFinding(MAP,hit);
		
		Integer[] starts = integerToPoint(start);
		Integer[] stops = integerToPoint(stop);
		Point startPos = new Point(starts[0], starts[1]);
		Point stopPos = new Point(stops[0],stops[1]);
		List path = pathFinding.searchPath(startPos, stopPos);
		
		return path;
	}
	
	/**
	 * 
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @param control 当前控制者，用于计算敌人的站位
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	private List route(Integer start, Integer stop, Integer moveType, IPlayer control){
		updateMAP_Landform(moveType);           //加载地形
		updateMAP_Stance(control);                     //加载站位
		
		PathFinding pathFinding = new PathFinding(MAP,hit);
		
		Integer[] starts = integerToPoint(start);
		Integer[] stops = integerToPoint(stop);
		Point startPos = new Point(starts[0], starts[1]);
		Point stopPos = new Point(stops[0],stops[1]);
		List path = pathFinding.searchPath(startPos, stopPos);
		
		return path;
	}
	
	public static void main(String[] args) {
		/*Integer x1=1,y1=1,x2=2,y2=2;
		System.out.println(Util.convertInteger(Math.sqrt(Math.pow(x1.doubleValue()-x2.doubleValue(),2)+Math.pow(y1.doubleValue()-y2.doubleValue(),2))));
		
		System.out.println(Math.abs(x1-x2)+Math.abs(y1-y2)) ;*/
		Random r = new Random();
		System.out.println(r.nextInt(10));
		
		IGround ground = new HoneycombGround(21,13,null);
		System.out.println(ground);
	}

}
