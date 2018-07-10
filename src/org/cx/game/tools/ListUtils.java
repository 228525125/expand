package org.cx.game.tools;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

	public static <T> List<T> filter(List<T> list, IListFilter<T> filter) {
		List<T> ret = new ArrayList<T>();
		
		for(T t : list){
			if(filter.content(t))
				ret.add(t);
		}
		return ret;
	}
}
