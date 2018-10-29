package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;

	
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	@RequestMapping(value="/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value="/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("idJabatan") Long id, Model model ) {
		JabatanModel jabatan = jabatanService.getJabatanById(id);
		model.addAttribute("jabatan",jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	private String updateJabatan(@RequestParam("idJabatan") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	@RequestMapping (value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan){
		jabatanService.updateJabatan(jabatan, jabatan.getId());
		return "success";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.GET)
	private String deleteJabatan(@RequestParam("idJabatan") Long id) {
		jabatanService.deleteJabatan(id);
		return "delete";
	}
	
	@RequestMapping(value="/jabatan/viewall")
	private String viewAll(Model model) {
		List<JabatanModel> listJabatan = jabatanService.allJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "viewall";
	}
	
	
}
