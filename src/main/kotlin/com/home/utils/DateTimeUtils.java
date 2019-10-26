package com.home.utils;

import lombok.NonNull;
import org.joda.time.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Утилита для работы с датой.
 */
public final class DateTimeUtils {

    public static DateTime utcDateTime() {
        return new DateTime().withZone(DateTimeZone.UTC);
    }

    public static DateTime utcDateTime(final Long millis) {
        return utcDateTime(new Date(millis));
    }

    public static DateTime utcDateTime(@NonNull final Date date) {
        return new DateTime(date).withZone(DateTimeZone.UTC);
    }

    public static DateTime utcDateTime(DateTime dateTime) {
        return dateTime.withZone(DateTimeZone.UTC);
    }

    public static Between between(final Date start,
                                  final Date end)
    {
        return new Between(start, end);
    }


    /**
     * Добавление секунд к дате без мутации.
     *
     * @param date    дата, к которой добавляются секунды.
     * @param seconds количество секунд для добавления.
     * @return новую дату с учетом добавленных секунд.
     */
    public static Date plusSeconds(@NonNull final Date date,
                                   @NonNull final Integer seconds)
    {
        return calc(date, DateTime::plusSeconds, seconds);
    }

    /**
     * Вычет минут от даты без мутации.
     *
     * @param date    дата, из которой вычетаются секунды.
     * @param seconds количество секунд для вычета.
     * @return новую дату с учетом вычтенных секунд.
     */
    public static Date minusSeconds(@NonNull final Date date,
                                    @NonNull final Integer seconds)
    {
        return calc(date, DateTime::minusSeconds, seconds);
    }

    /**
     * Добавление минут к дате без мутации.
     *
     * @param date    дата, к которой добавляются минуты.
     * @param minutes количество минут для добавления.
     * @return новую дату с учетом добавленных минут.
     */
    public static Date plusMinutes(@NonNull final Date date,
                                   @NonNull final Integer minutes)
    {
        return calc(date, DateTime::plusMinutes, minutes);
    }

    /**
     * Вычет минут из даты без мутации.
     *
     * @param date    дата, от которой ведется вычет минут.
     * @param minutes количество минут для вычета.
     * @return новую дату с учетом вычета минут.
     */
    public static Date minusMinutes(@NonNull final Date date,
                                    @NonNull final Integer minutes)
    {
        return calc(date, DateTime::minusMinutes, minutes);
    }

    /**
     * Добавление дней к дате без мутации.
     *
     * @param date дата, к которой добавляются дни.
     * @param days количество дней для добавления.
     * @return новую дату с учетом добавленных дней.
     */

    public static Date plusDays(@NonNull final Date date,
                                @NonNull final Integer days)
    {
        return calc(date, DateTime::plusDays, days);
    }

    /**
     * Вычет дней из даты без мутации.
     *
     * @param date дата, от которой ведется вычет дней.
     * @param days количество дней для вычета.
     * @return новую дату с учетом вычета дней.
     */
    public static Date minusDays(@NonNull final Date date,
                                 @NonNull final Integer days)
    {
        return calc(date, DateTime::minusDays, days);
    }

    /**
     * Добавление определенного количества месяцев из даты без мутации.
     *
     * @param date   дата, от которой ведется вычет дней.
     * @param months количество месяцев для вычета.
     * @return новую дату с учетом добавления месяцев.
     */
    public static Date plusMonths(@NonNull final Date date,
                                  @NonNull final Integer months)
    {
        return calc(date, DateTime::plusMonths, months);
    }

    /**
     * Вычет определенного количества месяцев из даты без мутации.
     *
     * @param date   дата, от которой ведется вычет дней.
     * @param months количество месяцев для вычета.
     * @return новую дату с учетом вычета месяцев.
     */
    public static Date minusMonths(@NonNull final Date date,
                                   @NonNull final Integer months)
    {
        return calc(date, DateTime::minusMonths, months);
    }

    /**
     * Вычисление разницы между датами в днях. Разница в часах и меньших единицах учитвается.
     * Т.е. если из 03.04.18 00:01 вычесть 03.04.18 00:00 получим один неполный день - 00:01.
     * Такое значение считается как 1 день.
     *
     * @param start начальная дата.
     * @param end   конечная дата.
     * @return разница в днях.
     */
    public static Integer notWholeDayDiff(@NonNull final Date start,
                                          @NonNull final Date end)
    {
        return DateTimeUtils.wholeDayDiff(start, end) + 1;
    }

    /**
     * Вычисление разницы между датами в днях. Разница в часах и меньших единицах не учитвается.
     * Т.е. если из 03.04.18 23:59 вычесть 03.04.18 00:00 получим один неполный день - 23:59.
     * Такое значение считается как 0 дней.
     *
     * @param start начальная дата.
     * @param end   конечная дата.
     * @return разница в днях.
     */
    public static Integer wholeDayDiff(@NonNull final Date start,
                                       @NonNull final Date end)
    {
        final Long difference = getDateDiff(start, end);
        final long days = TimeUnit.MILLISECONDS.toDays(difference);
        return (int) days;
    }

    public static Long getDateDiff(@NonNull final Date start,
                                   @NonNull final Date end)
    {
        final long startTime = start.getTime();
        final long endTime = end.getTime();
        final long diff = endTime - startTime;
        return diff;
    }

    /**
     * Формирование даты по номерам года, месяца и дня.
     * При передаче номера месяца должна быть использована нумерация с единицы.
     *
     * @param year        номер года.
     * @param monthOfYear номер месяца в году.
     * @param dayOfMonth  номер дня в месяце.
     * @return сформированную дату, соответствующую входящим значениям.
     */
    public static Date dateTime(final int year,
                                final int monthOfYear,
                                final int dayOfMonth)
    {
        return new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
    }

    /**
     * Формирование даты по номерам года, месяца и дня.
     * При передаче номера месяца должна быть использована нумерация с единицы.
     *
     * @param year        номер года.
     * @param monthOfYear номер месяца в году.
     * @param dayOfMonth  номер дня в месяце.
     * @param hourOfDay   номер года.
     * @return сформированную дату, соответствующую входящим значениям.
     */
    public static Date dateTime(final int year,
                                final int monthOfYear,
                                final int dayOfMonth,
                                final int hourOfDay)
    {
        return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, 0).toDate();
    }


    public static Date defaultDate(final Date date) {
        return date != null ? date : new Date(0);
    }

    private static <T> Date calc(final Date date,
                                 final BiFunction<DateTime, T, DateTime> operation,
                                 final T value)
    {
        return operation.apply(new DateTime(date), value).toDate();
    }

    public static Date plus(final Date oldDuration, final Long suspenseDuration) {
        return calc(oldDuration, DateTimeUtils::plusMillis, suspenseDuration);
    }

    public static Date plus(final Date start, final Date end) {
        return calc(start, DateTimeUtils::plusMillis, end.getTime());
    }

    private static DateTime plusMillis(DateTime start, Long millis) {
        return new DateTime(start.getMillis() + millis);
    }

    public static final class Between {

        private final Date start;
        private final Date end;

        Between(final Date start, final Date end) {
            this.start = start;
            this.end = end;
        }

        public int toYears() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Years.yearsBetween(start, end).getYears();
        }

        public int toMonth() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Months.monthsBetween(start, end).getMonths();
        }

        public int toDays() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Days.daysBetween(start, end).getDays();
        }

        public int toHours() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Hours.hoursBetween(start, end).getHours();
        }

        public int toMinutes() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Minutes.minutesBetween(start, end).getMinutes();
        }

        public int toSeconds() {
            final DateTime start = new DateTime(this.start);
            final DateTime end = new DateTime(this.end);
            return Seconds.secondsBetween(start, end).getSeconds();
        }

        public long toMillis() {
            return this.end.getTime() - this.start.getTime();
        }


        public Date toDate() {
            return new Date(this.end.getTime() - this.start.getTime());
        }

        public String format(String pattern) {
            return new LocalTime(this.end.getTime() - this.start.getTime()).toString(pattern);
        }
    }
}