package com.material.website.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.material.website.dto.RoleFunctionDto;
import com.material.website.system.Pager;
import com.material.website.system.SystemContext;
import com.material.website.util.BeanMapUtil;

/**
 * base-dao 实现
 * @author sunxiaorong
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBase<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	private Class<?> clz;
	
	public Class<?> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public T load(int id) {
		return (T)getSession().load(getClz(),id);
	}
	
	@Override
	public T get(int id) {
		return (T)getSession().get(getClz(),id);
	}

	public List<T> list(String hql, Object[] args) {
		return list(hql, args, null);
	}

	public List<T> list(String hql, Object arg) {
		return list(hql, new Object[]{arg});
	}

	public List<T> list(String hql) {
		return list(hql,null);
	}
	
	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if(sort!=null&&!"".equals(sort.trim())) {
			hql+=" order by "+sort;
			if(!"desc".equals(order)) hql+=" asc";
			else hql+=" desc";
		}
		return hql;
	}
	
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias) {
		if(alias!=null) {
			Set<String> keys = alias.keySet();
			for(String key:keys) {
				Object val = alias.get(key);
				if(val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}
	
	private void setParameter(Query query,Object[] args) {
		if(args!=null&&args.length>0) {
			int index = 0;
			for(Object arg:args) {
				query.setParameter(index++, arg);
			}
		}
	}

	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}

	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return list(hql, null, alias);
	}

	public Pager<T> find(String hql, Object[] args) {
		return find(hql, args, null);
	}

	public Pager<T> find(String hql, Object arg) {
		return find(hql, new Object[]{arg});
	}

	public Pager<T> find(String hql) {
		return find(hql,null);
	}
	
	@SuppressWarnings("rawtypes")
	private void setPagers(Query query,Pager pages) {
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	
	private String getCountHql(String hql,boolean isHql) {
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+e;
		if(isHql)
			c.replaceAll("fetch", "");
		return c;
	}

	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setRows(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		pages.setTotalPage((total+pages.getSize()-1)/pages.getSize());
		pages.setCurrentPage(SystemContext.getCurrentPage());
		return pages;
	}

	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return find(hql,null, alias);
	}

	public Object queryObject(String hql, Object[] args) {
		return queryObject(hql, args,null);
	}
	
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return queryObject(hql,null,alias);
	}

	public Object queryObject(String hql, Object arg) {
		return queryObject(hql, new Object[]{arg});
	}

	public Object queryObject(String hql) {
		return queryObject(hql,null);
	}
	
	@Override
	public void updateBySql(String sql, Object[] args) {
		Query query = getSession().createSQLQuery(sql);
		setParameter(query, args);
		query.executeUpdate();
	}

	public void updateBySql(String sql, Object arg) {
		updateBySql(sql,new Object[]{arg});
	}

	public void updateBySql(String sql) {
		updateBySql(sql,null);
	}

	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	public void updateByHql(String hql, Object arg) {
		updateByHql(hql,new Object[]{arg});
	}

	public void updateByHql(String hql) {
		updateByHql(hql,null);
	}

	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return listBySql(sql, args, null, clz, hasEntity);
	}

	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return listBySql(sql, null, clz, hasEntity);
	}

	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity) {
			sq.addEntity(clz);
			return sq.list();
		} else {
			sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			//TODO	下面将结果集转换为自定义对象在这里报转换错误 所以只能转换为map 
			//sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<Map<String,Object>>list = sq.list();
		List<Object> convert = convert(clz, list);
		return (List<N>) convert;
	}

	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return listBySql(sql, null, alias, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return findBySql(sql, args, null, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
		return findBySql(sql, null, clz, hasEntity);
	}

	public <N extends Object>Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		String cq = getCountHql(sql,false);
		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(sq, alias);
		setAliasParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(sq, pages);
		if(hasEntity) {
			sq.addEntity(clz);
			pages.setRows(sq.list());
		} else {
		 	//sq.setResultTransformer(Transformers.aliasToBean(clz));
		    sq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		    List<Map<String,Object>>list = sq.list();
		    List<Object> convert = convert(clz, list);
		    pages.setRows((List<N>) convert);
		}
		//((BigInteger)cquery.uniqueResult()).longValue();
		long total =  listBySql(sql,args, clz, hasEntity).size();
		pages.setTotal(total);
		pages.setTotalPage((total+pages.getSize()-1)/pages.getSize());
		pages.setCurrentPage(SystemContext.getCurrentPage());
		return pages;
	}

	public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return findBySql(sql, null, alias, clz, hasEntity);
	}
	
	public Object queryObjectBySql(String sql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createSQLQuery(sql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}
	
	public Object queryObjectBySql(String sql, Object args,
			Map<String, Object> alias) {
		return queryObjectBySql(sql, new Object[]{args}, alias);
	}
	
	public Object queryObjectBySql(String sql, Object args) {
		return queryObjectBySql(sql, new Object[]{args},null);
	}
	
	public Object queryObjectBySql(String sql, Object[] args) {
		return queryObjectBySql(sql,args,null);
	}
	
	public Object queryObjectBySql(String sql) {
		return queryObjectBySql(sql,null,null);
	}
	
	public Object queryObjectBySqlAlias(String sql,Map<String, Object> alias) {
		return queryObjectBySql(sql,null,alias);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public Object addEntity(Object entity) {
		return getSession().save(entity);
	}

	@Override
	public void updateEntity(Object entity) {
		getSession().update(entity);
	}

	@Override
	public void deleteEntity(Object entity) {
		getSession().delete(entity);
	}

	@Override
	public Object loadEntity(Class<?> clz,int id) {
		return getSession().load(clz, id);
	}
	
	@Override
	public Object getEntity(Class<?> clz,int id) {
		return getSession().get(clz, id);
	}
	
	
	/**
     * 数据转换
     * @param clazz
     * @param list
     * @return
     */
	private List<Object> convert(Class<?> clazz, List<Map<String, Object>> list) {  
        List<Object> result;  
        if (CollectionUtils.isEmpty(list)) {  
            return null;  
        }  
        result = new ArrayList<Object>();  
        try {  
             PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();  
             for (Map<String, Object> map : list) {  
                 Object obj = clazz.newInstance();
                 for (String key:map.keySet()) {  
                     String attrName = key;    //key.toLowerCase(); 这里转换小写的问题  
                     for (PropertyDescriptor prop : props) {  
                         attrName = removeUnderLine(attrName);  
                         if (!attrName.equals(prop.getName())) {  
                             continue;  
                         }  
                         Method method = prop.getWriteMethod();  
                         Object value = map.get(key);
                         //System.out.println(prop.getPropertyType());
                         /*if (value != null) {  
                             value = ConvertUtils.convert(value,prop.getPropertyType());  
                         }  */
                         method.invoke(obj,value);  
                     }  
                 }  
                 result.add(obj);  
             }  
        } catch (Exception e) {  
            //throw new RuntimeException("数据转换错误"); 
            e.printStackTrace();
        }  
        return result;  
	  } 
	  
	  /**
	   * 去除字段中的下划线
	   * @param attrName
	   * @return
	   */
	  private String removeUnderLine(String attrName) {  
        //去掉数据库字段的下划线  
         if(attrName.contains("_")) {  
            String[] names = attrName.split("_");  
            String firstPart = names[0];  
            String otherPart = "";  
            for (int i = 1; i < names.length; i++) {  
                String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());  
                otherPart += word;  
            }  
            attrName = firstPart + otherPart;  
         }  
        return attrName;  
    }  
}
