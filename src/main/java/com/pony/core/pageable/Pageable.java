package com.pony.core.pageable;

//分页
public interface Pageable {

	public 	Integer		getPageNumber();
	public 	Integer		getPageSize();
	public	Integer		getOffset();
	public 	Sort		getSort();
	public	Pageable	fisrt();
	public	Pageable	next();
	public	Pageable	previous();
	public 	Pageable 	previousOrFirst();
	public	Boolean		hasPrevious();
	
}
