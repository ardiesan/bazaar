package com.jadc.bazaar.event;

import org.springframework.context.ApplicationEvent;

public class AccountEvent extends ApplicationEvent {
	public AccountEvent(Object source) {
		super(source);
	}
}
