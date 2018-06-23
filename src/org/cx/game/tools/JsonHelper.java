package org.cx.game.tools;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.widget.treasure.ITreasure;

public class JsonHelper {

	public static Map<String, Integer> convertForGroundLandform(Map<Integer, Integer> landformMap) {
		Map<String, Integer> landform = new HashMap<String, Integer>();
		for(Integer i : landformMap.keySet())       //加载地形
			landform.put(i.toString(), landformMap.get(i));
		return landform;
	}
}
