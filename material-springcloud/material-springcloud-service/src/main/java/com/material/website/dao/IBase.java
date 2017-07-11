package com.material.website.dao;

/**  
 * @Description: dao层公共接口(功能描述) 
 * @author 张明虎 zhangminghu@yuntengzhiyong.com   
 * @date 2014年12月4日 上午12:37:23  
 */
public interface IBase<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t);
	/**
	 * 删除对象
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 查询对象 延迟加载
	 * @param id
	 * @return
	 */
	public T load(int id);
	/**
	 * 查询对象 非延迟
	 * @param id
	 * @return
	 */
	public T get(int id);
	
	/**
	 * 添加一个实体  非泛型
	 * @param entity
	 * @return
	 */
	public Object addEntity(Object entity);
	/**
	 * 修改一个实体  非泛型
	 * @param entity
	 * @return
	 */
	public void updateEntity(Object entity);
	/**
	 * 删除一个实体  非泛型
	 * @param entity
	 * @return
	 */
	public void deleteEntity(Object entity);
	/**
	 * 获取一个实体  非泛型 延迟加载
	 * @param entity
	 * @return
	 */
	public Object loadEntity(Class<?> clz,int id);
	/**
	 * 获取一个实体  非泛型 非延迟加载
	 * @param entity
	 * @return
	 */
	public Object getEntity(Class<?> clz,int id);
	
	/**
	 * 更新数据
	 * @param sql
	 * @param args
	 */
	public void updateBySql(String sql, Object[] args);
	
}
