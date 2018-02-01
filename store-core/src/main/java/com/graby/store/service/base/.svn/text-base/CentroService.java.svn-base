package com.graby.store.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.CentroDao;
import com.graby.store.entity.Centro;

@Component
public class CentroService {
		
	@Autowired
	private CentroDao centroDao;
	
	public List<Centro> findCentros() {
	
		return centroDao.findCentros();
	}
	public Centro findCentro(Long id){
		return centroDao.findCentro(id);
	}
	public Centro findCentroById(String id){
		return centroDao.findCentroById(id);
	}
	
	public Centro findCentroByCode(String code){
		return this.centroDao.findCentroByCode(code);
	}
	
	
	public void saveCentro(Centro centro) {
		// TODO Auto-generated method stub
		this.centroDao.saveCentro(centro);
	}

	public void updateCentro(Centro centro) {
		// TODO Auto-generated method stub
		this.centroDao.updateCentro(centro);
	}
	
	public  Centro findCentrosByCity(String city){
		return this.centroDao.findCentrosByCity(city);
	}

	
}
