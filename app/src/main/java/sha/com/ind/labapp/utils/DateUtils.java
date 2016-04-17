package sha.com.ind.labapp.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static int HOURS_PER_DAY = 24;
    public static int MINUTES_PER_HOUR = 60;
    public static int SECONDS_PER_MINUTE = 60;
    public static long MILLIS_PER_SECOND = 1000;
    public static long MILLIS_PER_MINUTE = SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
    public static long MILLIS_PER_HOUR = MINUTES_PER_HOUR * MILLIS_PER_MINUTE;


    public static Calendar sUtcCalendarInstance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static Calendar sLocalCalendarInstance = Calendar.getInstance();

    /**
     * Returns the current time in UTC time zone.
     *
     * @return Current UTC time elapsed from Epoch in milliseconds.
     */
    public static long getCurrentUtcTimeInMilliseconds() {
        return getNewUtcCalendar().getTimeInMillis();
    }

    /**
     * Returns a string with a formatted UTC date according to the specified date format string.
     *
     * @param dateFormat
     * 		Date format string.
     * @param utcTime
     * 		Date to be formatted in UTC time zone.
     *
     * @return String with formatted date.
     */
    public static String formatUtcDateFromLong(String dateFormat, long utcTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setCalendar(sUtcCalendarInstance);
        return simpleDateFormat.format(new Date(utcTime));
    }

    /**
     * Returns a string with a formatted local date according to the specified date format string.
     *
     * @param dateFormat
     * 		Date format string.
     * @param localTime
     * 		Date to be formatted in local time zone.
     *
     * @return String with formatted date.
     */
    public static String formatLocalDateFromLong(String dateFormat, long localTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setCalendar(sLocalCalendarInstance);
        return simpleDateFormat.format(new Date(localTime));
    }

    /**
     * Convert UTC date to local time zone.
     *
     * @param utcDate
     * 		UTC date to be converted.
     *
     * @return Date converted to local time zone.
     */
    public static Date convertUtcToLocalDate(Date utcDate) {
        Calendar utcCalendar = getNewUtcCalendar();

        utcCalendar.setTimeInMillis(utcDate.getTime());
        utcCalendar.setTimeZone(TimeZone.getDefault());
        return utcCalendar.getTime();
    }

    /**
     * Formats the timestamp provided to Date object assuming the inputDateFormat specified
     * @param timestamp	Date time stamp in string
     * @param inputDateFormat	Date format of the timestamp provided
     * @return	Date object for specified timestamp, if failure returns null
     */
    public static Date formatTimestampToDate(String timestamp, String inputDateFormat)
    {
        if (timestamp != null)
        {
            SimpleDateFormat format = new SimpleDateFormat(inputDateFormat);

            try
            {
                return format.parse(timestamp);
            }
            catch (ParseException e) { e.printStackTrace();}

        }
        return null;
    }

    /**
     * Returns a string with a formatted UTC date. Ex : 01hr 03mins, Ex : 12hrs 55mins
     * Ex : 46 mins,  Ex: 34 secs
     *
     * @param utcTime
     * 		Millis in UTC format
     *
     * @return String with formatted remaining time.
     */
    public static String formatRemainingTimeToHHMMSS(long utcTime) {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        String formattedTime = "";
        int hoursRemaining = (int)(utcTime / MILLIS_PER_HOUR);

        if(hoursRemaining != 0)
        {
            formattedTime = decimalFormat.format(hoursRemaining);
            formattedTime += hoursRemaining == 1 ? " hr " : " hrs ";
        }

        utcTime = utcTime % MILLIS_PER_HOUR;

        int minutesRemaining = (int)(utcTime / MILLIS_PER_MINUTE);

        if(minutesRemaining != 0){
            formattedTime += decimalFormat.format(minutesRemaining);
            formattedTime += minutesRemaining == 1 ? " min " : " mins ";
        }

        utcTime = utcTime % MILLIS_PER_MINUTE;

        if(hoursRemaining == 0 && minutesRemaining == 0)
        {
            int secondsRemaining = (int)(utcTime / MILLIS_PER_SECOND);
            if(secondsRemaining != 0)
            {
                formattedTime += decimalFormat.format(secondsRemaining);
                formattedTime += secondsRemaining == 1 ? " sec " : " secs ";
            }
        }

        return formattedTime;
    }

    public static long getDaysBetweenDates(Date endDate, Date startDate)
    {
        long diff =  endDate.getTime() - startDate.getTime();

        return (long)Math.ceil(diff / (double)(24 * 60 * 60 * 1000));
    }

    private static Calendar getNewUtcCalendar() {
        return new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    }
}
