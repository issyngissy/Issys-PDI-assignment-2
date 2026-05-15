// 1.15.11.13: Timestamp stores the date and time for a sensor reading.
public class Timestamp {

    // 1.15.11.14: The timestamp is stored as separate number fields.
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private int hour;
    private int minute;

    // 1.15.11.15: Create an empty timestamp.
    public Timestamp() {
        dayOfMonth = 0;
        monthOfYear = 0;
        year = 0;
        hour = 0;
        minute = 0;
    }

    // 1.15.11.16: Create a timestamp from the date and time values.
    public Timestamp(
        int dayOfMonth,
        int monthOfYear,
        int year,
        int hour,
        int minute
    ) {
        // 1.15.11.17: Store each time part in the matching field.
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    // 1.15.11.18: Create a separate copy of an existing timestamp.
    public Timestamp(Timestamp sourceTimestamp) {
        this.dayOfMonth = sourceTimestamp.dayOfMonth;
        this.monthOfYear = sourceTimestamp.monthOfYear;
        this.year = sourceTimestamp.year;
        this.hour = sourceTimestamp.hour;
        this.minute = sourceTimestamp.minute;
    }

    // 1.15.11.28: Return the day of the month.
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    // 1.15.11.29: Return the month of the year.
    public int getMonthOfYear() {
        return monthOfYear;
    }

    // 1.15.11.30: Return the year.
    public int getYear() {
        return year;
    }

    // 1.15.11.31: Return the hour.
    public int getHour() {
        return hour;
    }

    // 1.15.11.32: Return the minute.
    public int getMinute() {
        return minute;
    }

    // 1.15.11.19: Update the day of the month.
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    // 1.15.11.20: Update the month of the year.
    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    // 1.15.11.21: Update the year.
    public void setYear(int year) {
        this.year = year;
    }

    // 1.15.11.22: Update the hour.
    public void setHour(int hour) {
        this.hour = hour;
    }

    // 1.15.11.23: Update the minute.
    public void setMinute(int minute) {
        this.minute = minute;
    }

    // 2.5.2.3: Format the timestamp as day/month/year hour:minute.
    public String toString() {
        // 2.5.2.4: Pad small day, month, hour, and minute values with a leading zero.
        String dayPadded = padWithLeadingZero(dayOfMonth);
        String monthPadded = padWithLeadingZero(monthOfYear);
        String hourPadded = padWithLeadingZero(hour);
        String minutePadded = padWithLeadingZero(minute);
        return dayPadded + "/" + monthPadded + "/" + year + " " + hourPadded + ":" + minutePadded;
    }

    // 2.5.2.5: Add a leading zero to single digit numbers.
    private String padWithLeadingZero(int numberToPad) {
        String paddedNumber = "";

        // 2.5.2.6: Numbers below 10 need the extra zero for display.
        if (numberToPad < 10) {
            paddedNumber = "0" + numberToPad;
        } else {
            paddedNumber = "" + numberToPad;
        }
        return paddedNumber;
    }
}
