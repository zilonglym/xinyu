package com.graby.store.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Centro;
import com.graby.store.service.base.CentroService;

@RemotingService(serviceInterface = CentroRemote.class, serviceUrl = "/centro.call")
public class CentroRemoteImpl implements CentroRemote {
	
	@Autowired
	public CentroService centroService;
	
	@Override
	public List<Centro> findCentros() {
		return centroService.findCentros();
	}

	@Override
	public Centro getCentroById(int id) {
		// TODO Auto-generated method stub
		return centroService.findCentroById(String.valueOf(id));
	}

	@Override
	public void saveCentro(Centro centro) {
		// TODO Auto-generated method stub
		centroService.saveCentro(centro);
	}

	@Override
	public void updateCentro(Centro centro) {
		// TODO Auto-generated method stub
		centroService.updateCentro(centro);
	}

	@Override
	public Centro findCentrosByCity(String city) {
		// TODO Auto-generated method stub
		return centroService.findCentrosByCity(city);
	}

	@Override
	public Centro findCentroById(String id) {
		// TODO Auto-generated method stub
		return centroService.findCentroById(id);
	}

	@Override
	public Centro findCentroById(Long id) {
		// TODO Auto-generated method stub
		return centroService.findCentro(id);
	}

}
