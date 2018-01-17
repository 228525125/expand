package org.cx.game.action;

public class Random {

	/**
	 * 是否触发事件
	 * @param probability 概率，以百为单位例如：15.55%
	 * @return
	 */
	public static Boolean isTrigger(Double chance){
		Integer precision = 100;
		Integer p = Integer.parseInt(new java.text.DecimalFormat("0").format(chance*precision));
		java.util.Random r = new java.util.Random();
		Integer rand = r.nextInt(100*precision);
		if(rand<=--p)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param chance 基数：100
	 * @return
	 */
	public static Boolean isTrigger(Integer chance){
		java.util.Random r = new java.util.Random();
		Integer rand = r.nextInt(100);
		if(rand<=--chance)
			return true;
		else
			return false;
	}
	
	public static Integer nextInt(Integer number){
		java.util.Random r = new java.util.Random();
		return r.nextInt(number);
	}

	public static void main(String[] args) {
		/*for(int i=0;i<50;i++)
			System.out.println((int)(Math.random()*21));
		Double d = 100*0.958;
		Integer i=0;
		i+=d.intValue();
		System.out.println(i);*/
		Integer i1 = 10;
		Integer i2 = 10;
		System.out.println(i1<=--i2);
	}
}
