package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneDispensation;
import org.openmrs.module.pharmagest.PharmOperation;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.OperationService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.api.RegimeService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireRapportCommande;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

@Controller
@SessionAttributes("formulaireRapportCommande")
@RequestMapping(value = "/module/pharmagest/importDispensations.form")
public class ImportDispensationsController {

	private static String UPLOADED_FOLDER = "C://Upload/";
	 private final static char SEPARATOR = ';';
	 
	 private static PharmGestionnairePharma pharmGestionnairePharma;
	 
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/importDispensations";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_rechercher" })
	public void export(@RequestParam("file") MultipartFile file, HttpSession session, ModelMap model) {

		 try {
		
			 if (!file.isEmpty()) {
				 String fileName=file.getOriginalFilename();
				 String RESOURCES_PATH=UPLOADED_FOLDER + file.getOriginalFilename();				 			 
				

			            // Get the file and save it somewhere
			            byte[] bytes = file.getBytes();
			            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			            Files.write(path, bytes);
			        
			 
			 
			    File fichier = new File(RESOURCES_PATH);
		        FileReader fr = new FileReader(fichier);
		        CSVReader csvReader = new CSVReader(fr, SEPARATOR);
		        
		        List<String[] > data = new ArrayList<String[]>();
		        
		        String[] nextLine = null;
		        while ((nextLine = csvReader.readNext()) != null) {
		            int size = nextLine.length;
		            // ligne vide
		            if (size == 0) {
		                continue;
		            }
		            String debut = nextLine[0].trim();
		            if (debut.length() == 0 && size == 1) {
		                continue;
		            }
		            System.out.println("-----------------debut-------------------"+debut);
		            // ligne de commentaire
		            if (debut.startsWith("#")) {
		                continue;
		            }
		            
		            data.add(nextLine);
		        }
		        
		        csvReader.close();
		        fr.close();
		        
		        for (String[] oneData : data) {
		        	
		        	
		        	String numPatient = oneData[0];
		        	System.out.println("-----------------numPatient-------------------"+numPatient);
		            String dateRegim = oneData[1];
		            System.out.println("------------------dateRegim------------------"+dateRegim);
		            String regim = oneData[2];
		            System.out.println("---------------------regim---------------"+regim);
		            String jours = oneData[3];
		            System.out.println("---------------------jours---------------"+jours);
		            String but = oneData[4];
		            System.out.println("---------------------but--------------"+but);
		              
		            if(numPatient!=null ) {
		            	PharmOrdonnance ord = new PharmOrdonnance();
			            ord.setOrdNumeroPatient(numPatient.replaceAll(" ", ""));
			            int nbJours=(jours!=null)?Integer.parseInt(jours):0;
			            
			            if(dateRegim!=null) {
			            	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			            	Date dateDisp=sf.parse(dateRegim);
			            	ord.setOrdDateDispen(dateDisp);
			            	
			            	GregorianCalendar gc = new GregorianCalendar();
			 				gc.setTime(dateDisp);
			 				gc.add(GregorianCalendar.DATE, nbJours);
			 				ord.setOrdDateRdv(gc.getTime());
			            }
			            
			            if(regim!=null) {
			            	regim=regim.trim();
			            	PharmRegime pharmRegime=Context.getService(RegimeService.class).findRegimeByName(regim);
			            	if(pharmRegime!=null) ord.setPharmRegime(pharmRegime);			            	
			            }
			            
			            PharmProgramme pharmProgramme=Context.getService(ProgrammeService.class).getPharmProgrammeById(1);
			            ord.setPharmProgramme(pharmProgramme);
			            ord.setOrdNbreJrsTrai(nbJours);
			    		
			    		ord.setPharmGestionnairePharma(pharmGestionnairePharma);
			    		ord.setOrdDateSaisi(new Date());
			    		Context.getService(DispensationService.class).savePharmOrdonnance(ord);
			    		
						/*
						 * PharmLigneDispensation ligneDispensation =new PharmLigneDispensation();
						 * ligneDispensation.setPharmOrdonnance(ord);
						 * ligneDispensation.setPharmProduit(Context.getService(ProduitService.class).
						 * findProduitByCode("AR01340")); ligneDispensation.setLdQteServi(nbJours);
						 * Context.getService(DispensationService.class).savePharmLigneDispensation(
						 * ligneDispensation);
						 */
			    		
			            PharmOperation operation = new PharmOperation();
						operation.setPharmTypeOperation(Context.getService(ParametresService.class).getTypeOperationById(2));
						operation.setOperDateRecept(ord.getOrdDateDispen());
						operation.setOperDateSaisi(new Date());
						operation.setPharmGestionnairePharma(pharmGestionnairePharma);
						operation.setPharmProgramme(pharmProgramme);
						operation.setOperRef(ord.getOrdId()+"");
						Context.getService(OperationService.class).savePharmOperation(operation);
			            
		            }
		            
		            
		            
		          
		        }
		       
		       //System.out.println("-----------------------------Suprimer ? :"+fichier.delete());
		        
		        
			 }
		
			 } catch (IOException e) {
		            e.printStackTrace();
		     } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	

	public void initialisation(ModelMap model) {
		FormulaireRapportCommande formulaireRapportCommande = new FormulaireRapportCommande();
		model.addAttribute("formulaireRapportCommande", formulaireRapportCommande);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		pharmGestionnairePharma=gestionnairePharma;

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();

		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(PharmProduit.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				int nbr = 0;
				NumberFormat format = NumberFormat.getInstance();
				try {
					nbr = format.parse(text).intValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PharmProduit produit = Context.getService(ParametresService.class).getProduitById(nbr);
				this.setValue(produit);
			}
		});
		binder.registerCustomEditor(PharmSite.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmSite site = Context.getService(SiteService.class).getPharmSiteById(Integer.parseInt(text));
				this.setValue(site);
			}
		});
		binder.registerCustomEditor(PharmProgramme.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				PharmProgramme programme = Context.getService(ParametresService.class)
						.getProgrammeById(Integer.parseInt(text));
				this.setValue(programme);
			}
		});

	}

}
