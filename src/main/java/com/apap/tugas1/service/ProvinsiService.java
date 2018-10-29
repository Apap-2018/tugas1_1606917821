package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	ProvinsiModel getProvinsiById(long id);
	ProvinsiModel getProvinsiByNama(String nama);
	void addProvinsi(ProvinsiModel provinsi);
	List<ProvinsiModel> allProvinsi();
}
