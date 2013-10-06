package org.effortless.model.restrictions;

import java.util.List;

import org.effortless.core.ListModelException;
import org.effortless.core.ModelException;

public class AndRestriction extends CompositeRestriction {

	public AndRestriction () {
		super();
	}
	
	public void check (Object value) {
		List items = getItems();
		int length = (items != null ? items.size() : 0);
		length = (length >= 0 ? length : 0);
		if (length > 0) {
			ListModelException listModelException = new ListModelException();
			for (int i = 0; i < length; i++) {
				Restriction restriction = (Restriction)items.get(i);
				if (restriction != null) {
					try {
						restriction.check(value);
					}
					catch (ModelException e) {
						listModelException.addException(e);
					}
				}
			}
			int sizeExceptions = listModelException.sizeExceptions();
			if (sizeExceptions == 1) {
				throw listModelException.getException(0);
			}
			else if (sizeExceptions > 1) {
				throw listModelException;
			}
		}
	}

}
