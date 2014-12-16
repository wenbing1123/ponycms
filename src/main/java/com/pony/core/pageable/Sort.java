package com.pony.core.pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.springframework.util.StringUtils;

public class Sort {
	
	public static final Direction DEFAULT_DIRECTION = Direction.ASC;

	public static enum Direction {
		ASC, DESC;
		
		public static Direction fromString(String value) {
			try{
				return valueOf(value.toUpperCase(Locale.US));
			}catch(Exception e){
				throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", new Object[] { value }), e);
			}
		}
		
		public static Direction fromStringOrNull(String value) {
			try{
				return fromString(value);
			}catch(IllegalArgumentException e){
				return null;
			}
		}
		
	}
	
	public static enum NullHandling {
		NATIVE, NULLS_FIRST, NULLS_LAST
	}
	
	public static class Order {
		
		private Sort.Direction direction;
		private String property;
		private boolean ignoreCase;
		private Sort.NullHandling nullHandling;
		
		public Order(Sort.Direction direction, String property) {
			this(direction, property, false, null);
		}
		
		public Order(Sort.Direction direction, String property, Sort.NullHandling nullHandlingHint) {
			this(direction, property, false, nullHandlingHint);
		}
		
		public Order(String property) {
			this(Sort.DEFAULT_DIRECTION, property);
		}
		
		private Order(Sort.Direction direction, String property, boolean ignoreCase, Sort.NullHandling nullHandling) {
			
			if (!StringUtils.hasText(property)) {
				throw new IllegalArgumentException("Property must not null or empty!");
			}
			
			this.direction = (direction == null ? Sort.DEFAULT_DIRECTION : direction);
			this.property = property;
			this.ignoreCase = ignoreCase;
			this.nullHandling = (nullHandling == null ? Sort.NullHandling.NATIVE : nullHandling);
			
		}
		
		public Sort.Direction getDirection() {
			return this.direction;
		}
		
		public String getProperty() {
			return this.property;
		}
		
		public boolean isAscending() {
			return this.direction.equals(Sort.Direction.ASC);
		}
		
		public boolean isIgnoreCase() {
			return this.ignoreCase;
		}
		
		public Order with(Sort.Direction direction) {
			return new Order(direction, this.property, this.nullHandling);
		}
		
		public Sort withProperties(String... properties) {
			return new Sort(this.direction, properties);
		}
		
		public Order ignoreCase() {
			return new Order(this.direction, this.property, true, this.nullHandling);
		}
		
		public Order with(Sort.NullHandling nullHandling) {
			return new Order(this.direction, this.property, this.ignoreCase, nullHandling);
		}
		
		public Order nullsFirst() {
			return with(Sort.NullHandling.NULLS_FIRST);
		}
		
		public Order nullsLast() {
			return with(Sort.NullHandling.NULLS_LAST);
		}
		
		public Order nullsNative() {
			return with(Sort.NullHandling.NATIVE);
		}
		
		public Sort.NullHandling getNullHandling() {
			return this.nullHandling;
		}
		
		public int hashCode(){
			int result = 17;
		     
			result = 31 * result + this.direction.hashCode();
			result = 31 * result + this.property.hashCode();
			result = 31 * result + (this.ignoreCase ? 1 : 0);
			result = 31 * result + this.nullHandling.hashCode();
		      
			return result;
		}
		
		public boolean equals(Object obj) {
			if (this == obj) {
		        return true;
		    }
		      
		    if (!(obj instanceof Order)) {
		    	return false;
		    }
		       
		    Order that = (Order)obj;
		  
		    return (this.direction.equals(that.direction)) && (this.property.equals(that.property)) && (this.ignoreCase == that.ignoreCase) && (this.nullHandling.equals(that.nullHandling));
		}
		
		public String toString() {
			String result = String.format("%s: %s", new Object[] { this.property, this.direction });
		      
			if (!Sort.NullHandling.NATIVE.equals(this.nullHandling)) {
				result = result + ", " + this.nullHandling;
			}
		      
			if (this.ignoreCase) {
				result = result + ", ignoring case";
			}
		     
			return result;
		}
		
	}
	
	private List<Order> orders;
	
	public Sort(Order... orders) {
		this(Arrays.asList(orders));
	}
	
	public Sort(List<Order> orders) {
		
		if ((null == orders) || (orders.isEmpty())) {
			throw new IllegalArgumentException("You have to provide at least one sort property to sort by!");
		}
		
		this.orders = orders;
		
	}
	
	public Sort(String... properties){
		this(DEFAULT_DIRECTION, properties);
	}
	
	public Sort(Direction direction, String... properties) {
		this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
	}
	
	public Sort(Direction direction, List<String> properties) {
		
		if ((properties == null) || (properties.isEmpty())) {
			throw new IllegalArgumentException("You have to provide at least one property to sort by!");
		}
		
		this.orders = new ArrayList<Order>(properties.size());
		
		for (String property : properties) {
			this.orders.add(new Order(direction, property));
		}
		
	}
	
	/**
	 * 从请求中解析排序参数
	 * desc_name1,name2;asc_name1,name2
	 */
	public static Sort parse(HttpServletRequest request){
		Validate.notNull(request, "Request must not be null");
		Sort sort = null;
		Enumeration<?> paramNames = request.getParameterNames();
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if(paramName.startsWith("asc") || paramName.startsWith("desc")){
				String[] names =StringUtils.split(paramName, "_");
				if (names.length == 2) {
					Direction direciton = Direction.fromString(names[0]);
					String[] properties = names[1].split(",");
					if(sort == null){
						sort = new Sort(direciton,properties);
					}else{
						sort.and(new Sort(direciton,properties));
					}
				}
			}
		}
		return sort;
	}
	
	public Sort and(Sort sort) {
		
		if (sort == null) {
			return this;
		}
		
		ArrayList<Order> these = new ArrayList<Order>(this.orders);
		
		for (Order order : sort.orders) {
			these.add(order);
		}
		
		return new Sort(these);
		 
	}
	
	public Order getOrderFor(String property) {
		
		for (Order order : this.orders) {
			if (order.getProperty().equals(property)) {
				return order;
			}
		}
		
		return null;
		
	}
	
	public Iterator<Order> iterator() {
		
		return this.orders.iterator();
		
	}
	
	public boolean equals(Object obj) {
		
	     if (this == obj) {
	       return true;
	     }
	     
	     if (!(obj instanceof Sort)) {
	       return false;
	     }
	     
	     Sort that = (Sort)obj;
	     
	     return this.orders.equals(that.orders);
	     
	}
	
	public int hashCode() {
		
	    int result = 17;
	    result = 31 * result + this.orders.hashCode();
	    
	    return result;
	    
	}
	
	public String toString() {
		
	    return StringUtils.collectionToCommaDelimitedString(this.orders);
	    
	}
	
}
