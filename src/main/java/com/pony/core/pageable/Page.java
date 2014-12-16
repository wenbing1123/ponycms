package com.pony.core.pageable;

import java.util.List;

//分页接口
public interface Page<T> {

	public 	Integer		getPageNumber();
	public 	Integer		getPageSize();
	public 	List<T>		getContent();
	public 	Boolean		hasContent();
	public 	Sort		getSort();
	public	Boolean		isFirst();
	public 	Boolean		isLast();
	public 	Boolean		hasNext();
	public	Boolean		hasPrevious();
	public	Pageable	nextPageable();
	public	Pageable	previousPageable();
	public 	Integer		getTotalPages();
	public 	Long		getTotalRecords();
	
}
