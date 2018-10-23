package org.cx.game.tools;

import java.util.Comparator;

import org.cx.game.corps.Corps;
import org.cx.game.corps.Hero;
 
public class CorpsGradeComparator implements Comparator<Corps> {

	@Override
	public int compare(Corps c1, Corps c2) {
		// TODO Auto-generated method stub
		
		//c1和c2都是hero
		if (c1 instanceof Hero && c2 instanceof Hero) {
			if(c1.getStar()<c2.getStar()) {
				return 1;
			}else if(c1.getStar().equals(c2.getStar())) {
				if(c1.getUpgrade().getLevel()<c2.getUpgrade().getLevel()) {
					return 1;
				}else if(c1.getUpgrade().getLevel().equals(c2.getUpgrade().getLevel())){
					return 0;
				}else{
					return -1;
				}
			}else{
				return -1;
			}
		}
		
		//c1和c2其中一个是Hero
		if (c1 instanceof Hero || c2 instanceof Hero) {
			if (c1 instanceof Hero) {
				return -1;
			}else{
				return 1;
			}
		}
		
		//c1和c2都不是Hero
		if(c1.getStar()<c2.getStar()) {
			return 1;
		}else if(c1.getStar().equals(c2.getStar())) {
			if(c1.getUpgrade().getLevel()<c2.getUpgrade().getLevel()) {
				return 1;
			}else if(c1.getUpgrade().getLevel().equals(c2.getUpgrade().getLevel())) {
				return 0;
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}
}
