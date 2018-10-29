package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDB pegawaiDB;
	
	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDB;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDB.save(pegawai);
	}

	@Override
	public PegawaiModel getPegawaiByNip(String NIP) {
		// TODO Auto-generated method stub
		return pegawaiDB.findPegawaiByNip(NIP);
	}

	@Override
	public PegawaiModel getPegawaiById(long id) {
		// TODO Auto-generated method stub
		return pegawaiDB.findPegawaiById(id);
	}

	@Override
	public PegawaiModel getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDB.findPegawaiByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> allPegawai() {
		// TODO Auto-generated method stub
		
		return pegawaiDB.findAll();
	}

	@Override
	public PegawaiModel getPegawaiMuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		int banyak = pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi).size();
		return pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi).get(banyak-1);
	}

	@Override
	public PegawaiModel getPegawaiTua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByInstansiOrderByTanggalLahirAsc(instansi).get(0);
	}

	@Override
	public List<PegawaiModel> allPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> allPegawai = pegawaiDB.findAll();
		List<PegawaiModel> allPegawaiByInstansi = new ArrayList<>();
		for(PegawaiModel pegawai : allPegawai) {
			if(pegawai.getInstansi().equals(instansi)) {
				allPegawaiByInstansi.add(pegawai);
			}
		}
		return allPegawaiByInstansi;
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
				String nip = "";
				nip += pegawai.getInstansi().getId();
				Date date = pegawai.getTanggalLahir();
				String[] tgl = (""+date).split("-");
				for (int i = tgl.length-1; i >= 0; i--) {
					int ukuranTgl = tgl[i].length();
					nip += tgl[i].substring(ukuranTgl-2, ukuranTgl);
				}
				nip += pegawai.getTahunMasuk();
				List<PegawaiModel> listPegawai = pegawaiDB.findByTanggalLahirAndTahunMasukAndInstansi(date, pegawai.getTahunMasuk(), pegawai.getInstansi());
				if(listPegawai.size() < 10) {
					nip += "0"+listPegawai.size();
				}
				else {
					nip += listPegawai.size();
				}
				pegawai.setNip(nip);
				return nip;
	
	}

	@Override
	public void hapusJabatanPegawai(PegawaiModel pegawai) {
		List <JabatanPegawaiModel> listJabatan = jabatanPegawaiService.findJabatanPegawaiByPegawai(pegawai);
		for (JabatanPegawaiModel jab : listJabatan) {
			jabatanPegawaiService.hapusJabatanPegawai(jab);
		}				
	}

	@Override
	public void updatePegawai(PegawaiModel newPegawai, long id) {
		// TODO Auto-generated method stub
		PegawaiModel pegawaiLama = pegawaiDB.findPegawaiById(id);
		pegawaiLama.setInstansi(newPegawai.getInstansi());
		pegawaiLama.setListJabatanPegawai(newPegawai.getListJabatanPegawai());
		pegawaiLama.setNama(newPegawai.getNama());
		pegawaiLama.setNip(newPegawai.getNip());
		pegawaiLama.setTahunMasuk(newPegawai.getTahunMasuk());
		pegawaiLama.setTanggalLahir(newPegawai.getTanggalLahir());
		pegawaiLama.setTempatLahir(newPegawai.getTempatLahir());
		pegawaiDB.save(pegawaiLama);
	}






}
