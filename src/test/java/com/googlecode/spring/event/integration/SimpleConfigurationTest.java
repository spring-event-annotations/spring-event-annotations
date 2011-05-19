package com.googlecode.spring.event.integration;

import junit.framework.Assert;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.googlecode.spring.event.EventRegistry;
import com.googlecode.spring.event.annotation.ObservesAnotationBeanPostProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/simple-context.xml")
@Ignore
public class SimpleConfigurationTest {
	
	@Autowired
	ApplicationContext context;
	
	@Test
	public void testBasicConfiguration() throws InterruptedException {
		Assert.assertNotNull(context.getBean(ObservesAnotationBeanPostProcessor.class));
		EventRegistry event = context.getBean(EventRegistry.class);
		Assert.assertNotNull(event);
		Assert.assertTrue(event instanceof EventRegistry);
		
		event.fire("string");
		event.fire(new Bean());
	}
}
