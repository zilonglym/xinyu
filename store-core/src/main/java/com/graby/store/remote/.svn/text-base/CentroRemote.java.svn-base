package com.graby.store.remote;

import java.util.List;

import com.graby.store.entity.Centro;

/**
 * 仓库服务
 * serviceUrl = "/centro.call"
 */
public interface CentroRemote {
	
	/**
	 * 根据ID查询仓库
	 * @param id
	 * @return
	 */
	Centro findCentroById(Long id);
	
	Centro findCentroById(String id);
	/**
	 * 返回所有仓库
	 * @return
	 */
	List<Centro> findCentros();
	/**
	 * 根据ID查询仓库
	 * @param id
	 * @return
	 */
	Centro getCentroById(int id);
	/**
	 * 保存一个新的仓库
	 * @param centro
	 */
	void saveCentro(Centro centro);
	/**
	 * 修改一个仓库信息
	 * @param centro
	 */
	void updateCentro(Centro centro);
	
	Centro findCentrosByCity(String city);
	
}
