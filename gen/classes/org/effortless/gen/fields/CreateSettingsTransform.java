package org.effortless.gen.fields;

import java.util.List;

import org.effortless.ann.Module;
import org.effortless.ann.Singleton;
import org.effortless.core.MethodUtils;
import org.effortless.core.StringUtils;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GMethod;
import org.effortless.gen.GenContext;
import org.effortless.gen.Transform;
//import org.effortless.gen.impl.HibernateEntityTransform;
import org.effortless.model.Filter;

public class CreateSettingsTransform extends Object implements Transform<GApplication> {

	public CreateSettingsTransform () {
		super();
	}
	
	@Override
	public void process(GApplication node) {
		if (node != null && node.getSettingsClass() == null) {
			GClass cg = node.newClass("Settings");
			node.setSettingsClass(cg);
			cg.addAnnotation(Singleton.class);
			cg.addAnnotation(Module.class, "appSettings");
			
			cg.addField(Integer.class, "defaultPageSize");
	
			GMethod mg = null;
			
			//public static Settings getCurrent ();
			mg = cg.addMethod("getCurrent").setPublic(true).setStatic(true).setReturnType(cg.getClassNode());
			//Settings result = null;
			mg.declVariable(cg.getClassNode(), "result", mg.cteNull());
			//org.effortless.model.SimpleFilter filter = new org.effortless.model.SimpleFilter(Settings.class);
			mg.declVariable(org.effortless.model.SimpleFilter.class, "filter", mg.callConstructor(org.effortless.model.SimpleFilter.class, mg.cteClass(cg.getClassNode())));
			//filter.sortBy("id DESC");
			mg.add(mg.call(mg.var("filter"), "sortBy", mg.cte("id DESC")));
//			result = (Settings)(filter.size() > 0 ? filter.get(0) : null);
			mg.add(mg.assign(mg.var("result"), mg.cast(node.getSettingsClass().getClassNode(), mg.triple(mg.gt(mg.call(mg.var("filter"), "size"), mg.cte(Integer.valueOf(0))), mg.call(mg.var("filter"), "get", mg.cte(Integer.valueOf(0))), mg.cteNull()))));
			//return result;
			mg.addReturn(mg.var("result"));
			
			List<Transform> transforms = GenContext.getClassTransforms();		
			for (Transform t : transforms) {
				t.process(cg);
			}
		}
	}

}
