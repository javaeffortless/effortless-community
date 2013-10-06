
Emplear un único resource y un único response
Los recursos se deben identificar directamente. Es el sistema de seguridad el que incorpora recursos vinculados a permisos, condiciones de filtrado y restricciones de registro

public void ejecutar () {
  if (_canExecute(this, "ejecutar")) {
  	this -> object -> class -> package -> module
  	                  class -> annotation -> unit
  	actionId -> 
  	
  	
  	¿A qué recursos pertenece?
  	¿Tiene el usuario los permisos para acceder a los recursos a los que pertenece?
  	
  }
}

persist


Ideas:
· Cada módulo tendrá su sistema de seguridad.
· El módulo principal también tendrá su sistema de seguridad.
· Un sistema de seguridad podrá depender de otros sistemas.
· Un sistema de seguridad se compondrá de un conjunto de permisos que serán aplicados en función de las circunstancias.


Los permisos podrán ser aplicados de las siguientes formas:
  · Pueden modificar un {query:String,params:Object[]}
  · Indican si se puede acceder o no a un objeto
  · Indican el tipo de acceso permitido a un objeto
  · Controlar las operaciones que se pueden realizar
  · Los permisos controlan el acceso a:
    · Operaciones
    · Objetos
    · Pantallas

El sistema de seguridad nos indica si se puede acceder o no a recursos, tales como:
  · Operaciones (el SdS indica si se puede ejecutar o no una operación).
  · Objetos (el Sds indica si se puede acceder o no a un objeto).
  · Ventanas (el SdS indica si se puede acceder o no a una pantalla. Si no se puede, informa en el modo en el que se puede acceder).
  · Contenido de ventanas (el SdS indica si se puede acceder o no a una pantalla. Si no se puede, informa en el modo en el que se puede acceder).

El sistema de seguridad es llamado desde:
  · BaseAction en el caso de operaciones.
  · ManagerSB en el caso de objetos. Si traslado BaseAction a ManagerSB, también se llamará al sistema de seguridad desde el ManagerSB para el caso de las operaciones.
  · Desde Controller en el caso de pantallas.
  · Desde Zul en el caso de componentes de pantallas.

SecuritySystem
  checkOperation (id:String) : boolean
  checkReadableObject (object:Object) : boolean
  checkWriteableObject (object:Object) : boolean
  checkReadableObjectProperty (object:Object,propertyName:String) : boolean
  checkWriteableObjectProperty (object:Object,propertyName:String) : boolean
  adaptFilter (query:String,params:Object[]) : Object[]{query,params[]}

  Subdividir un SecuritySystem en múltiples ResourceSecuritySystem
    SecuritySystem
      subsystems:List<SecuritySystem>
    
    ResourceSecuritySystem
      
    Un subsistema ResourceSecuritySystem utiliza múltiples permisos (permission)
    
    Un sistema de seguridad posee también un mecanismo para acceder a los permisos de los que dispone el usuario que usa el sistema de seguridad
       UserPermissionManager
           hasRole(User, Permission):boolean
           hasAllRoles(User, Permission[]):boolean
           hasSomeAnyRole(User, Permission[]):boolean
           getRoles(User): Permission[]
           
    Un permiso es un elemento de un sistema de seguridad. Se traduce en una clase reutilizable.
    Debe haber un mapeo entre el permiso que dice poseer el usuario y el permiso del sistema de seguridad
    
    Un permiso tiene las siguientes funciones:
       Aplicar cambios a consulta: apply(query:String,params:Object[]):Object[]{newQuery,newParams[]}
       Básicamente las mismas funciones que un sistema de seguridad:
         checkOperation (id:String) : boolean
         checkReadableObject (object:Object) : boolean
         checkWriteableObject (object:Object) : boolean
         checkReadableObjectProperty (object:Object,propertyName:String) : boolean
         checkWriteableObjectProperty (object:Object,propertyName:String) : boolean
         adaptFilter (query:String,params:Object[]) : Object[]{query,params[]}
       check (request: SecurityRequest): SecurityResponse
       
       
       
   SecuritySystem
       check (request: SecurityRequest): SecurityResponse
       
       
   SecuritySystem                       SecurityRequest                             SecurityResponse
       OperationSecuritySystem                OperationSecurityRequest                      OperationSecurityResponse
       ObjectSecuritySystem                   ObjectSecurityRequest                         ObjectSecurityResponse
       ObjectPropertySecuritySystem           ObjectPropertySecurityRequest                 ObjectPropertySecurityResponse
       FilterSecuritySystem                   FilterSecurityRequest                         FilterSecurityResponse
       ViewSecuritySystem                     ViewSecurityRequest                           ViewSecurityResponse

    
    public void ACTION_OPERATION () {
        if (checkSecurityAction(ACTION_OPERATION)) {
        	...bodyAction...
        }
    }
    
    protected boolean checkSecurityAction (String id) throws SecurityException {
    	boolean result = false;
        SecuritySystem ss = ...f(dao)...;
        OperationSecurityRequest request = new OperationSecurityRequest(dao, object, id);
        OperationSecurityResponse response = (OperationSecurityResponse)ss.check(request);
        result = response.isAllow();
        if (!result) {
        	SecuritySeverity severity = response.getSeverity();
        	if (SecuritySeverityEnum.ERROR.equals(severity)) {
        		response.throwSecurityException();SecurityException-|>ModelException
        		//throw new ModelException(response.getMessage());
        	}
        }
        return result;
    }
    
    ManagerImplSB: readByPk
    
        readByPk_Reference(): object
        if (checkSecurityObject(object)) {
        	result = object;
        }
        
        protected boolean checkSecurityObject(object) throws SecurityException {
          	boolean result = false;
	        SecuritySystem ss = ...f(dao)...;
    	    ObjectSecurityRequest request = new ObjectSecurityRequest(dao, object);
	        ObjectSecurityResponse response = (ObjectSecurityResponse)ss.check(request);
        	result = response.isAllow();
        	if (!result) {
	        	SecuritySeverity severity = response.getSeverity();
        		if (SecuritySeverityEnum.ERROR.equals(severity)) {
	        		response.throwSecurityException();SecurityException-|>ModelException
        			//throw new ModelException(response.getMessage());
        		}
        	}
          	return result;
        }
    

    ManagerImplSB: loadProperty
    
        loadPropertyName(): propertyValue
        if (checkSecurityObjectReadProperty(object, propertyName)) {
            readProperty
        	result = propertyValue;
        }
        
        protected boolean checkSecurityObjectReadProperty(object, propertyName) throws SecurityException {
          	boolean result = false;
	        SecuritySystem ss = ...f(dao)...;
    	    ObjectPropertySecurityRequest request = new ObjectSecurityPropertyRequest(dao, object, ObjectPropertySecurityRequestMode.READ);
	        ObjectPropertySecurityResponse response = (ObjectSecurityPropertyResponse)ss.check(request);
        	result = response.isAllow();
        	if (!result) {
	        	SecuritySeverity severity = response.getSeverity();
        		if (SecuritySeverityEnum.ERROR.equals(severity)) {
	        		response.throwSecurityException();SecurityException-|>ModelException
        			//throw new ModelException(response.getMessage());
        		}
        	}
          	return result;
        }
    
    ManagerImplSB: saveProperty
    
        savePropertyName(): object
        if (checkSecurityObjectSaveProperty(object, propertyName)) {
            saveProperty
        	result = propertyValue;
        }
        
        protected boolean checkSecurityObjectSaveProperty(object, propertyName) throws SecurityException {
          	boolean result = false;
	        SecuritySystem ss = ...f(dao)...;
    	    ObjectPropertySecurityRequest request = new ObjectSecurityPropertyRequest(dao, object, ObjectPropertySecurityRequestMode.SAVE);
	        ObjectPropertySecurityResponse response = (ObjectSecurityPropertyResponse)ss.check(request);
        	result = response.isAllow();
        	if (!result) {
	        	SecuritySeverity severity = response.getSeverity();
        		if (SecuritySeverityEnum.ERROR.equals(severity)) {
	        		response.throwSecurityException();SecurityException-|>ModelException
        			//throw new ModelException(response.getMessage());
        		}
        	}
          	return result;
        }
    
    ManagerImplSB: findList & findCount
        FilterSecurityResponse sResponse = (FilterSecurityResponse)checkSecurityFilter(query, params);
        if (sResponse.isAllow()) {
            String newQuery = sResponse.getNewQuery();
            Object[] newParams = sResponse.getNewParams();
            result = findList(newQuery, newParams);
        }
        else {
        	SecuritySeverity severity = response.getSeverity();
       		if (SecuritySeverityEnum.ERROR.equals(severity)) {
        		response.throwSecurityException();SecurityException-|>ModelException
       			//throw new ModelException(response.getMessage());
       		}
        }
        
        protected boolean checkSecurityFilter(query: String, params: Object[]) throws SecurityException {
          	boolean result = false;
	        SecuritySystem ss = ...f(dao)...;
    	    ObjectPropertySecurityRequest request = new ObjectSecurityPropertyRequest(dao, object, ObjectPropertySecurityRequestMode.SAVE);
	        ObjectPropertySecurityResponse response = (ObjectSecurityPropertyResponse)ss.check(request);
        	result = response.isAllow();
        	if (!result) {
	        	SecuritySeverity severity = response.getSeverity();
        		if (SecuritySeverityEnum.ERROR.equals(severity)) {
	        		response.throwSecurityException();SecurityException-|>ModelException
        			//throw new ModelException(response.getMessage());
        		}
        	}
          	return result;
        }
        
    
    SecuritySystem PRINCIPAL
    MainSecuritySystem -|> SecuritySystem
        // Debe obtener un subsistema SecuritySystem y ejecutarlo
        securitySystem = loadSecuritySystem(request):SecuritySystem
        result = securitySystem.check(request);
        
        
        loadSecuritySystem (request) : SecuritySystem
        
             if (request instanceof OperationSecurityRequest) {
             	result = loadOperationSecuritySystem((OperationSecurityRequest)request);
             }
             else if (request instanceof FilterSecurityRequest) {
             	result = loadFilterSecuritySystem((FilterSecurityRequest)request);
             }
             else if (request instanceof ViewSecurityRequest) {
             	result = loadViewSecuritySystem((ViewSecurityRequest)request);
             }
             else if (request instanceof ObjectSecurityRequest) {
             	result = loadObjectSecuritySystem((ObjectSecurityRequest)request);
             }
             else if (request instanceof ObjectPropertySecurityRequest) {
             	result = loadObjectPropertySecuritySystem((ObjectPropertySecurityRequest)request);
             }

        loadOperationSecuritySystem (request: OperationSecurityRequest) : OperationSecurityResponse
        loadFilterSecuritySystem (request: FilterSecurityRequest) : FilterSecurityResponse
        loadViewSecuritySystem (request: ViewSecurityRequest) : ViewSecurityResponse
        loadObjectSecuritySystem (request: ObjectSecurityRequest) : ObjectSecurityResponse
        loadObjectPropertySecuritySystem (request: ObjectPropertySecurityRequest) : ObjectPropertySecurityRequest

             
             
   CompositeSecuritySystem
   AndSecuritySystem
   OrSecuritySystem
   
             
   SecuritySystem                       SecurityRequest                             SecurityResponse
       OperationSecuritySystem                OperationSecurityRequest                      OperationSecurityResponse
       ObjectSecuritySystem                   ObjectSecurityRequest                         ObjectSecurityResponse
       ObjectPropertySecuritySystem           ObjectPropertySecurityRequest                 ObjectPropertySecurityResponse
       FilterSecuritySystem                   FilterSecurityRequest                         FilterSecurityResponse
       ViewSecuritySystem                     ViewSecurityRequest                           ViewSecurityResponse
           
    