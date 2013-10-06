package org.effortless.core;

import java.util.ArrayList;
import java.util.List;

public class ExecutionTime extends Object {

	public ExecutionTime () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.times = null;
	}
	
	protected List<Long> times;
	
	public Long start() {
		Long result = null;
		this.times = (this.times != null ? this.times : new ArrayList<Long>());
		result = Long.valueOf(System.currentTimeMillis());
		this.times.add(Long.valueOf(result));
		return result;
	}

	public long stop() {
		Long result = null;
		int length = (this.times != null ? this.times.size() : 0);
		Long start = (length > 0 ? this.times.remove(length - 1) : null);
		result = (start != null ? Long.valueOf(System.currentTimeMillis() - start.longValue()) : null);
		return result;
	}

}
