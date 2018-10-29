package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findPegawaiByNip(String nip);
	PegawaiModel findPegawaiById(long id);
	PegawaiModel findPegawaiByNama(String Nama);
	PegawaiModel findPegawaiByTahunMasuk(String tahunMasuk);
	PegawaiModel findPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date date, String tahunMasuk, InstansiModel instansi);
	
}
