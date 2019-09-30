package HelmaYuniaProjectSpringBoot.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HelmaYuniaProjectSpringBoot.Model.TabunganModel;
import HelmaYuniaProjectSpringBoot.Repository.TabunganRepository;

@Service
public class TabunganDao {
	@Autowired
	TabunganRepository TabRepository;
	
	public TabunganModel save(TabunganModel tm){
		TabunganModel t=tm;
		TabunganModel tabmo=TabRepository.findSaldoTabungan(tm.getNik());
		if(tabmo==null) {
			t.setSaldo(0+t.getKredit()-t.getDebet());
			return TabRepository.save(t);
		}else {
			t.setSaldo(tabmo.getSaldo()+t.getKredit()-t.getDebet());
			return TabRepository.save(t);
		}
		
	}
	
	public List<TabunganModel> findAll(){
		return TabRepository.findAll();
	}
	
	public TabunganModel getFindOne(Long id) {
		return TabRepository.findOne(id);
	}
	public List<TabunganModel> getFindByNik(String nik){
		return TabRepository.findTabunganByNik(nik);
	}
	public TabunganModel getfindSaldo(String nik) {
		return TabRepository.findSaldoTabungan(nik);
	}
	
	//delete
	public void hapusTabungan(Long id) {
		TabunganModel tabu=TabRepository.findOne(id);
		List<TabunganModel> dList=TabRepository.findTabunganByNik(tabu.getNik());
		for(TabunganModel dt : dList) {
			if(dt.getId() > id) {
				TabunganModel tam1=TabRepository.findOne(dt.getId());
				tam1.setSaldo(tam1.getSaldo()+tabu.getKredit()-tabu.getDebet());
				TabRepository.save(tam1);
			}
		}
		TabRepository.delete(id);
	}
	
	//update
	public TabunganModel update(TabunganModel tabungModel) {
		TabunganModel tbng=tabungModel;
		TabunganModel nabung=TabRepository.findOne(tbng.getId());
		nabung.setSaldo(nabung.getSaldo()-tbng.getDebet()+tbng.getKredit());
		nabung.setDebet(tbng.getDebet());
		nabung.setKredit(tbng.getKredit());
		int hasil=nabung.getSaldo();
		List<TabunganModel> dataList=TabRepository.findTabunganByNik(tbng.getNik());
		for (TabunganModel data : dataList) {
			if(data.getId() > tbng.getId()) {
				TabunganModel hasil1=TabRepository.findOne(data.getId());
				hasil1.setSaldo(hasil-hasil1.getDebet()+hasil1.getKredit());
				TabRepository.save(hasil1);
				hasil=hasil1.getSaldo();
			}
		}
		return TabRepository.save(nabung);
	}
}