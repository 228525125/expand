package org.cx.game.tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.widget.IGround;

/**
 * 空间算法，主要应用于战场；
 * @author chenxian
 *
 */
public class SpaceArithmetic {
	
	public static String space = "8008";                               //位置坐标间隔符
	
	public static Integer[] integerToPoint(Integer point){
		String [] points = point.toString().split(space);
		Integer x = Integer.valueOf(points[0]);
		Integer y = Integer.valueOf(points[1]);

		return new Integer[]{x,y}; 
	}
	
	/**
	 * 用于表示越界的点，isOver = true
	 */
	private static final Integer OverPoint = 110010;
	
	public static Integer pointToInteger(Integer x,Integer y){
		if(x<1 || y<1)
			return OverPoint;
		else
			return Integer.valueOf(x+space+y);
	}

	/**
	 * 相对于目标位置，六角形的任意方向，stand与target位置必须是相邻的
	 * @param stand 站位
	 * @param target 目标位置
	 * @return 方向
	 */
	public static Integer getDirection(Integer stand, Integer target) {
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
	
	/**
	 * 根据方向查询相邻位置
	 * @param stand 站位
	 * @param direction 方向
	 * @return 位置坐标
	 */
	public static Integer getPosition(Integer stand, Integer direction){
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
	
	/**
	 * 两侧的位置，stand与target位置必须是相邻的
	 * @param stand 站位
	 * @param direction 方向
	 * @return
	 */
	public static List<Integer> twoFlanks(Integer stand, Integer direction){
		List<Integer> list = new ArrayList<Integer>();
		
		Integer position  = null;
		
		switch (direction) {
		case 10:
			position = getPosition(stand, IGround.Relative_Left);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightTop);
			list.add(position);
			
			break;
		case 2:
			position = getPosition(stand, IGround.Relative_LeftTop);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_Right);
			list.add(position);
			
			break;
		case 9:
			position = getPosition(stand, IGround.Relative_LeftTop);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_LeftBottom);
			list.add(position);
			
			break;
		case 3:
			position = getPosition(stand, IGround.Relative_RightTop);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightBottom);
			list.add(position);
			
			break;
		case 8:
			position = getPosition(stand, IGround.Relative_Left);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_RightBottom);
			list.add(position);
			
			break;
		case 4:
			position = getPosition(stand, IGround.Relative_Right);
			list.add(position);
			
			position = getPosition(stand, IGround.Relative_LeftBottom);
			list.add(position);
			
			break;

		default:
			break;
		}
		
		return list;
	}
	
	/**
	 * 两点之间的矩阵，包含起点和终点
	 * @param start 左上角
	 * @param stop  右下角
	 * @return
	 */
	public static List<Integer> rectangle(Integer start, Integer stop){
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
	 * 获得两点之间的最短路线，考虑障碍物，并且start<>stop
	 * 注意，在调用该方法之前，必须调用updateMAP，之所以将两个方法分开，也是为了提高
	 * 计算效率，例如在一次route方法调用，只更新一次MAP
	 * @param start
	 * @param stop
	 * @return LinkedList<Node> 包含启动和终点，如果stop不可到达则返回null
	 */
	public static List route(Integer start, Integer stop, int[][] MAP, int[] hit){
		PathFinding pathFinding = new PathFinding(MAP,hit);
		
		Integer[] starts = integerToPoint(start);
		Integer[] stops = integerToPoint(stop);
		Point startPos = new Point(starts[0], starts[1]);
		Point stopPos = new Point(stops[0],stops[1]);
		List path = pathFinding.searchPath(startPos, stopPos);
		
		return path;
	}
	
	/**
	 * 根据圈数计算最大编号
	 * @param circleNumber 圈数，原点不算，即第一圈为7
	 * @return
	 
	private Integer getMaxSerial(Integer circleNumber){
		Integer number = 1;
    	for (int i = 1; i < circleNumber+1; i++) {
			number += i*6;
		}
    	return number;
    }*/
	
	/**
	 * 根据边界计算中心点
	 * @param xBorder x边界
	 * @param yBorder y边界
	 * @return
	 
	private Integer getCentrePoint(Integer xBorder, Integer yBorder){
		return pointToInteger(xBorder/2, yBorder/2);
    }*/
	
	//private static final Integer base = 100;
	
	/**
	 * 判断坐标是否越界，只用于初始化坐标系与序号映射时使用
	 * @param point 坐标
	 * @return
	 
	private Boolean isCoordinateOver(Integer point){
    	Integer [] ps = integerToPoint(point);
		if(ps[0]<base+1)
			return true;
		if(ps[1]<base+1)
			return true;
		return false;
    }*/
    
	/**
	 * 添加坐标系与序号的映射
	 * @param serialNumber 序号
	 * @param point 坐标
	 
    private void addCoordinateMap(Integer serialNumber, Integer point){
    	if(!isCoordinateOver(point)){
			coordinateMap.put(serialNumber, point);
    		serialNumberMap.put(point, serialNumber);
		}
    }*/
	
	/**
	 * 初始化坐标系与序号的映射
	 * @param centrePoint 原点/中心点
	 * @param circleNumber 圈数，原点不算，即第一圈为7
	 
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
    }*/
}
