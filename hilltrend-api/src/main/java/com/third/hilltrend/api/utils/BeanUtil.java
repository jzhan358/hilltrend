package com.third.hilltrend.api.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public final class BeanUtil {

	private BeanUtil(){

	}

	public static Map beanListToMap(Collection beanList, String keyproperty) {
		Map result = new HashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object key = PropertyUtils.getNestedProperty(bean, keyproperty);
				if (key != null) {
					result.put(key, bean);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static <T> Map beanListToMap(Collection<T> beanList,
										String keyproperty, String valueproperty, boolean ignoreNull) {

		Map result = new HashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object key = PropertyUtils.getNestedProperty(bean, keyproperty);
				Object value = PropertyUtils.getNestedProperty(bean,
						valueproperty);
				if (key == null) {
					continue;
				}
				if (value != null) {
					result.put(key, value);
				} else if (!ignoreNull) {
					result.put(key, value);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static Map groupBeanList(Collection beanList, String property) {
		return groupBeanList(beanList, property, null);
	}

	public static Map groupBeanList(Collection beanList,
									String property, Object nullKey) {
		Map result = new LinkedHashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object keyvalue = PropertyUtils.getNestedProperty(bean,
						property);
				if (keyvalue == null) {
					keyvalue = nullKey;
				}
				if (keyvalue != null) {
					List tmpList = (List) result.get(keyvalue);
					if (tmpList == null) {
						tmpList = new ArrayList();
						result.put(keyvalue, tmpList);
					}
					tmpList.add(bean);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static <T> List<T> getBeanPropertyList(Collection beanList,
												  Class<T> clazz, String propertyname,
												  boolean unique) {
		return getBeanPropertyList(beanList, propertyname, unique);
	}

	public static <T> List<T> getBeanPropertyList(Collection beanList,
												  String propertyname, boolean unique) {
		List result = new ArrayList();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object pv = PropertyUtils.getProperty(bean, propertyname);
				if ((pv != null) && ((!unique) || (!result.contains(pv)))) {
					result.add(pv);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static List<Map> getBeanMapList(Collection beanList,
										   String[] properties) {
		List result = new ArrayList();
		Iterator it = beanList.iterator();
		Object bean = null;
		while (it.hasNext()) {
			bean = it.next();
			Map beanMap = new HashMap();
			boolean hasProperty = false;
			for (String property : properties){
				try {
					Object pv = null;
					String keyname = property;
					if (property.indexOf(".") > 0) {
						pv = PropertyUtils.getNestedProperty(bean, property);
						keyname = keyname.replace('.', '_');
					} else {
						pv = PropertyUtils.getProperty(bean, property);
					}
					if (pv != null) {
						hasProperty = true;
						beanMap.put(keyname, pv);
					}
				} catch (Exception e) {
					e.getMessage();
				}
		}
			if (hasProperty) {
				result.add(beanMap);
			}
		}
		return result;
	}

	public static Map getKeyValuePairMap(List beanList, String keyProperty,
										 String valueProperty) {
		Map result = new HashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object keyvalue = PropertyUtils.getProperty(bean, keyProperty);
				if (keyvalue != null) {
					result.put(keyvalue,
							PropertyUtils.getProperty(bean, valueProperty));
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static Map<Object, List> groupBeanProperty(List beanList,
													  String keyname, String valuename) {
		Map result = new HashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object keyvalue = PropertyUtils.getProperty(bean, keyname);
				if (keyvalue != null) {
					List list = (List) result.get(keyvalue);
					if (list == null) {
						list = new ArrayList();
						result.put(keyvalue, list);
					}
					list.add(PropertyUtils.getProperty(bean, valuename));
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	public static <T> Map<Object, List<T>> groupBeanProperty(List<T> beanList, String keyname) {
		Map result = new HashMap();
		for (Iterator itr = beanList.iterator(); itr.hasNext();) {
			Object bean = itr.next();
			try {
				Object keyvalue = PropertyUtils.getProperty(bean, keyname);
				if (keyvalue != null) {
					List list = (List) result.get(keyvalue);
					if (list == null) {
						list = new ArrayList();
						result.put(keyvalue, list);
					}
					list.add(bean);
				}
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return result;
	}

	/**
	 * 截取list
	 *
	 * @param list
	 * @param from
	 * @param maxnum
	 * @return
	 */
	public static <T> List<T> getSubList(List<T> list, int from, int maxnum) {
		if ((list == null) || (list.size() <= from)) {
			return new ArrayList();
		}
		return new ArrayList(list.subList(from,
				Math.min(from + maxnum, list.size())));
	}

	private static <K, V extends Comparable<V>> List<Map.Entry<K, V>> sortEntriesByValue(
			Set<Map.Entry<K, V>> entries, boolean asc) {
		List sortedEntries = new ArrayList(entries);
		Collections.sort(sortedEntries, new ValueComparator(asc));
		return sortedEntries;
	}

	private static class ValueComparator<V extends Comparable<V>> implements
			Comparator<Map.Entry<?, V>> {
		private boolean asc;

		ValueComparator(boolean asc) {
			this.asc = asc;
		}

		public int compare(Map.Entry<?, V> entry1, Map.Entry<?, V> entry2) {
			int result = ((Comparable) entry1.getValue()).compareTo(entry2
					.getValue());
			return this.asc ? result : -result;
		}
	}

	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(value!= null&&!value.equals("")){
						map.put(key, value);
					}
				}

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return map;

	}

}
