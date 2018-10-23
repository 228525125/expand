package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.tools.CellularDistrict;
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
		
	public void setCorpsData(List<String> corpsData){
		for(int i=0;null!=corpsData && i<corpsData.size();i++){
			XmlConfigureHelper.map_corpsData_corps(corpsData.get(i), this);
		}
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
	
	@Override
	public List<Integer> move(AbstractCorps corps, Integer position, Integer type) {
		// TODO Auto-generated method stub
		List<Integer> route = new ArrayList<Integer>();
		Corps sc = (Corps) corps;
		List path = route(getPosition(sc), position, type);
		path.remove(0);                         //路径包含起点
		
		for(int i=0;i<path.size();i++){
			
			/*
			 * moveable状态可能会在place.in中改变
			 */
			if(!sc.getMove().getMoveable())
				break;
			
			Integer original = sc.getPosition();
			Node node = (Node) path.get(i);
			
			AbstractPlace place = getPlace(original);
			place.out();
			place = getPlace(SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y));
			place.in(sc);
			
			/*
			 * 减少精力
			 */
			Integer energy = sc.getMove().getEnergy();
			System.out.println(sc.getName()+":"+energy);
			energy -= node.consume;
			System.out.println("("+node._Pos.x+","+node._Pos.y+")"+node.consume);
			sc.getMove().setEnergy(energy);
			
			/*
			 * 生成朝向信息
			 */
			if(null!=original){
				AbstractGround ground = sc.getPlayer().getContext().getGround();
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
	
	//----------------------- Landform ---------------------------
	
	public List<Integer> queryEmptyList() {
		List<Integer> emptyList = new ArrayList<Integer>();
		
		List<Place> list = ListUtils.filter(this.placeList, new IListFilter<Place>(){

			@Override
			public Boolean content(Place t) {
				// TODO Auto-generated method stub
				return t.isEmpty();
			}
			
		});
		
		for(Place place : list)
			emptyList.add(place.getPosition());
		
		return emptyList;
	}
	
	//----------------------- Landform End -----------------------

	@Override
	public List<Integer> queryRange(AbstractCorps corps, String action) {
		List<Integer> positionList = new ArrayList<Integer>();
		Corps sc = (Corps) corps;
		if(CommonIdentifierE.Command_Query_Attack.equals(action)){
			Integer range = sc.getAttack().getRange();
			/*
			 * 这里要考虑远程单位射程切换为近战时的变化
			 * 当远程单位在战场上时，如果附近有敌方单位，则只能近身攻击
			 */
			if(CommonIdentifierE.Attack_Mode_Far.equals(sc.getAttack().getMode())){
				List<Integer> list = areaForDistance(corps.getPosition(), 1, AbstractGround.Equal);
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
	
	@Override
	public List<Integer> queryRange(AbstractOption option, String action) {
		// TODO Auto-generated method stub
		List<Integer> positionList = new ArrayList<Integer>();
		if(CommonIdentifierE.Command_Query_Execute.equals(action)){
			positionList = option.getExecuteRange(this);
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
			List path = route(start, stop, moveType);
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
	public Integer distance(Integer start, Integer stop, Integer moveType, AbstractPlayer control) {
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
	
	@Override
	public List<Integer> areaForDistance(Integer position, Integer step,
			Integer type, Integer moveType, AbstractPlayer control) {
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
					if(getPlace(curPos).isEmpty())             //友方可以穿人，因此在计算路径时，不考虑友军站位，但这里就要判断
						list.add(curPos);
				break;
					
			case 1:
				if(step>=distance(position, curPos, moveType, control))
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
	 * 获得两点之间的最短路线，不加载地形，并且start<>stop
	 * @param start 起点
	 * @param stop 终点
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达则返回null
	 */
	protected abstract List route(Integer start, Integer stop);
	
	/**
	 * 获得两点之间的最短路线，加载地形，并且start<>stop
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	protected abstract List route(Integer start, Integer stop, Integer moveType);
	
	/**
	 * 获得两点之间的最短路线，加载地形和敌军位置，并且start<>stop
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @param control 当前玩家
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	protected abstract List route(Integer start, Integer stop, Integer moveType, AbstractPlayer control);

}
