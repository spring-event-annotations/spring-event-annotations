# CDI style event handling for Spring

## Configuration

### Programmatic

Programmatic configuration can be done by importing the ```com.github.spring.event.config.EventConfiguration```

```java
@ComponentScan
@EnableAutoConfiguration
@Configuration
@Import({com.github.spring.event.config.EventConfiguration.class})
public class MyConfig {...{
```

### XML

Configuring in XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:event="http://spring-event-annotations.github.com"
	xsi:schemaLocation="http://spring-event-annotations.github.com http://spring-event-annotations.github.com/spring-event-1.0.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd">
	<context:annotation-config />
    <context:component-scan base-package="com.github.spring.event.samples.basic" />
    <bean id="executor" class="org.springframework.core.task.SyncTaskExecutor" />
    <!-- 
         Executor attribute is optional and it specifies the java.util.Executor 
         instance to use when invoking asynchronous methods. If not provided, 
         an instance of org.springframework.core.task.SimpleAsyncTaskExecutor 
         will be used by default. 
    -->
    <event:annotation-driven executor="executor"/>
</beans>
```

## Firing events

Event class must be created

    public class OnConnectedEvent {
    
       private boolean connected;
    
       public OnConnectedEvent(boolean c) {
          connected = c;
       }
    
       public boolean isConnected() {
          return connected;
       }
    
       public void setConnected(boolean connected) {
          this.connected = connected;
       }
    
       @Override
       public boolean equals(Object o) {
          if (this == o) return true;
          if (!(o instanceof OnConnectedEvent)) return false;
          OnConnectedEvent that = (OnConnectedEvent) o;
          return Objects.equal(connected, that.connected);
       }
    
       @Override
       public int hashCode() {
          return Objects.hashCode(connected);
       }
    }

Events must be injected and then fired

    @Autowired
    private Event<OnConnectedEvent> connectionEvent;

    private void fireEvent(boolean connected) {
        connectionEvent.fire(new OnConnectedEvent(connected));
    }

## Observing events

Methods annotated with *@Observes* will respond to events.

    public void onConnectionChanged(@Observes OnConnectedEvent evt) {
        if(evt.isConnected()) {
         //TODO   
        } else {
         //TODO
        }
    }