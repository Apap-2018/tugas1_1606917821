package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	InstansiModel getInstansiById(long id);
	InstansiModel getInstansiByProvinsi(ProvinsiModel provinsi);
	void addInstansi(InstansiModel instansi);
	List<InstansiModel> allInstansi();
	List<InstansiModel> getAllInstansiByProvinsi(ProvinsiModel provinsi);

}
