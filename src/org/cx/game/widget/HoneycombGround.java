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
import org.cx.game.widget.AbstractOption;
import org.cx.game.widget.treasure.Treasure;

import com.oracle.jrockit.jfr.ValueDefinition;

public abstract class HoneycombGround extends Ground {
	
	private static int balk = -1;
	private int[] hit = new int []{balk};                                  //-1表示障碍物
	
	public HoneycombGround(Integer id, String name, Integer xBorder, Integer yBorder, String imagePath) {
		// TODO Auto-generated constructor stub
		super(id, name, xBorder, yBorder, imagePath);
	}
	
	//----------------------- Calculate ------------------------
		
	@Override
	public Integer getDirection(Integer stand, Integer target) {
		// TODO Auto-generated method stub
		return SpaceArithmetic.getDirection(stand, target);
	}
	
	@Override
	public Integer getPosition(Integer stand, Integer direction) {
		// TODO Auto-generated method stub
		Integer ret = SpaceArithmetic.getPosition(stand, direction);
		return isOver(ret) ? null : ret;
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
			Integer moveType) {
		// TODO Auto-generated method stub
		List path = route(stand, dest, moveType);
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
			List<Integer> m = SpaceArithmetic.rectangle(Integer.valueOf(1+SpaceArithmetic.space+1), Integer.valueOf(getXBorder()+SpaceArithmetic.space+getYBorder()));
			for(Integer i : m){
				String [] is = i.toString().split(SpaceArithmetic.space);
				Integer ix = Integer.valueOf(is[0]);
				Integer iy = Integer.valueOf(is[1]);
				AbstractPlace p = getPlace(i);
				MAP[ix][iy] = LandformEffect.getConsume(moveType, p.getLandform());
			}
		}
		
		/*
		 * 站位
		 */
		if(stance){
			List<Integer> notEmptyList = queryPositionList(false);
			
			for(Integer position : notEmptyList){
				Integer x = SpaceArithmetic.integerToPoint(position)[0];
				Integer y = SpaceArithmetic.integerToPoint(position)[1];
				MAP[x][y] = balk;
			}
		}
		
		if(foe){
			List<Integer> pList = new ArrayList<Integer>();         //敌方单位的站位，友方允许穿过
			List<Integer> nList = new ArrayList<Integer>();         //敌方单位附近1个单元格
			
			AbstractPlayer control = getCorpsList().get(0).getPlayer().getContext().getControlPlayer();
			List<AbstractCorps> cList = getCorpsList(control);
			List<AbstractCorps> eList = getCorpsList();            
			eList.removeAll(cList);                                  //非友方单位
			
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
				MAP[point[0]][point[1]] = balk;
			}
		}
		
		return MAP;
	}
	
	protected List<Node> route(Integer start, Integer stop){
		return SpaceArithmetic.route(start, stop, updateMAP(), hit);
	}
	
	protected List<Node> route(Integer start, Integer stop, Integer moveType) {
		List<Node> list = null;
		switch (moveType) {
		case 141:    //步行
			list = SpaceArithmetic.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 142:    //骑行
			list = SpaceArithmetic.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 143:    //驾驶
			list = SpaceArithmetic.route(start, stop, updateMAP(true,true,false,moveType), hit);
			break;
		case 144:    //飞行
			list = SpaceArithmetic.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
		case 145:    //传送
			list = SpaceArithmetic.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
		case 146:    //潜行
			list = SpaceArithmetic.route(start, stop, updateMAP(false,false,false,moveType), hit);
			break;
			
		default:
			break;
		}
		
		return list;
	}
	
	/*
	protected List route(Integer start, Integer stop, Integer moveType, AbstractPlayer control) {
		return SpaceArithmetic.route(start, stop, updateMAP_LandformAndStance(moveType, control), hit);
	}*/
	
	/**
	 * 更新MAP加载地形和站位；
	 * @param moveType 移动类型
	 * @return
	 
	private int[][] updateMAP_Landform(Integer moveType){
		int[][] MAP = createMAP();
		
		List<Integer> m = SpaceArithmetic.rectangle(Integer.valueOf(1+SpaceArithmetic.space+1), Integer.valueOf(getXBorder()+SpaceArithmetic.space+getYBorder()));
		
		for(Integer i : m){
			String [] is = i.toString().split(SpaceArithmetic.space);
			Integer ix = Integer.valueOf(is[0]);
			Integer iy = Integer.valueOf(is[1]);
			AbstractPlace p = getPlace(i);
			if(p.isEmpty())
				MAP[ix][iy] = LandformEffect.getConsume(moveType, p.getLandform());
			else
				MAP[ix][iy] = balk;
		}
		
		return MAP;
	}*/
	
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
		
		List<Integer> list = new ArrayList<Integer>();
		
		
		
		System.out.print(list.remove(0));
		
		//AbstractGround ground = new HoneycombGround(21,13,null);
		//System.out.println(ground);
	}

}
