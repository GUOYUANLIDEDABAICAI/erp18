package cn.itcast.erp.dao;

import java.util.List;

public interface IBaseDao<T> {
	
	/**
	 * 条件查询
	 * @param t1
	 * @return
	 */
	List<T> getList(T t1,T t2,Object param);
	
	/**
	 * 分页查询
	 * @param t1
	 * @param t2
	 * @param param
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<T> getListByPage(T t1,T t2,Object param,int firstResult, int maxResults);
	
	/**
	 * 记录条件查询的总记录数
	 * @param t1
	 * @return
	 */
	long getCount(T t1,T t2,Object param);
	
	/**
	 * 新增
	 * @param t
	 */
	void add(T t);
	
	/**
	 * 删除
	 */
	void delete(Long uuid);
	
	/**
	 * 通过编号查询对象
	 * @param uuid
	 * @return
	 */
	T get(Long uuid);
	
	/**
	 * 通过编号查询对象
	 * @param uuid
	 * @return
	 */
	T get(String uuid);
	
	/**
	 * 更新
	 */
	void update(T t);
	
	/**
	 * 根据编号获取对象的名称，子类dao中的实体必须有getName公有方法 
	 * @param uuid 对象主键编号
	 * @param nameMap 保存已经查询过的名称
	 * @return
	 */
	String getObjectName(Long uuid);
}
