
iron speed
double guardarlo en db como long y definir previamente nº decimales, de esta manera evitamos problemas de redondeo

Idea muy buena. Generar plantilla para generar 100% la parte visual de una aplicación 100% asistente wizard. ¿Qué quiere hacer? Siguiente ¿Cómo quiere hacerlo? Siguiente Introduzca los siguientes campos Siguiente Confirme la operación.

DynamicFinders
//Emplear la técnica descrita en:
//http://groovy.codehaus.org/Using+methodMissing+and+propertyMissing
//http://groovy.codehaus.org/ExpandoMetaClass
//http://groovy.codehaus.org/ExpandoMetaClass+-+Dynamic+Method+Names



List-Field, Text-Field, Bool-Field -> FUSIONAR EN UN COMPONENTE ÚNICO con el atributo type como en HTML pasa con input: <field type="text" . Pero el campo type será opcional.
MVVM No se usará mediante @bind, sino mediante @nombre, ejemplo: <field value="@nombre" />
	//Inspect https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/BindComposer.java
	// https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/AnnotateBinder.java
	// https://github.com/zkoss/zk/blob/master/zkbind/src/org/zkoss/bind/impl/AnnotateBinderHelper.java
    // http://code.google.com/p/zkbooks/source/browse/trunk/developersreference/developersreference/src/main/java/org/zkoss/reference/developer/mvvm/advance/DynamicFormBindingComposer.java?r=478

http://tv.adobe.com/watch/max-2013/architecting-a-phonegap-application/

ZK-646: Allow WebAppInit listenes to override default extendlet, such as WPD and WCS

http://emrpmsexpert.com:8080/ZKExamples/
http://opencv.org/

http://books.zkoss.org/wiki/ZK_Developer%27s_Reference/UI_Patterns/Useful_Java_Utilities#showNotification

http://tympanus.net/Tutorials/CreativeCSS3AnimationMenus/index4.html
http://www.hongkiat.com/blog/css3-animation-transition-demos/


icon font
icon font generator
http://fortawesome.github.io/Font-Awesome/
http://fontello.com/
http://icomoon.io/
http://icomoon.io/app/#library
http://fontcustom.com/


listByProperty1Property2(name1, name2);

WHERE property1 LIKE name1 AND property2 = name2

text->like (si no null)
ref->eq (si no null)
bool->is null o false or is not null o true
integer->eq
number->eq
list->in


filtros

 Criteria	add(Criterion criterion) 
          Add a restriction to constrain the results to be retrieved.
 Criteria	addOrder(Order order) 
          Add an ordering to the result set.

Criteria criteria = session.createCriteria(CLAZZ);
if (pText != null && !"".equals(pText)) {//text
	criteria = criteria.add(Restrictions.like("pText", "%" + text + "%"));
}
if (pBool != null) {//boolean
	Disjunction or = Restrictions.disjunction();
	or.add(Restrictions.eq("pBool", pBool));
	if (pBool.booleanValue()) {
		or.add(Restrictions.isNotNull("pBool"));
	}
	else {
		or.add(Restrictions.isNull("pBool"));
	}
	criteria = criteria.add(or);
}
if (pCount != null) {//integer
	criteria = criteria.add(Restrictions.eq("pCount", pCount));
}
if (pDate != null) {//Date
	criteria = criteria.add(Restrictions.eq("pDate", pDate));
}
if (pEnum != null) {//Enum
	criteria = criteria.add(Restrictions.eq("pEnum", pEnum));
}
if (pFile != null) {//File
}
if (pList != null) {//List
	criteria = criteria.in(Restrictions.in("pList", pList));
}
if (pNumber != null) {//Number
	criteria = criteria.add(Restrictions.eq("pNumber", pNumber));
}
if (pRef != null) {//Ref Object
	criteria = criteria.add(Restrictions.eq("pRef", pRef));
}

listByCentro
Class search Centro


 List cats = session.createCriteria(Cat.class)
     .add( Restrictions.like("name", "Iz%") )
     .add( Restrictions.gt( "weight", new Float(minWeight) ) )
     .addOrder( Order.asc("age") )
     .list();
 
You may navigate associations using createAlias() or createCriteria().
 List cats = session.createCriteria(Cat.class)
     .createCriteria("kittens")
         .add( Restrictions.like("name", "Iz%") )
     .list();
 
 List cats = session.createCriteria(Cat.class)
     .createAlias("kittens", "kit")
     .add( Restrictions.like("kit.name", "Iz%") )
     .list();
 
You may specify projection and aggregation using Projection instances obtained via the factory methods on Projections.
 List cats = session.createCriteria(Cat.class)
     .setProjection( Projections.projectionList()
         .add( Projections.rowCount() )
         .add( Projections.avg("weight") )
         .add( Projections.max("weight") )
         .add( Projections.min("weight") )
         .add( Projections.groupProperty("color") )
     )
     .addOrder( Order.asc("color") )
     .list();          
          
          


Restrictions.

static Conjunction	and(Criterion... predicates)

static Conjunction	conjunction() 
static Disjunction	disjunction() 
static SimpleExpression	eq(String propertyName, Object value) 
static SimpleExpression	ge(String propertyName, Object value) 
static SimpleExpression	gt(String propertyName, Object value) 
static Criterion	ilike(String propertyName, Object value) 
static Criterion	ilike(String propertyName, String value, MatchMode matchMode) 
static Criterion	in(String propertyName, Collection values) 
static Criterion	in(String propertyName, Object[] values) 
static Criterion	isEmpty(String propertyName) 
static Criterion	isNotEmpty(String propertyName) 
static Criterion	isNotNull(String propertyName) 
static Criterion	isNull(String propertyName) 
static SimpleExpression	le(String propertyName, Object value) 

 static SimpleExpression	like(String propertyName, Object value) 
          Apply a "like" constraint to the named property
static SimpleExpression	like(String propertyName, String value, MatchMode matchMode) 
          Apply a "like" constraint to the named property
static SimpleExpression	lt(String propertyName, Object value) 
          Apply a "less than" constraint to the named property
          
          
static SimpleExpression	ne(String propertyName, Object value) 


static Criterion	not(Criterion expression) 





static Criterion	sizeEq(String propertyName, int size) 
          Constrain a collection valued property by size
static Criterion	sizeGe(String propertyName, int size) 
          Constrain a collection valued property by size
static Criterion	sizeGt(String propertyName, int size) 
          Constrain a collection valued property by size
static Criterion	sizeLe(String propertyName, int size) 
          Constrain a collection valued property by size
static Criterion	sizeLt(String propertyName, int size) 
          Constrain a collection valued property by size
static Criterion	sizeNe(String propertyName, int size) 
          Constrain a collection valued property by size
          
          
          