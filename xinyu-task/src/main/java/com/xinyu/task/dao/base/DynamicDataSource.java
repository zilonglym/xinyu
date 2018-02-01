package com.xinyu.task.dao.base;

import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource
{
  private Map<Object, Object> backupDataSources;
  private DataSource defaultBackupDataSource;
  private static volatile boolean isEnableBackup = false;
  private static DynamicDataSource dynamicDataSource;

  public DynamicDataSource()
  {
    dynamicDataSource = this;
  }

  public void setDefaultBackupDataSource(DataSource defaultBackupDataSource) {
    this.defaultBackupDataSource = defaultBackupDataSource;
  }

  public void setBackupDataSources(Map<Object, Object> backupDataSources)
  {
    this.backupDataSources = backupDataSources;
  }

  protected Object determineCurrentLookupKey()
  {
    return ContextHolder.getDataSource();
  }

  protected DataSource determineTargetDataSource()
  {
    if (!isEnableBackup) {
      return getMasterDataSource();
    }
    this.logger.warn("************************************");
    this.logger.warn("Current is using backup datasource");
    this.logger.warn("************************************");
    DataSource dataSource = (DataSource)this.backupDataSources.get(determineCurrentLookupKey());
    if (dataSource == null) {
      this.logger.warn("DataSource is null for lookup key [" + determineCurrentLookupKey() + "]");
      return this.defaultBackupDataSource;
    }
    return dataSource;
  }

  private DataSource getMasterDataSource() {
    return super.determineTargetDataSource();
  }


}