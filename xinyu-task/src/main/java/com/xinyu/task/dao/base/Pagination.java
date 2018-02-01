package com.xinyu.task.dao.base;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用分页排序�?
 */
public class Pagination<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8526247195479423534L;
	private static final int DEFAULT_PAGE = 1;
	public static int DEFAULT_PAGE_SIZE = 30;
	private List<T> items;
	private int recordCount;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int currentPageStartIndex = 0;
	private int currentPage = DEFAULT_PAGE;
	private String uri; // 分页页面的URI
	private String pageTemplate; // 分页模板
	private Sort sort = Sort.DESC; // 排序方式 DESC/ASC
	private String sortField; // 排序字段
	private String queryString;
	private Map<String, Object> otherValue;//其它�?
	
	public Map<String, Object> getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(Map<String, Object> otherValue) {
		this.otherValue = otherValue;
	}

	public static enum Sort {
		DESC, ASC
	}
	
	public Pagination() {
		this(DEFAULT_PAGE_SIZE, DEFAULT_PAGE, null);
	}

	/**
	 * 用于直查询条数用
	 * @return
	 */
	public static<T> Pagination<T> getZeroPageSizePagination(){
		final Pagination<T> page=new Pagination<T>();
		page.pageSize=0;
		return page;
	}
	
	public Pagination(int pageSize, int page, String uri) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("Count should be greater than zero!");
		} else if (currentPage < 1) {
			page = 1;
		} else {
			this.pageSize = pageSize;
			this.currentPage = page;
			this.uri = uri;
		}
	}
	public Pagination(int pageSize, int page) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("Count should be greater than zero!");
		} else if (currentPage < 1) {
			page = 1;
		} else {
			this.pageSize = pageSize;
			this.currentPage = page;
		}
	}
	
	public String getCount(){
		return this.getRecordCount()+"";
	}
	
	public void setPageSize(int countOnEachPage) {
		this.pageSize = countOnEachPage;
	}

	public List<T> getItems() {
		if(null==this.items)
			this.items=new ArrayList();
		return items;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public int getCurrentPageStartIndex() {
		currentPageStartIndex = (currentPage - 1) * pageSize;
//		if(currentPageStartIndex==0){
//			currentPageStartIndex=1;
//		}
		return currentPageStartIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void setRecordCount(int totalCount) {
		this.recordCount = totalCount;
	}
	

	public int getPageCount() {
		return (recordCount == 0) ? 1 : ((recordCount % pageSize == 0) ? (recordCount / pageSize)
				: (recordCount / pageSize) + 1);
	}

	public int getPreviousPage() {
		if(currentPage > 1) return currentPage - 1;
		else return DEFAULT_PAGE;
	}
	
	public int getNextPage() {
		if(currentPage < getPageCount()) return currentPage + 1;
		else return getPageCount();
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPageTemplate() {
		return pageTemplate;
	}

	public void setPageTemplate(String pageTemplate) {
		this.pageTemplate = pageTemplate;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	/**
	 * 存在上一�?
	 * @return
	 */
	public boolean getExistUp()
	{
		return currentPage>1?true:false;
	}
	
	/**
	 * 存在下一�?
	 * @return
	 */
	public boolean getExistNext()
	{
		return currentPage<this.getPageCount()?true:false;
	}
	
	public static<T> Pagination<T> createZeroPageSizePagination(){
		final Pagination<T> page=new Pagination<T>();
		page.pageSize=0;
		return page;
	}
	
	
	
}