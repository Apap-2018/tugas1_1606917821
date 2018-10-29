package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDB provinsiDB;

	@Override
	public void addProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		provinsiDB.save(provinsi);
	}

	@Override
	public ProvinsiModel getProvinsiById(long id) {
		// TODO Auto-generated method stub
		return provinsiDB.findProvinsiById(id);
	}

	@Override
	public ProvinsiModel getProvinsiByNama(String nama) {
		// TODO Auto-generated method stub
		return provinsiDB.findProvinsiByNama(nama);
	}

	@Override
	public List<ProvinsiModel> allProvinsi() {
		// TODO Auto-generated method stub
		return provinsiDB.findAll();
	}
	
	

}
