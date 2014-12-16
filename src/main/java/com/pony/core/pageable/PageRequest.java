package com.pony.core.pageable;


//分页请求，页码从1开始，偏移量从0开始
public class PageRequest implements Pageable {
	
	private	int pageNumber;
	private	int	pageSize;
	
	private	Sort sort;
	
	public PageRequest(int pageNumber, int pageSize) {
		
		if(pageNumber < 1 ) {
			throw new IllegalArgumentException("Page index must not be less than 1!");
		}
		
		if(pageSize < 1){
			throw new IllegalArgumentException("Page size must not be less than one!");
		}
		
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		
	}
	
	public PageRequest(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
		this(pageNumber, pageSize, new Sort(direction, properties));
	}
	
	public PageRequest(int pageNumber, int pageSize, Sort sort) {
		this(pageNumber, pageSize);
		this.sort = sort;
	}

	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public Integer getOffset() {
		return (this.pageNumber-1) * this.pageSize;
	}

	public Sort getSort() {
		return this.sort;
	}

	public Pageable fisrt() {
		return new PageRequest(1, getPageSize(), getSort());
	}

	public Pageable next() {
		return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	public Pageable previous() {
		return getPageNumber() == 1 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
	}

	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : fisrt();
	}

	public Boolean hasPrevious() {
		return this.pageNumber > 1;
	}

}
