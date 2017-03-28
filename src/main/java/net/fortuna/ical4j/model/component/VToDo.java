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
package net.fortuna.ical4j.model.component;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.Strings;
import net.fortuna.ical4j.validate.PropertyValidator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.Validator;
import net.fortuna.ical4j.validate.component.*;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * $Id$ [Apr 5, 2004]
 *
 * Defines an iCalendar VTODO component.
 * 
 * <pre>
 *       4.6.2 To-do Component
 *  
 *          Component Name: VTODO
 *  
 *          Purpose: Provide a grouping of calendar properties that describe a
 *          to-do.
 *  
 *          Formal Definition: A &quot;VTODO&quot; calendar component is defined by the
 *          following notation:
 *  
 *            todoc      = &quot;BEGIN&quot; &quot;:&quot; &quot;VTODO&quot; CRLF
 *                         todoprop *alarmc
 *                         &quot;END&quot; &quot;:&quot; &quot;VTODO&quot; CRLF
 *  
 *            todoprop   = *(
 *  
 *                       ; the following are optional,
 *                       ; but MUST NOT occur more than once
 *  
 *                       class / completed / created / description / dtstamp /
 *                       dtstart / geo / last-mod / location / organizer /
 *                       percent / priority / recurid / seq / status /
 *                       summary / uid / url /
 *  
 *                       ; either 'due' or 'duration' may appear in
 *                       ; a 'todoprop', but 'due' and 'duration'
 *                       ; MUST NOT occur in the same 'todoprop'
 *  
 *                       due / duration /
 *  
 *                       ; the following are optional,
 *                       ; and MAY occur more than once
 *                       attach / attendee / categories / comment / contact /
 *                       exdate / exrule / rstatus / related / resources /
 *                       rdate / rrule / x-prop
 *  
 *                       )
 * </pre>
 * 
 * Example 1 - Creating a todo of two (2) hour duration starting tomorrow:
 * 
 * <pre>
 * <code>
 * java.util.Calendar cal = java.util.Calendar.getInstance();
 * // tomorrow..
 * cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
 * cal.set(java.util.Calendar.HOUR_OF_DAY, 11);
 * cal.set(java.util.Calendar.MINUTE, 00);
 * 
 * VToDo documentation = new VEvent(cal.getTime(), 1000 * 60 * 60 * 2,
 *         &quot;Document calendar component usage&quot;);
 * 
 * // add timezone information..
 * VTimeZone tz = VTimeZone.getDefault();
 * TzId tzParam = new TzId(tz.getProperties().getProperty(Property.TZID)
 *         .getValue());
 * documentation.getProperties().getProperty(Property.DTSTART).getParameters()
 *         .add(tzParam);
 * </code>
 * </pre>
 * 
 * @author Ben Fortuna
 */
public class VToDo extends CalendarComponent {

	private static final long serialVersionUID = -269658210065896668L;

	private final Map<Method, Validator> methodValidators = new HashMap<Method, Validator>();
	{
		methodValidators.put(Method.ADD, new VToDoAddValidator());
		methodValidators.put(Method.CANCEL, new VToDoCancelValidator());
		methodValidators.put(Method.COUNTER, new VToDoCounterValidator());
		methodValidators.put(Method.DECLINE_COUNTER, new VToDoDeclineCounterValidator());
		methodValidators.put(Method.PUBLISH, new VToDoPublishValidator());
		methodValidators.put(Method.REFRESH, new VToDoRefreshValidator());
		methodValidators.put(Method.REPLY, new VToDoReplyValidator());
		methodValidators.put(Method.REQUEST, new VToDoRequestValidator());
	}

	private ComponentList<VAlarm> alarms = new ComponentList<VAlarm>();

	/**
	 * Default constructor.
	 */
	public VToDo() {
		this(true);
	}

	public VToDo(boolean initialise) {
		super(VTODO);
		if (initialise) {
			getProperties().add(new DtStamp());
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param properties
	 *            a list of properties
	 */
	public VToDo(final PropertyList properties) {
		super(VTODO, properties);
	}

	/**
	 * Constructs a new VTODO instance starting at the specified time with the
	 * specified summary.
	 * 
	 * @param start
	 *            the start date of the new todo
	 * @param summary
	 *            the todo summary
	 */
	public VToDo(final Date start, final String summary) {
		this();
		getProperties().add(new DtStart(start));
		getProperties().add(new Summary(summary));
	}

	/**
	 * Constructs a new VTODO instance starting and ending at the specified
	 * times with the specified summary.
	 * 
	 * @param start
	 *            the start date of the new todo
	 * @param due
	 *            the due date of the new todo
	 * @param summary
	 *            the todo summary
	 */
	public VToDo(final Date start, final Date due, final String summary) {
		this();
		getProperties().add(new DtStart(start));
		getProperties().add(new Due(due));
		getProperties().add(new Summary(summary));
	}

	/**
	 * Constructs a new VTODO instance starting at the specified times, for the
	 * specified duration, with the specified summary.
	 * 
	 * @param start
	 *            the start date of the new todo
	 * @param duration
	 *            the duration of the new todo
	 * @param summary
	 *            the todo summary
	 */
	public VToDo(final Date start, final Dur duration, final String summary) {
		this();
		getProperties().add(new DtStart(start));
		getProperties().add(new Duration(duration));
		getProperties().add(new Summary(summary));
	}

	/**
	 * Returns the list of alarms for this todo.
	 * 
	 * @return a component list
	 */
	public final ComponentList<VAlarm> getAlarms() {
		return alarms;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		String buffer = BEGIN + ':' + getName() + Strings.LINE_SEPARATOR + getProperties() + getAlarms() + END + ':'
				+ getName() + Strings.LINE_SEPARATOR;
		return buffer;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void validate(final boolean recurse) throws ValidationException {

		// validate that getAlarms() only contains VAlarm components
		for (VAlarm component : getAlarms()) {
			component.validate(recurse);
		}

		if (!CompatibilityHints.isHintEnabled(CompatibilityHints.KEY_RELAXED_VALIDATION)) {

			// From "4.8.4.7 Unique Identifier":
			// Conformance: The property MUST be specified in the "VEVENT",
			// "VTODO",
			// "VJOURNAL" or "VFREEBUSY" calendar components.
			PropertyValidator.getInstance().assertOne(Property.UID, getProperties());

			// From "4.8.7.2 Date/Time Stamp":
			// Conformance: This property MUST be included in the "VEVENT",
			// "VTODO",
			// "VJOURNAL" or "VFREEBUSY" calendar components.
			PropertyValidator.getInstance().assertOne(Property.DTSTAMP, getProperties());
		}

		/*
		 * ; the following are optional, ; but MUST NOT occur more than once
		 * class / completed / created / description / dtstamp / dtstart / geo /
		 * last-mod / location / organizer / percent / priority / recurid / seq
		 * / status / summary / uid / url /
		 */
		CollectionUtils.forAllDo(
				Arrays.asList(Property.CLASS, Property.COMPLETED, Property.CREATED, Property.DESCRIPTION,
						Property.DTSTAMP, Property.DTSTART, Property.GEO, Property.LAST_MODIFIED, Property.LOCATION,
						Property.ORGANIZER, Property.PERCENT_COMPLETE, Property.PRIORITY, Property.RECURRENCE_ID,
						Property.SEQUENCE, Property.STATUS, Property.SUMMARY, Property.UID, Property.URL),
				new Closure<String>() {
					@Override
					public void execute(String input) {
						PropertyValidator.getInstance().assertOneOrLess(input, getProperties());
					}
				});

		Status status;
		try {
			status = (Status) getProperty(Property.STATUS);
			if (status != null && !Status.VTODO_NEEDS_ACTION.getValue().equals(status.getValue())
					&& !Status.VTODO_COMPLETED.getValue().equals(status.getValue())
					&& !Status.VTODO_IN_PROCESS.getValue().equals(status.getValue())
					&& !Status.VTODO_CANCELLED.getValue().equals(status.getValue())) {
				throw new ValidationException("Status property [" + status.toString() + "] may not occur in VTODO");
			}
		} catch (PropertyNotFoundException e1) {

			/*
			 * ; either 'due' or 'duration' may appear in ; a 'todoprop', but
			 * 'due' and 'duration' ; MUST NOT occur in the same 'todoprop' due
			 * / duration /
			 */
			try {
				PropertyValidator.getInstance().assertNone(Property.DUE, getProperties());
			} catch (ValidationException | PropertyNotFoundException ve) {
				try {
					PropertyValidator.getInstance().assertNone(Property.DURATION, getProperties());
				} catch (PropertyNotFoundException e) {
					throw new ValidationException();
				}
			}

			/*
			 * ; the following are optional, ; and MAY occur more than once
			 * attach / attendee / categories / comment / contact / exdate /
			 * exrule / rstatus / related / resources / rdate / rrule / x-prop
			 */

			if (recurse) {
				validateProperties();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected Validator getValidator(Method method) {
		return methodValidators.get(method);
	}

	/**
	 * @return the optional access classification property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Clazz getClassification() throws PropertyNotFoundException {
		return (Clazz) getProperty(Property.CLASS);
	}

	/**
	 * @return the optional date completed property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Completed getDateCompleted() throws PropertyNotFoundException {
		return (Completed) getProperty(Property.COMPLETED);
	}

	/**
	 * @return the optional creation-time property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Created getCreated() throws PropertyNotFoundException {
		return (Created) getProperty(Property.CREATED);
	}

	/**
	 * @return the optional description property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Description getDescription() throws PropertyNotFoundException {
		return (Description) getProperty(Property.DESCRIPTION);
	}

	/**
	 * Convenience method to pull the DTSTART out of the property list.
	 * 
	 * @return The DtStart object representation of the start Date
	 * @throws PropertyNotFoundException Property not found
	 */
	public final DtStart getStartDate() throws PropertyNotFoundException {
		return (DtStart) getProperty(Property.DTSTART);
	}

	/**
	 * @return the optional geographic position property
	 * @throws PropertyNotFoundException Geographic position property not found
	 */
	public final Geo getGeographicPos() throws PropertyNotFoundException {
		return (Geo) getProperty(Property.GEO);
	}

	/**
	 * @return the optional last-modified property
	 * @throws PropertyNotFoundException Last modified property not found
	 */
	public final LastModified getLastModified() throws PropertyNotFoundException {
		return (LastModified) getProperty(Property.LAST_MODIFIED);
	}

	/**
	 * @return the optional location property
	 * @throws PropertyNotFoundException Location  property not found
	 */
	public final Location getLocation() throws PropertyNotFoundException {
		return (Location) getProperty(Property.LOCATION);
	}

	/**
	 * @return the optional organizer property
	 * @throws PropertyNotFoundException Organizer property not found
	 */
	public final Organizer getOrganizer() throws PropertyNotFoundException {
		return (Organizer) getProperty(Property.ORGANIZER);
	}

	/**
	 * @return the optional percentage complete property
	 * @throws PropertyNotFoundException Percent complete property not found
	 */
	public final PercentComplete getPercentComplete() throws PropertyNotFoundException {
		return (PercentComplete) getProperty(Property.PERCENT_COMPLETE);
	}

	/**
	 * @return the optional priority property
	 * @throws PropertyNotFoundException Priority property not found
	 */
	public final Priority getPriority() throws PropertyNotFoundException {
		return (Priority) getProperty(Property.PRIORITY);
	}

	/**
	 * @return the optional date-stamp property
	 * @throws PropertyNotFoundException Date stamp property not found
	 */
	public final DtStamp getDateStamp() throws PropertyNotFoundException {
		return (DtStamp) getProperty(Property.DTSTAMP);
	}

	/**
	 * @return the optional sequence number property
	 * @throws PropertyNotFoundException Sequence  property not found
	 */
	public final Sequence getSequence() throws PropertyNotFoundException {
		return (Sequence) getProperty(Property.SEQUENCE);
	}

	/**
	 * @return the optional status property
	 * @throws PropertyNotFoundException Status property not found
	 */
	public final Status getStatus() throws PropertyNotFoundException {
		return (Status) getProperty(Property.STATUS);
	}

	/**
	 * @return the optional summary property
	 * @throws PropertyNotFoundException Property summary not found
	 */
	public final Summary getSummary() throws PropertyNotFoundException {
		return (Summary) getProperty(Property.SUMMARY);
	}

	/**
	 * @return the optional URL property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Url getUrl() throws PropertyNotFoundException {
		return (Url) getProperty(Property.URL);
	}

	/**
	 * @return the optional recurrence identifier property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final RecurrenceId getRecurrenceId() throws PropertyNotFoundException {
		return (RecurrenceId) getProperty(Property.RECURRENCE_ID);
	}

	/**
	 * @return the optional Duration property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Duration getDuration() throws PropertyNotFoundException {
		return (Duration) getProperty(Property.DURATION);
	}

	/**
	 * @return the optional due property
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Due getDue() throws PropertyNotFoundException {
		return (Due) getProperty(Property.DUE);
	}

	/**
	 * Returns the UID property of this component if available.
	 * 
	 * @return a Uid instance, or throw exception if no UID property exists
	 * @throws PropertyNotFoundException Property not found
	 */
	public final Uid getUid() throws PropertyNotFoundException {
		return (Uid) getProperty(Property.UID);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(final Object arg0) {
		if (arg0 instanceof VToDo) {
			return super.equals(arg0) && ObjectUtils.equals(alarms, ((VToDo) arg0).getAlarms());
		}
		return super.equals(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).append(getProperties()).append(getAlarms()).toHashCode();
	}

	/**
	 * Overrides default copy method to add support for copying alarm
	 * sub-components.
	 * 
	 * @return a copy of the instance
	 * @throws ParseException
	 *             where an error occurs parsing data
	 * @throws IOException
	 *             where an error occurs reading data
	 * @throws URISyntaxException
	 *             where an invalid URI is encountered
	 * @see net.fortuna.ical4j.model.Component#copy()
	 */
	public Component copy() throws ParseException, IOException, URISyntaxException {
		final VToDo copy = (VToDo) super.copy();
		copy.alarms = new ComponentList<VAlarm>(alarms);
		return copy;
	}

	public static class Factory extends Content.Factory implements ComponentFactory<VToDo> {

		public Factory() {
			super(VTODO);
		}

		@Override
		public VToDo createComponent() {
			return new VToDo(false);
		}

		@Override
		public VToDo createComponent(PropertyList properties) {
			return new VToDo(properties);
		}

		@Override
		public VToDo createComponent(PropertyList properties, ComponentList subComponents) {
			throw new UnsupportedOperationException(String.format("%s does not support sub-components", VTODO));
		}
	}
}
