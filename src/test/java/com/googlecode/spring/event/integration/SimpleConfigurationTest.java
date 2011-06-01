package com.googlecode.spring.event.integration;

import junit.framework.Assert;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.spring.event.*;
import com.googlecode.spring.event.annotation.ObservesAnotationBeanPostProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simple-context.xml")
//@Ignore
public class SimpleConfigurationTest {
	
	@Autowired
	ApplicationContext context;
	
	@Test
	public void testBasicConfiguration() throws InterruptedException {
		Assert.assertNotNull(context.getBean(ObservesAnotationBeanPostProcessor.class));
		Event<String> event1 = context.getBean(EventRegistry.class);
		Event<Bean> event2 = context.getBean(EventRegistry.class);
		
		Assert.assertNotNull(event1);
		Assert.assertTrue(event2 instanceof EventRegistry);
		
		event1.fire("string");
		event2.fire(new Bean());
	}
}
