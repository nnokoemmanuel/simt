package ppp.simt.util;

import java.util.Date;

public class Helper {
	
	/**
	 * Nombre de jour entre 2 date
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long daysBetween(Date date1, Date date2) {
        long difference = (date1.getTime()-date2.getTime())/86400000;
        return Math.abs(difference);
    }

}
