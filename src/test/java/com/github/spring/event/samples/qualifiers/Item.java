package com.github.spring.event.samples.qualifiers;

import com.github.spring.event.EventHandledCallback;
import com.github.spring.event.EventHandledCallbackAware;

public class Item extends EventHandledCallbackAware {

	public Item(EventHandledCallback eventHandledCallback) {
		super(eventHandledCallback);
	}

}
