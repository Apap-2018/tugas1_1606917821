package com.apap.tugas1.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {

	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDB;

	@Override
	public List<JabatanPegawaiModel> allJabatanPegawai() {
		// TODO Auto-generated method stub
		return jabatanPegawaiDB.findAll();
	}

	@Override
	public PegawaiModel getjabatanPegawaiByIdPegawai(long id) {
		// TODO Auto-generated method stub
		return jabatanPegawaiDB.findPegawaiJPById(id);
	}

	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDB.save(jabatanPegawai);
	}
	
	@Override
	public void setPegawaiJabatan(PegawaiModel pegawai) {
		for (JabatanPegawaiModel pegjab : pegawai.getListJabatanPegawai()) {
			pegjab.setPegawai(pegawai);
		}
	}
	
	@Override
	public void hapusJabatanPegawai(JabatanPegawaiModel jabatan) {
		jabatanPegawaiDB.delete(jabatan);
		
	}
	
	@Override
	public List<JabatanPegawaiModel> findJabatanPegawaiByPegawai(PegawaiModel pegawai) {
		return jabatanPegawaiDB.findByPegawai(pegawai);
	}


	

}
