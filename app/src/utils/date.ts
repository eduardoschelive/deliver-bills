import {
  CalendarDate,
  CalendarDateTime,
  fromDate,
  getLocalTimeZone,
  toCalendarDate,
  ZonedDateTime,
} from '@internationalized/date'

function toDDMMYYYY(date: Date) {
  return date.toLocaleDateString('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  })
}

function convertToCalendarDate(date: Date) {
  const timezone = getLocalTimeZone()
  const zonedCalendar = fromDate(date, timezone)
  return toCalendarDate(zonedCalendar)
}

function convertToNativeDate(
  date: ZonedDateTime | CalendarDate | CalendarDateTime
) {
  const timezone = getLocalTimeZone()
  return date.toDate(timezone)
}

export const DateUtils = {
  toDDMMYYYY,
  convertToCalendarDate,
  convertToNativeDate,
}
