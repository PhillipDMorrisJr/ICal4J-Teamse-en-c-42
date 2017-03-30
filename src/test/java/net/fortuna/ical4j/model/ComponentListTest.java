/**
 * 
 */
package net.fortuna.ical4j.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import net.fortuna.ical4j.model.component.Standard;
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

		Standard std = new Standard();
		this.componentList.add(std);
		ComponentList<Component> testComponentList = new ComponentList<Component>();
		testComponentList.add(std);
		assertEquals(testComponentList, this.componentList.getComponents("STANDARD"));
	}
	
	@Test
	public void testGetComponentsWithAThreeRecords() {
		this.componentList = new ComponentList<Component>();		
		Standard std = new Standard();
		Standard std2 = new Standard();
		Standard std3 = new Standard();
		this.componentList.add(std);
		this.componentList.add(std2);
		this.componentList.add(std3);
		ComponentList<Component> testComponentList = new ComponentList<Component>();
		testComponentList.add(std);
		assertEquals(testComponentList, this.componentList.getComponents("STANDARD"));
	}
	
	@Test
	public void testGetComponentsWithAMultipleRecords() {
		this.componentList = new ComponentList<Component>();		
		Date componentDate = new Date(1000);
		Dur componentDur = new Dur(0, 0, 0, 0);
		Component component1 = new VEvent(componentDate, componentDur, "testDate1");
		VEvent component2 = new VEvent();
		Standard std = new Standard();
		this.componentList.add(component1);
		this.componentList.add(component2);
		this.componentList.add(std);
		ComponentList<Component> testComponentList = new ComponentList<Component>();
		testComponentList.add(std);
		assertEquals(testComponentList, this.componentList.getComponents("STANDARD"));
	}

}
