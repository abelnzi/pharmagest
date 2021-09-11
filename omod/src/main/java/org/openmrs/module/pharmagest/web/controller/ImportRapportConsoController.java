package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmCommandeSite;
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmLigneCommandeSite;
import org.openmrs.module.pharmagest.PharmLigneCommandeSiteId;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireSaisiesPPS;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
import org.openmrs.module.pharmagest.validator.SaisiePPS2Validator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidator;
import org.openmrs.module.pharmagest.validator.SaisiePPSValidatorAjout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Controller
@SessionAttributes("formulaireSaisiesPPS")
@RequestMapping(value = "/module/pharmagest/importRapportConso.form")
public class ImportRapportConsoController {

	private static String UPLOADED_FOLDER = "C://Upload/";
	 private final static char SEPARATOR = ';';
	 
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	SaisiePPSValidator saisiePPSValidator;
	@Autowired
	SaisiePPSValidatorAjout saisiePPSValidatorAjout;
	@Autowired
	SaisiePPS2Validator saisiePPS2Validator;

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/importRapportConso";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_rechercher" })
	public void export(@ModelAttribute("formulaireSaisiesPPS") FormulaireSaisiesPPS formulaireSaisiesPPS,
			@RequestParam("file") MultipartFile file, HttpSession session, ModelMap model, HttpServletRequest request) {
		
		//saisiePPS2Validator.validate(formulaireSaisiesPPS, result);
		
		 try {
			 List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
				List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
						.getAllProgrammes();
			 String type = request.getParameter("urgent");
			//verifier le type de rapport
				ArrayList<PharmCommandeSite> consoSites = new ArrayList<PharmCommandeSite>();
			if(type==null){
					consoSites = (ArrayList<PharmCommandeSite>) Context.getService(CommandeSiteService.class)
					.getPharmCommandeSiteByPeriod(formulaireSaisiesPPS.getPharmSite(),formulaireSaisiesPPS.getPharmProgramme(), formulaireSaisiesPPS.getComSitePeriodDate(),"NUR");
					formulaireSaisiesPPS.setComSitePeriodLib("");					
			}else { //rapport urgent
					consoSites = (ArrayList<PharmCommandeSite>) Context
						.getService(CommandeSiteService.class)
						.getPharmCommandeSiteByPeriod(formulaireSaisiesPPS.getPharmSite(),
								formulaireSaisiesPPS.getPharmProgramme(), formulaireSaisiesPPS.getComSitePeriodDate(),"UR");
					formulaireSaisiesPPS.setComSitePeriodLib("urgent");
			}
			 
			 if (consoSites.isEmpty()) {
		
			 if (!file.isEmpty()) {
				 
				String fileName=file.getOriginalFilename();
				String RESOURCES_PATH=UPLOADED_FOLDER + file.getOriginalFilename(); 			 
				//System.out.println("===============RESOURCES_PATH============="+RESOURCES_PATH);
			    // Get the file and save it somewhere
			    byte[] bytes = file.getBytes();
			    Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			    Files.write(path, bytes);		        
			 
			 
			    File fichier = new File(RESOURCES_PATH);
		        FileReader fr = new FileReader(fichier);
		        //CSVReader csvReader = new CSVReader(fr, SEPARATOR);
		        
		       
		        
		        String line = "";
		        String cvsSplitBy = ";";
		        List<String> listData = new ArrayList<String>();
		        BufferedReader br = new BufferedReader(fr);
		        
		        while ((line = br.readLine()) != null) {
		        	
		        	  int size = line.length();
		        	 // ligne vide
		           /* if (size == 0) {
		                continue;
		            }
		            String debut = line.trim();
		            if (debut.length() == 0 && size == 1) {
		                continue;
		            }*/
		        	 listData.add(line);
		        	 //line = br.readLine();       	
		        	
		        }
		        
		        //System.out.println("============= listData.size() ============"+listData.size());
		        
		        boolean check=false;//gerer les fichiers sans données
		        
		        //Creer l'entête
				formulaireSaisiesPPS.getCommandeSite().setComSiteFlag(0);
				formulaireSaisiesPPS.getCommandeSite().setComSiteDateSaisi(new Date());
				Context.getService(CommandeSiteService.class).savePharmCommandeSite(formulaireSaisiesPPS.getCommandeSite());
		       
		        for(int i=0; i<listData.size(); i++)
	             {
	                //System.out.println("============= i ============"+i);	                 
	                String[] colonne = listData.get(i).split(cvsSplitBy);
	                
			        String code= ((colonne.length>0)?colonne[0]:"0");
			       			        
			        //recuperer le produit
			        PharmProduit produit = Context.getService(ProduitService.class).findProduitByCode(code);       
			        if(produit!=null) {
			        	
			        				        	
			        	PharmLigneCommandeSite lgnCommande = new PharmLigneCommandeSite();
						PharmLigneCommandeSiteId lgnCommandeId = new PharmLigneCommandeSiteId();

						lgnCommandeId.setComSiteId(formulaireSaisiesPPS.getCommandeSite().getComSiteId());
						lgnCommandeId.setProdId(produit.getProdId());
						lgnCommande.setId(lgnCommandeId);
						
						//System.out.println("===============colonne==============="+colonne.length);
						
						//Gestion des colonnne
						for(int j=0; j<colonne.length; j++) {
							//System.out.println("============verification=============== :"+j+" - "+colonne[j]);
							
							 switch (j) {
							case 3:
								int stockIni=0;
								stockIni=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								//System.out.println("============stockIni=============== :"+stockIni);
								lgnCommande.setLgnComQteIni(stockIni);
								//System.out.println("============colonne[3]=============== :"+colonne[3]);
								check=true;	
								break;
							case 4:
								int qteRecu=0;
								qteRecu=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;	
								lgnCommande.setLgnComQteRecu(qteRecu);
								check=true;	
								break;
							case 5:
								int qteUtil=0;
								qteUtil=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;	
								lgnCommande.setLgnComQteUtil(qteUtil);
								check=true;	
							break;
							case 6:
								int perte=0;
								perte=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								lgnCommande.setLgnComPertes(perte);
								check=true;	
							break;
							
							case 7:
								int stockDispo=0;
								stockDispo=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								lgnCommande.setLgnComStockDispo(stockDispo);
								check=true;	
							break;
							case 8:
								int jourRup=0;
								jourRup=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								lgnCommande.setLgnComNbreJrsRup(jourRup);
								check=true;	
							break;
							case 9:
								int qteDistM1=0;
								qteDistM1=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								lgnCommande.setLgnQteDistriM1(qteDistM1);
								check=true;	
							break;
							case 10:
								int qteDistM2=0;
								qteDistM2=(colonne[j]!=null && !(colonne[j].equals(""))) ? Integer.parseInt(colonne[j]) : 0 ;
								lgnCommande.setLgnQteDistriM2(qteDistM2);
								check=true;	
							break;

							default:
								break;
							}

							lgnCommande.setPharmCommandeSite(formulaireSaisiesPPS.getCommandeSite());
							lgnCommande.setPharmProduit(produit);
							if(this.emptyField(lgnCommande))Context.getService(CommandeSiteService.class).savePharmLigneCommandeSite(lgnCommande);
							
							
						}
			        	
			        }
			        
			        
	             }
		        
		        br.close();
		        fr.close();  
		   
		       if(check==false)Context.getService(CommandeSiteService.class).deletePharmCommandeSite(formulaireSaisiesPPS.getCommandeSite());
		       
		       model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
		       model.addAttribute("sites", sites);
		       model.addAttribute("programmes", programmes);
		       model.addAttribute("mess", "success");
		       this.initialisation(model);
		        
		        
			 }
			 } else {
				 	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					model.addAttribute("moisDe", formatter.format(formulaireSaisiesPPS.getComSitePeriodDate()));
					model.addAttribute("mess", "existe");
					this.initialisation(model);
				}
			 
		
			 } catch (IOException e) {
		            e.printStackTrace();
		     }

	}
	
	public boolean emptyField(PharmLigneCommandeSite ligne) {
		if((ligne.getLgnComQteIni()==null||ligne.getLgnComQteIni()==0) && (ligne.getLgnComQteRecu()==null||ligne.getLgnComQteRecu()==0) &&
				(ligne.getLgnComQteUtil()==null||ligne.getLgnComQteUtil()==0) && (ligne.getLgnComStockDispo()==null||ligne.getLgnComStockDispo()==0) &&
				(ligne.getLgnQteDistriM1()==null||ligne.getLgnQteDistriM1()==0) && (ligne.getLgnQteDistriM2()==null||ligne.getLgnQteDistriM2()==0) &&
				(ligne.getLgnComPertes()==null||ligne.getLgnComPertes()==0) && (ligne.getLgnComNbreJrsRup()==null||ligne.getLgnComNbreJrsRup()==0)
			) {
			return false ;
		}
		
		return true;
	}
	
	public String getChemin(){
		
				JFileChooser chooser = new JFileChooser();
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		           String ff =(chooser.getSelectedFile().getPath());
		           //System.out.println("===================================================================");
				 
		           return ff;
		        }
		  return null;  
							
				
	}
	
	public void initialisation(ModelMap model) {
		FormulaireSaisiesPPS formulaireSaisiesPPS = new FormulaireSaisiesPPS();
		model.addAttribute("formulaireSaisiesPPS", formulaireSaisiesPPS);
		// gestion du gestionnaire
		PharmGestionnairePharma gestionnairePharma = new PharmGestionnairePharma();
		gestionnairePharma.setGestId(Context.getAuthenticatedUser().getUserId());
		gestionnairePharma.setGestNom(Context.getAuthenticatedUser().getFirstName());
		gestionnairePharma.setGestPrenom(Context.getAuthenticatedUser().getLastName());
		Context.getService(ParametresService.class).saveOrUpdateGestionnaire(gestionnairePharma);
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);
		List<PharmSite> sites = (List<PharmSite>) Context.getService(SiteService.class).getAllPharmSites();
		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();
		List<PharmProduit> produits = (List<PharmProduit>) Context.getService(ParametresService.class).getAllProduits();
		model.addAttribute("sites", sites);
		model.addAttribute("programmes", programmes);
		model.addAttribute("produits", produits);
		model.addAttribute("var", "0");
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
