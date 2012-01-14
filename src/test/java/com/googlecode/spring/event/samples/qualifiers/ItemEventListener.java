package com.googlecode.spring.event.samples.qualifiers;

import org.springframework.stereotype.Service;

import com.googlecode.spring.event.annotation.Observes;

@Service
public class ItemEventListener  {

	public void itemModified(@Observes @ItemModified Item item) {
		item.eventHandled("itemModified");
	}
	
	public void itemCreated(@Observes @ItemCreated Item item) {
		item.eventHandled("itemCreated");
	}
}
