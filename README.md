Finished tasks:<br/>
Services:
- Created services for working with users, events, tickets.
- Created service for working with auditoriums from property file. 
- Created booking service and discount strategies for booking tickets.
- Created aspects for counters, discounts, lucky winners.
- Created classes for working with database via jdbc.
<br/><br/>
Aspects:
- CounterAspect - count how many times each event was accessed by name, how many times its prices were queried, how many times its tickets were booked.
- DiscountAspect - count how many times each discount was given total and for specific user.
- LuckyWinnerAspect - gives the free ticket to lucku user. 
<br/><br/>
Simple DB access:
- Created DAO objects that use JDBCTemplate to store and retrieve data from DB.
- Added DAO object to store all counters into the database.
<br/><br/>
Spring MVC:
- Create a web application, configure Spring MVC application context and dispatcher servlet.
- For all BookingFacade operations implemented Spring MVC annotation-based controllers.
- Implemented basic web interface via Freemarker template engine.
- For operations, that return list of users implemented alternative controller, that returned result as PDF document.
- Implement batch loading of users and events into system form files via Multipart Resolver.
- Implemented exception handler which redirected all controller exceptions to simple Freemarker view, that just printed exception message.
<br/><br/>
Spring security:
- Configured Spring Security for ticket booking web application.
- Configure access control based on user roles.
- Implemented form-based login, added custom login page, configured DAOAuthenticationProvider and UserDetailsService to load user data from database. Configured logout filter.
- Configured Remember-Me authentication.
- Implemented password encoding during authentication.
<br/><br/>
Spring transactions:
- Added field "balance" to User. Added methods for refilling it.
- Updated ticket booking methods to check and withdraw money from user account according to the ticketPrice for particular event.
- Configured DataSourceTransactionManager in Spring application context.
- Maked ticket booking methods transactional using Spring declarative transactions management.
<br/><br/>
SOAP WS:
- Marked User and Event entites with appropriate JAXB annotations to enable JAXB binding.
- Create SOAP based web-service endpoints to serve all User and Event related operations.
- Added Spring WS application context configuration.
<br/><br/>
REST WS:
- Implemented REST endpoints for ticket booking operations and user/event CRUD operations via annotation based controllers.
- Implemented HttpMessageConverter which can write Ticket objects into application/pdf MIME type.
- Added two types of message converters - newly implemented application/pdf http message converter and Jackson JSON message converter.
