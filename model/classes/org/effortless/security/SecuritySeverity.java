package org.effortless.security;

public enum SecuritySeverity {
	ERROR(2),
	NULL(1);

    protected final int priority;   // in kilograms

    SecuritySeverity(int priority) {
        this.priority = priority;
    }

    public int getPriority () {
    	return this.priority;
    }
	
}
