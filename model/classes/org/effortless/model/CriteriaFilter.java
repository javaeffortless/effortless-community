package org.effortless.model;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.effortless.core.ModelException;
import org.effortless.util.FileHashes;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CriteriaFilter<Type extends Entity<Type>> extends AbstractFilter<Type> implements PropertyChangeListener {

	public CriteriaFilter () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateCriteria();
		initCurrentList();
		initSize();
		this.subcriterias = null;
	}

	protected Criteria criteria;
	protected List<Criterion> criterionList;
	
	
//	protected DetachedCriteria _dCriteria;
	
	protected void initiateCriteria () {
		this.criteria = null;
	}
	
	public Criteria getCriteria () {
		return this.criteria;
	}
	
	protected void setCriteria (Criteria newValue) {
		this.criteria = newValue;
	}
	
	protected void buildCriteria () {
		if (this.criteria == null) {
			Class<?> clazz = doGetFilterClass();		
			Session session = SessionManager.loadSession(clazz);
			this.criteria = session.createCriteria(clazz);
		}
	}
	
	protected void setupCriteria (boolean list) {
		if (_checkSecurityAction("read")) {
			if (this.criteria == null) {
				buildCriteria();
				setupConditions();
			}
			
			_checkSecuritySystem();
			if (list) {
				setupPagination();
				setupOrderBy();
			}
			else {
				this.criteria.setFirstResult(0);
				this.criteria.setMaxResults(-1);
			}
		}
	}

	protected void setupDeleted () {
		if (this.deleted != null) {
			Class<?> clazz = doGetFilterClass();
			if (MarkDeleted.class.isAssignableFrom(clazz)) {
				String pName = "deleted";
				Junction andOr = null;
				if (this.deleted.booleanValue()) {
					andOr = Restrictions.conjunction();
					andOr.add(Restrictions.isNotNull(pName));
				}
				else {
					andOr = Restrictions.disjunction();
					andOr.add(Restrictions.isNull(pName));
				}
				andOr.add(Restrictions.eq(pName, this.deleted));
//				add(andOr);
				this.criteria.add(andOr);
			}
		}
	}
	
	protected void setupCriterionList () {
		if (this.criterionList != null) {
			for (Criterion criterion : this.criterionList) {
				this.criteria.add(criterion);
			}
		}
	}
	
	protected void setupConditions () {
		setupCriterionList();
		setupDeleted();
	}
	
	protected void setupPagination() {
		if (this.paginated != null && this.paginated.booleanValue()) {
			int pageSize = (this.pageSize != null ? this.pageSize.intValue() : -1);
			int pageIndex = (this.pageIndex != null ? this.pageIndex.intValue() : 0);
			int offset = (pageSize > 0 && pageIndex > 0 ? pageIndex * pageSize : 0);
			this.criteria.setFirstResult(offset);
			this.criteria.setMaxResults(pageSize);
		}
		else {
			this.criteria.setFirstResult(0);
			this.criteria.setMaxResults(-1);
		}
	}

	protected void setupOrderBy() {
		String orderBy = (this.orderBy != null ? this.orderBy.trim() : "");
		if (orderBy.length() > 0) {
			String[] orderArray = orderBy.split(",");
			int lengthOrderArray = (orderArray != null ? orderArray.length : 0);
			for (int i = 0; i < lengthOrderArray; i++) {
				String itemOrderArray = orderArray[i];
				String[] parts = (itemOrderArray != null ? itemOrderArray.split(" ") : null);
				int lengthParts = (parts != null ? parts.length : 0);
				boolean desc = (lengthParts > 1 && parts[1] != null && parts[1].trim().toLowerCase().equals("desc"));
				String propertyName = parts[0];
				Order order = (desc ? Order.desc(propertyName) : Order.asc(propertyName));
				this.criteria.addOrder(order);
			}
		}
	}

	public List<Type> listPage() {
		List<Type> result = null;
//		Boolean paginated = getPaginated();
//		if (paginated != null && paginated.booleanValue()) {
//			Integer pageIndex = getPageIndex();
//			Integer _pageSize = getPageSize();
//			int pageSize = (_pageSize != null ? _pageSize.intValue() : 0);
//			
//			if (pageSize > 0) {
//				int offset = (pageIndex != null ? pageIndex.intValue() : 0) * pageSize;
//				result = subList(offset, offset + pageSize);
//			}
//		}
//		else {
//			result = doGetCurrentList();
//		}
		result = doGetCurrentList();
		return result;
	}
	
	protected void updateFilter() {
		this.criteria = null;
		this._currentList = null;
		this._size = null;
		this._junctions = null;
		this.criterionList = null;
		this.subcriterias = null;
//		this.persistList = null;
	}
	
	public void reset () {
		updateFilter();
	}
	
	protected List<Type> _currentList;
	
	protected void initCurrentList () {
		this._currentList = null;
	}
	
	protected List<Type> doGetCurrentList () {
		if (this._currentList == null) {
//			this.criteria = null;
			setupCriteria(true);
			this.criteria.setProjection(null);
			this._currentList = this.criteria.list();
		}
		return this._currentList;
	}
	
	protected int updatePagination (int index) {
		int result = 0;
		boolean paginated = (this.paginated != null && this.paginated.booleanValue());
		if (paginated) {
			int pageSize = (this.pageSize != null ? this.pageSize.intValue() : -1);
			result = index % pageSize;
			int newPageIndex = index / pageSize;
			
			int pageIndex = (this.pageIndex != null ? this.pageIndex.intValue() : 0);
			if (pageIndex != newPageIndex) {
				Integer oldSize = this._size;
				setPageIndex(Integer.valueOf(newPageIndex));
				this._size = oldSize;
			}
		}
		else {
			result = index;
		}
		return result;
	}
	
	public Type get(int index) {
		Type result = null;
		int newIndex = updatePagination(index);
		List<Type> list = doGetCurrentList();
		result = list.get(newIndex);
		applyListener(result);
		doCallOnRead(result);
		return result;
	}

	protected void doCallOnRead(Type item) {
		if (item != null) {
			Boolean callOnRead = item.getAttribute(Boolean.class, Entity.CALL_ON_READ, Boolean.FALSE);
			if (callOnRead == null || callOnRead.booleanValue() == false) {
				item.onRead();
				item.setAttribute(Entity.CALL_ON_READ, Boolean.TRUE);
			}
		}
	}

	protected Integer _size;
	
	protected void initSize () {
		this._size = null;
	}
	
	public int size() {
		int result = 0;
		if (this._size == null) {
//			this.criteria = null;
			setupCriteria(false);
//			this.criteria.setProjection(Projections.count("id"));
			this.criteria.setProjection(Projections.rowCount());
			Number number = (Number)this.criteria.uniqueResult();
			this._size = Integer.valueOf((number != null ? number.intValue() : 0));
			this.criteria.setProjection(null);
		}
		result = this._size.intValue();
		return result;
	}

	
	
	
	protected Criteria _doGetCriteria () {
		if (this.criteria == null) {
			setupCriteria(true);
		}
		return this.criteria;
	}
	
	public Filter<Type> offset (int index) {
		setPageIndex(Integer.valueOf(index));
		return this;
	}

	public Filter<Type> limit (int limit) {
		if (limit >= 0) {
			setPageSize(Integer.valueOf(limit));
		}
		else {
			setPaginated(Boolean.FALSE);
			setPageSize(Integer.MAX_VALUE);
		}
		return this;
	}
	
	public Filter<Type> sortBy (String order) {
		if (order != null) {
			String newOrder = getOrderBy();
			newOrder = (newOrder != null ? newOrder : "");
			newOrder += (newOrder.length() > 0 && order.length() > 0 ? "," : "") + order;
			setOrderBy(order);
		}
		return this;
	}

	public Filter<Type> eqFile (String name, FileEntity param) {
		Filter<Type> result = null;
		File file = (param != null ? param.getContent() : null);
		String hash1 = (param != null ? param.getHash1() : null);
		String hash2 = (param != null ? param.getHash2() : null);
		String hash3 = (param != null ? param.getHash3() : null);
		result = eqFile(name, file, hash1, hash2, hash3);
		return result;
	}

	public Filter<Type> eqFile (String name, File param, String hash1, String hash2, String hash3) {
		if (param != null) {
			if (hash1 == null && hash2 == null && hash3 == null) {
				String[] hashes = FileHashes.getInstance().tryToHashes(param);
				hash1 = hashes[0];
				hash2 = hashes[1];
				hash3 = hashes[2];
			}
			if (hash1 != null || hash2 != null || hash3 != null) {
				startCriteria(name);
//				Criteria criteria = this.criteria.createCriteria(name);
//				criteria.add
				eqOrNull(name + ".hash1", hash1);
				eqOrNull(name + ".hash2", hash2);
				eqOrNull(name + ".hash3", hash3);
				
				Long fileSize = Long.valueOf(param.length());
				eq(name + ".size", fileSize);
				
				endCriteria(name);
			}
		}
		return this;
	}

	protected List<Criteria> subcriterias;
	
	protected static final boolean USE_ALIAS = true;
	
	protected void startCriteria(String name) {
		if (USE_ALIAS) {
			this.criteria.createAlias(name, name);
		}
		else {
			this.subcriterias = (this.subcriterias != null ? this.subcriterias : new ArrayList<Criteria>());
			Criteria subcriteria = this.criteria.createCriteria(name);
			this.subcriterias.add(subcriteria);
		}
	}

	protected void endCriteria(String name) {
		int length = (this.subcriterias != null ? this.subcriterias.size() : 0);
		if (length > 0) {
			int lastIndex = length - 1;
			this.subcriterias.remove(lastIndex);
		}
	}

	public Filter<Type> eqFile (String name, File param) {
		return eqFile(name, param, null, null, null);
	}
	
	
	public Filter<Type> eq (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.eq(name, param));
		}
		return this;
	}

	public Filter<Type> eqOrNull (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.eq(name, param));
		}
		else {
			add(Restrictions.isNotNull(name));
		}

		return this;
	}

	
	
	public Filter<Type> eq (String name, Boolean param) {
		Disjunction or = Restrictions.disjunction();
		if (param != null && param.booleanValue()) {
			or.add(Restrictions.isNotNull(name));
			or.add(Restrictions.eq(name, Boolean.TRUE));
		}
		else {
			or.add(Restrictions.isNull(name));
			or.add(Restrictions.eq(name, Boolean.FALSE));
		}
		add(or);

		return this;
	}

	public Filter<Type> eqBooleanDouble (String name, Boolean valueTrue, Boolean valueFalse) {
		boolean _valueTrue = (valueTrue != null && valueTrue.booleanValue());
		boolean _valueFalse = (valueFalse != null && valueFalse.booleanValue());
		if (_valueTrue != _valueFalse) {
			Boolean param = (_valueTrue ? Boolean.TRUE : Boolean.FALSE);
			eq(name, param);
		}
		return this;
	}
	
	protected boolean _notNull (Object param) {
		return (param != null && !"".equals(param));
	}
	
	public Filter<Type> ne (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.ne(name, param));
		}
		return this;
	}
	
	public Filter<Type> lt (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.lt(name, param));
		}
		return this;
	}

	public Filter<Type> le (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.le(name, param));
		}
		return this;
	}

	public Filter<Type> gt (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.gt(name, param));
		}
		return this;
	}
	
	public Filter<Type> ge (String name, Object param) {
		if (_notNull(param)) {
			add(Restrictions.ge(name, param));
		}
		return this;
	}
	
	public Filter<Type> lk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";
					add(Restrictions.like(name, text));
				}
			}
			catch (ClassCastException e) {
				add(Restrictions.like(name, param));
			}
		}
		return this;
	}
	
	public Filter<Type> nlk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";
					add(Restrictions.not(Restrictions.like(name, text)));
				}
			}
			catch (ClassCastException e) {
				add(Restrictions.not(Restrictions.like(name, param)));
			}
		}
		return this;
	}

	public Filter<Type> ilk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";
					add(Restrictions.ilike(name, text));
				}
			}
			catch (ClassCastException e) {
				add(Restrictions.ilike(name, param));
			}
		}
		return this;
	}
	
	public Filter<Type> nilk (String name, Object param) {
		if (_notNull(param)) {
			try {
				String text = (String)param;
				if (text.length() > 0) {
					text = "%" + text + "%";
					add(Restrictions.not(Restrictions.ilike(name, text)));
				}
			}
			catch (ClassCastException e) {
				add(Restrictions.not(Restrictions.ilike(name, param)));
			}
		}
		return this;
	}
	
	public Filter<Type> bt (String name, Object lo, Object hi) {
		if (_notNull(lo) && _notNull(hi)) {
			add(Restrictions.between(name, lo, hi));
		}
		else if (_notNull(lo) && !_notNull(hi)) {
			add(Restrictions.ge(name, lo));
		}
		else if (!_notNull(lo) && _notNull(hi)) {
			add(Restrictions.le(name, hi));
		}
		return this;
	}

	public Filter<Type> nbt (String name, Object lo, Object hi) {
		if (_notNull(lo) && _notNull(hi)) {
			add(Restrictions.not(Restrictions.between(name, lo, hi)));
		}
		else if (_notNull(lo) && !_notNull(hi)) {
			add(Restrictions.lt(name, lo));
		}
		else if (!_notNull(lo) && _notNull(hi)) {
			add(Restrictions.gt(name, hi));
		}
		return this;
	}

	public Filter<Type> isTrue (String name) {
		eq(name, Boolean.TRUE);
		return this;
	}

	public Filter<Type> isFalse (String name) {
		eq(name, Boolean.FALSE);
		return this;
	}

	public Filter<Type> isZero (String name) {
		add(Restrictions.eq(name, Integer.valueOf(0)));
		return this;
	}

	public Filter<Type> isNotZero (String name) {
		add(Restrictions.ne(name, Integer.valueOf(0)));
		return this;
	}

	public Filter<Type> isPositive (String name) {
		add(Restrictions.gt(name, Integer.valueOf(0)));
		return this;
	}

	public Filter<Type> isNegative (String name) {
		add(Restrictions.lt(name, Integer.valueOf(0)));
		return this;
	}
	
	public Filter<Type> in (String name, Object... param) {
		if (param != null && param.length > 0) {
			add(Restrictions.in(name, param));
		}
		return this;
	}

	public Filter<Type> in (String name, Collection<?> param) {
		if (param != null && param.size() > 0) {
			add(Restrictions.in(name, param));
		}
		return this;
	}
	
	public Filter<Type> nin (String name, Object... param) {
		if (param != null && param.length > 0) {
			add(Restrictions.not(Restrictions.in(name, param)));
		}
		return this;
	}

	public Filter<Type> nin (String name, Collection<?> param) {
		if (param != null && param.size() > 0) {
			add(Restrictions.not(Restrictions.in(name, param)));
		}
		return this;
	}

	public Filter<Type> isEmpty (String name) {
		add(Restrictions.isEmpty(name));
		return this;
	}
	
	public Filter<Type> isNotEmpty (String name) {
		add(Restrictions.isNotEmpty(name));
		return this;
	}
	
	public Filter<Type> isNotNull (String name) {
		add(Restrictions.isNotNull(name));
		return this;
	}
	
	public Filter<Type> isNull (String name) {
		add(Restrictions.isNull(name));
		return this;
	}

	public Filter<Type> sizeEq (String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeEq(name, size));
		}
		return this;
	}

	public Filter<Type> sizeGe(String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeGe(name, size));
		}
		return this;
	}

	public Filter<Type> sizeGt(String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeGt(name, size));
		}
		return this;
	}

	public Filter<Type> sizeLe(String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeLe(name, size));
		}
		return this;
	}

	public Filter<Type> sizeLt(String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeLt(name, size));
		}
		return this;
	}

	public Filter<Type> sizeNe(String name, int size) {
		if (size >= 0) {
			add(Restrictions.sizeNe(name, size));
		}
		return this;
	}

	public Type first () {
		return nth(0);
	}
	
	public Type second () {
		return nth(1);
	}

	public Type third () {
		return nth(2);
	}

	public Type antepenultimate () {
		Type result = null;
		int size = size();
		result = (size > 2 ? nth(size - 3) : null);
		return result;
	}
	
	public Type penultimate () {
		Type result = null;
		int size = size();
		result = (size > 1 ? nth(size - 2) : null);
		return result;
	}
	
	public Type last () {
		Type result = null;
		int size = size();
		result = (size > 0 ? nth(size - 1) : null);
		return result;
	}
	
	public Type nth (int index) {
		Type result = null;
		result = get(index);
		return result;
	}

	public boolean exists () {
		return size() > 0;
	}

	public Filter<Type> end () {
		if (this._junctions != null) {
			int size = this._junctions.size();
			if (size > 0) {
				this._junctions.remove(size - 1);
			}
			size = this._junctions.size();
			if (size <= 0) {
				this._junctions = null;
			}
		}
		return this;
	}
	
	public Filter<Type> negative () {
		if ((this.criterionList != null && this.criterionList.size() > 0) || (this._junctions != null && this._junctions.size() > 0)) {
			this.end();
//			IMPLEMENT ME!!
		}
		else {
			
		}
		return this;
	}
	
	public Filter<Type> eq (String name, Filter<?> param) {
//		IMPLEMENT ME!!
//		DetachedCriteria.forClass(clazz);
		return this;
	}
	
	public Filter<Type> ne (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}
	
	public Filter<Type> lt (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}
	
	public Filter<Type> le (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> gt (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> ge (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> lk (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> nlk (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> ilk (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> nilk (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> bt (String name, Filter<?> lo, Filter<?> hi) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> nbt (String name, Filter<?> lo, Filter<?> hi) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> in (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> nin (String name, Filter<?> param) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeEq (String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeGe(String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeGt(String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeLe(String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeLt(String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}

	public Filter<Type> sizeNe(String name, Filter<?> size) {
//		IMPLEMENT ME!!
		
		return this;
	}
	
	
	protected List<Junction> _junctions;

	public Filter<Type> add (Criterion criterion) {
		if (criterion != null) {
			int lengthSubcriterias = (this.subcriterias != null ? this.subcriterias.size() : 0);
			if (lengthSubcriterias > 0) {
				Criteria subcriteria = this.subcriterias.get(lengthSubcriterias - 1);
				subcriteria.add(criterion);
			}
			else {
				int size = (this._junctions != null ? this._junctions.size() : 0);
				if (size > 0) {
					this._junctions.get(size - 1).add(criterion);
				}
				else {
					this.criterionList = (this.criterionList != null ? this.criterionList : new ArrayList<Criterion>());
					this.criterionList.add(criterion);
//					_doGetCriteria().add(criterion);
				}
			}
		}
		return this;
	}
	
	public Filter<Type> and () {
		Junction junction = Restrictions.conjunction();
		this._junctions = (this._junctions != null ? this._junctions : new ArrayList<Junction>());
		this._junctions.add(junction);
		add(junction);
		return this;
	}
	
	public Filter<Type> or () {
		Junction junction = Restrictions.disjunction();
		this._junctions = (this._junctions != null ? this._junctions : new ArrayList<Junction>());
		this._junctions.add(junction);
		add(junction);
		return this;
	}
	
	public Filter<Type> nand () {
		Junction junction = Restrictions.conjunction();
		this._junctions = (this._junctions != null ? this._junctions : new ArrayList<Junction>());
		this._junctions.add(junction);
		add(Restrictions.not(junction));
		return this;
	}

	public Filter<Type> nor () {
		Junction junction = Restrictions.disjunction();
		this._junctions = (this._junctions != null ? this._junctions : new ArrayList<Junction>());
		this._junctions.add(junction);
		add(Restrictions.not(junction));
		return this;
	}

	public Filter<Type> reverse () {
		String orderBy = this.getOrderBy();
		setOrderBy(reverseOrderBy(orderBy));
		return this;
	}
	
	
	public <T extends Entity<T>> Filter<T> link (Class<T> clazz, String name) {
		Filter<T> result = null;
		
		
		Criteria criteria = _doGetCriteria();
		criteria = criteria.createCriteria(name);
		
		
		return result;
	}
	
	public Number min (String name) {
		Number result = null;
		Criteria criteria = _doGetCriteria();
		criteria.setProjection(Projections.min(name));
		List<?> list = criteria.list();
		result = (Number)list.get(0);
		return result;
	}
	
	public Number max (String name) {
		Number result = null;
		Criteria criteria = _doGetCriteria();
		criteria.setProjection(Projections.max(name));
		List<?> list = criteria.list();
		result = (Number)list.get(0);
		return result;
	}
	
	public Number sum (String name) {
		Number result = null;
		Criteria criteria = _doGetCriteria();
		criteria.setProjection(Projections.sum(name));
		List<?> list = criteria.list();
		result = (Number)list.get(0);
		return result;
	}
	
	public Number avg (String name) {
		Number result = null;
		Criteria criteria = _doGetCriteria();
		criteria.setProjection(Projections.avg(name));
		List<?> list = criteria.list();
		result = (Number)list.get(0);
		return result;
	}
	
	public Number count() {
		return Integer.valueOf(size());
	}

	
	
	

	@Override
	public void add(int index, Type element) {
		throw new org.effortless.core.ModelException(new UnsupportedOperationException());
	}

	@Override
	public boolean contains(Object o) {
		boolean result = false;
		result = (this.persistList != null ? this.persistList.contains(0) : null);
		if (!result) {
			try {
				AbstractIdEntity<?> entity = (AbstractIdEntity<?>)o;
				List<Type> list = this.eq("id", entity.getId());
				result = (list != null && list.size() > 0);
			}
			catch (ClassCastException e) {
			}
		}
		return result;
	}

	@Override
	public Iterator<Type> iterator() {
		return new FilterIterator(this);
	}

	@Override
	public ListIterator<Type> listIterator(int index) {
		return new FilterIterator(this, index);
	}

	@Override
	public Type set(int index, Type element) {
		throw new org.effortless.core.ModelException(new UnsupportedOperationException());
	}

	
	protected class FilterIterator extends Object implements ListIterator<Type> {

		public FilterIterator (CriteriaFilter<Type> owner) {
			this(owner, 0);
		}
		
		public FilterIterator (CriteriaFilter<Type> owner, int offset) {
			super();
			this._owner = owner;
			this._offset = offset;
			this._last = null;
		}
		
		protected CriteriaFilter<Type> _owner;
		protected Type _last;
		protected int _offset;
		
		@Override
		public boolean hasNext() {
			boolean result = false;
			try {
				this._last = this._owner.get(this._offset);
				result = (this._last != null);
				this._offset += 1;
			}
			catch (Throwable t) {
			}
			return result;
		}

		@Override
		public Type next() {
			Type result = null;
			result = this._last;
			this._last = null;
			return result;
		}

		@Override
		public void remove() {
			if (this._last != null) {
				this._owner.remove(this._last);
			}
		}

		@Override
		public void add(Type o) {
			this._owner.add(o);
		}

		@Override
		public boolean hasPrevious() {
			boolean result = false;
			result = (this._offset > 0);
			return result;
		}

		@Override
		public int nextIndex() {
			int result = 0;
			result = this._offset + 1;
			return result;
		}

		@Override
		public Type previous() {
			Type result = null;
			result = (this._offset > 0 ? this._owner.get(this._offset - 1) : null);
			return result;
		}

		@Override
		public int previousIndex() {
			int result = 0;
			result = (this._offset > 0 ? this._offset - 1 : 0);
			return result;
		}

		@Override
		public void set(Type o) {
			this._owner.set(this._offset, o);
		}
		
	}
	
	
	
	public void persist () {
		persist(true);
	}
	
	public boolean persist (boolean validate) {
		boolean result = true;
		if (this.persistList != null) {
			Iterator<Type> iterator = this.persistList.iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					Entity<?> item = iterator.next();
					if (item != null) {
						if (item.hasBeenCreated()) {
							item.create(validate);
						}
						else if (item.hasBeenDeleted()) {
							item.delete(validate);
						}
						else if (item.hasBeenChanged()) {
							item.update(validate);
						}
					}
				}
			}
			this.persistList = null;
		}
		return result;
	}
	
	
	protected List<Type> persistList;
	
	public List<Type> toPersist () {
		return this.persistList;
	}
	
	public void clearPersist () {
		this.persistList = null;
	}
	
	protected boolean clear;
	
	public void clear () {
		this.clear = true;
		this.persistList = null;
	}
	
	@Override
	public boolean add(Type o) {
		boolean result = false;
		if (o != null && !contains(o)) {
			applyListener(o);
			this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
			this.persistList.add(o);
		}
		return result;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean result = false;
		if (o != null) {
			if (true) {
				AbstractIdEntity toDelete = null;
				try {
					toDelete = (AbstractIdEntity)o;
				}
				catch (ClassCastException e) {
				}
				if (toDelete != null ) {
					toDelete.setDeleted(Boolean.TRUE);
				}
			}
//			result = super.remove(o);
			result = true;
			if (true) {
				this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
				if (!this.persistList.contains(o)) {
					this.persistList.add((Type)o);
				}
			}
		}
		return result;
	}
	
	protected void applyListener (Type o) {
		if (o != null && !o.containsPropertyChangeListener(this)) {
			o.addPropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event != null) {
			Type evtSource = (Type)event.getSource();
			if (evtSource != null) {
				if (this.persistList == null || !this.persistList.contains(evtSource)) {
					this.persistList = (this.persistList != null ? this.persistList : new ArrayList<Type>());
					this.persistList.add(evtSource);
				}
			}
		}
	}

	
	
	
	public Filter<Type> batch (EntityProcess<Type, ?> process) {
		if (process != null) {
			Class<?> clazz = doGetFilterClass();
			String txId = SessionManager.newTransaction(clazz);
			for (Type entity : this) {
				if (entity != null) {
					process.run(entity);
				}
			}
			SessionManager.endTransaction(clazz, txId);
		}
		return this;
	}

	public Filter<Type> persistAll (EntityProcess<Type, ?> process) {
		if (process != null) {
			Class<?> clazz = doGetFilterClass();
			String txId = SessionManager.newTransaction(clazz);
			for (Type entity : this) {
				if (entity != null) {
					process.run(entity);
					entity.persist();
				}
			}
			SessionManager.endTransaction(clazz, txId);
		}
		return this;
	}
	
	public Filter<Type> deleteAll (EntityProcess<Type, Boolean> process) {
		Class<?> clazz = doGetFilterClass();
		String txId = SessionManager.newTransaction(clazz);
		reverse();
		int length = size();
		for (int i = length - 1; i >= 0; i--) {
			Type entity = get(i);
			if (entity != null) {
				Boolean toDelete = process.run(entity);
				if (toDelete != null) {
					entity.delete();
				}
			}
		}
		reverse();
		SessionManager.endTransaction(clazz, txId);
		return this;
	}
	
	public Filter<Type> eraseAll (EntityProcess<Type, Boolean> process) {
		Class<?> clazz = doGetFilterClass();
		String txId = SessionManager.newTransaction(clazz);
		reverse();
		int length = size();
		for (int i = length - 1; i >= 0; i--) {
			Type entity = get(i);
			if (entity != null) {
				Boolean toErase = process.run(entity);
				if (toErase != null) {
					entity.erase();
				}
			}
		}
		reverse();
		SessionManager.endTransaction(clazz, txId);
		return this;
	}

	protected String reverseOrderBy(String orderBy) {
		String result = null;
		if (orderBy != null) {
			result = "";
			String[] parts = orderBy.split(",");
			int length = (parts != null ? parts.length : 0);
			for (int i = 0; i < length; i++) {
				String part = parts[i];
				String[] propertyOrder = part.split(" ");
				int lengthPropertyOrder = (propertyOrder != null ? propertyOrder.length : 0);
				String name = (lengthPropertyOrder > 0 ? propertyOrder[0] : null);
				String order = (lengthPropertyOrder > 1 ? propertyOrder[1] : null);
				order = (order != null ? order.trim().toUpperCase() : "");
				order = (order.length() > 0 ? order : "ASC");
				order = ("ASC".equals(order) ? "DESC" : "ASC");
				result += (result.length() > 0 ? ", " : "") + name + " " + order;
			}
		}
		return result;
	}

}
