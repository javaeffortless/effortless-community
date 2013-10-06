package org.effortless.model;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

//Implement listByName(nombre).listByApellidos(apellidos).listByFecha(fecha);
//Entidad.listBy().lk("name", nombre).lk("apellidos", apellidos).eq("fecha", fecha);
public interface Filter<ItemType extends Object> extends List<ItemType> {

	public void reset();
	
	public Boolean getPaginated();
	public void setPaginated (Boolean newValue);
	
	public Integer getPageIndex();
	public void setPageIndex (Integer newValue);
	
	public Integer getPageSize();
	public void setPageSize (Integer newValue);
	
	public String getOrderBy();
	public void setOrderBy (String newValue);
	
	public Boolean getDeleted();
	public void setDeleted (Boolean newValue);
	
	public Integer getTotalPages ();
	
	public void setTotalPages (Integer newValue);
	
	public Integer getSize ();

	public void previousPage ();
	
	public void nextPage ();
	
	public Filter<ItemType> batch (EntityProcess<ItemType, ?> process);

	public Filter<ItemType> persistAll (EntityProcess<ItemType, ?> process);
	
	public Filter<ItemType> deleteAll (EntityProcess<ItemType, Boolean> process);
	
	public Filter<ItemType> eraseAll (EntityProcess<ItemType, Boolean> process);
	

	public Map<String, Object> toMap ();
	
	public List<ItemType> listPage ();
	
	public Filter<ItemType> offset (int index);

	public Filter<ItemType> limit (int index);
	
	public Filter<ItemType> sortBy (String order);
	
	public Filter<ItemType> eq (String name, Object param);

	public Filter<ItemType> eq (String name, Boolean param);
	
	public Filter<ItemType> eqBooleanDouble (String name, Boolean valueTrue, Boolean valueFalse);

	public Filter<ItemType> ne (String name, Object param);

	public Filter<ItemType> lt (String name, Object param);

	public Filter<ItemType> le (String name, Object param);

	public Filter<ItemType> gt (String name, Object param);
	
	public Filter<ItemType> ge (String name, Object param);
	
	public Filter<ItemType> lk (String name, Object param);
	
	public Filter<ItemType> nlk (String name, Object param);

	public Filter<ItemType> ilk (String name, Object param);
	
	public Filter<ItemType> nilk (String name, Object param);
	
	public Filter<ItemType> bt (String name, Object lo, Object hi);

	public Filter<ItemType> nbt (String name, Object lo, Object hi);

	public Filter<ItemType> isTrue (String name);

	public Filter<ItemType> isFalse (String name);

	public Filter<ItemType> isZero (String name);

	public Filter<ItemType> isNotZero (String name);

	public Filter<ItemType> isPositive (String name);

	public Filter<ItemType> isNegative (String name);
	
	public Filter<ItemType> in (String name, Object... param);

	public Filter<ItemType> in (String name, Collection<?> param);
	
	public Filter<ItemType> nin (String name, Object... param);

	public Filter<ItemType> nin (String name, Collection<?> param);
	
	public Filter<ItemType> isEmpty (String name);
	
	public Filter<ItemType> isNotEmpty (String name);
	
	public Filter<ItemType> isNotNull (String name);
	
	public Filter<ItemType> isNull (String name);

	
	public Filter<ItemType> sizeEq (String name, int size);

	public Filter<ItemType> sizeGe(String name, int size);

	public Filter<ItemType> sizeGt(String name, int size);

	public Filter<ItemType> sizeLe(String name, int size);

	public Filter<ItemType> sizeLt(String name, int size);

	public Filter<ItemType> sizeNe(String name, int size);

	
	public boolean exists ();
	
	public Filter<ItemType> negative ();
	
	public Filter<ItemType> add (Criterion criterion);
	
	public Filter<ItemType> and ();
	
	public Filter<ItemType> or ();
	
	public Filter<ItemType> nor ();

	public Filter<ItemType> nand ();
	
	public Filter<ItemType> end ();
	
	public <T extends Entity<T>> Filter<T> link (Class<T> clazz, String name);
	
	
	public ItemType first ();
	
	public ItemType second ();

	public ItemType third ();

	public ItemType antepenultimate ();
	
	public ItemType penultimate ();
	
	public ItemType last ();
	
	public ItemType nth (int index);
	
	
	public Number min (String name);
	
	public Number max (String name);
	
	public Number sum (String name);
	
	public Number avg (String name);
	
	public Number count();
	
	public Filter<ItemType> reverse ();

	
	
	
	
	
	public Filter<ItemType> eq (String name, Filter<?> param);
	public Filter<ItemType> ne (String name, Filter<?> param);
	public Filter<ItemType> lt (String name, Filter<?> param);
	public Filter<ItemType> le (String name, Filter<?> param);
	public Filter<ItemType> gt (String name, Filter<?> param);
	public Filter<ItemType> ge (String name, Filter<?> param);
	public Filter<ItemType> lk (String name, Filter<?> param);
	public Filter<ItemType> nlk (String name, Filter<?> param);
	public Filter<ItemType> ilk (String name, Filter<?> param);
	public Filter<ItemType> nilk (String name, Filter<?> param);
	public Filter<ItemType> bt (String name, Filter<?> lo, Filter<?> hi);
	public Filter<ItemType> nbt (String name, Filter<?> lo, Filter<?> hi);
	public Filter<ItemType> in (String name, Filter<?> param);
	public Filter<ItemType> nin (String name, Filter<?> param);
	public Filter<ItemType> sizeEq (String name, Filter<?> size);
	public Filter<ItemType> sizeGe(String name, Filter<?> size);
	public Filter<ItemType> sizeGt(String name, Filter<?> size);
	public Filter<ItemType> sizeLe(String name, Filter<?> size);
	public Filter<ItemType> sizeLt(String name, Filter<?> size);
	public Filter<ItemType> sizeNe(String name, Filter<?> size);

	public void addPropertyChangeListener (PropertyChangeListener listener);
	
	
}
