package HelmaYuniaProjectSpringBoot.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HelmaYuniaProjectSpringBoot.Dao.TabunganDao;
import HelmaYuniaProjectSpringBoot.Model.TabunganModel;

@RestController
@RequestMapping("/bank")
public class TabunganController {
	@Autowired
	TabunganDao TabDao;
	
	@PostMapping("/tabunganPost")
	public TabunganModel saveTabungan(@Valid @RequestBody TabunganModel tm) {
		return TabDao.save(tm);
	}
	
	@GetMapping("/tabungan")
	public List<TabunganModel> getSemuaTabungan(){
		return TabDao.findAll();
	}
	
	@GetMapping("/tabungan/{id}")
	public ResponseEntity<TabunganModel> getTabunganById(@PathVariable(value="id") Long id){
		TabunganModel tm=TabDao.getFindOne(id);
		if(tm==null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(tm);
		}
	}
	
	@GetMapping("/tabunganNik/{nik}")
	public List<TabunganModel> getTabunganByNik(@PathVariable(value="nik") String nik){
		return TabDao.getFindByNik(nik);
	}
	
	@DeleteMapping("tabunganDelete/{id}")
	public ResponseEntity<TabunganModel> deleteBuku(@PathVariable(value="id")Long id){
		TabunganModel b=TabDao.getFindOne(id);
		if(b==null) {
			return ResponseEntity.notFound().build();
		}else {
			TabDao.hapusTabungan(id);
			return  ResponseEntity.ok().build();
		}
	}
	
	@PutMapping("tabunganPut/{id}")
	public ResponseEntity<TabunganModel> updateSaldo (@PathVariable(value="id") Long id,@Valid @RequestBody TabunganModel tabungan){
			TabunganModel tm=TabDao.getFindOne(id);
			if(tm==null) {
				return ResponseEntity.notFound().build();
			}else {
				tm.setSaldo(tm.getSaldo()+tm.getDebet()-tm.getKredit());
				tm.setKredit(tabungan.getKredit());
				tm.setDebet(tabungan.getDebet());
				TabunganModel Hasil=TabDao.update(tm);
				return ResponseEntity.ok().body(Hasil);
			}
	}
}
