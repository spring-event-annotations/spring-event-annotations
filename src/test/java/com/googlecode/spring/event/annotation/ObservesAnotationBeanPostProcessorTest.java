package com.googlecode.spring.event.annotation;

import static org.mockito.Mockito.*;

import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;
import org.mockito.Mockito;
import org.springframework.core.task.SyncTaskExecutor;

import com.googlecode.spring.event.EventHandledCallback;
import com.googlecode.spring.event.EventRegistry;

@SuppressWarnings("unused")
public class ObservesAnotationBeanPostProcessorTest {
	
	private ObservesAnotationBeanPostProcessor proc;
	private EventRegistry eventRegistry;
	private EventHandledCallback verifier = mock(EventHandledCallback.class);

	@Before
	public void before() {
		eventRegistry = new EventRegistry();
		eventRegistry.setExecutor(new SyncTaskExecutor());
		proc = new ObservesAnotationBeanPostProcessor();
		proc.setEventRegistry(eventRegistry);
	}

	@Test(expected = IllegalStateException.class)
	public void postProcessAfterInitialization_2AnnotatedMethodParameters_ThrowsException() {
		proc.postProcessAfterInitialization(new Object() {
			void observeString1(@Observes String param1, @Observes String param2) {
			}
		}, getClass().getName());
		eventRegistry.fire("event");
	}
	
	@Test(expected = IllegalStateException.class)
	public void postProcessAfterInitialization_1AnnotatedMethodParameterAndOtherNot_ThrowsException() {
		proc.postProcessAfterInitialization(new Object() {
			void observeString1(@Observes String param1, String param2) {
			}
		}, getClass().getName());
		eventRegistry.fire("event");
	}
	
	@Test(expected = IllegalStateException.class)
	public void postProcessAfterInitialization_1AnnotatedVarArgsParameter_ThrowsException() {
		proc.postProcessAfterInitialization(new Object() {
			void observeString1(@Observes String... param1) {
			}
		}, getClass().getName());
		eventRegistry.fire("varargs");
	}

	@Test
	public void postProcessAfterInitialization_1AnnotatedMethodParameter_EventsExecuted() {
		proc.postProcessAfterInitialization(new StringListener1(verifier), "bean");
		eventRegistry.fire("event");
		verify(verifier).eventHandled("observeString1");
	}
	
	@Test
	public void postProcessAfterInitialization_2Methods_EventsExecuted() {
		proc.postProcessAfterInitialization(new StringListener2(verifier), "bean");
		eventRegistry.fire("event");
		verify(verifier).eventHandled("observeString1");
		verify(verifier).eventHandled("observeString2");
	}
}
