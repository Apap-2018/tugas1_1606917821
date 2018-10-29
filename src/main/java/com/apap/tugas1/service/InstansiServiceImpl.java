package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDB instansiDB;

	@Override
	public void addInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		instansiDB.save(instansi);
		
	}

	@Override
	public InstansiModel getInstansiById(long id) {
		// TODO Auto-generated method stub
		return instansiDB.findInstansiById(id);
	}

	@Override
	public List<InstansiModel> allInstansi() {
		// TODO Auto-generated method stub
		return instansiDB.findAll();
	}

	@Override
	public InstansiModel getInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return instansiDB.findInstansiByProvinsi(provinsi);
	}

	@Override
	public List<InstansiModel> getAllInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		List<InstansiModel> allInstansi = instansiDB.findAll();
		List<InstansiModel> allInstansiByProvinsi = new ArrayList<>();
		for(InstansiModel instansi : allInstansi) {
			if(instansi.getProvinsi().equals(provinsi)) {
				allInstansiByProvinsi.add(instansi);
			}
		}
		return allInstansiByProvinsi;
	}

}
