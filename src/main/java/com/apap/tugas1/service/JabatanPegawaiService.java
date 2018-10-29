package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanPegawaiService {
	List<JabatanPegawaiModel> allJabatanPegawai();
	PegawaiModel getjabatanPegawaiByIdPegawai(long id);
	void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai);
	void setPegawaiJabatan(PegawaiModel pegawai);
	void hapusJabatanPegawai(JabatanPegawaiModel jabatan);
	List<JabatanPegawaiModel> findJabatanPegawaiByPegawai(PegawaiModel pegawai);
}
