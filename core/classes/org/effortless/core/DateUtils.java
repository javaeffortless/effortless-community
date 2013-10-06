package org.effortless.core;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.joda.time.DateTime;

/**
 * Proporciona algunas funcionalidades relacionadas con fechas
 * @author 
 */
public class DateUtils extends Object {

    /**
     * Devuelve la representación textual de una fecha dada según un patrón de formateo
     * @param date Fecha
     * @param pattern Patrón de formateo
     * @return Representación textual de la fecha dada según un patrón de formateo
     */
    public static String format (Date date, String pattern) {
        String result = null;
        result = "";
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.applyPattern(pattern);
            result = formatter.format(date);
        }
        return result;
    }

    public static String format (Date date, String pattern, String locale) {
        String result = null;
        result = "";
        if (date != null) {
        	Locale _locale = null;
        	try {
            	_locale = new Locale(locale);
        	}
        	catch (Throwable t) {
        		_locale = null;
        	}
            SimpleDateFormat formatter = (_locale != null ? new SimpleDateFormat(pattern, _locale) : new SimpleDateFormat(pattern));
            //formatter.applyPattern(pattern);
            result = formatter.format(date);
        }
        return result;
    }

    public static String formatEn (Date date, String pattern) {
        String result = null;
        result = format(date, pattern, "en");
        return result;
    }

    public static String formatEs (Date date, String pattern) {
        String result = null;
        result = format(date, pattern, "es");
        return result;
    }

    /**
     * Devuelve la fecha actual
     * @return Fecha actual
     */
    public static Date getCurrentDate () {
        Date result = null;
        result = new Date();
        return result;
    }
    
    /**
     * Suma a una fecha dada una cantidad de milisegundos y devuelve la fecha resultante
     * @param date Fecha
     * @param ms Cantidad de milisegundos a sumar
     * @return Nueva fecha resultante de la suma
     */
    public static Date add (Date date, long ms) {
        Date result = null;
        if (date != null) {
            result = new Date(date.getTime() + ms);
        }
        return result;
    }

    public static Date addMinutes (Date date, int minutes) {
        Date result = null;
        if (date != null) {
        	long ms = (long)minutes * 60L * 1000L;
            result = new Date(date.getTime() + ms);
        }
        return result;
    }

    public static Date addHours (Date date, int hours) {
        Date result = null;
        if (date != null) {
        	long ms = (long)hours * 60L * 60L * 1000L;
            result = new Date(date.getTime() + ms);
        }
        return result;
    }

    public static Date addSeconds (Date date, int seconds) {
        Date result = null;
        if (date != null) {
        	long ms = (long)seconds * 1000L;
            result = new Date(date.getTime() + ms);
        }
        return result;
    }

    /**
     * 
     * @param value
     * @param pattern
     * @return
     * @throws ParseException
     */
	public static Date decodeDate (String value, String pattern) throws ParseException {
		Date result = null;
        if (value != null && !"".equals(value)) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.applyPattern(pattern);
            result = formatter.parse(value);
        }
        return result;
	}
	
	/**
	 * Obtiene el siguiente día al indicado
	 * @param date
	 * @return
	 */
	public static Date nextDay (Date date) {
		Date result = null;
		if (date != null) {
			result = add(date, DAY_MILIS);
		}
		return result;
	}
	
	/**
	 * Obtiene el siguiente día al indicado
	 * @param date
	 * @return
	 */
	public static Date nextDay (Date date, int days) {
		Date result = null;
		if (date != null) {
			days = Math.abs(days);
			result = add(date, days * DAY_MILIS);
		}
		return result;
	}
	
	public static Date addDays (Date date, int days) {
		Date result = null;
		if (date != null) {
			result = add(date, days * DAY_MILIS);
		}
		return result;
	}
	
	/**
	 * Obtiene el día anterior al indicado
	 * @param date
	 * @return
	 */
	public static Date prevDay (Date date) {
		Date result = null;
		if (date != null) {
			result = add(date, -DAY_MILIS);
		}
		return result;
	}

	/**
	 * Obtiene el día anterior al indicado
	 * @param date
	 * @return
	 */
	public static Date prevDay (Date date, int days) {
		Date result = null;
		if (date != null) {
			days = Math.abs(days);
			result = add(date, -1 * days * DAY_MILIS);
		}
		return result;
	}

	public static Date nextCurrentWorkDay () {
		Date result = null;
		result = nextWorkDay(getCurrentDate());
		return result;
	}
	
	public static Date prevCurrentWorkDay () {
		Date result = null;
		result = prevWorkDay(getCurrentDate());
		return result;
	}

	public static Date nextWorkDay (Date date, int days) {
		Date result = date;
		for (int i = 0; i < days; i++) {
			result = nextWorkDay(result);
		}
		return result;
	}
	
	public static Date prevWorkDay (Date date, int days) {
		Date result = date;
		for (int i = 0; i < days; i++) {
			result = prevWorkDay(result);
		}
		return result;
	}
	
	
	public static Date nextWorkDay (Date day) {
		Date result = null;
		result = day;
		boolean weekend = false;
		do {
			result = nextDay(result);
			weekend = isWeekend(result);
		} while (weekend);
		return result;
	}
	
	public static Date prevWorkDay (Date day) {
		Date result = null;
		result = day;
		boolean weekend = false;
		do {
			result = prevDay(result);
			weekend = isWeekend(result);
		} while (weekend);
		return result;
	}
	
	/**
	 * Obtiene days días anterior al actual
	 * @param date
	 * @return
	 */
	public static Date prevCurrentDay (int days) {
		Date result = null;
		result = prevDay(getCurrentDate(), days);
		return result;
	}

	/**
	 * Obtiene days días siguientes al actual
	 * @param date
	 * @return
	 */
	public static Date nextCurrentDay (int days) {
		Date result = null;
		result = nextDay(getCurrentDate(), days);
		return result;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date firstTime (Date date) {
		Date result = null;
		if (date != null) {
			try {
				result = updateTime(date, FIRST_TIME);
			} catch (ParseException e) {
			}
		}
		return result;
	}

	/**
	 * Patrón de hora completa
	 */
	public static final String PATTERN_FULLTIME = "HH:mm:ss:SSS";

	/**
	 * Patrón de día completo
	 */
	public static final String PATTERN_FULLDAY = "dd/MM/yyyy";

	/**
	 * Primer instante de un día
	 */
	public static final String FIRST_TIME = "00:00:00:000";
	
	/**
	 * Último instante de un día
	 */
	public static final String LAST_TIME = "23:59:59:999";
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastTime (Date date) {
		Date result = null;
		if (date != null) {
			try {
				result = updateTime(date, LAST_TIME);
			} catch (ParseException e) {
			}
		}
		return result;
	}

	public static Date updateTime (Date date, String hour) throws ParseException {
		Date result = null;
		if (date != null) {
			result = new Date(date.getTime());
			Calendar calendar = Calendar.getInstance();
			
			Date _hour = decodeDate(hour, PATTERN_FULLTIME);
			calendar.setTime(_hour);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int minutes = calendar.get(Calendar.MINUTE);
			int seconds = calendar.get(Calendar.SECOND);
			int mseconds = calendar.get(Calendar.MILLISECOND);
			
			calendar.setTime(result);
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, seconds);
			calendar.set(Calendar.MILLISECOND, mseconds);
			result = calendar.getTime();
		}
		return result;
	}
	
	public static Date updateTime (Date date, Date _hour) throws ParseException {
		Date result = date;
		if (date != null && _hour != null) {
			result = new Date(date.getTime());
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(_hour);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int minutes = calendar.get(Calendar.MINUTE);
			int seconds = calendar.get(Calendar.SECOND);
			int mseconds = calendar.get(Calendar.MILLISECOND);
			
			calendar.setTime(result);
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, seconds);
			calendar.set(Calendar.MILLISECOND, mseconds);
			result = calendar.getTime();
		}
		return result;
	}
	
	public static Date updateDay (Date date, String day) throws ParseException {
		Date result = null;
		if (date != null) {
			result = new Date(date.getTime());
			Calendar calendar = Calendar.getInstance();
			
			Date _day = decodeDate(day, PATTERN_FULLDAY);
			calendar.setTime(_day);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			
			calendar.setTime(result);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);
			result = calendar.getTime();
		}
		return result;
	}
	
	public static Date updateDay (Date date, Date _day) throws ParseException {
		Date result = date;
		if (date != null && _day != null) {
			result = new Date(date.getTime());
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(_day);
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			
			calendar.setTime(result);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);
			result = calendar.getTime();
		}
		return result;
	}
	
	public static Date buildDate (java.util.Date day, java.util.Date time) {
		Date result = null;
		if (day != null/* && time != null*/) {
			if (time != null) {
				result = resetDay(time);

				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(day);

				int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				int month = calendar.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR);
				
				calendar = Calendar.getInstance();
				calendar.setTime(result);
				
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.YEAR, year);

				result = calendar.getTime();
			}
			else {
				result = day;
			}
		}
		return result;
	}
	
	public static boolean equalsTime (Time time1, Time time2) {
		boolean result = false;
		if (time1 != null && time2 != null) {
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(time1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.YEAR, 1970);
			calendar.set(Calendar.MILLISECOND, 0);
			java.util.Date date1 = calendar.getTime();

			calendar.setTime(time2);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.YEAR, 1970);
			calendar.set(Calendar.MILLISECOND, 0);
			java.util.Date date2 = calendar.getTime();
			
			long v1 = date1.getTime();
			long v2 = date2.getTime();
			result = (v1 == v2);
		}
		else if (time1 == null && time2 == null) {
			result = true;
		}
		return result;
	}
	
	public static Date resetDay (Date date) {
		Date result = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.YEAR, 1970);

			result = calendar.getTime();
		}
		return result;
	}
	
	public static Time resetDayTime (Time date) {
		Time result = null;
		result = (date != null ? new Time(resetDay(date).getTime()) : null);
		return result;
	}
	
	public static Date buildDate (String day) throws ParseException {
		Date result = null;
		result = updateDay(getCurrentDate(), day);
		result = updateTime(result, FIRST_TIME);
		return result;
	}
	
	public static Date buildDate (String day, String hour) throws ParseException {
		Date result = null;
		result = updateDay(getCurrentDate(), day);
		result = updateTime(result, hour);
		return result;
	}
	
	public static Date min (Date date1, Date date2) {
		Date result = null;
		if (date1 != null && date2 != null) {
			long time = Math.min(date1.getTime(), date2.getTime());
			result = new Date(time);
		}
		return result;
	}
	
	public static Date max (Date date1, Date date2) {
		Date result = null;
		if (date1 != null && date2 != null) {
			long time = Math.max(date1.getTime(), date2.getTime());
			result = new Date(time);
		}
		return result;
	}
	
	public static boolean isWeekend (Date date) {
		boolean result = false;
		int day = getDayOfWeek(date);
		result = false;
		result = result || day == Calendar.SATURDAY;
		result = result || day == Calendar.SUNDAY;
		return result;
	}
	
	public static int getDayOfMonth (Date date) {
		return getField(date, Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfWeek (Date date) {
		return getField(date, Calendar.DAY_OF_WEEK);
	}

	public static int getYear (Date date) {
		return getField(date, Calendar.YEAR);
	}

	public static Date setDayOfMonth (Date date, int day) {
		return setField(date, Calendar.DAY_OF_MONTH, day);
	}

	public static Date setMonthOfYear (Date date, int month) {
		return setField(date, Calendar.MONTH, month);
	}

	public static Date setYear (Date date, int year) {
		return setField(date, Calendar.YEAR, year);
	}

	public static Date buildDate (int day, int month, int year) {
		Date result = null;
		result = getCurrentDate();
		result = setDayOfMonth(result, day);
		result = setMonthOfYear(result, month);
		result = setYear(result, year);
		return result;
	}
	
	
	public static int getField (Date date, int field) {
		int result = -1;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			result = calendar.get(field);
		}
		return result;
	}
	
	public static Date setField (Date date, int field, int value) {
		Date result = date;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(field, value);
			result = calendar.getTime();
		}
		return result;
	}

	

	public static int getMonthOfYear (Date date) {
		return getField(date, Calendar.MONTH);
	}

	/**
	 * Milisegundos de un día
	 */
	public static final long DAY_MILIS = 86400000L;
	
	/**
	 * Días de una semana
	 */
	public static final int DAYS_OF_WEEK = 7;

	/**
	 * Indica si una fecha es posterior a otra
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isLater (Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			result = date1.getTime() > date2.getTime();
		}
		return result;
	}
	
	/**
	 * Indica si una fecha es anterior a otra
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isPrevious (Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			result = date1.getTime() < date2.getTime();
		}
		return result;
	}
	
	/**
	 * Indica si una fecha es igual a otra
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEquals (Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			result = date1.getTime() == date2.getTime();
		}
		return result;
	}

	/**
	 * Indica si una fecha es posterior o igual a otra
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isLaterEqual (Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			result = date1.getTime() >= date2.getTime();
		}
		return result;
	}
	
	public static boolean checkPeriod (Date date, Date begin, Date end) {
		boolean result = false;
		result = (date != null ? (begin == null || isLaterEqual(date, begin)) && (end == null && isPreviousEqual(date, end)) : false);
		return result;
	}
	
	/**
	 * Indica si una fecha es anterior o igual a otra
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isPreviousEqual (Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			result = date1.getTime() <= date2.getTime();
		}
		return result;
	}

	public static Date nextYear (Date date) {
		Date result = null;
		result = addYears(date, +1);
		return result;
	}
	
	public static Date previousYear (Date date) {
		Date result = null;
		result = addYears(date, -1);
		return result;
	}
	
	public static Date addYears (Date date, int count) {
		Date result = null;
		if (date != null) {
			result = date;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, year + count);
			result = calendar.getTime();
		}
		return result;
	}
	
/*	
	public static Date nextMonth (Date date) {
		Date result = null;
		result = addMonths(date, +1);
		return result;
	}
	
	public static Date previousMonth (Date date) {
		Date result = null;
		result = addMonths(date, -1);
		return result;
	}
*/

	public static int diffMinutes (Date date1, Date date2) {
		int result = -1;
		if (date1 != null && date2 != null) {
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long time = time1 - time2;
			result = (int)(time / (1000L * 60L));
		}
		return result;
	}
/*
	public static Date addMonths (Date date, int count) {
		Date result = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			DateTime dateTime = new DateTime(calendar);
			dateTime = dateTime.plusMonths(count);
			
			result = dateTime.toDate();
			
//			result = date;
//			int month = calendar.get(Calendar.MONTH);
//			int newMonth = month + count;
//			int yearsCount = newMonth / 12;
//			newMonth = newMonth % 11;
////			int day = calendar.get(Calendar.DAY_OF_MONTH);
//			
//			calendar.set(Calendar.DAY_OF_MONTH, 1);
//			calendar.set(Calendar.MONTH, newMonth);
//			int year = calendar.get(Calendar.YEAR);
//			calendar.set(Calendar.YEAR, year + yearsCount);
////			int newDay = calendar.get(Calendar.DAY_OF_MONTH);
////			if (day != newDay) {
////				calendar.set(Calendar.MONTH, newMonth - 1);
////			}
//			result = calendar.getTime();
		}
		return result;
	}
	
	public Date firstDayOfMonth (Date date) {
		Date result = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			DateTime dateTime = new DateTime(calendar);
			dateTime = dateTime.withDayOfMonth(1);
			
			result = dateTime.toDate();
//			calendar.set(Calendar.DAY_OF_MONTH, 1);
//			result = calendar.getTime();
		}
		return result;
	}

	public Date lastDayOfMonth (Date date) {
		Date result = null;
		if (date != null) {
			result = date;
			result = nextMonth(result);
			result = firstDayOfMonth(result);
			result = prevDay(result);
		}
		return result;
	}
	
*/
	public Date nextNearDayOfWeek (Date date, int day) {
		Date result = null;
		if (date != null && checkDay(day)) {
			int dayOfWeek = -1;
			result = date;
			do {
				result = nextDay(result);
				dayOfWeek = getDayOfWeek(result);
			} while (dayOfWeek != day);
		}
		return result;
	}
	
	public Date prevNearDayOfWeek (Date date, int day) {
		Date result = null;
		if (date != null && checkDay(day)) {
			int dayOfWeek = -1;
			result = date;
			do {
				result = prevDay(result);
				dayOfWeek = getDayOfWeek(result);
			} while (dayOfWeek != day);
		}
		return result;
	}
	
	protected boolean checkDay (int day) {
		boolean result = false;
		result = false;
		result = result || Calendar.MONDAY == day;
		result = result || Calendar.TUESDAY == day;
		result = result || Calendar.WEDNESDAY == day;
		result = result || Calendar.THURSDAY == day;
		result = result || Calendar.FRIDAY == day;
		result = result || Calendar.SATURDAY == day;
		result = result || Calendar.SUNDAY == day;
		return result;
	}

	public boolean isSameDay(Date date1, Date date2) {
		boolean result = false;
		if (date1 != null && date2 != null) {
			date1 = firstTime(date1);
			date2 = firstTime(date2);
			result = isEquals(date1, date2);
		}
		return result;
	}
	
	public boolean isCurrentDay (Date date) {
		boolean result = false;
		if (date != null) {
			result = isSameDay(date, getCurrentDate());
		}
		return result;
	}
	
	public Date firstDayOfYear (Integer year) {
		Date result = null;
		if (year != null) {
			result = getCurrentDate();
			result = setDayOfMonth(result, 1);
			result = setMonthOfYear(result, 1-1);
			result = setYear(result, year.intValue());
			result = firstTime(result);
		}
		return result;
	}

	public Date lastDayOfYear (Integer year) {
		Date result = null;
		if (year != null) {
			result = getCurrentDate();
			result = setDayOfMonth(result, 31);
			result = setMonthOfYear(result, 12-1);
			result = setYear(result, year.intValue());
			result = lastTime(result);
		}
		return result;
	}

	public static java.util.Date parse(String value) throws ParseException {
		java.util.Date result = null;
		SimpleDateFormat formatter = new SimpleDateFormat();
		result = formatter.parse(value);
		return result;
	}
	
}
