package org.walkframework.data.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author shf675
 *
 */
public abstract class AbstractEntity implements Entity {
	private static final long serialVersionUID = 1L;

	protected static final SerializerFeature[] serializerFeatures = new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect};
	
	//存储本次操作的列
	private Map<String, OperColumn> operColumns;
	
	private boolean isNoAnyCondition;
	
	/**
	 * 记录操作的列
	 * 每次调用Setter方法的时候自动调用
	 * 
	 * @param column
	 * @param property
	 * @param value
	 * @param type
	 */
	protected OperColumn addOperColumn(String column, String property, Object value, Class<?> type) {
		OperColumn operColumn = privateAddOperColumn(column, property, value, type);
		this.isNoAnyCondition = false;
		return operColumn;
	}
	
	/**
	 * 记录操作的列
	 * 每次调用Setter方法的时候自动调用
	 * 
	 * @param column
	 * @param property
	 * @param value
	 * @param type
	 */
	protected OperColumn privateAddOperColumn(String column, String property, Object value, Class<?> type) {
		if(this.operColumns == null){
			this.operColumns = new LinkedHashMap<String, OperColumn>();
		}
		OperColumn operColumn = this.operColumns.get(column);
		if(operColumn == null){
			operColumn = new OperColumn(column, property, value, type);
			this.operColumns.put(column, operColumn);
		} else {
			operColumn.setOperColumnValue(value);
		}
		
		return operColumn;
	}
	
	/**
	 * 获取操作的列
	 * 
	 * @return
	 */
	Map<String, OperColumn> operColumns() {
		return operColumns;
	}
	
	boolean isNoAnyCondition() {
		return isNoAnyCondition;
	}
	
	/**
	 * 不设置任何条件
	 */
	public Entity noAnyCondition() {
		this.isNoAnyCondition = true;
		return this;
	}
}