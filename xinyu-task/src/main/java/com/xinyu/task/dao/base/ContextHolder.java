package com.xinyu.task.dao.base;

import java.util.HashMap;
import java.util.Map;

public class ContextHolder
{
  private static final ThreadLocal<Map<String, Object>> contextHolder = new ThreadLocal();
  private static final String DATA_SOURCE = "DATA_SOURCE";

  public static void setDataSource(String dataSource)
  {
    Map holder = (Map)contextHolder.get();
    if (holder == null) {
      holder = new HashMap();
      contextHolder.set(holder);
    }
    holder.put("DATA_SOURCE", dataSource);
  }

  public static String getDataSource() {
    Map holder = (Map)contextHolder.get();
    if (holder == null) return null;
    return (String)holder.get("DATA_SOURCE");
  }

  public static void clear() {
    contextHolder.remove();
  }
}