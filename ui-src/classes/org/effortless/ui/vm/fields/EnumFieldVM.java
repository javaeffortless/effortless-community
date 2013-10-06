package org.effortless.ui.vm.fields;

import java.util.ArrayList;
import java.util.List;

import org.effortless.core.ClassUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;

public class EnumFieldVM extends Object {

	public EnumFieldVM () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateValues();
	}
	
	//@init('org.effortless.ui.EnumFieldVM', enumClass='org.effortless.icondb.icons.IconStatus')
	@Init
	public void init(@BindingParam("enumClass") String enumClass) {
//		try {
//			ClassLoader cl = Thread.currentThread().getContextClassLoader();
//			Class type = cl.loadClass(enumClass);
			Class type = ClassUtils.loadClassRE(enumClass);
			Object[] values = type.getEnumConstants();
			List list = new ArrayList();
			for (Object value : values) {
				list.add(value);
			}
			this.values = list;
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	protected List values;
	
	protected void initiateValues () {
		this.values = null;
	}
	
	public List getValues () {
		return this.values;
	}
	
	public void setValues (List newValue) {
		this.values = newValue;
	}
	
}
