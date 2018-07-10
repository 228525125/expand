package org.cx.game.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.AbstractSkill;
import org.cx.game.magic.skill.ActiveSkill;
import org.cx.game.tools.CellularDistrict;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.tools.IListFilter;
import org.cx.game.tools.ListUtils;
import org.cx.game.tools.Node;
import org.cx.game.tools.SpaceArithmetic;
import org.cx.game.tools.XmlConfigureHelper;
import org.cx.game.widget.building.AbstractBuilding;
import org.cx.game.widget.building.AbstractOption;
import org.cx.game.widget.treasure.Treasure;

import com.oracle.jrockit.jfr.ValueDefinition;

public class HoneycombGround extends AbstractGround {

	private Integer id = null;
	private String imagePath = "";                                     //背景图片

	private int[] hit = new int []{-1};                                  //-1表示障碍物
	private Map<Integer, AbstractBuilding> buildingMap = new HashMap<Integer, AbstractBuilding>();   //位置 - 建筑
	private List<String> buildingData = new ArrayList<String>();
	private List<String> npcData = new ArrayList<String>();
	private Map<AbstractBuilding, Integer> buildingIsTroop = new HashMap<AbstractBuilding, Integer>();
	private Map<Corps, Integer> corpsIsTroop = new HashMap<Corps, Integer>();
	
	private List<Place> placeList = new ArrayList<Place>();
	
	public HoneycombGround(Integer id, String name, Integer xBorder, Integer yBorder, String imagePath) {
		// TODO Auto-generated constructor stub
		super(id, name, xBorder, yBorder);
		
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
	
	@Override
	public void afterConstruct() {
		// TODO Auto-generated method stub
		for(String data : getBuildingData()){            //加载建筑
			XmlConfigureHelper.map_buildingData_building(data, this);
		}
	}

	//----------------------- Corps ----------------------
	
	@Override
	public List<Integer> move(AbstractCorps corps, Integer position, Integer type) {
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
			
			AbstractPlace place = getPlace(original);
			place.out();
			place = getPlace(SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y));
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
	
	@Override
	public void placementCorps(Integer position, AbstractCorps corps) {
		// TODO Auto-generated method stub
		super.placementCorps(position, corps);
		
		Corps c = (Corps) corps;
		c.getDeath().setStatus(CommonIdentifierE.Death_Status_Live);
	}
	
	public Map<Corps, Integer> getCorpsIsTroop() {
		return corpsIsTroop;
	}
	
	//------------------------ Corps End ------------------------
	
	//------------------------ Area ----------------------------
	
	@Override
	public void setTroopList(List<Integer> troopList) {
		// TODO Auto-generated method stub
		super.setTroopList(troopList);
	}
	
	@Override
	public void setEntranceMap(Map<Integer, Integer> entranceMap) {
		// TODO Auto-generated method stub
		super.setEntranceMap(entranceMap);
	}
	
	//----------------------- Area End --------------------------
	
	//----------------------- NPC ---------------------

	public List<String> getNpcData() {
		// TODO Auto-generated method stub
		return this.npcData;
	}
		
	public void setNpcData(List<String> npcData){
		this.npcData = npcData;
	}
		
	//------------------------ NPC End ----------------
	
	//----------------------- Building ------------------------
	
	public List<String> getBuildingData(){
		return this.buildingData;
	}
	
	public void setBuildingData(List<String> buildings) {
		this.buildingData = buildings;
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
	
	//----------------------- Building End------------------------
	
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
	
	public void setLandformMap(Map<Integer, Integer> landformMap) {
		// TODO Auto-generated method stub
		super.setLandformMap(landformMap);
	}
	
	//----------------------- Landform End -----------------------
	
	//----------------------- TreasureMap ------------------------
	@Override
	public void setTreasureMap(Map<Integer, Treasure> treasureMap) {
		// TODO Auto-generated method stub
		super.setTreasureMap(treasureMap);
	}
	
	//----------------------- TreasureMap End ------------------------
	
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
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Walk, corps.getPlayer());
				//distance(280083, 280083, IMove.Type_Walk);
				break;
			case 142:    //骑行
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Equitation, corps.getPlayer());
				break;
			case 143:    //驾驶
				positionList = areaForDistance(corps.getPosition(), step, Contain, CommonIdentifierE.Move_Type_Drive, corps.getPlayer());
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
	public List<Integer> queryRange(AbstractSkill skill, String action){
		List<Integer> positionList = new ArrayList<Integer>();
		if(CommonIdentifierE.Command_Query_Conjure.equals(action) && skill.getOwner() instanceof AbstractCorps){
			positionList = ((ActiveSkill) skill).getConjureRange();
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
	
	@Override
	public Integer distance(Integer start, Integer stop) {
		// TODO Auto-generated method stub
		return CellularDistrict.getShortPathLength(start.longValue(), stop.longValue());
	}
	
	private static final Integer Distance_Max = 9999;
	
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
		
	@Override
	public Integer getDirection(Integer stand, Integer target) {
		// TODO Auto-generated method stub
		return SpaceArithmetic.getDirection(stand, target);
	}
	
	@Override
	public Integer getPosition(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		return SpaceArithmetic.getPosition(stand, direction);
	}
	
	@Override
	public List<Integer> twoFlanks(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> l = SpaceArithmetic.twoFlanks(stand, direction);
		for(Integer position : l){
			if(!isOver(position))
				list.add(position);
		}
		
		return list;
	}
	
	@Override
	public Integer getPointByWay(Integer stand, Integer dest, Integer step,
			Integer moveType, AbstractPlayer control) {
		// TODO Auto-generated method stub
		List path = route(stand, dest, moveType, control);
		for(int i=0;i<path.size();i++){
			Node node = (Node) path.get(i);
			Integer pos = SpaceArithmetic.pointToInteger(node._Pos.x, node._Pos.y);
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
	private Boolean isOver(Integer position){
		Integer [] ps = SpaceArithmetic.integerToPoint(position);
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
		
		List<Integer> m = SpaceArithmetic.rectangle(Integer.valueOf(1+SpaceArithmetic.space+1), Integer.valueOf(getXBorder()+SpaceArithmetic.space+getYBorder()));
		
		for(Integer i : m){
			String [] is = i.toString().split(SpaceArithmetic.space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			MAP[ix][iy] = 1;
		}
		
		return MAP;
	}
	
	/**
	 * 更新MAP加载地形；
	 * 为什么是独立为一个方法，主要考虑hide状态下系统只需加载地形
	 * @param moveType 移动类型
	 * @return
	 */
	private int[][] updateMAP_Landform(Integer moveType){
		int[][] MAP = createMAP();
		
		List<Integer> m = SpaceArithmetic.rectangle(Integer.valueOf(1+SpaceArithmetic.space+1), Integer.valueOf(getXBorder()+SpaceArithmetic.space+getYBorder()));
		
		for(Integer i : m){
			String [] is = i.toString().split(SpaceArithmetic.space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			AbstractPlace p = getPlace(i);
			MAP[ix][iy] = LandformEffect.getConsume(moveType, p.getLandform());
		}
		
		return MAP;
	}
	
	/**
	 * 加载地形和战场单位的站位情况；
	 * @param moveType 移动类型
	 * @param control 阵营
	 * @return
	 */
	private int[][] updateMAP_Stance(Integer moveType, AbstractPlayer control){
		int [][] MAP = updateMAP_Landform(moveType);
		
		List<Integer> pList = new ArrayList<Integer>();         //敌方单位的站位，友方允许穿过
		List<Integer> nList = new ArrayList<Integer>();         //敌方单位附近1个单元格
		
		List<AbstractCorps> cList = getCorpsList(control, CommonIdentifierE.Death_Status_Live);
		List<AbstractCorps> eList = getCorpsList(CommonIdentifierE.Death_Status_Live);            //非友方单位
		eList.removeAll(cList);
		
		for(AbstractCorps corps : eList){
			pList.add(corps.getPosition());
			
			List<Integer> list = areaForDistance(corps.getPosition(), 1, AbstractGround.Contain);
			list.removeAll(nList);
			nList.addAll(list);
		}
		
		for(Integer p : nList){
			Integer [] point = SpaceArithmetic.integerToPoint(p);
			if(-1!=MAP[point[0]][point[1]])
				MAP[point[0]][point[1]] += 1;
		}
		
		for(Integer p : pList){
			Integer [] point = SpaceArithmetic.integerToPoint(p);
			MAP[point[0]][point[1]] = -1;
		}
		
		return MAP;
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
		return SpaceArithmetic.route(start, stop, updateMAP(), hit);
	}
	
	/**
	 * 
	 * @param start
	 * @param stop 
	 * @param moveType 移动类型
	 * @param control 当前控制者，用于计算敌人的站位
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达，即MAP中为-1，则返回null
	 */
	private List route(Integer start, Integer stop, Integer moveType, AbstractPlayer control){
		return SpaceArithmetic.route(start, stop, updateMAP_Stance(moveType, control), hit);
	}
	
	/**
     * 根据坐标查找序号
     * @param coordinate 坐标
     * @return
     
    private Integer getSerialNumber(Integer coordinate){
    	Integer [] ps = integerToPoint(coordinate);
    	Integer point = pointToInteger(base+ps[0], base+ps[1]);
    	return serialNumberMap.get(point);
    }*/
    
    /**
     * 根据序号查找坐标
     * @param serialNumber 序号
     * @return
     
    private Integer getCoordinate(Integer serialNumber){
    	Integer point = coordinateMap.get(serialNumber);
    	Integer [] ps = integerToPoint(point);
    	return pointToInteger(ps[0]%base, ps[1]%base);
    }*/
    
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
	
	/*@Override
	public Boolean removeCorps(AbstractCorps corps)
			throws RuleValidatorException {
		// TODO Auto-generated method stub
		Integer position = corps.getPosition();
		Boolean ret = super.removeCorps(corps);
		
		if(ret){
			this.emptyList.add(position);
		}
		
		return ret;
	}*/
	
	/*@Override
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
	}*/
	
	/*@Override
	public List<Corps> listForID(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<Corps> ret = new ArrayList<Corps>();
		for(AbstractCorps corps : getLivingCorpsList()){
			if(ids.contains(corps.getType()))
				ret.add((Corps)corps);
		}
		return ret;
	}*/

	/*@Override
	public List<Corps> listForID(AbstractPlayer player, List<Integer> ids) {
		// TODO Auto-generated method stub
		List<Corps> ret = new ArrayList<Corps>();
		for(AbstractCorps corps : getLivingCorpsList()){
			if(ids.contains(corps.getType()) && player.equals(corps.getPlayer()))
				ret.add((Corps)corps);
		}
		return ret;
	}*/
	
	public static void main(String[] args) {
		/*Integer x1=1,y1=1,x2=2,y2=2;
		System.out.println(Util.convertInteger(Math.sqrt(Math.pow(x1.doubleValue()-x2.doubleValue(),2)+Math.pow(y1.doubleValue()-y2.doubleValue(),2))));
		
		System.out.println(Math.abs(x1-x2)+Math.abs(y1-y2)) ;*/
		Random r = new Random();
		System.out.println(r.nextInt(10));
		
		//AbstractGround ground = new HoneycombGround(21,13,null);
		//System.out.println(ground);
	}

}
