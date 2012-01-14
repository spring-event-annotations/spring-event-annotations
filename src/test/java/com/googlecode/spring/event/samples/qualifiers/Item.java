package com.googlecode.spring.event.samples.qualifiers;

import com.googlecode.spring.event.EventHandledCallbackAware;
import com.googlecode.spring.event.EventHandledCallback;

public class Item extends EventHandledCallbackAware {

	public Item(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

}
