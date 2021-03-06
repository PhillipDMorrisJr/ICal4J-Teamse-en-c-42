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

import java.util.GregorianCalendar;
import java.text.ParseException;
//import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

import junit.framework.TestCase;
//import junit.framework.TestSuite;
//import net.fortuna.ical4j.util.TimeZones;

/**
 * $Id$
 *
 * Created on 30/06/2005, Modified on 30/03/2017
 *
 * @author Ben Fortuna && Caleb Farara
 * @version Spring 2017
 */
public class DateTest extends TestCase {

	private final int EASTERN_STANDAR_TIME = 240;
	private Date date;
	//
	// private java.util.Date date2;
	//
	// private String expectedString;
	//
	// /**
	// * @param date
	// * @param expectedString
	// */
	// public DateTest(Date date, String expectedString) {
	// super("testToString");
	// this.date = date;
	// this.expectedString = expectedString;
	// }
	//
	// /**
	// * @param date
	// * @param date2
	// */
	// public DateTest(Date date, java.util.Date date2) {
	// super("testEquals");
	// this.date = date;
	// this.date2 = date2;
	// }
	//
	// /**
	// *
	// */
	// public void testToString() {
	// assertEquals(expectedString, date.toString());
	// }
	//
	// /**
	// *
	// */
	// public void testEquals() {
	// assertEquals(date2, date);
	// }
	//
	// /* (non-Javadoc)
	// * @see junit.framework.TestCase#getName()
	// */
	// public String getName() {
	// return super.getName() + " [" + date.toString() + "]";
	// }
	//
	// /**
	// * @return
	// * @throws ParseException
	// */
	// public static TestSuite suite() throws ParseException {
	// TestSuite suite = new TestSuite();
	// suite.addTest(new DateTest(new Date(0l), "19700101"));
	//
	// Calendar cal = Calendar.getInstance(TimeZones.getDateTimeZone());
	// cal.clear();
	// cal.set(Calendar.YEAR, 1984);
	// // months are zero-based..
	// cal.set(Calendar.MONTH, 3);
	// cal.set(Calendar.DAY_OF_MONTH, 17);
	// suite.addTest(new DateTest(new Date(cal.getTime()), "19840417"));
	//
	// suite.addTest(new DateTest(new Date("20050630"), "20050630"));
	//
	// // Test equality of Date instances created using different constructors..
	// Calendar calendar = Calendar.getInstance(TimeZones.getDateTimeZone());
	// calendar.clear();
	// calendar.set(2005, 0, 1);
	// calendar.set(Calendar.MILLISECOND, 1);
	// suite.addTest(new DateTest(new Date(calendar.getTime()), new
	// Date("20050101").toString()));
	// suite.addTest(new DateTest(new Date(calendar.getTime()), new
	// Date("20050101")));
	//
	// calendar = Calendar.getInstance(TimeZones.getDateTimeZone());
	// calendar.clear();
	// calendar.set(2005, 0, 1);
	// calendar.clear(Calendar.HOUR_OF_DAY);
	// calendar.clear(Calendar.MINUTE);
	// calendar.clear(Calendar.SECOND);
	// calendar.clear(Calendar.MILLISECOND);
	// suite.addTest(new DateTest(new Date("20050101"), calendar.getTime()));
	// return suite;
	// }

	/**
	 * Test for default constructor - Default date value formatted "yyyyMMdd"
	 * with precision down to the day.
	 */
	@Test
	public void testForDefaultConstructorDefaultDateFormattedYyyyMMdd() {
		this.date = new Date();
		assertEquals("20170330", this.date.toString());
	}

	/**
	 * Test for 2 parameter (precision, timezone) constructor - Date value
	 * formatted "yyyyMMdd" from provided precision and timezone.
	 */
	@Test
	public void testForTwoParameterPrecisionTimezoneFormattedYyyyMMdd() {
		this.date = new Date(1, TimeZone.getTimeZone("America/New York"));
		assertEquals("20170330", this.date.toString());
		assertEquals(EASTERN_STANDAR_TIME, this.date.getTimezoneOffset());
	}

	/**
	 * Test for 1 parameter (time) constructor - Date value formatted "yyyyMMdd"
	 * from provided time in milliseconds.
	 */
	@Test
	public void testForOneParameterTimeConstructorFormattedYyyyMMdd() {
		this.date = new Date(1490877332557L);
		assertEquals("20170330", this.date.toString());
	}

	/**
	 * Test for 3 parameter (time, precision, timezone) constructor - Date value
	 * formatted "yyyyMMdd" from provided time in milliseconds and precision and
	 * timezone.
	 */
	@Test
	public void testThreeParameterTimePrecisionTimezoneFormattedYyyyMMdd() {
		this.date = new Date(1490877332557L, 1, TimeZone.getTimeZone("America/New York"));
		assertEquals("20170330", this.date.toString());
		assertEquals(EASTERN_STANDAR_TIME, this.date.getTimezoneOffset());

	}

	/**
	 * Test for 1 parameter (calendar) constructor - Date value formatted
	 * "yyyyMMdd" from provided Calendar object.
	 */
	@Test
	public void testOneParameterCalendarFormattedYyyyMMdd() {
		Calendar calendar = new GregorianCalendar();
		this.date = new Date(calendar);

		// calendar date matches time of calendar initialization
		assertEquals("20170330", this.date.toString());
	}

	/**
	 * Test for 1 parameter (date) constructor - Date value formatted "yyyyMMdd"
	 * from provided Date object.
	 */
	@Test
	public void testOneParameterDateFormattedYyyyMMdd() {
		this.date = new Date(new Date());
		assertEquals("20170330", this.date.toString());
	}

	/**
	 * Test for 1 parameter (string of date) constructor - Date value formatted
	 * "yyyyMMdd" from provided string representation of the date.
	 */
	@Test
	public void testOneParameterStringOfDateFormattedYyyyMMdd() {
		try {
			this.date = new Date("20170425");
		} catch (ParseException e) {
			System.out.println("Could not format date.");
			e.printStackTrace();
		}
		assertEquals("20170425", this.date.toString());
	}

	/**
	 * Test for 2 parameter (string of date and date pattern) constructor - Date
	 * value formatted "yyyyMMdd" from provided string representation of the
	 * date and date pattern.
	 */
	@Test
	public void testTwoarametersStringOfDateAndDatePatternFormattedYyyyMMdd() {
		try {
			this.date = new Date("25.04.2017", "dd.MM.yy");
		} catch (ParseException e) {
			System.out.println("Could not format date.");
			e.printStackTrace();
		}
		assertEquals("20170425", this.date.toString());
	}
}
