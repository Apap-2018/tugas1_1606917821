package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDB jabatanDB;
	
	@Override
	public JabatanModel getJabatanById(long id) {
		// TODO Auto-generated method stub
		return jabatanDB.findJabatanById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDB.save(jabatan);
	}

	@Override
	public List<JabatanModel> allJabatan() {
		// TODO Auto-generated method stub
		return jabatanDB.findAll();
	}

	@Override
	public void updateJabatan(JabatanModel jabatan, long id) {
		JabatanModel newJabatan = jabatanDB.findJabatanById(id);
		newJabatan.setNama(jabatan.getNama());
		newJabatan.setDeskripsi(jabatan.getDeskripsi());
		newJabatan.setGajiPokok(jabatan.getGajiPokok());
		jabatanDB.save(newJabatan);
		
	}

	@Override
	public void deleteJabatan(long id) {
		jabatanDB.deleteById(id);
		
	}

}
