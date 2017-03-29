/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.model;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.TzId;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.validate.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * Created on 16/03/2005
 *
 * $Id$
 *
 * @author Ben
 *
 *         A test case for creating calendars.
 */
public class CalendarTest extends TestCase {

	private static Logger log = LoggerFactory.getLogger(Calendar.class);

	private Calendar calendar;

	/**
	 * @param testMethod
	 * @param calendar
	 */
	public CalendarTest(String testMethod, Calendar calendar) {
		super(testMethod);
		this.calendar = calendar;
	}

	/**
	 * @throws ValidationException
	 */
	public void testValid() throws ValidationException, PropertyNotFoundException {
		calendar.validate();
	}

	/**
	 * 
	 */
	public void testInvalid() {
		try {
			calendar.validate();
			fail("Should throw a ValidationException");
		} catch (ValidationException ve) {
			log.trace(ve.toString());
		}
	}

	/**
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ParseException
	 */
//	public static TestSuite suite() throws ParseException, IOException, URISyntaxException, PropertyNotFoundException {
//		TestSuite suite = new TestSuite();
//		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
//
//		Calendar baseCalendar = new Calendar();
//		baseCalendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
//		baseCalendar.getProperties().add(Version.VERSION_2_0);
//		baseCalendar.getProperties().add(CalScale.GREGORIAN);
//		suite.addTest(new CalendarTest("testValid", baseCalendar));
//
//		VTimeZone tz = registry.getTimeZone("Australia/Melbourne").getVTimeZone();
//		TzId tzParam = new TzId(tz.getProperty(Property.TZID).getValue());
//		baseCalendar.getComponents().add(tz);
//
//		// Add events, etc..
//		Calendar calendar = new Calendar(baseCalendar);
//		java.util.Calendar calStart = java.util.Calendar.getInstance();
//		calStart.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
//		calStart.set(java.util.Calendar.HOUR_OF_DAY, 9);
//		calStart.clear(java.util.Calendar.MINUTE);
//		calStart.clear(java.util.Calendar.SECOND);
//
//		java.util.Calendar calEnd = java.util.Calendar.getInstance();
//		calEnd.setTime(calStart.getTime());
//		calEnd.add(java.util.Calendar.YEAR, 1);
//		for (int i = 0; i < 3; i++) {
//
//			VEvent week1UserA = new VEvent(new Date(calStart.getTime().getTime()), new Dur(0, 8, 0, 0),
//					"Week 1 - User A");
//			week1UserA.getProperty(Property.DTSTART).getParameters().replace(tzParam);
//			week1UserA.getProperty(Property.DTSTART).getParameters().replace(Value.DATE);
//
//			Recur week1UserARecur = new Recur(Recur.WEEKLY, new Date(calEnd.getTime().getTime()));
//			week1UserARecur.setInterval(3);
//			addWeekDays(week1UserARecur);
//			week1UserARecur.getHourList().add(new Integer(9));
//			week1UserA.getProperties().add(new RRule(week1UserARecur));
//			week1UserA.getProperties().add(new Uid("000001@modularity.net.au"));
//
//			calStart.add(java.util.Calendar.WEEK_OF_YEAR, 1);
//			calEnd.add(java.util.Calendar.WEEK_OF_YEAR, 1);
//
//			calendar.getComponents().add(week1UserA);
//
//			suite.addTest(new CalendarTest("testValid", calendar));
//
//			// test event date ranges..
//			calendar = new Calendar(baseCalendar);
//
//			calStart = java.util.Calendar.getInstance();
//			calStart.set(java.util.Calendar.YEAR, 2006);
//			calStart.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
//			calStart.set(java.util.Calendar.DAY_OF_MONTH, 1 + i);
//			calStart.set(java.util.Calendar.HOUR_OF_DAY, 9);
//			calStart.clear(java.util.Calendar.MINUTE);
//			calStart.clear(java.util.Calendar.SECOND);
//
//			calEnd = java.util.Calendar.getInstance();
//			calEnd.setTime(calStart.getTime());
//			calEnd.add(java.util.Calendar.YEAR, 1);
//
//			week1UserA = new VEvent(new Date(calStart.getTime().getTime()), new Dur(0, 8, 0, 0),
//					"Week" + i + " - User A");
//			week1UserA.getProperty(Property.DTSTART).getParameters().replace(tzParam);
//			week1UserA.getProperty(Property.DTSTART).getParameters().replace(Value.DATE);
//
//			week1UserARecur = new Recur(Recur.WEEKLY, new Date(calEnd.getTime().getTime()));
//			week1UserARecur.setInterval(3);
//
//			week1UserARecur = addWeekDays(week1UserARecur);
//
//			calendar.getComponents().add(week1UserA);
//		}
//		suite.addTest(new CalendarTest("testValid", calendar));
//
//		// test invalid calendar..
//		// calendar = new Calendar(baseCalendar);
//		// calendar.getComponents().add(new Daylight());
//		// suite.addTest(new CalendarTest("testInvalid", calendar));
//
//		return suite;
//	}

	private static Recur addWeekDays(Recur week) {
		week.getDayList().add(WeekDay.MO);
		week.getDayList().add(WeekDay.TU);
		week.getDayList().add(WeekDay.WE);
		week.getDayList().add(WeekDay.TH);
		week.getDayList().add(WeekDay.FR);
		week.getHourList().add(new Integer(9));
		return week;
	}

	/**
	 * @throws ValidationException
	 */
	public void testGetEventDateRanges() throws ValidationException {
		// Add events, etc..
		// VTimeZone tz =
		// registry.getTimeZone("Australia/Melbourne").getVTimeZone();
		// TzId tzParam = new TzId(tz.getProperty(Property.TZID).getValue());

		calendar.validate();

		// Start the logic testing.
		java.util.Calendar queryStartCal = java.util.Calendar.getInstance();
		java.util.Calendar queryEndCal = java.util.Calendar.getInstance();

		queryStartCal.set(2006, java.util.Calendar.JULY, 1, 9, 0, 0);
		queryEndCal.set(2006, java.util.Calendar.AUGUST, 1, 9, 0, 0);

		VFreeBusy request = new VFreeBusy(new DateTime(queryStartCal.getTime()), new DateTime(queryEndCal.getTime()));

		VFreeBusy reply = new VFreeBusy(request, calendar.getComponents());
		/*
		 * SortedSet dateRangeSet =
		 * calendar.getEventDateRanges(queryStartCal.getTime(),
		 * queryEndCal.getTime());
		 */

		log.info(reply.toString());
	}
}
