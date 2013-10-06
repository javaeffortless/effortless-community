package org.effortless.core;


public class I18nTranslator extends Object {

	protected static final String APP_TITLE = "app_title";
	
	protected static final String _MODULE_LABEL = "_module_label";
	protected static final String _MODULE_TOOLTIP = "_module_tooltip";
	
	protected static final String _FINDER_TITLE = "_finder_";

	private static final String MSG_TITLE = "msg.title";
	
	
	/*
	 * 
icons_module_label = Iconos   -> ImoduleLabel
icons_module_tooltip = Iconos,Conjuntos   moduleTooltip
app_title = Gesti\u00C3\u00B3n de iconos
IconTag_finder_title = Etiquetas ->finderTitle
IconTag_finder_search_label = Buscar ->finderSearchLabel
IconTag_finder_emptyMessage = No hay elementos ->finderEmptyMessage
IconTag_name_finder_column_label = Nombre -> finderColumnLabel
IconTag_name_label = Nombre -> label
iconTag_option_label = Etiquetas -> optionLabel
iconTag_option_tooltip = Etiquetas -> optionTooltip
icondbLogData_option_label = icondbLogData_option_label -> optionLabel
	 * 
	 * 
	 */
	public static String translate(String key) {
		String result = null;
		result = key;
		if (key != null) {
			if (APP_TITLE.equals(key)) {
				result = translateAppTitle(key);
			}
			else if (MSG_TITLE.equals(key)) {
				result = "Confirmación";
			}
			else if (key.startsWith("finder_")) {
				result = translateFinderGeneral(key);
			}
			else if (key.startsWith("action.")) {
				result = translateAction(key);
			}
			else {
				String[] array = key.split("_");
				if (array.length > 1) {
					int idx = (array.length - 2);
					String word = array[idx];
					word = (word != null ? word.trim() : "");
					word = StringUtils.capFirst(word);
					result = word;
				}
			}
		}
		return result;
	}

	protected static String translateAction(String key) {
		String result = null;
		String prefix = "action.";
		int idx = key.indexOf(prefix);
		result = (idx == 0 ? key.substring(prefix.length()) : key);
		return result;
	}

	/*
	 * 
finder_button_search_label = Search
finder_size_finderMessage = Size
finder_sepPage_finderMessage = SepPage
finder_button_clone_label = Clone
finder_button_create_label = Create
finder_button_read_label = Read
finder_button_update_label = Update
finder_button_delete_label = Delete
finder_button_select_item_label = Item
finder_button_cancel_select_item_label = Item
	 * 
	 * 
	 */
	public static String translateFinderGeneral (String key) {
		String result = null;
		result = (result == null && "finder_button_search_label".equals(result) ? "Buscar" : result);
		result = (result == null && "finder_size_finderMessage".equals(result) ? "Nº registros" : result);
		result = (result == null && "finder_sepPage_finderMessage".equals(result) ? "/" : result);
		result = (result == null && "finder_button_clone_label".equals(result) ? "Duplicar" : result);
		result = (result == null && "finder_button_create_label".equals(result) ? "Nuevo" : result);
		result = (result == null && "finder_button_read_label".equals(result) ? "Consultar" : result);
		result = (result == null && "finder_button_update_label".equals(result) ? "Modificar" : result);
		result = (result == null && "finder_button_delete_label".equals(result) ? "Eliminar" : result);
		result = (result == null && "finder_button_select_item_label".equals(result) ? "Aceptar" : result);
		result = (result == null && "finder_button_cancel_select_item_label".equals(result) ? "Cancelar" : result);
		return result;
	}

	public static String translateAppTitle(String key) {
		String result = null;
		result = key;
		String appId = GlobalContext.get(GlobalContext.APP_ID, String.class);
		if (appId != null) {
			int idx = appId.lastIndexOf(".");
			String appName = (idx > -1 ? appId.substring(idx + 1) : appId);
			result = StringUtils.capFirst(appName);
		}
//		result = MySession.getAppId();
		
		// TODO Auto-generated method stub
		return result;
	}
	
	
}
