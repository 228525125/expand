package org.cx.game.ai.policy;

import java.util.Comparator;

import org.cx.game.corps.Corps;
import org.cx.game.widget.Ground;

public class DistanceComparator implements Comparator<Corps> {

	private Integer position = null;
	
	public DistanceComparator(Integer position) {
		// TODO Auto-generated constructor stub
		this.position = position;
	}
	
	@Override
	public int compare(Corps o1, Corps o2) {
		// TODO Auto-generated method stub
		Ground ground = o1.getGround();
		Integer distance1 = ground.distance(position, o1.getPosition());
		Integer distance2 = ground.distance(position, o2.getPosition());
		return distance1.compareTo(distance2);
	}

	

}
