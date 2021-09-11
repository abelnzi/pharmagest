package org.openmrs.module.pharmagest.web.controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
import org.openmrs.module.pharmagest.PharmGestionnairePharma;
import org.openmrs.module.pharmagest.PharmInventaire;
import org.openmrs.module.pharmagest.PharmLigneInventaire;
import org.openmrs.module.pharmagest.PharmLigneRc;
import org.openmrs.module.pharmagest.PharmPrixProduit;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProduitCompl;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRapportCommande;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.InventaireService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.api.RapportCommandeService;
import org.openmrs.module.pharmagest.api.SiteService;
import org.openmrs.module.pharmagest.metier.FormulaireRapportCommande;
import org.openmrs.module.pharmagest.validator.ListRPPSValidator;
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

import au.com.bytecode.opencsv.CSVReader;

@Controller
@SessionAttributes("formulaireRapportCommande")
@RequestMapping(value = "/module/pharmagest/dispensDistrict.form")
public class DispensDistrictController {

	private static String UPLOADED_FOLDER = "C://Upload/";
	 private final static char SEPARATOR = ';';
	 
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/dispensDistrict";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_rechercher" })
	public void export(@RequestParam("file") MultipartFile file, HttpSession session, ModelMap model) {/*

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
		        
		        List<String[] > data = new ArrayList<String[] >();
		        
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
		            // ligne de commentaire
		            if (debut.startsWith("#")) {
		                continue;
		            }
		            data.add(nextLine);
		        }
		        
		        csvReader.close();
		        fr.close();
		        
		        for (String[] oneData : data) {
		        	
		        	
		        	String dateDispens = oneData[0];
		        	//System.out.println("-----------------Date dispensation-------------------"+dateDispens);
		            String desgn = oneData[1];
		            //System.out.println("------------------desgn------------------"+desgn);
		            String codePatient = oneData[2];
		            //System.out.println("---------------------codePatient---------------"+codePatient);
		            String sexe = oneData[3];
		          //System.out.println("---------------------sexe---------------"+sexe);
		            String age = oneData[4];
		            //System.out.println("---------------------age--------------"+age);
		            String nbreJourT = oneData[5];
		          //System.out.println("---------------------nbreJourT--------------"+nbreJourT);
		            String lastRegim = oneData[6];
		          //System.out.println("---------------------lastRegim--------------"+lastRegim);
		            String regim = oneData[6];
			          //System.out.println("---------------------regim--------------"+regim);
		            String ctx = oneData[6];
			          //System.out.println("---------------------ctx--------------"+ctx);
		            
		            
		            int nbreUnitCondInt=(nbreUnitCond!=null) ? Integer.parseInt(nbreUnitCond) : 1 ;
		            //System.out.println("-----------------------nbreUnitCondInt-------------"+nbreUnitCondInt);
		            
		            int prixVenteInt=(prixVente!=null) ? Integer.parseInt(prixVente) : 0 ;
		            String progLib = oneData[7];            
		                       
		            
		            //gestion du produit
		            PharmProduit produit = Context.getService(ProduitService.class).findProduitByCode(code);
		            if(produit==null){
		            	produit=new PharmProduit();
		            	produit.setProdCode(code);
		            	produit.setProdLib(desgnDetail);
		            	produit.setProdUnite(unitDis);
		            	Context.getService(ProduitService.class).savePharmProduit(produit);
		            }else{
		            	produit.setProdCode(code);
		            	produit.setProdLib(desgnDetail);
		            	produit.setProdUnite(unitDis);
		            	//System.out.println("=================ProdLib==================="+desgnDetail);
		            	Context.getService(ProduitService.class).updatePharmProduit(produit);
		            }
		            
		            //gestion de programme
		            PharmProgramme programme = Context.getService(ProgrammeService.class).getPharmProgrammeByLib(progLib);
		            if(programme==null){
		            	programme=new PharmProgramme();
		            	programme.setProgramLib(progLib);
		            	Context.getService(ProgrammeService.class).savePharmProgramme(programme);
		            }		            
		            //liée le programme au produit
		            
		            if(!produit.getPharmProgrammes().contains(programme)){
		            	
		            	Context.getService(ProduitService.class).savePharmProgrammeProduit(programme.getProgramId(), produit.getProdId());
		            }
		            
		            //Gestion du complement produit
		            PharmProduitCompl produitCompl=Context.getService(ProduitService.class).getProduitComplByProduit(produit);
		            if(produitCompl==null)produitCompl= new PharmProduitCompl();
		            	produitCompl.setPharmProduit(produit);
		            	produitCompl.setProdCplCode(code);
		            	produitCompl.setProdCplLibDetail(desgnDetail);
		            	produitCompl.setProdCplLibGros(desgn);
		            	produitCompl.setProdCplUnitConvers(nbreUnitCondInt);
		            	produitCompl.setProdCplUnitDetail(unitDis);
		            	produitCompl.setProdCplUnitGros(unitCond);
		            	
		            	Context.getService(ProduitService.class).savePharmProduitCompl(produitCompl);
		            	
		            //Gestion du prix des produits
		            PharmPrixProduit prixProduit=Context.getService(ProduitService.class).getPharmPrixProduitByPP(produit, programme.getProgramId());
		            if(prixProduit==null)prixProduit=new PharmPrixProduit();
		            prixProduit.setPrixVente(prixVenteInt);
		            prixProduit.setPrixDateEff(new Date());
		            prixProduit.setPrixFlagActif(true);
		            prixProduit.setPharmProduit(produit);
		            prixProduit.setProgramId(programme.getProgramId());
		            Context.getService(ProduitService.class).savePharmPrixProduit(prixProduit);
		            
		            
		          
		        }
		       
		       //System.out.println("-----------------------------Suprimer ? :"+fichier.delete());
		        
		        
			 }
		
			 } catch (IOException e) {
		            e.printStackTrace();
		     }

	*/}
	
	public String getChemin(){
		
				JFileChooser chooser = new JFileChooser();
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		           String ff =(chooser.getSelectedFile().getPath());
		           //System.out.println("===================================================================");
				   //System.out.println("Save as file: " + ff);
		           return ff;
		        }
		  return null;
		    
		
			/*JFrame parentFrame = new JFrame();
			 
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specifier le chemin du fichier");   
			 
			int userSelection = fileChooser.showSaveDialog(parentFrame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
			   // System.out.println("===================================================================");
			    //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			    return fileToSave.getAbsolutePath();
			} else {
	            //System.out.println("L'enregistrement est annulée\n");
	            return null;
	       }*/
        
			
			/*final JFrame fenetre = new JFrame();
	        fenetre.setSize(200,200);
	        fenetre.setLocationRelativeTo(null);
	        fenetre.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        fenetre.setVisible(true);
	        
	        //Instanciation d'un objet JPanel
	        JPanel pan = new JPanel();
	        
	        pan.setLayout(new FlowLayout());
	        
			JButton bouton = new JButton("Annuler");
			pan.add(bouton);
	 
			JButton bouton2 = new JButton("Enregistrer");
			pan.add(bouton2);
			fenetre.setContentPane(pan);
	        
	        
	  
	        final JFileChooser fc = new JFileChooser();
	  
	        bouton2.addActionListener(new ActionListener() {
	          @Override
	          public void actionPerformed(ActionEvent arg0) {
	             // TODO Auto-generated method stub
	             int val_retour = fc.showSaveDialog(fenetre);

	             if (val_retour == JFileChooser.APPROVE_OPTION) {
	                File fichier = fc.getSelectedFile();
	                //afficher le chemin absolu du fichier
	                System.out.println("Chemin absolu : "+fichier.getAbsolutePath()+"\n");
	             } else {
	                  System.out.println("L'enregistrement est annulée\n");
	             }
	          }
	       });*/
		
				
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
		// formulaireSaisiesPPS.setPharmGestionnairePharma(gestionnairePharma);

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
