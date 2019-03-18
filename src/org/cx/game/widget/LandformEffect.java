package org.cx.game.widget;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cx.game.tools.PropertiesUtil;
import org.cx.game.tools.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 地形影响
 * 单位移动时的消耗，根据移动类型与地形的关系
 * 职业与地形的关系
 * @author chenxian
 *
 */
public class LandformEffect {

	private static Map<Integer, Map<Integer, Integer>> landform_MoveType = new HashMap<Integer, Map<Integer, Integer>>();  //地形与移动类型对应的消耗
	
	private static Map<Integer, Map<Integer, String>> landform_ProfessionType = new HashMap<Integer, Map<Integer, String>>();    //地形与职业类型的优劣关系
	
	private static final Integer Object_Code_Default = 40;               //<object type="MoveConsume" code="40" description="移动消耗默认">1</object>
	
	private static Element getRoot(String path) {
		SAXReader saxReader = new SAXReader();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(PropertiesUtil.getConfigure(path)));
			Document document = saxReader.read(is);
			return document.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 地形对不同职业攻击力的影响
	 * @param profession 职业
	 * @param landform 地形
	 * @return
	 */
	public static Integer getAttackAdvantage(Integer profession, Integer landform){
		Map<Integer, String> profession_atkdef = landform_ProfessionType.get(landform);
		String value = profession_atkdef.get(profession);
		if(null==value){
			value = profession_atkdef.get(Object_Code_Default);
		}
		
		return Integer.valueOf(value.split(",")[0]); 
	}
	
	/**
	 * 地形对不同职业防御力的影响
	 * @param profession 职业
	 * @param landform 地形
	 * @return
	 */
	public static Integer getDefendAdvantage(Integer profession, Integer landform){
		Map<Integer, String> profession_atkdef = landform_ProfessionType.get(landform);
		String value = profession_atkdef.get(profession);
		if(null==value){
			value = profession_atkdef.get(Object_Code_Default);
		}
		
		return Integer.valueOf(value.split(",")[1]);
	}
	
	/**
	 * 移动类型在地形上的影响
	 * @param moveType 移动类型
	 * @param landform 地形
	 * @return 移动一格的消耗，-1为不可到达
	 */
	public static Integer getConsume(Integer moveType, Integer landform){
		Map<Integer, Integer> moveType_consume = landform_MoveType.get(landform);
		Integer consume = moveType_consume.get(moveType);
		if(null==consume){
			consume = moveType_consume.get(Object_Code_Default);
		}

		return consume;
	}
	
	/*public static List<Integer> getDisable(Integer moveType, Map<Integer, AbstractPlace> ground){
		List<Integer> list = new ArrayList<Integer>();
		for(Integer pos : ground.keySet()){
			Integer landform = ground.get(pos).getLandform();
			if(Integer.valueOf(-1).equals(getConsume(moveType, landform)))
				list.add(pos);
		}
		
		return list;
	}*/
	
	static {
		Element parameterEl = getRoot("gameparameter.path");
		Element topographicAdvantageEl = parameterEl.element(XmlUtil.GameParameter_TopographicAdvantage); 
		for(Iterator it = topographicAdvantageEl.elementIterator();it.hasNext();){
			Element landformEl = (Element) it.next();
			Integer landform = Integer.valueOf(landformEl.attribute(XmlUtil.GameParameter_Landform_Code).getText());
			
			Map<Integer, Integer> moveType_consume = new HashMap<Integer, Integer>();
			
			for(Iterator itr = landformEl.elementIterator(XmlUtil.GameParameter_Object);itr.hasNext();){
				Element objEl = (Element) itr.next();
				if(XmlUtil.GameParameter_Object_Type_MoveConsume.equals(objEl.attribute(XmlUtil.GameParameter_Object_Type).getText())){
					Element moveEl = objEl;
					Integer moveType = Integer.valueOf(moveEl.attribute(XmlUtil.GameParameter_Object_Code).getText());
					Integer consume = Integer.valueOf(moveEl.getText());
					
					moveType_consume.put(moveType, consume);
				}
			}
			
			landform_MoveType.put(landform, moveType_consume);
			
			Map<Integer, String> profession_atkdef = new HashMap<Integer, String>();
			for(Iterator itr = landformEl.elementIterator(XmlUtil.GameParameter_Object);itr.hasNext();){
				Element objEl = (Element) itr.next();
				if(XmlUtil.GameParameter_Object_Type_OffensiveAndDefensiveOfProfession.equals(objEl.attribute(XmlUtil.GameParameter_Object_Type).getText())){
					Element professionEl = objEl;
					Integer professionType = Integer.valueOf(professionEl.attribute(XmlUtil.GameParameter_Object_Code).getText());
					String value = professionEl.getText();
					
					profession_atkdef.put(professionType, value);
				}
			}
			
			landform_ProfessionType.put(landform, profession_atkdef);
		}
	}
}
