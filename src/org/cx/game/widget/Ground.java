package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CellularDistrict;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.IListFilter;
import org.cx.game.tools.ListUtils;
import org.cx.game.tools.Node;
import org.cx.game.tools.SpaceArithmetic;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.AbstractBuilding;
 
public abstract class Ground extends AbstractGround {
	
	private String imagePath = "";                                     //背景图片
	
	private Map<Integer, AbstractBuilding> buildingMap = new HashMap<Integer, AbstractBuilding>();   //位置 - 建筑
	private Map<AbstractBuilding, Integer> buildingIsTroop = new HashMap<AbstractBuilding, Integer>();
	
	private List<Place> placeList = new ArrayList<Place>();
	
	public Ground(Integer id, String name, Integer xBorder, Integer yBorder, String imagePath) {
		super(id, name, xBorder, yBorder);
		// TODO Auto-generated constructor stub
		
		this.imagePath = imagePath;
		
		for(int i=1;i<getXBorder()+1;i++){
			for(int j=1;j<getYBorder()+1;j++){
				Integer curPos = Integer.valueOf(""+i+SpaceArithmetic.space+j);
				Place place = new Place(this, curPos);
				place.setLandform(AbstractPlace.Landform_Sward);
				addPlace(place);
				placeList.add(place);
			}
		}
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
	
	//----------------------- NPC ---------------------
		
	/*public void setCorpsData(List<String> corpsData){
		for(int i=0;null!=corpsData && i<corpsData.size();i++){
			XmlConfigureHelper.map_corpsData_corps(corpsData.get(i), this);
		}
	}*/
	
	//----------------------- Building ------------------------
	
	public void setBuildingData(List<String> buildings) {
		
		for(int i=0;null!=buildings && i<buildings.size();i++){
			XmlConfigureHelper.map_buildingData_building(buildings.get(i), this);
		}
	}
	
	public Map<AbstractBuilding, Integer> getBuildingIsTroop() {
		return buildingIsTroop;
	}
	
	@Override
	public void placementBuilding(Integer position, AbstractBuilding building) {
		// TODO Auto-generated method stub
		super.placementBuilding(position, building);
		
		buildingMap.put(position, building);
		
	}
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @return
	 */
	public List<Integer> getBuildingPosition(AbstractPlayer player) {
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
	public List<Integer> getBuildingPosition(AbstractPlayer player,
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
	
	/**
	 * 获取建筑的坐标
	 * @param player 玩家
	 * @param buildingType 建筑类型
	 * @param level 大于等于这个等级
	 * @return
	 */
	public List<Integer> getBuildingPosition(AbstractPlayer player, Integer buildingType, Integer level) {
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
	public void captureBuilding(AbstractPlayer player, AbstractBuilding building) {
		// TODO Auto-generated method stub
		building.setPlayer(player);
	}
	
	/**
	 * 获取建筑物
	 * @param player 玩家
	 * @return
	 */
	public List<AbstractBuilding> getBuildingList(AbstractPlayer player) {
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
	public List<AbstractBuilding> getBuildingList(AbstractPlayer player, Integer type) {
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
			AbstractPlace place = getPlace(position);
			if(place.isEmpty())
				entrance = position;
		}
		
		return entrance;
	}
	
	//----------------------- Corps ----------------------
	
	/**
	 * 移动到指定位置
	 * @param corps 生物
	 * @param position 指定位置
	 * @param type 移动类型
	 */
	public List<Integer> move(AbstractCorps corps, Integer position, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> route = new ArrayList<Integer>();
		Corps c = (Corps) corps;
		List<Node> path = route(getPosition(c), position, type);
		
		if(null==path)
			return route;
		
		AbstractCorps lastCorps = null;
		Integer lastPosition = c.getPosition();
		route.add(lastPosition);
		
		for(int i=1;i<path.size();i++){
			
			/*
			 * moveable状态可能会在place.in中改变
			 */
			if(!c.getMove().getMoveable())
				break;
			
			Node node = (Node) path.get(i);
			
			AbstractPlace place = getPlace(lastPosition);
			place.out();
			if(null!=lastCorps)
				place.in(lastCorps);
			
			Integer curPosition = SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y);
			
			place = getPlace(curPosition);
			lastCorps = place.out();
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
	public void translocation(AbstractCorps corps, Integer position) {
		if(removeCorps(corps))
			placementCorps(position, corps);
	}
	
	/**
	 * 获取某玩家战场上所有生物
	 * @param player
	 * @return
	 */
	public List<AbstractCorps> getCorpsList(AbstractPlayer player) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ret = new ArrayList<AbstractCorps>();
		
		for(AbstractCorps corps : getCorpsList()){
			if(player.equals(corps.getPlayer()))
				ret.add(corps);
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
	public List<AbstractCorps> queryCorpsList(Integer stand, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<AbstractCorps> ls = new ArrayList<AbstractCorps>();
		
		List<Integer> list = areaForDistance(stand, step, type);
		for(Integer position : list){
			AbstractCorps corps = getCorps(position);
			if(null!=corps){
				ls.add(corps);
			}
		}
		
		return ls;
	}
	
	public List<Integer> queryRange(AbstractCorps corps, String action) {
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
	
	@Override
	public Integer distance(Integer start, Integer stop) {
		// TODO Auto-generated method stub
		return CellularDistrict.getShortPathLength(start.longValue(), stop.longValue());
	}
	
	@Override
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

	@Override
	public List<Integer> areaForDistance(Integer position, Integer step, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=1;i<getXBorder()+1;i++){
			for(int j=1;j<getYBorder()+1;j++){
				Integer curPos = Integer.valueOf(""+i+SpaceArithmetic.space+j);
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
	public List<Integer> areaForDistance(Integer position, String range,
			Integer type) {
		// TODO Auto-generated method stub
		String [] rs = range.split("-");
		List<Integer> list1 = areaForDistance(position, Integer.valueOf(rs[0])-1, AbstractGround.Contain);
		List<Integer> list2 = areaForDistance(position, Integer.valueOf(rs[1]), AbstractGround.Contain);
		list2.removeAll(list1);
		return list2;
	}
	
	@Override
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
				AbstractPlace place = getPlace(pos);
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
			retList.add(SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y));
		}
		return retList.isEmpty() ? null : retList;
	}
	
	/**
	 * 获得两点之间的最短路线，不加载地形，并且start<>stop
	 * @param start 起点 
	 * @param stop 终点
	 * @return 包含启动和终点，即使stop不可到达也会返回null
	 */
	protected abstract List<Node> route(Integer start, Integer stop);
	
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
			retList.add(SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y));
		}
		return retList.isEmpty() ? null : retList;
	}
	
	/**
	 * 获得两点之间的最短路线，加载地形，并且start<>stop
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @return 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	protected abstract List<Node> route(Integer start, Integer stop, Integer moveType);

}
