package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

@Repository
public interface InstansiDB extends JpaRepository<InstansiModel, Long> {
	InstansiModel findInstansiByProvinsi(ProvinsiModel provinsi);
	InstansiModel findInstansiById(long id);
	InstansiModel findInstansiByNama(String nama);
}
