package onlineticketing.domain;

public abstract class DomainObject {
	
	protected String id;

	/**
	 * Get the id of a DomainObject object
	 * @return the id of the DomainObject
	 */
	public String getId() {
		return id;
	}
}
