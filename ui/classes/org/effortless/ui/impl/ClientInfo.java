package org.effortless.ui.impl;

/**
 * Se podrá usar para que los componentes transformen su aspecto visual en base al dispositivo en el que se pintan (cliente navegador, móvil o de escritorio)
 * De esta manera, no habrá que dedicarle esfuerzo para desarrollar una aplicación web móvil.
 * Si es móvil, MainWindow pintara sus componentes de otra manera.
 * ListField, si es móvil, se pintarán menos columnas.
 * @author jesus
 *
 */
public class ClientInfo {

	public static boolean isMobile () {
		return false;
	}
	
}
