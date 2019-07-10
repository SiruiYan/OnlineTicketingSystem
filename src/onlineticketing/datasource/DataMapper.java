package onlineticketing.datasource;

import onlineticketing.domain.DomainObject;
import onlineticketing.domain.Film;
import onlineticketing.domain.Order;
import onlineticketing.domain.Schedule;
import onlineticketing.domain.Ticket;
import onlineticketing.domain.User;

public interface DataMapper {
	
	/**
	 * Get the DataMapper of a class
	 * @param class1  the class of a class
	 * @return  the responding DataMapper of class1
	 */
	public static DataMapper getMapper(Class<?> class1) {
		DataMapper mapper = null;
		if(class1 == Schedule.class)
			mapper = new ScheduleMapper();
		else if (class1 == User.class)
			mapper = new UserMapper();
		else if (class1 == Ticket.class)
			mapper = new TicketMapper();
		else if (class1 == Order.class)
			mapper = new OrderMapper();
		else if (class1 == Film.class)
			mapper = new FilmMapper();
		return mapper;
	};
	
	/**
	 * Insert a new domainObject into database
	 * @param obj  the new domainObject object
	 */
	public void insert(DomainObject obj);
	
	/**
	 * Update the information of a domainObject in database
	 * @param obj  the domainObject object with updated information
	 */
	public void update(DomainObject obj);
	
	/**
	 * Delete a DomainObject in database
	 * @param obj  the domainObject to be deleted
	 */
	public void delete(DomainObject obj);
}
