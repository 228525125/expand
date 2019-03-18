package org.cx.game.widget.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IRecover;
import org.cx.game.widget.AbstractControlQueue;

public abstract class AbstractProcess implements IIntercepter, IRecover {

	private Integer waitBout = 0;
	private Integer beginBout = 0;
	private Integer curBout = 0;
	private Boolean processing = false;
	
	private List<Map<IInterceptable, IIntercepter>> resetList = new ArrayList<Map<IInterceptable, IIntercepter>>();
	
	private Object owner = null;
	private AbstractControlQueue queue = null;
	
	public AbstractProcess(AbstractControlQueue queue, Object owner) {
		// TODO Auto-generated constructor stub
		this.owner = owner;
		this.queue = queue;
	}
	
	public void begin() {
		this.beginBout = queue.getBout();
		this.curBout = queue.getBout();
		this.processing = true;
		recordIntercepter(queue.getAddBoutAction(), this);
	}
	
	public Integer getRemainBout() {
		if(processing)
			return this.waitBout-(this.curBout-this.beginBout);
		else
			return 0;
	}
	
	public void setWaitBout(Integer waitBout) {
		this.waitBout = waitBout;
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "action";
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		this.curBout = queue.getBout();
	}
	
	public Object getOwner(){
		return this.owner;
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return IIntercepter.Order_Default;
	}

	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Current;
	}
	
	public void stop(){
		this.processing = false;
		resetIntercepter();
	}
	
	public void resetIntercepter() {
		// TODO Auto-generated method stub
		resetIntercepter(IIntercepter.Level_Current);
	}
	
	@Override
	public void resetIntercepter(Integer level) {
		// TODO Auto-generated method stub
		for(Map<IInterceptable, IIntercepter> map : resetList){
			for(Entry<IInterceptable, IIntercepter> entry : map.entrySet()){
				IInterceptable interceptable = entry.getKey();
				IIntercepter intercepter = entry.getValue();	
				if(level.equals(intercepter.getLevel()))
					interceptable.deleteIntercepter(intercepter);
			}
		}
	}
	
	public void recordIntercepter(IInterceptable interceptable, IIntercepter intercepter){
		interceptable.addIntercepter(intercepter);
		Map entry = new HashMap<IInterceptable, IIntercepter>();
		entry.put(interceptable, intercepter);
		resetList.add(entry);
	}

}
