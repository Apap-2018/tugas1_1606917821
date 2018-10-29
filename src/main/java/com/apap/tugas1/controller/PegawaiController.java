package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired 
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired 
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatanList = jabatanService.allJabatan();
		List<InstansiModel> instansiList = instansiService.allInstansi();
		model.addAttribute("jabatanList", jabatanList);
		model.addAttribute("instansiList",instansiList);
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam ("nip") String nip, Model model ) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		model.addAttribute("pegawai", pegawai);
		JabatanModel jabatan = jabatanService.getJabatanById(pegawai.getId());
		model.addAttribute("jabatan",jabatan);
		List<JabatanPegawaiModel> listJabatan = jabatanPegawaiService.allJabatanPegawai();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<>();
		
		for (JabatanPegawaiModel jabatanP: listJabatan) {
			if (jabatanP.getPegawai().getId() == (pegawai.getId())) {
				listJabatanPegawai.add(jabatanP);
			}
		}
		if(listJabatanPegawai.size() == 0) {
			return "error";
		}
		double gaji = 0;
		for(JabatanPegawaiModel jabatanP: listJabatan) {
			if(jabatanP.getJabatan().getGajiPokok() >= gaji) {
				gaji = jabatanP.getJabatan().getGajiPokok();
			}
		}
		double gajiFix = gaji + (pegawai.getInstansi().getProvinsi().getPresentaseTunjangan()*gaji/100);
		int gajiPegawai = (int) gajiFix;
		model.addAttribute("gajiPegawai", gajiPegawai);
		model.addAttribute("listJabatanPegawai",listJabatanPegawai);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTuaMuda(@RequestParam("idInstansi") long idInstansi, Model model) {
		InstansiModel instansiYangDicari = instansiService.getInstansiById(idInstansi);
		
		PegawaiModel pegawaiTua = pegawaiService.getPegawaiTua(instansiYangDicari);
		PegawaiModel pegawaiMuda = pegawaiService.getPegawaiMuda(instansiYangDicari);
		
		model.addAttribute("pegawaiTua", pegawaiTua);
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		
		List<JabatanPegawaiModel> listJabatan = jabatanPegawaiService.allJabatanPegawai();
		List<JabatanPegawaiModel> jabatanPegawaiTua = new ArrayList<>();
		List<JabatanPegawaiModel> jabatanPegawaiMuda = new ArrayList<>();
		
		for (JabatanPegawaiModel jabatan: listJabatan) {
			if (jabatan.getPegawai().getId() == (pegawaiTua.getId())) {
				jabatanPegawaiTua.add(jabatan);
			}
		}
		
		for (JabatanPegawaiModel jabatan: listJabatan) {
			if (jabatan.getPegawai().getId() == (pegawaiMuda.getId())) {
				jabatanPegawaiMuda.add(jabatan);
			}
		}
		
		model.addAttribute("jabatanPegawaiTua",jabatanPegawaiTua);
		model.addAttribute("jabatanPegawaiMuda",jabatanPegawaiMuda);
		
		double gajiTua = 0;
		for(JabatanPegawaiModel jabatanP: jabatanPegawaiTua) {
			if(jabatanP.getJabatan().getGajiPokok() >= gajiTua) {
				gajiTua = jabatanP.getJabatan().getGajiPokok();
			}
		}
		double gajiPegawaiTua = gajiTua + (pegawaiTua.getInstansi().getProvinsi().getPresentaseTunjangan()*gajiTua/100);
		int gajiPegawaiTertua = (int) gajiPegawaiTua;
		model.addAttribute("gajiPegawaiTua", gajiPegawaiTertua);
		
		double gajiMuda = 0;
		for(JabatanPegawaiModel jabatanP: jabatanPegawaiMuda) {
			if(jabatanP.getJabatan().getGajiPokok() >= gajiMuda) {
				gajiMuda = jabatanP.getJabatan().getGajiPokok();
			}
		}
		double gajiPegawaiMuda = gajiMuda + (pegawaiMuda.getInstansi().getProvinsi().getPresentaseTunjangan()*gajiMuda/100);
		int gajiPegawaiTermuda = (int) gajiPegawaiMuda;
		model.addAttribute("gajiPegawaiMuda", gajiPegawaiTermuda);
		return "view-tuamuda";
		
			
	}
	
	@RequestMapping("/pegawai/cari")
	private String viewPegawai(Model model){
		model.addAttribute("pegawai", new PegawaiModel());
		List<ProvinsiModel> provinsiList = provinsiService.allProvinsi();
		List<InstansiModel> instansiList = instansiService.allInstansi();
		List<JabatanModel> jabatanList = jabatanService.allJabatan();
		
		model.addAttribute("provinsiList", provinsiList);
		model.addAttribute("instansiList", instansiList);
		model.addAttribute("jabatanList", jabatanList);
		
		return "cari-pegawai";
	}
	@RequestMapping(value = "pegawai/cari/hasil", method = RequestMethod.GET)
	private String viewPegawaiGet(@RequestParam(value = "idProvinsi", required = false) String idProvinsi, @RequestParam(value = "id_Instansi", required = false) String idInstansi, @RequestParam(value = "idJabatan", required = false) String idJabatan, Model model){
		model.addAttribute("pegawai", new PegawaiModel());
		List<InstansiModel> instansiList = instansiService.allInstansi();
		List<PegawaiModel> pegawaiList = pegawaiService.allPegawai();
		List<JabatanPegawaiModel> jabatanPegawaiList = jabatanPegawaiService.allJabatanPegawai();
		List<ProvinsiModel> provinsiList = provinsiService.allProvinsi();
		List<JabatanModel> jabatanList = jabatanService.allJabatan();
		
		model.addAttribute("provinsiList", provinsiList);
		model.addAttribute("instansiList", instansiList);
		model.addAttribute("jabatanList", jabatanList);
		System.out.println(idProvinsi);
		System.out.println(idJabatan);
		System.out.println(idInstansi);
	
		List<PegawaiModel> allPegawai = new ArrayList<>();
		
		if(!(idProvinsi == null && idInstansi == null && idJabatan == null)) {
			// provinsi
			if(idProvinsi != null && idInstansi == null && idJabatan == null) {
				List<InstansiModel> instansiP = new ArrayList<>();
				for(InstansiModel instansiContoh : instansiList) {
					if(instansiContoh.getProvinsi().getId() == Long.parseLong(idProvinsi)) {
						instansiP.add(instansiContoh);
						}
					}
							
					for(PegawaiModel pegawai: pegawaiList) {
						for(InstansiModel instansi : instansiP) {
							if(pegawai.getInstansi().equals(instansi)) {
								allPegawai.add(pegawai);
							}
						}
								
					}
				}
			//instansi
			else if((idProvinsi == null && idInstansi != null && idJabatan == null)) {
				InstansiModel instansiP = instansiService.getInstansiById(Long.parseLong(idInstansi));
				for(PegawaiModel pegawai: pegawaiList) {
					if(pegawai.getInstansi().equals(instansiP)) {
						allPegawai.add(pegawai);
					}	
				}
			}
						
			//jabatan
			else if(idProvinsi == null && idInstansi == null && idJabatan != null) {
				for(JabatanPegawaiModel jabatanPegawai :jabatanPegawaiList) {
					if(jabatanPegawai.getJabatan().getId() == Long.parseLong(idJabatan) ) {
						allPegawai.add(jabatanPegawai.getPegawai());
					}
				}
			}
						
			//provinsi dan instansi
			else if(idProvinsi != null && idInstansi != null && idJabatan == null) {
			InstansiModel instansiP = instansiService.getInstansiByProvinsi(provinsiService.getProvinsiById(Long.parseLong(idProvinsi)));
			for(PegawaiModel pegawai: pegawaiList) {
				if(pegawai.getInstansi().equals(instansiP)) {
					allPegawai.add(pegawai);
					}	
				}
			}
			//instansi dan jabatan
			else if(idProvinsi == null && idInstansi != null && idJabatan != null) {
				List<PegawaiModel> pegawaiContoh =new ArrayList<>();
				for(JabatanPegawaiModel jabatanP : jabatanPegawaiList) {
					if(jabatanP.getJabatan().getId() == Long.parseLong(idJabatan)) {
						pegawaiContoh.add(jabatanP.getPegawai());
						}
				}
				for(PegawaiModel pegawai1 : pegawaiContoh) {
					if(pegawai1.getInstansi().getId() == Long.parseLong(idInstansi)) {
						allPegawai.add(pegawai1);
						}
				}
			}
			//provinsi dan jabatan
			else if(idProvinsi != null && idInstansi == null && idJabatan != null) {
				List<PegawaiModel> pegawaiContoh =new ArrayList<>();
				List<InstansiModel> allInstansiByProv = instansiService.getAllInstansiByProvinsi(provinsiService.getProvinsiById(Long.parseLong(idProvinsi)));
				for(InstansiModel instansi : allInstansiByProv) {
					List<PegawaiModel> pegawaiInstansi = pegawaiService.allPegawaiByInstansi(instansi);
					for(PegawaiModel pegawai : pegawaiInstansi) {
						pegawaiContoh.add(pegawai);
						}
				}
				for(PegawaiModel pegawai1 : pegawaiContoh) {
					for(JabatanPegawaiModel jbPegawai: jabatanPegawaiList) {
						if(jbPegawai.getPegawai().equals(pegawai1)) {
							allPegawai.add(pegawai1);
						}
					}
				}	
			}
			//semua
			else if(idProvinsi != null && idInstansi != null && idJabatan != null) {
				InstansiModel instansiP = instansiService.getInstansiByProvinsi(provinsiService.getProvinsiById(Long.parseLong(idProvinsi)));
				List<PegawaiModel> pegawaiContoh1 = new ArrayList<>();		
				for(PegawaiModel pegawai :pegawaiList) {
					if(pegawai.getInstansi().equals(instansiP)) {
						pegawaiContoh1.add(pegawai);
					}
				}
				for(JabatanPegawaiModel jabatanPegawai :jabatanPegawaiList) {
					for(PegawaiModel pegawaiContoh2 : pegawaiContoh1) {
						if((jabatanPegawai.getJabatan().getId() == Long.parseLong(idJabatan)) && jabatanPegawai.getPegawai().equals(pegawaiContoh2)) {
							allPegawai.add(pegawaiContoh2);
						}
					}	
					
				}
				
				
			}
			model.addAttribute("allPegawai", allPegawai);
			return "hasil";
		}
		else {
			return "error";
		}			
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		List<ProvinsiModel> allProvinsi = provinsiService.allProvinsi();
		ProvinsiModel provPertama = allProvinsi.get(0);
		List<InstansiModel> allInstansi = provPertama.getListInstansiProvinsi();
		List<JabatanModel> allJabatan = jabatanService.allJabatan();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan",allJabatan);
		model.addAttribute("allInstansi",allInstansi);
		model.addAttribute("allProvinsi", allProvinsi);
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params= {"addJabatan"})
	private String addRowJabatan(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiBaru = pegawai;
		JabatanPegawaiModel jabatan = new JabatanPegawaiModel();
		jabatan.setPegawai(pegawaiBaru);
		pegawaiBaru.getListJabatanPegawai().add(jabatan);
		List<ProvinsiModel> allProvinsi = provinsiService.allProvinsi();
		List<InstansiModel> allInstansi = new ArrayList<InstansiModel>();
		allInstansi = allProvinsi.get(0).getListInstansiProvinsi();
		List<JabatanModel> allJabatan = jabatanService.allJabatan();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allInstansi", allInstansi);
		model.addAttribute("allProvinsi", allProvinsi);
		model.addAttribute("allJabatan", allJabatan);
		return "add-pegawai";
	}
	
	@RequestMapping(value="pegawai/tambah", method=RequestMethod.POST, params= {"save"})
	private String submitPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatanPegawai = pegawai.getListJabatanPegawai();
		pegawai.setListJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		pegawaiService.addPegawai(pegawai);
		
		for(JabatanPegawaiModel jabatanpegawai : listJabatanPegawai) {
			jabatanpegawai.setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatanpegawai);
		}
		model.addAttribute("tambahPegawai", "Pegawai");
		return "submit-add";
	}
	
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "submit-add";
		
	}
	
	@RequestMapping (value = "pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai (@RequestParam (value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		List <ProvinsiModel> allProvinsi = provinsiService.allProvinsi();
		List <JabatanModel> allJabatan = jabatanService.allJabatan();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("size", pegawai.getListJabatanPegawai().size());
		model.addAttribute("allProvinsi", allProvinsi);
		model.addAttribute("allJabatan", allJabatan);
		return "update-pegawai";
	}
	
	
	@RequestMapping (value = "pegawai/ubah", method = RequestMethod.POST)
	public String simpanUbahPegawai (@ModelAttribute PegawaiModel pegawai, Model model) {
		jabatanPegawaiService.setPegawaiJabatan(pegawai);
		PegawaiModel pegawaiLama = pegawaiService.getPegawaiById(pegawai.getId());
		pegawaiService.hapusJabatanPegawai(pegawaiLama);
		pegawaiService.generateNip(pegawai);
		pegawaiService.updatePegawai(pegawai, pegawai.getId());
		model.addAttribute("nip", pegawai.getNip());
		return "success-Update";
	}
	
	
}
