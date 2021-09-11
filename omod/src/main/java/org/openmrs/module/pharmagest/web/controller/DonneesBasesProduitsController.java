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
@RequestMapping(value = "/module/pharmagest/donneesBasesProduits.form")
public class DonneesBasesProduitsController {

	private static String UPLOADED_FOLDER = "C://Upload/";
	 private final static char SEPARATOR = ';';
	 
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/donneesBasesProduits";
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
		        	
		        	
		        	String code = oneData[0];
		        	//System.out.println("-----------------code-------------------"+code);
		            String desgn = oneData[1];
		            //System.out.println("------------------desgn------------------"+desgn);
		            String desgnDetail = oneData[2];
		            //System.out.println("---------------------desgnDetail---------------"+desgnDetail);
		            String unitCond = oneData[3];
		            String nbreUnitCond = oneData[4];
		            //System.out.println("---------------------nbreUnitCond--------------"+nbreUnitCond);
		            int nbreUnitCondInt=(nbreUnitCond!=null) ? Integer.parseInt(nbreUnitCond) : 1 ;
		            //System.out.println("-----------------------nbreUnitCondInt-------------"+nbreUnitCondInt);
		            String unitDis = oneData[5];
		            String prixVente = oneData[6];
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

	}
	
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
	
	public void writeRC(PharmRapportCommande rc, String chemin){ 
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.newDocument();
			
			// création de l'Element racine
			final Element racine = document.createElement("rapportCommandes");
			document.appendChild(racine);
			//creation du rapport
			final Element rapportCommande = document.createElement("rapportCommande");
			racine.appendChild(rapportCommande);
			
			final Element rap_com_id = document.createElement("rap_com_id");
			rap_com_id.appendChild(document.createTextNode(rc.getRapComId()+""));
			rapportCommande.appendChild(rap_com_id);

			final Element rap_com_code = document.createElement("rap_com_code");
			rap_com_code.appendChild(document.createTextNode(rc.getRapComCode()+""));
			rapportCommande.appendChild(rap_com_code);
			final Element rap_com_date_com = document.createElement("rap_com_date_com");
			if(rc.getRapComDateCom()!=null){						
				rap_com_date_com.appendChild(document.createTextNode(formatter.format(rc.getRapComDateCom())));					
			}else{ rap_com_date_com.appendChild(document.createTextNode("")) ; }
			rapportCommande.appendChild(rap_com_date_com);
			final Element rap_com_period_lib = document.createElement("rap_com_period_lib");
			rap_com_period_lib.appendChild(document.createTextNode(rc.getRapComPeriodLib()+""));
			rapportCommande.appendChild(rap_com_period_lib);
			final Element rap_com_period_date = document.createElement("rap_com_period_date");
			if(rc.getRapComPeriodDate()!=null){						
				rap_com_period_date.appendChild(document.createTextNode(formatter.format(rc.getRapComPeriodDate())));					
			}else{ rap_com_period_date.appendChild(document.createTextNode("")) ; }
			rapportCommande.appendChild(rap_com_period_date);
			final Element rap_com_stock_max = document.createElement("rap_com_stock_max");
			rap_com_stock_max.appendChild(document.createTextNode(rc.getRapComStockMax()+""));
			rapportCommande.appendChild(rap_com_stock_max);
			final Element rap_com_date_saisi = document.createElement("rap_com_date_saisi");
			if(rc.getRapComDateSaisi()!=null){						
				rap_com_date_saisi.appendChild(document.createTextNode(formatter.format(rc.getRapComDateSaisi())));						
			} else{ rap_com_date_saisi.appendChild(document.createTextNode("")) ; }
			rapportCommande.appendChild(rap_com_date_saisi);
			final Element rap_com_date_modif = document.createElement("rap_com_date_modif");
			if(rc.getRapComDateModif()!=null){						
				rap_com_date_modif.appendChild(document.createTextNode(formatter.format(rc.getRapComDateModif())));
			} else{ rap_com_date_modif.appendChild(document.createTextNode("")) ; }
			rapportCommande.appendChild(rap_com_date_modif);
			final Element program_id = document.createElement("program_id");
			program_id.appendChild(document.createTextNode(rc.getPharmProgramme().getProgramId()+""));
			rapportCommande.appendChild(program_id);
			
			Location defaultLocation = Context.getLocationService().getDefaultLocation();
			final Element location_id = document.createElement("location_id");
			final Element postal_code = document.createElement("postal_code");
			if (defaultLocation != null) {
				location_id.appendChild(document.createTextNode(defaultLocation.getLocationId()+""));
				postal_code.appendChild(document.createTextNode(defaultLocation.getPostalCode()+""));
			}									
			rapportCommande.appendChild(location_id);					
			rapportCommande.appendChild(postal_code);
			
			//affichage du résultat
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();

			final DOMSource source = new DOMSource(document);
			//Code à utiliser pour afficher dans un fichier
			//System.out.println("====================================================================");
			//System.out.println("Chemin absolu : " + chemin);
			File fichier = new File(chemin+"/rc.xml");
			// Créer un nouveau fichier
			fichier.createNewFile();  				 
		    // Modifier l'attribut du fichier en mode écrture
			fichier.setWritable(true);
			final StreamResult sortie = new StreamResult(fichier);
			
			
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-15");
			//transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, sortie);
			
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeLigneRC(PharmRapportCommande rc, String chemin){ 
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.newDocument();
			
			// création de l'Element racine
			final Element racine = document.createElement("ligne_rapport_commandes");
			document.appendChild(racine);
			for (PharmLigneRc lgnRc : rc.getPharmLigneRcs()) {
				//creation du rapport
				final Element ligne_rapport_commande = document.createElement("ligne_rapport_commande");
				racine.appendChild(ligne_rapport_commande);
				
				final Element prod_code = document.createElement("prod_code");
				prod_code.appendChild(document.createTextNode(lgnRc.getPharmProduit().getProdCode()));
				ligne_rapport_commande.appendChild(prod_code); 
				
				/*final Element prod_id = document.createElement("prod_id");
				prod_id.appendChild(document.createTextNode(lgnRc.getPharmProduit().getProdId()+""));
				ligne_rapport_commande.appendChild(prod_id);
								
				final Element rap_com_id = document.createElement("rap_com_id");
				rap_com_id.appendChild(document.createTextNode(lgnRc.getId().getRapComId()+""));
				ligne_rapport_commande.appendChild(rap_com_id);*/
				
				final Element lgn_rc_stock_ini = document.createElement("lgn_rc_stock_ini");
				lgn_rc_stock_ini.appendChild(document.createTextNode(lgnRc.getLgnRcStockIni()+""));
				ligne_rapport_commande.appendChild(lgn_rc_stock_ini);
				
				final Element lgn_rc_qte_recu = document.createElement("lgn_rc_qte_recu");
				lgn_rc_qte_recu.appendChild(document.createTextNode(lgnRc.getLgnRcQteRecu()+""));
				ligne_rapport_commande.appendChild(lgn_rc_qte_recu);
				
				final Element lgn_rc_qte_util = document.createElement("lgn_rc_qte_util");
				lgn_rc_qte_util.appendChild(document.createTextNode(lgnRc.getLgnRcQteUtil()+""));
				ligne_rapport_commande.appendChild(lgn_rc_qte_util);
				
				final Element lgn_rc_perte = document.createElement("lgn_rc_perte");
				lgn_rc_perte.appendChild(document.createTextNode(lgnRc.getLgnRcPerte()+""));
				ligne_rapport_commande.appendChild(lgn_rc_perte);
				
				final Element lgn_rc_ajust = document.createElement("lgn_rc_ajust");
				lgn_rc_ajust.appendChild(document.createTextNode(lgnRc.getLgnRcAjust()+""));
				ligne_rapport_commande.appendChild(lgn_rc_ajust);
				
				final Element lgn_rc_stock_dispo = document.createElement("lgn_rc_stock_dispo");
				lgn_rc_stock_dispo.appendChild(document.createTextNode(lgnRc.getLgnRcStockDispo()+""));
				ligne_rapport_commande.appendChild(lgn_rc_stock_dispo);
				
				final Element lgn_rc_jrs_rup = document.createElement("lgn_rc_jrs_rup");
				lgn_rc_jrs_rup.appendChild(document.createTextNode(lgnRc.getLgnRcJrsRup()+""));
				ligne_rapport_commande.appendChild(lgn_rc_jrs_rup);
				
				final Element lgn_rc_site_rup = document.createElement("lgn_rc_site_rup");
				lgn_rc_site_rup.appendChild(document.createTextNode(lgnRc.getLgnRcSiteRup()+""));
				ligne_rapport_commande.appendChild(lgn_rc_site_rup);
				
				final Element lgn_rc_conso_moyen_mens = document.createElement("lgn_rc_conso_moyen_mens");
				lgn_rc_conso_moyen_mens.appendChild(document.createTextNode(lgnRc.getLgnRcConsoMoyenMens()+""));
				ligne_rapport_commande.appendChild(lgn_rc_conso_moyen_mens);
				
				final Element lgn_rc__mois_stock_dispo = document.createElement("lgn_rc__mois_stock_dispo");
				lgn_rc__mois_stock_dispo.appendChild(document.createTextNode(lgnRc.getLgnRcMoisStockDispo()+""));
				ligne_rapport_commande.appendChild(lgn_rc__mois_stock_dispo);
				
				final Element lgn_rc_qte_com = document.createElement("lgn_rc_qte_com");
				lgn_rc_qte_com.appendChild(document.createTextNode(lgnRc.getLgnRcQteCom()+""));
				ligne_rapport_commande.appendChild(lgn_rc_qte_com);
				
				
				final Element lgn_rc_qte_acc = document.createElement("lgn_rc_qte_acc");
				if(lgnRc.getLgnRcQteAcc()!=null){
				lgn_rc_qte_acc.appendChild(document.createTextNode(lgnRc.getLgnRcQteAcc()+""));
				}else {lgn_rc_qte_acc.appendChild(document.createTextNode(""));}
				ligne_rapport_commande.appendChild(lgn_rc_qte_acc);
				
			}
			
		
			
			//affichage du résultat
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();

			final DOMSource source = new DOMSource(document);
			//Code à utiliser pour afficher dans un fichier
			//System.out.println("====================================================================");
			//System.out.println("Chemin absolu : " + chemin);
			File fichier = new File(chemin+"/ligne_rc.xml");
			// Créer un nouveau fichier
			fichier.createNewFile();  				 
		    // Modifier l'attribut du fichier en mode écrture
			fichier.setWritable(true);
			final StreamResult sortie = new StreamResult(fichier);
			
			
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-15");
			//transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, sortie);
			
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
