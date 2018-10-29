package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanById(long id);
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> allJabatan();
	void updateJabatan(JabatanModel jabatan, long id);
	void deleteJabatan(long id);
}
