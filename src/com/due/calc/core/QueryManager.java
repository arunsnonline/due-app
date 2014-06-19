package com.due.calc.core;

import com.due.calc.util.StringUtil;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class QueryManager {
	
	private enum SortOrder {
		ASC {
			@Override
			public String getSortClause(String... fields) {
				return StringUtil.join(",", fields)+" asc";
			}
		},
		DESC {
			@Override
			public String getSortClause(String... fields) {
				return StringUtil.join(",", fields)+" desc";
			}
		};
		
		public abstract String getSortClause(String... fields);
	}
	
	private Uri uriSMSURI;
	private String[] mProjection;
	private String[] selectionArgs;
	private String sortClause; 
	private ContentResolver contentResolver;
	
	private QueryManager(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
		String[] nullArray = null;
		this.sortClause = SortOrder.ASC.getSortClause(nullArray);
	}
	
	public  static QueryManager build(ContentResolver contentResolver) {
		return new QueryManager(contentResolver);
	}
	
	public QueryManager queryOn(String contentUrl) {
		 this.uriSMSURI = Uri.parse(contentUrl);
		 return this;
	}
	
	public QueryManager getFields(String... fields) {
		this.mProjection = fields;
		return this;
	}
	
	public QueryManager selectionArgs(String... fields) {
		this.selectionArgs = fields;
		return this;
	}
	
	public QueryManager sortAsc(String... fields) {
		this.sortClause = SortOrder.ASC.getSortClause(fields);
		return this;
	}
	
	public QueryManager sortDesc(String... fields) {
		this.sortClause = SortOrder.DESC.getSortClause(fields);
		return this;
	}
	
	public Cursor query(String queryString) {
		return this.contentResolver.query(uriSMSURI, mProjection,queryString
				, selectionArgs, sortClause);
	}
}
