package org.cx.game.widget.treasure;

import java.util.Map.Entry;

import org.cx.game.exception.OperatingException;
import org.cx.game.tools.CommonIdentifier;
import org.cx.game.tools.Util;

public class ResourceHelper {

	
	/**
	 * 将字符串表示的资源信息，转换为相应对象
	 * @param resString
	 * @return
	 */
	public static Resource stringToResource(String resString){
		String [] res = resString.split(",");
		return new Mineral(Integer.valueOf(res[0]), Integer.valueOf(res[1]), Integer.valueOf(res[2]), Integer.valueOf(res[3]));
	}

	/**
	 * 根据funType，对r1和r2进行函数运算，并返回结果
	 * @param funType 函数类型
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static Resource operating(Integer funType, Resource r1, Resource r2) {
		Resource ret = null;
		switch (funType) {
		case Util.Sum:
			try {
				ret = sum(r1, r2);
			} catch (OperatingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case Util.Sub:
			try {
				ret = sub(r1, r2);
			} catch (OperatingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
				
		default:
			break;
		}
		
		return ret;
	}
	
	public static Resource operating(Integer funType, Resource r1, Integer number) {
		Resource ret = null;
		switch (funType) {
		case Util.Mul:
			try {
				ret = multiplicative(r1, number);
			} catch (OperatingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			break;
		}
		
		return ret;
	}
	
	/**
	 * 两种资源相加，这里要保证r1和r2同属一种类型
	 * @param r1
	 * @param r2
	 * @return r1和r2不能为空，并且必须同属一种类型，否则返回null
	 */
	public static Resource sum(Resource r1, Resource r2) throws OperatingException {
		Resource ret = null;
		if(null!=r1 && null!=r2 && r1.getClass().equals(r2.getClass())){
			if (r1 instanceof EmpiricValue) {
				EmpiricValue ev1 = (EmpiricValue) r1;
				EmpiricValue ev2 = (EmpiricValue) r2;
				Integer ev = ev1.getValue()+ev2.getValue();
				ret = new EmpiricValue(ev);
			}else if (r1 instanceof SkillCount) {
				SkillCount sc1 = (SkillCount) r1;
				SkillCount sc2 = (SkillCount) r2;
				Integer sc = sc1.getCount()+sc2.getCount();
				ret = new SkillCount(sc);
			}else{
				Integer gold = r1.get(CommonIdentifier.Gold)+r2.get(CommonIdentifier.Gold);
				Integer stone = r1.get(CommonIdentifier.Stone)+r2.get(CommonIdentifier.Stone);
				Integer wood = r1.get(CommonIdentifier.Wood)+r2.get(CommonIdentifier.Wood);
				Integer ore = r1.get(CommonIdentifier.Ore)+r2.get(CommonIdentifier.Ore);
				ret = new Mineral(gold,stone,wood,ore);
			}
		}else{
			throw new OperatingException();
		}
		
		return ret;
	}
	
	/**
	 * 两种资源求差，r1 - r2，这里要保证r1和r2同属一种类型；
	 * @param r1
	 * @param r2
	 * @return r1和r2不能为空，并且必须同属一种类型，否则返回null
	 */
	public static Resource sub(Resource r1, Resource r2) throws OperatingException {
		Resource ret = null;
		if(null!=r1 && null!=r2 && r1.getClass().equals(r2.getClass())){
			if (r1 instanceof EmpiricValue) {
				EmpiricValue ev1 = (EmpiricValue) r1;
				EmpiricValue ev2 = (EmpiricValue) r2;
				Integer ev = ev1.getValue() - ev2.getValue();
				ret = new EmpiricValue(ev);
			}else if (r1 instanceof SkillCount) {
				SkillCount sc1 = (SkillCount) r1;
				SkillCount sc2 = (SkillCount) r2;
				Integer sc = sc1.getCount() - sc2.getCount();
				ret = new SkillCount(sc);
			}else{
				Integer gold = r1.get(CommonIdentifier.Gold) - r2.get(CommonIdentifier.Gold);
				Integer stone = r1.get(CommonIdentifier.Stone) - r2.get(CommonIdentifier.Stone);
				Integer wood = r1.get(CommonIdentifier.Wood) - r2.get(CommonIdentifier.Wood);
				Integer ore = r1.get(CommonIdentifier.Ore) - r2.get(CommonIdentifier.Ore);
				ret = new Mineral(gold,stone,wood,ore);
			}
		}else{
			throw new OperatingException();
		}
		
		return ret;
	}
	
	/**
	 * 资源做乘法
	 * @param r1 
	 * @param number 倍数
	 * @return
	 * @throws OperatingException
	 */
	public static Resource multiplicative(Resource r1, Integer number) throws OperatingException {
		Resource ret = null;
		if(null!=r1 && null!=number){
			if (r1 instanceof EmpiricValue) {
				EmpiricValue ev1 = (EmpiricValue) r1;
				Integer ev = ev1.getValue() * number;
				ret = new EmpiricValue(ev);
			}else if (r1 instanceof SkillCount) {
				SkillCount sc1 = (SkillCount) r1;
				Integer sc = sc1.getCount() * number;
				ret = new SkillCount(sc);
			}else{
				Integer gold = r1.get(CommonIdentifier.Gold) * number;
				Integer stone = r1.get(CommonIdentifier.Stone) * number;
				Integer wood = r1.get(CommonIdentifier.Wood) * number;
				Integer ore = r1.get(CommonIdentifier.Ore) * number;
				ret = new Mineral(gold,stone,wood,ore);
			}
		}else{
			throw new OperatingException();
		}
		
		return ret;
	}
	
	/**
	 * 绝对值小于，r1<r2，例如判断CallConsume时，会用到；
	 * @param r1
	 * @param r2
	 * @return
	 */
	public static Boolean absoluteLessThan(Resource r1, Resource r2) {
		Boolean ret = true;
		for(Entry<Integer,Integer> entry : r1.entrySet()){
			Integer r1Type = entry.getKey();
			Integer r1Value = Math.abs(entry.getValue());
			Integer r2Value = Math.abs(r2.get(r1Type));
			if(r1Value<r2Value){
				ret = true;
			}else{
				ret = false;
				break;
			}
		}
		return ret;
	}
}
