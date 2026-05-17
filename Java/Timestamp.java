// Stores the date and time for a sensor reading.
public class Timestamp {

    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private int hour;
    private int minute;

    public Timestamp() {
        dayOfMonth = 0;
        monthOfYear = 0;
        year = 0;
        hour = 0;
        minute = 0;
    }

    public Timestamp(
        int dayOfMonth,
        int monthOfYear,
        int year,
        int hour,
        int minute
    ) {
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public Timestamp(Timestamp sourceTimestamp) {
        this.dayOfMonth = sourceTimestamp.dayOfMonth;
        this.monthOfYear = sourceTimestamp.monthOfYear;
        this.year = sourceTimestamp.year;
        this.hour = sourceTimestamp.hour;
        this.minute = sourceTimestamp.minute;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString() {
        String dayPadded = padWithLeadingZero(dayOfMonth);
        String monthPadded = padWithLeadingZero(monthOfYear);
        String hourPadded = padWithLeadingZero(hour);
        String minutePadded = padWithLeadingZero(minute);
        return dayPadded + "/" + monthPadded + "/" + year + " " + hourPadded + ":" + minutePadded;
    }

    private String padWithLeadingZero(int numberToPad) {
        String paddedNumber = "";

        if (numberToPad < 10) {
            paddedNumber = "0" + numberToPad;
        } else {
            paddedNumber = "" + numberToPad;
        }
        return paddedNumber;
    }
}
