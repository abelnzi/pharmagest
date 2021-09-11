package org.openmrs.module.pharmagest.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitAttribut;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmStocker;
import org.openmrs.module.pharmagest.Stocker;
import org.openmrs.module.pharmagest.api.GestionStockService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;

import net.sf.saxon.functions.Data;

public class GestionAlert {
	
	

	public List<ContaintStock> getStockMin() {
		int stockMax =2;
		int stockMin=0;
		List<ContaintStock> listMinStock = new ArrayList<ContaintStock>();
		Collection<PharmProgramme> listProgram = Context.getService(ProgrammeService.class).getAllPharmProgrammes();
		Iterator it = listProgram.iterator();
		while (it.hasNext()) {
			PharmProgramme program = (PharmProgramme) it.next();
			PharmRapportCommande rapportCommande = Context.getService(RapportCommandeService.class)
					.getPharmRapportCommande(program);
			if (rapportCommande != null) {
				if (Context.hasPrivilege("pharmacie DISTRICT"))	stockMax=4;
				 stockMin = stockMax / 2;
				Map<PharmProduit, Integer> listStocks = this.listStockByProduit(
						Context.getService(GestionStockService.class).getPharmStockersByProgram(program));
				Map<PharmProduit, PharmLigneRc> listCommandes = this
						.listCommandeByProduit(rapportCommande.getPharmLigneRcs());
				for (Map.Entry<PharmProduit, PharmLigneRc> entry : listCommandes.entrySet()) {
					PharmProduit keyProduit = entry.getKey();
					PharmLigneRc ligneRcValue = entry.getValue();
					Integer qteStock = listStocks.get(keyProduit);
					double msd = 0;
					if (ligneRcValue.getLgnRcConsoMoyenMens() != null && ligneRcValue.getLgnRcConsoMoyenMens() != 0){
						qteStock=(qteStock!=null) ? qteStock : 0 ;
						
						msd = this.arrondir((double)qteStock / (double)ligneRcValue.getLgnRcConsoMoyenMens(),1);
						}
					if (msd < stockMin) {
						ContaintStock c = new ContaintStock();
						c.setProduit(keyProduit);
						c.setProgramme(program);
						c.setQteStock(qteStock);
						c.setMsd(msd);
						c.setCmm(ligneRcValue.getLgnRcConsoMoyenMens());
						listMinStock.add(c);
					}

				}
			}

		}
		return listMinStock;

	}

	public List<ContaintStock> getStockMax() {
		int stockMax =2;
		int stockMin=0;
		List<ContaintStock> listMaxStock = new ArrayList<ContaintStock>();
		Collection<PharmProgramme> listProgram = Context.getService(ProgrammeService.class).getAllPharmProgrammes();
		Iterator it = listProgram.iterator();
		while (it.hasNext()) {
			PharmProgramme program = (PharmProgramme) it.next();
			PharmRapportCommande rapportCommande = Context.getService(RapportCommandeService.class)
					.getPharmRapportCommande(program);
			if (rapportCommande != null) {
				if (Context.hasPrivilege("pharmacie distribution"))	stockMax=4;
				 stockMin = stockMax / 2;
				 System.out.println("StockMax : "+stockMax);
				Map<PharmProduit, Integer> listStocks = this.listStockByProduit(
						Context.getService(GestionStockService.class).getPharmStockersByProgram(program));
				Map<PharmProduit, PharmLigneRc> listCommandes = this
						.listCommandeByProduit(rapportCommande.getPharmLigneRcs());
				for (Map.Entry<PharmProduit, PharmLigneRc> entry : listCommandes.entrySet()) {
					PharmProduit keyProduit = entry.getKey();
					PharmLigneRc ligneRcValue = entry.getValue();
					Integer qteStock = listStocks.get(keyProduit);
					double msd = 0;
					if (ligneRcValue.getLgnRcConsoMoyenMens() != null & ligneRcValue.getLgnRcConsoMoyenMens() != 0){
						qteStock=(qteStock!=null) ? qteStock : 0 ;						
						msd = this.arrondir((double)qteStock /(double)ligneRcValue.getLgnRcConsoMoyenMens(),1);
					}
					if (msd > stockMax) {
						ContaintStock c = new ContaintStock();
						c.setProduit(keyProduit);
						c.setProgramme(program);
						c.setQteStock(qteStock);
						c.setMsd(msd);
						c.setCmm(ligneRcValue.getLgnRcConsoMoyenMens());
						listMaxStock.add(c);
					}
				}
			}

		}
		return listMaxStock;

	}

	public Map<PharmProduit, Integer> listStockByProduit(Collection<PharmStocker> list) {

		Map<PharmProduit, Integer> listStock = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();
			if (listStock.containsKey(stock.getPharmProduitAttribut().getPharmProduit())) {
				int qteStock = listStock.get(stock.getPharmProduitAttribut().getPharmProduit());
				qteStock = qteStock + stock.getStockQte();
				listStock.put(stock.getPharmProduitAttribut().getPharmProduit(), qteStock);
			} else {
				listStock.put(stock.getPharmProduitAttribut().getPharmProduit(), stock.getStockQte());
			}

		}
		return listStock;

	}

	public Map<PharmProduit, PharmLigneRc> listCommandeByProduit(Set<PharmLigneRc> list) {

		Map<PharmProduit, PharmLigneRc> listRCommandes = Collections.synchronizedMap(new HashMap());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PharmLigneRc ligneRc = (PharmLigneRc) it.next();
			if (!listRCommandes.containsKey(ligneRc.getPharmProduit())) {
				listRCommandes.put(ligneRc.getPharmProduit(), ligneRc);
			}

		}
		return listRCommandes;

	}
	
	public List<ContaintStock> getProduitsPerimes(){
		List<ContaintStock> listStockPerim = new ArrayList<ContaintStock>();
		Date today= new Date();
		long oneDay= (1000*60*60*24);
		Collection<PharmStocker> stocks=Context.getService(GestionStockService.class).getAllPharmStockers();
		Iterator it = stocks.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();
			PharmProduitAttribut prodAtt=stock.getPharmProduitAttribut();
			long jour=(prodAtt.getProdDatePerem().getTime()-today.getTime())/oneDay;
			if(jour<=0){
				ContaintStock containt=new ContaintStock();
				containt.setPharmProduitAttribut(prodAtt);
				containt.setProgramme(stock.getPharmProgramme());
				containt.setQteStock(stock.getStockQte());
				listStockPerim.add(containt);
			}
		}
		return listStockPerim;
		
	}
	
	public List<ContaintStock> getProduitsPerimes(PharmProgramme programme){
		List<ContaintStock> listStockPerim = new ArrayList<ContaintStock>();
		Date today= new Date();
		long oneDay= (1000*60*60*24);
		Collection<PharmStocker> stocks=Context.getService(GestionStockService.class).getPharmStockersByProgram(programme);
		Iterator it = stocks.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();
			PharmProduitAttribut prodAtt=stock.getPharmProduitAttribut();
			long jour=(prodAtt.getProdDatePerem().getTime()-today.getTime())/oneDay;
			if(jour<=0){
				PharmPrixProduit prPd=Context.getService(ProduitService.class).getPharmPrixProduitByPP(prodAtt.getPharmProduit(), programme.getProgramId());
				ContaintStock containt=new ContaintStock();
				containt.setPharmProduitAttribut(prodAtt);
				containt.setProgramme(stock.getPharmProgramme());
				containt.setQteStock(stock.getStockQte());
				if(prPd!=null) containt.setPrixVente(prPd.getPrixVente());
				listStockPerim.add(containt);
			}
		}
		return listStockPerim;
		
	}
	
	public List<ContaintStock> getProduitsAlerte(){
		
		List<ContaintStock> listStockAlerte = new ArrayList<ContaintStock>();
		Date today= new Date();
		long oneDay= (1000*60*60*24);
		Collection<PharmStocker> stocks=Context.getService(GestionStockService.class).getAllPharmStockers();
		Iterator it = stocks.iterator();
		while (it.hasNext()) {
			PharmStocker stock = (PharmStocker) it.next();
			
			
			PharmProduitAttribut prodAtt=stock.getPharmProduitAttribut();
			long jour=(prodAtt.getProdDatePerem().getTime()-today.getTime())/oneDay;
			if(jour>0 && jour<=120){
				ContaintStock containt=new ContaintStock();
				containt.setPharmProduitAttribut(prodAtt);
				containt.setProgramme(stock.getPharmProgramme());
				containt.setQteStock(stock.getStockQte());
				/**********************determiner la msd du produit***************************/
				PharmRapportCommande rapportCommande = Context.getService(RapportCommandeService.class)
						.getPharmRapportCommande(stock.getPharmProgramme());
				if(rapportCommande!=null){
				Map<PharmProduit, PharmLigneRc> listCommandes = this
						.listCommandeByProduit(rapportCommande.getPharmLigneRcs());
				for (Map.Entry<PharmProduit, PharmLigneRc> entry : listCommandes.entrySet()) {
					PharmProduit keyProduit = entry.getKey();
					PharmLigneRc ligneRcValue = entry.getValue();
					
					double msd = 0;
					
					if (stock.getPharmProduitAttribut().getPharmProduit().getProdCode().equals(keyProduit)) {
						if (ligneRcValue.getLgnRcConsoMoyenMens() != null & ligneRcValue.getLgnRcConsoMoyenMens() != 0)
							msd = this.arrondir((double)stock.getStockQte() /(double) ligneRcValue.getLgnRcConsoMoyenMens(),1);
						
						containt.setCmm(ligneRcValue.getLgnRcConsoMoyenMens());
						containt.setMsd(msd);
					}
				}
				}
				/*************************************************/
				
				listStockAlerte.add(containt);
			}
		}
		return listStockAlerte;
		
	}
	
	public Double arrondir(Double d,int p){

		return (double) (Math.round(d * Math.pow(10,p))/Math.pow(10,p));
					
	}

}
