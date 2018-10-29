package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;


public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);
	PegawaiModel getPegawaiById(long id);
	PegawaiModel getPegawaiByInstansi(InstansiModel instansi);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> allPegawai();
	PegawaiModel getPegawaiMuda(InstansiModel instansi);
	PegawaiModel getPegawaiTua(InstansiModel instansi);
	List<PegawaiModel> allPegawaiByInstansi(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);
	void updatePegawai (PegawaiModel newPegawai, long id);
	void hapusJabatanPegawai(PegawaiModel pegawai);
}
