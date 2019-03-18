package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cx.game.core.Player;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CellularDistrict;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.IListFilter;
import org.cx.game.tools.ListUtils;
import org.cx.game.tools.Node;
import org.cx.game.tools.GroundUtil;
import org.cx.game.tools.Util;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.treasure.Treasure;
 
public abstract class Ground implements IGround {
	
	/**
	 * 边界上
	 */
	public static final Integer Equal = 0;
	
	/**
	 * 范围内
	 */
	public static final Integer Contain = 1;
	
	private static int balk = -1;
	private int[] hit = new int []{balk};                                  //-1表示障碍物
	
	private Integer id = null;
	private String name = "";
	private Integer xBorder = 0;                                       //边界x轴长度
	private Integer yBorder = 0;                                       //边界y轴长度
	
	private String imagePath = "";                                     //背景图片
	
	private Area area = null;
	private IArithmetic arithmetic = null;
	
	private List<Corps> corpsList = new ArrayList<Corps>();
	//private List<Corps> deadCorpsList = new ArrayList<Corps>();
	private Map<Integer,Place> ground = new HashMap<Integer,Place>();
	private List<AbstractBuilding> buildingList = new ArrayList<AbstractBuilding>();                 //建筑
	private Map<Integer, Integer> landformMap = new HashMap<Integer, Integer>();
	private List<Integer> troopList = new ArrayList<Integer>();         //阵营
	private Map<Integer, Integer> entranceMap = new HashMap<Integer, Integer>();  //入口  坐标 - 阵营
	
	private Map<Integer, Treasure> treasureMap = new HashMap<Integer, Treasure>();
	private List<Treasure> treasureList = new ArrayList<Treasure>();
	
	private Map<Integer, AbstractBuilding> buildingMap = new HashMap<Integer, AbstractBuilding>();   //位置 - 建筑
	private Map<AbstractBuilding, Integer> buildingIsTroop = new HashMap<AbstractBuilding, Integer>();
	
	private List<Place> placeList = new ArrayList<Place>();
	
	public Ground(Integer id, String name, Integer xBorder, Integer yBorder, String imagePath) {
		
		this.id = id;
		this.name = name;
		this.xBorder = xBorder;
		this.yBorder = yBorder;
		
		this.imagePath = imagePath;
		
		for(int i=1;i<getXBorder()+1;i++){
			for(int j=1;j<getYBorder()+1;j++){
				Integer curPos = Integer.valueOf(""+i+Util.space+j);
				Place place = new Place(this, curPos);
				place.setLandform(Place.Landform_Sward);
				addPlace(place);
				placeList.add(place);
			}
		}
		
		setArithmetic(new HexagonArithmetic());
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	/**
	 * 战场x边界
	 * @return
	 */
	public Integer getXBorder() {
		// TODO Auto-generated method stub
		return this.xBorder;
	}

	/**
	 * 战场y边界
	 * @return
	 */
	public Integer getYBorder() {
		// TODO Auto-generated method stub
		return this.yBorder;
	}
	
	/**
	 * 控制列表，根据战场类型的不同，控制列表也不同；
	 * @return
	 */
	public abstract AbstractControlQueue getQueue();
	
	public IArithmetic getArithmetic() {
		return arithmetic;
	}
	
	public void setArithmetic(IArithmetic arithmetic) {
		this.arithmetic = arithmetic;
	}
	
	//----------------------- NPC ---------------------
		
	/*public void setCorpsData(List<String> corpsData){
		for(int i=0;null!=corpsData && i<corpsData.size();i++){
			XmlConfigureHelper.map_corpsData_corps(corpsData.get(i), this);
		}
	}*/
	
	//-------------------- Area -----------------------------
	
	/**
	 * 所属区域
	 * @return
	 */
	public Area getArea() {
		return this.area;
	}
	
	public void setArea(Area area) {
		this.area = area;
	}
	
	public void setTroopList(List<Integer> troopList) {
		this.troopList = troopList;
	}
	
	//-------------------- Troop -------------------------
	
	/**
	 * 阵营
	 * @return 
	 */
	public List<Integer> getTroopList() {
		return this.troopList;
	}
	
	/**
	 * 入口，阵营 - 坐标
	 * @return
	 */
	public Map<Integer, Integer> getEntranceMap() {
		return entranceMap;
	}

	public void setEntranceMap(Map<Integer, Integer> entranceMap) {
		this.entranceMap = entranceMap;
	}
	
	/**
	 * 根据阵营查询入口
	 * @param troop 
	 * @return
	 */
	public List<Integer> getEntranceList(Integer troop) {
		List<Integer> retList = new ArrayList<Integer>();
		for(Integer position : getEntranceMap().keySet()){
			if(troop.equals(getEntranceMap().get(position)))
				retList.add(position);
		}
		
		return retList;				
	}
	
	//----------------------- Building ------------------------
	
	public void setBuildingData(List<String> buildings) {
		
		for(int i=0;null!=buildings && i<buildings.size();i++){
			XmlConfigureHelper.map_buildingData_building(buildings.get(i), this);
		}
	}
	
	public Map<AbstractBuilding, Integer> getBuildingIsTroop() {
		return buildingIsTroop;
	}
	
	/**
	 * 放置一个建筑物到战场
	 * @param position
	 * @param building
	 */
	public void placementBuilding(Integer position, AbstractBuilding building) {
		// TODO Auto-generated method stub
		Place place = getPlace(position);		
		place.setBuilding(building);
		
		buildingList.add(building);
		buildingMap.put(position, building);
		
	}
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @return
	 */
	public List<Integer> getBuildingPosition(Player player) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(AbstractBuilding building : getBuildingList(player)){
			list.add(building.getPlace().getPosition());
		}
		
		return list;
	}
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @param buildingType 建筑类型
	 * @return
	 */
	public List<Integer> getBuildingPosition(Player player,
			Integer buildingType) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(AbstractBuilding building : getBuildingList(player, buildingType)){
			list.add(building.getPlace().getPosition());
		}
		
		return list;
	}
	
	/**
	 * 根据坐标查找建筑
	 * @param position
	 * @return
	 */
	public AbstractBuilding getBuilding(Integer position) {
		// TODO Auto-generated method stub
		return this.buildingMap.get(position);
	}
	
	public List<AbstractBuilding> getBuildingList() {
		return buildingList;
	}
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @param buildingType 建筑类型
	 * @param level 大于等于这个等级
	 * @return
	 */
	public List<Integer> getBuildingPosition(Player player, Integer buildingType, Integer level) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for(AbstractBuilding building : getBuildingList(player, buildingType)){
			if(building.getUpgrade().getLevel()>=level)
				list.add(building.getPlace().getPosition());
		}
		
		return list;
	}
	
	/**
	 * 占领建筑
	 * @param position 位置
	 * @param player 玩家
	 */
	public void captureBuilding(Player player, AbstractBuilding building) {
		// TODO Auto-generated method stub
		building.setPlayer(player);
	}
	
	/**
	 * 获取建筑物
	 * @param player 玩家
	 * @return
	 */
	public List<AbstractBuilding> getBuildingList(Player player) {
		// TODO Auto-generated method stub
		List<AbstractBuilding> list = new ArrayList<AbstractBuilding>();
		for(AbstractBuilding building : getBuildingList()){
			if(null!=building.getPlayer() && player.equals(building.getPlayer()))
				list.add(building);
		}
		return list;
	}
	
	/**
	 * 获取建筑物
	 * @param player 玩家
	 * @param type 建筑物类型
	 * @return
	 */
	public List<AbstractBuilding> getBuildingList(Player player, Integer type) {
		// TODO Auto-generated method stub
		List<AbstractBuilding> list = new ArrayList<AbstractBuilding>();
		for(AbstractBuilding building : getBuildingList(player)){
			if(type.equals(building.getType()))
				list.add(building);
		}
		return list;
	}
	
	/**
	 * 根据建筑物class，进行查询
	 * @param clazz
	 * @return
	 */
	public List<AbstractBuilding> getBuildingList(Class clazz) {
		// TODO Auto-generated method stub
		List<AbstractBuilding> list = new ArrayList<AbstractBuilding>();
		for(AbstractBuilding building : getBuildingList()){
			if(building.getClass().equals(clazz))
				list.add(building);
		}
		return list;
	}
	
	public Integer getUsableEntrance(Integer troop) {
		Integer entrance = null;
		for(Integer position : getEntranceList(troop)) {
			Place place = getPlace(position);
			if(place.isEmpty())
				entrance = position;
		}
		
		return entrance;
	}
	
	//----------------------- Corps ----------------------
	
	/**
	 * 在指定位置放置一个corps，用于初始化ground
	 * @param position 位置
	 * @param corps
	 */
	public void placementCorps(Integer position, Corps corps) {
		// TODO Auto-generated method stub
		this.corpsList.add(corps);
		
		Place place = getPlace(position);
		place.in(corps);
	}
	
	/**
	 * 移除corps，从ground中彻底移除
	 * @param corps
	 */
	public Boolean removeCorps(Corps corps) {
		// TODO Auto-generated method stub
		Boolean ret = this.corpsList.remove(corps);
		
		if(ret){
			Integer position = corps.getPosition();
			Place place = getPlace(position);
			place.out(corps);
		}
		
		return ret;
	}

	/**
	 * 根据位置查找corps，不包括墓地，注意这里只返回一个corps
	 * @param position
	 * @return 
	 */
	public Corps getCorps(Integer position) {
		// TODO Auto-generated method stub
		if(null!=ground.get(position))
			return ground.get(position).getCorps();
		else
			return null;
	}
	
	/**
	 * 战场上的corps，不要试图通过getLivingCorpsList()调用方法来改变this.livingCorpsList；
	 * @return
	 */
	public List<Corps> getCorpsList() {
		// TODO Auto-generated method stub
		List<Corps> retList = new ArrayList<Corps>();
		retList.addAll(this.corpsList);
		return retList;
	}

	/**
	 * 只能查找战场上的生物位置，包含墓地
	 */
	public Integer getPosition(Corps corps) {
		// TODO Auto-generated method stub
		for(Entry<Integer, Place> entry : ground.entrySet()){
			if(entry.getValue().contains(corps)){
				return entry.getKey();
			}else if(entry.getValue().getCemetery().contains(corps)){
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * 移动到指定位置
	 * @param corps 生物
	 * @param position 指定位置
	 * @param type 移动类型
	 */
	public List<Integer> move(Corps corps, Integer position, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> route = new ArrayList<Integer>();
		Corps c = (Corps) corps;
		Integer stand = getPosition(c);
		List<Node> path = route(stand, position, type);
		
		if(null==path)
			return route;
		
		Corps lastCorps = null;
		Integer lastPosition = c.getPosition();
		route.add(lastPosition);
		
		for(int i=1;i<path.size();i++){
			
			/*
			 * moveable状态可能会在place.in中改变
			 */
			if(!c.getMove().getMoveable())
				break;
			
			Node node = (Node) path.get(i);
			
			Place place = getPlace(lastPosition);
			place.out(c);
			if(null!=lastCorps)
				place.in(lastCorps);
			
			Integer curPosition = Util.pointToInteger(node._Pos.x, node._Pos.y);
			
			place = getPlace(curPosition);
			lastCorps = (Corps) place.getCorps();
			place.out(lastCorps);
			place.in(c);
			
			/*
			 * 减少精力
			 */
			Integer energy = c.getMove().getEnergy();
			//System.out.println(sc.getName()+":"+energy);
			energy -= node.consume;
			//System.out.println("("+node._Pos.x+","+node._Pos.y+")"+node.consume);
			c.getMove().setEnergy(energy);
			
			/*
			 * 生成朝向信息
			 */
			if(null!=lastPosition){
				Integer direction = getDirection(lastPosition, curPosition);
				c.getMove().setDirection(direction);
			}
			
			/*
			 * 添加路径
			 */
			route.add(curPosition);
			
			lastPosition = curPosition;
		}
		
		return route;
	}
	
	/**
	 * 移动位置，它与move不同，它不考虑移动消耗，只是单纯的将corps的位置移动到指定位置；
	 * 它可以简单的理解为，removeCorps和placementCorps的组合
	 * @param corps 在战场上的Corps
	 * @param position
	 */
	public void translocation(Corps corps, Integer position) {
		if(removeCorps(corps))
			placementCorps(position, corps);
	}
	
	/**
	 * 获取某玩家战场上所有生物
	 * @param player
	 * @return
	 */
	public List<Corps> getCorpsList(Player player) {
		// TODO Auto-generated method stub
		List<Corps> ret = new ArrayList<Corps>();
		
		for(Corps corps : getCorpsList()){
			if(player.equals(corps.getPlayer()))
				ret.add((Corps) corps);
		}
		
		return ret;
	}
	
	/**
	 * 获取指定范围内的随从
	 * @param stand
	 * @param step
	 * @param type
	 * @return
	 */
	public List<Corps> queryCorpsList(Integer stand, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<Corps> ls = new ArrayList<Corps>();
		
		List<Integer> list = areaForDistance(stand, step, type);
		for(Integer position : list){
			Corps corps = (Corps) getCorps(position);
			if(null!=corps){
				ls.add(corps);
			}
		}
		
		return ls;
	}
	
	public List<Integer> queryRange(Corps corps, String action) {
		List<Integer> positionList = new ArrayList<Integer>();
		Corps sc = (Corps) corps;
		
		if(CommonIdentifierE.Command_Query_Move.equals(action) || CommonIdentifierE.Command_Query_Leave.equals(action)){
			Integer step = sc.getMove().getEnergy()/sc.getMove().getConsume();
			switch (sc.getMove().getType()) {
			case 141:    //步行
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Walk);
				//distance(280083, 280083, IMove.Type_Walk);
				break;
			case 142:    //骑行
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Equitation);
				break;
			case 143:    //驾驶
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Drive);
				break;
			case 144:    //飞行
				positionList = areaForDistance(corps.getPosition(), step, Contain);
				break;
				
			default:
				break;
			}
			positionList.remove(corps.getPosition());
		}
		
		if(CommonIdentifierE.Command_Query_Merge.equals(action)) {
			Integer step = sc.getMove().getEnergy()/sc.getMove().getConsume();
			positionList = areaForDistance(corps.getPosition(), step, Contain);
			positionList.remove(corps.getPosition());
		}
		
		return positionList;
	}
	
	/**
	 * 查询技能使用范围，现将查询逻辑交给ActiveSkill来完成
	 * @param skill
	 * @param action
	 * @return
	 
	public abstract List<Integer> queryRange(AbstractSkill skill, String action);*/
	
	//----------------------- Landform ---------------------------
	
	/**
	 * 将坐标转换为Place
	 * @param position 坐标
	 * @return
	 */
	public Place getPlace(Integer position) {
		// TODO Auto-generated method stub
		return ground.get(position);
	}

	/**
	 * 在地图上增加一块区域（地图是由若干区域组成）
	 * @param place
	 */
	public void addPlace(Place place) {
		// TODO Auto-generated method stub
		ground.put(place.getPosition(), place);
	}

	/**
	 * 设置地形
	 * @param landformMap 地形数据
	 */
	public void setLandformMap(Map<Integer, Integer> landformMap) {
		// TODO Auto-generated method stub
		this.landformMap = landformMap;
		
		for(Entry<Integer,Integer> entry : landformMap.entrySet()){
			Integer pos = entry.getKey();
			Place place = getPlace(pos);
			place.setLandform(entry.getValue());
		}
	}

	/**
	 * 
	 * @return 地形数据
	 */
	public Map<Integer, Integer> getLandformMap() {
		// TODO Auto-generated method stub
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		retMap.putAll(this.landformMap);
		return retMap;
	}
	
	//-------------------------- Treasure ---------------------------
	
	/**
	 * 初始地图时，放置物品
	 * @param treasureMap
	 */
	public void setTreasureMap(Map<Integer, Treasure> treasureMap) {
		this.treasureMap = treasureMap;
		
		for(Entry<Integer, Treasure> entry : treasureMap.entrySet()){
			Integer position = entry.getKey();
			Place place = getPlace(position);
			Treasure treasure = entry.getValue();
			place.setTreasure(treasure);
			this.treasureList.add(treasure);
		}
	}

	Map<Integer, Treasure> getTreasureMap() {
		return treasureMap;
	}
	
	/**
	 * 地图上的物品
	 * @return
	 */
	public List<Treasure> getTreasureList() {
		// TODO Auto-generated method stub
		return this.treasureList;
	}

	/**
	 * 移除物品
	 * @param treasure
	 */
	public void removeTreasure(Treasure treasure) {
		// TODO Auto-generated method stub
		Place place = getPlace(treasure.getPosition());
		place.setTreasure(null);
		this.treasureMap.remove(treasure);
		this.treasureList.remove(treasure);
	}
	
	/**
	 * 根据条件，查询不同状态的Place
	 * @param isEmpty 
	 * @return position的集合
	 */ 
	public List<Integer> queryPositionList(final Boolean isEmpty) {
		List<Integer> retList = new ArrayList<Integer>();
		
		List<Place> list = ListUtils.filter(this.placeList, new IListFilter<Place>(){

			@Override
			public Boolean content(Place t) {
				// TODO Auto-generated method stub
				return t.isEmpty().equals(isEmpty);
			}
			
		});
		
		for(Place place : list)
			retList.add(place.getPosition());
		
		return retList;
	}
	
	public List<Integer> queryRange(AbstractOption option, String action) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		if(CommonIdentifierE.Command_Query_Execute.equals(action)){
			positionList = option.getExecuteRange();
		}
		return positionList;
	}
	
	private static final Integer Distance_Max = 9999;
	
	/**
	 * 两个坐标之间的最短距离，不考虑其它因素
	 * @param start
	 * @param stop
	 * @return
	 */
	public Integer distance(Integer start, Integer stop) {
		// TODO Auto-generated method stub
		return CellularDistrict.getShortPathLength(start.longValue(), stop.longValue());
	}
	
	/**
	 * 两个坐标之间的最短距离，考虑地形
	 * @param start
	 * @param stop
	 * @param moveType 移动类型
	 * @return
	 */
	public Integer distance(Integer start, Integer stop, Integer moveType) {
		// TODO Auto-generated method stub
		Integer ret = 0;
		
		if(!start.equals(stop)){
			List<Node> path = route(start, stop, moveType);
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

	/**
	 * 获取指定距离的区域，不考虑其它因素
	 * @param position
	 * @param step
	 * @param type
	 * @return
	 */
	public List<Integer> areaForDistance(Integer position, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=1;i<getXBorder()+1;i++){
			for(int j=1;j<getYBorder()+1;j++){
				Integer curPos = Integer.valueOf(""+i+Util.space+j);
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
	
	/**
	 * 获取指定距离的区域，不考虑其它因素
	 * @param position
	 * @param range 与数字不同，这里用字符串表示例如：3-8；
	 * @param type
	 * @return
	 */
	public List<Integer> areaForDistance(Integer position, String range,
			Integer type) {
		// TODO Auto-generated method stub
		String [] rs = range.split("-");
		List<Integer> list1 = areaForDistance(position, Integer.valueOf(rs[0])-1, Ground.Contain);
		List<Integer> list2 = areaForDistance(position, Integer.valueOf(rs[1]), Ground.Contain);
		list2.removeAll(list1);
		return list2;
	}
	
	/**
	 * 获取指定距离的区域，考虑障碍物，加载地形
	 * @param position 指定坐标
	 * @param step 距离，注意step必须大于0，否则无意义
	 * @param type  0:刚好在边界上;1范围内;2范围外;
	 * @param moveType  移动类型
	 * @return
	 */
	public List<Integer> areaForDistance(Integer position, Integer step,
			Integer type, Integer moveType) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		/*
		 * 缩小计算范围
		 */
		List<Integer> posList = areaForDistance(position, step, type);
		
		for(Integer curPos : posList){
			switch (type) {
			case 0:
				if(step.equals(distance(position, curPos, moveType)))
					if(getPlace(curPos).isEmpty())             //友方可以穿人，因此在计算路径时，不考虑友军站位，但这里就要判断
						list.add(curPos);
				break;
					
			case 1:
				if(step>=distance(position, curPos, moveType))
					if(getPlace(curPos).isEmpty())
						list.add(curPos);
				break;	
					
			default:
				break;
			}
		}

		return list;
	}
	
	/**
	 * 获取某个方向直线上的点
	 * @param stand 站位
	 * @param direction 方向
	 * @param step 直线长度
	 * @return 不包含起点
	 */
	public List<Integer> getLine(Integer stand, Integer direction, Integer step) {
		List<Integer> line = new ArrayList<Integer>();
		Integer lastPos = stand;
		for(int i=0;i<step;i++){
			Integer pos = getPosition(lastPos, direction);
			if(null==pos)               //判断越界
				break;
			else{
				line.add(pos);
				lastPos = pos;
			}
		}
		
		return line;
	}
	
	/**
	 * 根据移动类型，获取某个方向直线上的点；
	 * LandformEffect.getConsume为-1和place.isEmpty为false 都是不可到达的点，从不可到达的点开始后面的点都不考虑
	 * @param stand 起点
	 * @param direction 方向
	 * @param step 直线长度
	 * @param stance 是否考虑站位
	 * @param moveType 移动类型
	 * @return 
	 */
	public List<Integer> getLine(Integer stand, Integer direction, Integer step, Boolean stance, Integer moveType) {
		List<Integer> line = new ArrayList<Integer>();
		Integer lastPos = stand;		
		
		for(int i=0;i<step;i++){
			Integer pos = getPosition(lastPos, direction);
			if(null==pos){               //判断越界
				break;
			}else{
				Place place = getPlace(pos);
				if(LandformEffect.getConsume(moveType, place.getLandform()).equals(-1))      //判断地形
					break;
				
				if(stance && !place.isEmpty())              //判断站位
					break;
				
				line.add(pos);
				lastPos = pos;
			}
		}
		
		return line;
	}
	
	/**
	 * 取两点之间的直线坐标
	 * @param stand 站位
	 * @param target 目标位
	 * @return 如果两点不在一条直线上，则返回null；
	 */
	public List<Integer> getLine(Integer stand, Integer target) {
		List<Integer> path = routeToPosition(stand, target);
		
		if(null!=path){       //如果path不为空，就表示path至少包含起始两个点
			Integer pos1 = path.get(0);
			Integer pos2 = path.get(1);
			Integer direction = getDirection(pos1, pos2);
			for(int i=2; i<path.size();i++){
				Integer pos3 = path.get(i);
				Integer d = getDirection(pos2, pos3);
				if(direction.equals(d)){
					pos2 = pos3;
				}else{
					return null;
				}
			}
		}
		
		return path;
	}
	
	/**
	 * 是否在一条直线上
	 * @param stand 站位
	 * @param target 目标位置
	 * @return
	 */
	public Boolean isLine(Integer stand, Integer target) {
		return null!=getLine(stand, target);
	}
	
	/**
	 * stand和target必须是在一条直线上，取stand到target直线上离stand最近的那个点
	 * @param stand 起点
	 * @param target 目标点
	 * @param type 0：表示离stand最近的点；1：离stand最远的点；
	 * @return 如果stand与target不在一条直线上则返回null；
	 */
	public Integer getPositionOnLineByTarget(Integer stand, Integer target) {
		List<Integer> line = getLine(stand, target);
		
		if(null==line)
			return null;
		
		List<Integer> nearList = areaForDistance(target, 1, Equal);
		nearList.retainAll(line);
		return nearList.get(0);
	}
	
	/**
	 * 
	 * @param start
	 * @param stop
	 * @return 包含启动和终点，即使stop不可到达也会返回null
	 */
	protected List<Integer> routeToPosition(Integer start, Integer stop){
		List<Integer> retList = new ArrayList<Integer>();
		List<Node> list = route(start, stop);
		for(int i=0; null!=list && i<list.size(); i++){
			Node node = list.get(i);
			retList.add(Util.pointToInteger(node._Pos.x, node._Pos.y));
		}
		return retList.isEmpty() ? null : retList;
	}
	
	/**
	 * 
	 * @param start
	 * @param stop
	 * @param moveType
	 * @return 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	protected List<Integer> routeToPosition(Integer start, Integer stop, Integer moveType) {
		List<Integer> retList = new ArrayList<Integer>();
		List<Node> list = route(start, stop, moveType);
		for(int i=0; null!=list && i<list.size(); i++){
			Node node = list.get(i);
			retList.add(Util.pointToInteger(node._Pos.x, node._Pos.y));
		}
		return retList.isEmpty() ? null : retList;
	}
	
	//---------------------- 与算法有关 ----------------------
	
	/**
	 * 获得两点之间的最短路线，不加载地形，并且start<>stop
	 * @param start 起点 
	 * @param stop 终点
	 * @return 包含启动和终点，即使stop不可到达也会返回null
	 */
	protected List<Node> route(Integer start, Integer stop) {
		return GroundUtil.route(start, stop, updateMAP(), hit);
	}
	
	/**
	 * 获得两点之间的最短路线，加载地形，并且start<>stop
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @return 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	protected List<Node> route(Integer start, Integer stop, Integer moveType) {
		List<Node> list = null;
		switch (moveType) {
		case 141:    //步行
			list = GroundUtil.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 142:    //骑行
			list = GroundUtil.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 143:    //驾驶
			list = GroundUtil.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 144:    //飞行
			list = GroundUtil.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
		case 145:    //传送
			list = GroundUtil.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
		case 146:    //潜行
			list = GroundUtil.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
			
		default:
			break;
		}
		
		return list;
	}
	
	/**
	 * 相对与目标位置的方向，stand与target位置必须是相邻的
	 * @param stand 站位
	 * @param target 目标位置
	 * @return 方向
	 */
	public Integer getDirection(Integer stand, Integer target) {
		return GroundUtil.getDirection(stand, target);
	}
	
	/**
	 * 根据方向查询相邻位置
	 * @param stand 站位
	 * @param Direction 方向
	 * @return 位置，如果越界则返回null
	 */
	public Integer getPosition(Integer stand, Integer direction) {
		Integer ret = GroundUtil.getPosition(stand, direction);
		return isOver(ret) ? null : ret;
	}
	
	/**
	 * 两侧的位置，stand与target位置必须是相邻的
	 * @param stand 站位
	 * @param direction 方向
	 * @return
	 */
	public List<Integer> twoFlanks(Integer stand, Integer direction) {
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> l = GroundUtil.twoFlanks(stand, direction);
		for(Integer position : l){
			if(!isOver(position))
				list.add(position);
		}
		
		return list;
	}
	
	/**
	 * 从起点到终点的最短路径，根据移动范围来获取路径上的那个点
	 * @param stand 当前位置
	 * @param dest 目标位置
	 * @param step 步数
	 * @param moveType 移动类型
	 * @param control 敌我状态
	 * @return
	 */
	public Integer getPointByWay(Integer stand, Integer dest, Integer step, Integer moveType) {
		List path = route(stand, dest, moveType);
		for(int i=0;i<path.size();i++){
			Node node = (Node) path.get(i);
			Integer pos = Util.pointToInteger(node._Pos.x, node._Pos.y);
			if(dest.equals(pos)         //在移动范围内
			|| node.sourcePoint>step)   //在移动范围外
				return pos;
		}
		return null;
	}
	
	/**
	 * 判断是否超出边界
	 * @param position
	 * @return
	 */
	protected Boolean isOver(Integer position){
		Integer [] ps = Util.integerToPoint(position);
		if(ps[0]<1||ps[0]>getXBorder())
			return true;
		if(ps[1]<1||ps[1]>getYBorder())
			return true;
		return false;
	}
	
	/**
	 * 创建一个初始化了的MAP
	 * @return 
	 */
	private int[][] createMAP(){
		int[][] MAP = new int[getXBorder()+2][getYBorder()+2];
		for(int i=0;i<getXBorder()+2;i++)
			for(int j=0;j<getYBorder()+2;j++)
				MAP[i][j] = -1;
		return MAP;
	}
	
	/**
	 * 初始化MAP，不加载地形等信息
	 */
	private int[][] updateMAP(){
		int[][] MAP = createMAP();
		
		List<Integer> m = GroundUtil.rectangle(Integer.valueOf(1+Util.space+1), Integer.valueOf(getXBorder()+Util.space+getYBorder()));
		
		for(Integer i : m){
			String [] is = i.toString().split(Util.space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			MAP[ix][iy] = 1;
		}
		
		return MAP;
	}
	
	/**
	 * 初始化MAP，并加载地形、站位等信息
	 * @param landform 地形
	 * @param stance 站位
	 * @param foe 敌我关系
	 * @param moveType 移动类型
	 * @return
	 */
	private int[][] updateMAP(Boolean landform, Boolean stance, Boolean foe, Integer moveType) {
		int[][] MAP = updateMAP();
		
		/*
		 * 加载地形
		 */
		if(landform){
			List<Integer> m = GroundUtil.rectangle(Integer.valueOf(1+Util.space+1), Integer.valueOf(getXBorder()+Util.space+getYBorder()));
			for(Integer i : m){
				String [] is = i.toString().split(Util.space);
				Integer ix = Integer.valueOf(is[0]);
				Integer iy = Integer.valueOf(is[1]);
				Place p = getPlace(i);
				MAP[ix][iy] = LandformEffect.getConsume(moveType, p.getLandform());
			}
		}
		
		/*
		 * 站位
		 */
		if(stance){
			List<Integer> notEmptyList = queryPositionList(false);
			
			for(Integer position : notEmptyList){
				Integer x = Util.integerToPoint(position)[0];
				Integer y = Util.integerToPoint(position)[1];
				MAP[x][y] = balk;
			}
		}
		
		if(foe){
			List<Integer> pList = new ArrayList<Integer>();         //敌方单位的站位，友方允许穿过
			List<Integer> nList = new ArrayList<Integer>();         //敌方单位附近1个单元格
			
			Player control = getCorpsList().get(0).getPlayer().getContext().getControlPlayer();
			List<Corps> cList = getCorpsList(control);
			List<Corps> eList = getCorpsList();            
			eList.removeAll(cList);                                  //非友方单位
			
			for(Corps corps : eList){
				pList.add(corps.getPosition());
				
				List<Integer> list = areaForDistance(corps.getPosition(), 1, Ground.Contain);
				list.removeAll(nList);
				nList.addAll(list);
			}
			
			for(Integer p : nList){
				Integer [] point = Util.integerToPoint(p);
				if(-1!=MAP[point[0]][point[1]])
					MAP[point[0]][point[1]] += 1;
			}
			
			for(Integer p : pList){
				Integer [] point = Util.integerToPoint(p);
				MAP[point[0]][point[1]] = balk;
			}
		}
		
		return MAP;
	}

}
