package org.cx.game.widget.treasure;

import org.cx.game.tools.CommonIdentifier;

public class Mineral extends Resource {
	
	public Mineral() {
		// TODO Auto-generated constructor stub
		add(CommonIdentifier.Gold, 0);
		add(CommonIdentifier.Wood, 0);
		add(CommonIdentifier.Stone, 0);
		add(CommonIdentifier.Ore, 0);
	}
	
	public Mineral(Integer gold, Integer wood, Integer stone, Integer ore) {
		add(CommonIdentifier.Gold, gold);
		add(CommonIdentifier.Wood, wood);
		add(CommonIdentifier.Stone, stone);
		add(CommonIdentifier.Ore, ore);
	}
	
	/**
	 * 
	 * @param resourceString 格式 G/W/S/O = '0,0,0,0'
	 */
	public Mineral(String resourceString) {
		// TODO Auto-generated constructor stub
		String [] res = resourceString.split(",");
		
		add(CommonIdentifier.Gold, res.length>0 ? Integer.valueOf(res[0]) : 0);
		add(CommonIdentifier.Wood, res.length>1 ? Integer.valueOf(res[1]) : 0);
		add(CommonIdentifier.Stone, res.length>2 ? Integer.valueOf(res[2]) : 0);
		add(CommonIdentifier.Ore, res.length>3 ? Integer.valueOf(res[3]) : 0);
	}

	public Integer getGold(){
		return get(CommonIdentifier.Gold);
	}
	
	public Integer getWood(){
		return get(CommonIdentifier.Wood);
	}
	
	public Integer getStone(){
		return get(CommonIdentifier.Stone);
	}
	
	public Integer getOre(){
		return get(CommonIdentifier.Ore);
	}
}
