package onlineticketing.datasource;

import java.util.ArrayList;
import java.util.List;

import onlineticketing.domain.DomainObject;

public class UnitOfWork {
	private static ThreadLocal current = new ThreadLocal();

	private List<DomainObject> newObjects = new ArrayList<DomainObject>();
	private List<DomainObject> dirtyObjects = new ArrayList<DomainObject>();
	private List<DomainObject> deletedObjects = new ArrayList<DomainObject>();

	/**
	 * Set the current thread to the new thread of unitOfWork
	 */
	public static void newCurrent() {
		setCurrent(new UnitOfWork());
	}

	/**
	 * Set the current thread
	 * @param uow  the input UnitOfWork
	 */
	public static void setCurrent(UnitOfWork uow) {
		current.set(uow);
	}

	/**
	 * Get the current UnitOfWork thread
	 * @return  the current UnitOfWork thread
	 */
	public static UnitOfWork getCurrent() {
		return (UnitOfWork) current.get();
	}

	/**
	 * Register a DomainObject object in new objets
	 * @param obj  the DomainObject object to be registered
	 */
	public void registerNew(DomainObject obj) {
		assert obj.getId() != null : "id is null";
		assert !dirtyObjects.contains(obj) : "object is dirty";
		assert !deletedObjects.contains(obj) : "object is deleted";
		assert !newObjects.contains(obj) : "object is new";
		newObjects.add(obj);
	}

	/**
	 * Register a DomainObject object in dirty objets
	 * @param obj  the DomainObject object to be registered
	 */
	public void registerDirty(DomainObject obj) {
		assert obj.getId() != null : "id is null";
		assert !deletedObjects.contains(obj) : "object is deleted";
		if (!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
			dirtyObjects.add(obj);
		}
	}

	/**
	 * Register a DomainObject object in deleted objets
	 * @param obj  the DomainObject object to be registered
	 */
	public void registerDeleted(DomainObject obj) {
		assert obj.getId() != null : "id is null";

		if (newObjects.remove(obj))
			return;
		dirtyObjects.remove(obj);
		if (!deletedObjects.contains(obj)) {
			deletedObjects.add(obj);
		}
	}

	/**
	 * Register a DomainObject object in clean objets
	 * @param obj  the DomainObject object to be registered
	 */
	public void registerClean(DomainObject obj) {
		assert obj.getId() != null : "id is null";
	}

	/**
	 * Commit the current unit of work
	 */
	public void commit() {
		
		for (DomainObject obj : newObjects) {
			DataMapper.getMapper(obj.getClass()).insert(obj);
		}
		for (DomainObject obj : dirtyObjects) {
			DataMapper.getMapper(obj.getClass()).update(obj);
		}
		for (DomainObject obj : deletedObjects) {
			DataMapper.getMapper(obj.getClass()).delete(obj);
		}
		
		this.newObjects = new ArrayList<DomainObject>();
		this.dirtyObjects = new ArrayList<DomainObject>();
		this.deletedObjects = new ArrayList<DomainObject>();
	}

}
