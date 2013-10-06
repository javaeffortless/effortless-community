package org.effortless.security.resources;

import java.util.ArrayList;
import java.util.List;

import org.effortless.security.Resource;

public abstract class ListResourceSet<Type extends ListResourceSet<Type>> extends AbstractResourceSet implements ResourceSet {

	public abstract boolean contains(Resource resource);

	protected List<ResourceSet> items;
	
	protected void initiateItems () {
		this.items = null;
	}
	
	protected List<ResourceSet> getItems () {
		return this.items;
	}
	
	protected void setItems (List<ResourceSet> newValue) {
		this.items = newValue;
	}
	
	public ListResourceSet<Type> add (ResourceSet resourceSet) {
		if (resourceSet != null) {
			this.items = (this.items != null ? this.items : new ArrayList<ResourceSet>());
			this.items.add(resourceSet);
		}
		return this;
	}

	protected List<ListResourceSet<?>> list;
	
	protected ListResourceSet<Type> _doAdd (ResourceSet resourceSet) {
		if (resourceSet != null) {
			if (this.list != null) {
				this.list.get(this.list.size() - 1).add(resourceSet);
			}
			else {
				add(resourceSet);
			}
		}
		return this;
	}

	protected ListResourceSet<Type> _doAdd (ListResourceSet<?> resourceSet) {
		if (resourceSet != null) {
			this.list = (this.list != null ? this.list : new ArrayList<ListResourceSet<?>>());
			this.list.add(resourceSet);

			add(resourceSet);
		}
		return this;
	}

	
	public ListResourceSet<Type> includeDeleteInner(String module) {
		return _doAdd(new IncludeDeleteInnerResourceSet(module));
	}
	
	public ListResourceSet<Type> subtract() {
		return _doAdd(new SubtractResourceSet());
	}

	public ListResourceSet<Type> addition() {
		return _doAdd(new AdditionResourceSet());
	}

	public ListResourceSet<Type> module(String module, String exclude) {
		return _doAdd(new ModuleResourceSet(module, exclude));
	}

	public ListResourceSet<Type> end() {
		int size = (this.list != null ? this.list.size() : 0);
		if (size > 0) {
			this.list.remove(size - 1);
		}
		return this;
	}

	public ListResourceSet<Type> operation(String operation) {
		return _doAdd(new OperationResourceSet(operation));
	}

	public ListResourceSet<Type> operation(String operation, String module) {
		return _doAdd(new OperationResourceSet(operation, module));
	}

	public ListResourceSet<Type> menu(String module) {
		return _doAdd(new MenuResourceSet(module));
	}

	public ListResourceSet<Type> readOperations(String module) {
		return _doAdd(new ReadOperationsResourceSet(module));
	}

	public ListResourceSet<Type> operation(String operation, String module, boolean excludeViews) {
		return _doAdd(new OperationResourceSet(operation, module, excludeViews));
	}
	
	
}
