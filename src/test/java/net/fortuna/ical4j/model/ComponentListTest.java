/**
 * 
 */
package net.fortuna.ical4j.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Unit tests for <code>ComponentList</code> base class.
 * 
 * @author Caleb Farara
 * @version Spring 2017
 */
public class ComponentListTest extends TestCase {
	
	private ComponentList<Component> componentList;

	/**
	 * Tests getComponents gets a list of components withthe emprty string as the name.
	 */
	@Test
	public void testGetComponentsWithAnEmptyString() {
		this.componentList = new ComponentList<Component>();
		VEvent component1 = new VEvent();
		this.componentList.add(component1);
		assertEquals(new ComponentList<Component>(), componentList.getComponents(""));
	}

	/**
	 * Tests getComponents gets a list of components with testDate1 string as the name.
	 */
	@Test
	public void testGetComponentsWithASingleRecord() {
		this.componentList = new ComponentList<Component>();		
		Date componentDate = new Date(1000);
		Dur componentDur = new Dur(0, 0, 0, 0);
		Component component1 = new VEvent(componentDate, componentDur, "testDate1");
		VEvent component2 = new VEvent();
		this.componentList.add(component1);
		this.componentList.add(component2);
		assertEquals(component1, componentList.getComponents("VEvent"));
	}
	
	@Test
	public void testGetComponentsWithAThreeRecords() {
		
	}
	
	@Test
	public void testGetComponentsWithAMultipleRecords() {
		
	}

}
