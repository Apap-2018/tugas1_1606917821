package com.apap.tugas1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanPegawaiController {
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/detail")
	private String viewAll(Model model) {
		Map<JabatanModel,Integer> data = new HashMap<JabatanModel,Integer>();
		List<JabatanModel> listJabatan = jabatanService.allJabatan();
		for(JabatanModel jabatan : listJabatan) {
			data.put(jabatan,0);
		}
		
		List<JabatanPegawaiModel> listJabatanPegawai = jabatanPegawaiService.allJabatanPegawai();
		for(JabatanPegawaiModel jabatanP : listJabatanPegawai) {
			if(data.containsKey(jabatanP.getJabatan())) {
				data.put(jabatanP.getJabatan(), data.get(jabatanP.getJabatan())+1);
			}
		}
		model.addAttribute("data",data);
		return "jabatan-detail";
	}
	
}
