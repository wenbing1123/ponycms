package com.pony.core.pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageImpl<T> implements Page<T> {
	
	private List<T> content = new ArrayList<T>();
	private Pageable pageable;
	private long total;
	
	public PageImpl(List<T> content, Pageable pageable, long total) {
		Assert.notNull(content, "Content must not be null!");
		
		this.content.addAll(content);
		this.pageable = pageable;
		this.total = total;
	}
	
	public PageImpl(List<T> content) {
		this(content, null, null == content ? 0L : content.size());
	}
	
	public Integer getPageNumber() {
		return this.pageable == null ? 0 : this.pageable.getPageNumber();
	}

	public Integer getPageSize() {
		return this.pageable == null ? 0 : this.pageable.getPageSize();
	}

	@JsonProperty("rows")
	public List<T> getContent() {
		return this.content;
	}

	public Boolean hasContent() {
		return !this.content.isEmpty();
	}

	@JsonIgnore
	public Sort getSort() {
		return this.pageable == null ? null : this.pageable.getSort();
	}

	@JsonIgnore
	public Boolean isFirst() {
		return !hasPrevious();
	}

	@JsonIgnore
	public Boolean isLast() {
		return !hasNext();
	}

	public Boolean hasNext() {
		return getPageNumber() < getTotalPages();
	}

	public Boolean hasPrevious() {
		return getPageNumber() > 1;
	}

	public Pageable nextPageable() {
		return hasNext() ? this.pageable.next() : null;
	}

	public Pageable previousPageable() {
		if (hasPrevious()) {
			return this.pageable.previousOrFirst();
		}
		
		return null;
	}

	@JsonIgnore
	public Integer getTotalPages() {
		return getPageSize() == 0 ? 1 : (int)((this.total / getPageSize())+1);
	}

	@JsonProperty("total")
	public Long getTotalRecords() {
		return this.total;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
		    return true;
		}
			 
		if (!(obj instanceof PageImpl)) {
			return false;
		}
		     
		PageImpl<?> that = (PageImpl<?>)obj;
		  
		return (this.total == that.total) && (super.equals(obj));
	}

	public int hashCode() {
		int result = 17;
		
		result += 31 * (int)(this.total ^ this.total >>> 32);
		result += 31 * super.hashCode();
		
		return result;
	}

	public String toString() {
		String contentType = "UNKNOWN";
		List<T> content = getContent();
		
		if (content.size() > 0) {
			contentType = content.get(0).getClass().getName();
		}
		
		return String.format("Page %s of %d containing %s instances", new Object[] { Integer.valueOf(getPageNumber()), Integer.valueOf(getTotalPages()), contentType });
	}
	
}
