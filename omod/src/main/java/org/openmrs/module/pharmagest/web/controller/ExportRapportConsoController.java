package org.openmrs.module.pharmagest.web.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmSite;
import org.openmrs.module.pharmagest.api.CommandeSiteService;
import org.openmrs.module.pharmagest.api.ParametresService;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Controller
@SessionAttributes("formulaireRapportCommande")
@RequestMapping(value = "/module/pharmagest/exportRapportConso.form")
public class ExportRapportConsoController {

	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	ListRPPSValidator listRPPSValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String init(ModelMap model) {
		this.initialisation(model);
		return "/module/pharmagest/exportRapportConso";
	}

	@RequestMapping(method = RequestMethod.POST, params = { "btn_rechercher" })
	public void export(@ModelAttribute("formulaireRapportCommande") FormulaireRapportCommande formulaireRapportCommande,
			BindingResult result, HttpSession session, ModelMap model) {

		List<PharmProgramme> programmes = (List<PharmProgramme>) Context.getService(ParametresService.class)
				.getAllProgrammes();

		model.addAttribute("programmes", programmes);

		if (!result.hasErrors()) {
			
			ArrayList<PharmCommandeSite> commandes = (ArrayList<PharmCommandeSite>) Context.getService(CommandeSiteService.class)
					.getPharmCommandeByPeriod(formulaireRapportCommande.getPharmProgramme(),
							formulaireRapportCommande.getDateCommande(),"VA");
			
			String chemin=this.getChemin();
			if(!commandes.isEmpty()&&chemin!=null){
				PharmCommandeSite rc= commandes.get(0);
				try {
					this.writeRC(rc, chemin);
					
					this.writeLigneRC(rc, chemin);
					
					model.addAttribute("mess", "success");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					model.addAttribute("mess", "echec");
				}
			}else {
				
				model.addAttribute("mess", "echec");
			}
			
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
							
				
	}
	
	public void writeRC(PharmCommandeSite rc, String chemin){ 
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.newDocument();
			
			// création de l'Element racine
			final Element racine = document.createElement("consommationSites");
			document.appendChild(racine);
			//creation du rapport
			final Element rapportConso = document.createElement("consommationSite");
			racine.appendChild(rapportConso);
			
			final Element comSiteId = document.createElement("comSiteId");
			comSiteId.appendChild(document.createTextNode(rc.getComSiteId()+""));
			rapportConso.appendChild(comSiteId);

			final Element comSiteCode = document.createElement("comSiteCode");
			comSiteCode.appendChild(document.createTextNode(rc.getComSiteCode()+""));
			rapportConso.appendChild(comSiteCode);
			final Element comSiteDateCom = document.createElement("comSiteDateCom");
			if(rc.getComSiteDateCom()!=null){						
				comSiteDateCom.appendChild(document.createTextNode(formatter.format(rc.getComSiteDateCom())));					
			}else{ comSiteDateCom.appendChild(document.createTextNode("")) ; }
			rapportConso.appendChild(comSiteDateCom);
			
			final Element comSitePeriodLib = document.createElement("comSitePeriodLib");
			comSitePeriodLib.appendChild(document.createTextNode(rc.getComSitePeriodLib()+""));
			rapportConso.appendChild(comSitePeriodLib);
			
			final Element comSitePeriodDate = document.createElement("comSitePeriodDate");
			if(rc.getComSitePeriodDate()!=null){						
				comSitePeriodDate.appendChild(document.createTextNode(formatter.format(rc.getComSitePeriodDate())));					
			}else{ comSitePeriodDate.appendChild(document.createTextNode("")) ; }
			rapportConso.appendChild(comSitePeriodDate);
			
			final Element comStockMax = document.createElement("comStockMax");
			comStockMax.appendChild(document.createTextNode(rc.getComStockMax()+""));
			rapportConso.appendChild(comStockMax);
			
			final Element comSiteDateSaisi = document.createElement("comSiteDateSaisi");
			if(rc.getComSiteDateSaisi()!=null){						
				comSiteDateSaisi.appendChild(document.createTextNode(formatter.format(rc.getComSiteDateSaisi())));						
			} else{ comSiteDateSaisi.appendChild(document.createTextNode("")) ; }
			rapportConso.appendChild(comSiteDateSaisi);
			
			final Element comSiteDateModif = document.createElement("comSiteDateModif");
			if(rc.getComSiteDateModif()!=null){						
				comSiteDateModif.appendChild(document.createTextNode(formatter.format(rc.getComSiteDateModif())));
			} else{ comSiteDateModif.appendChild(document.createTextNode("")) ; }
			rapportConso.appendChild(comSiteDateModif);
			
			final Element program_id = document.createElement("programId");
			program_id.appendChild(document.createTextNode(rc.getPharmProgramme().getProgramId()+""));
			rapportConso.appendChild(program_id);
			
			Location defaultLocation = Context.getLocationService().getDefaultLocation();
			final Element location_id = document.createElement("locationId");
			final Element postal_code = document.createElement("postalCode");
			if (defaultLocation != null) {
				location_id.appendChild(document.createTextNode(defaultLocation.getLocationId()+""));
				postal_code.appendChild(document.createTextNode(defaultLocation.getPostalCode()+""));
			}									
			rapportConso.appendChild(location_id);					
			rapportConso.appendChild(postal_code);
			
			//affichage du résultat
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();

			final DOMSource source = new DOMSource(document);
			//Code à utiliser pour afficher dans un fichier
			//System.out.println("====================================================================");
			//System.out.println("Chemin absolu : " + chemin);
			File fichier = new File(chemin+"/consommation_site.xml");
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

	public void writeLigneRC(PharmCommandeSite rc, String chemin){ 
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document= builder.newDocument();
			
			// création de l'Element racine
			final Element racine = document.createElement("ligneConsommationSites");
			document.appendChild(racine);
			for (PharmLigneCommandeSite lgnRc : rc.getPharmLigneCommandeSites()) {
				//creation du rapport
				final Element ligneConsoSite = document.createElement("ligneConsommationSite");
				racine.appendChild(ligneConsoSite);
				
				final Element prodCode = document.createElement("prodCode");
				prodCode.appendChild(document.createTextNode(lgnRc.getPharmProduit().getProdCode()));
				ligneConsoSite.appendChild(prodCode); 
				
								
				final Element lgnComQteIni = document.createElement("lgnComQteIni");
				lgnComQteIni.appendChild(document.createTextNode(lgnRc.getLgnComQteIni()+""));
				ligneConsoSite.appendChild(lgnComQteIni);
				
				final Element lgnComQteRecu = document.createElement("lgnComQteRecu");
				lgnComQteRecu.appendChild(document.createTextNode(lgnRc.getLgnComQteRecu()+""));
				ligneConsoSite.appendChild(lgnComQteRecu);
				
				final Element lgnComQteUtil = document.createElement("lgnComQteUtil");
				lgnComQteUtil.appendChild(document.createTextNode(lgnRc.getLgnComQteUtil()+""));
				ligneConsoSite.appendChild(lgnComQteUtil);
				
				final Element lgnComPertes = document.createElement("lgnComPertes");
				lgnComPertes.appendChild(document.createTextNode(lgnRc.getLgnComPertes()+""));
				ligneConsoSite.appendChild(lgnComPertes);
				
				
				final Element lgnComStockDispo = document.createElement("lgnComStockDispo");
				lgnComStockDispo.appendChild(document.createTextNode(lgnRc.getLgnComStockDispo()+""));
				ligneConsoSite.appendChild(lgnComStockDispo);
				
				final Element lgnComNbreJrsRup = document.createElement("lgnComNbreJrsRup");
				lgnComNbreJrsRup.appendChild(document.createTextNode(lgnRc.getLgnComNbreJrsRup()+""));
				ligneConsoSite.appendChild(lgnComNbreJrsRup);				
				
				final Element lgnQteDistriM1 = document.createElement("lgnQteDistriM1");
				lgnQteDistriM1.appendChild(document.createTextNode(lgnRc.getLgnQteDistriM1()+""));
				ligneConsoSite.appendChild(lgnQteDistriM1);
				
				final Element lgnQteDistriM2 = document.createElement("lgnQteDistriM2");
				lgnQteDistriM2.appendChild(document.createTextNode(lgnRc.getLgnQteDistriM2()+""));
				ligneConsoSite.appendChild(lgnQteDistriM2);
				
				
				final Element lgnQtePro = document.createElement("lgnQtePro");
				if(lgnRc.getLgnQtePro()!=null){
					lgnQtePro.appendChild(document.createTextNode(lgnRc.getLgnQtePro()+""));
				}else {lgnQtePro.appendChild(document.createTextNode(""));}
				ligneConsoSite.appendChild(lgnQtePro);
				
			}
			
		
			
			//affichage du résultat
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();

			final DOMSource source = new DOMSource(document);
			//Code à utiliser pour afficher dans un fichier
			//System.out.println("====================================================================");
			//System.out.println("Chemin absolu : " + chemin);
			File fichier = new File(chemin+"/ligne_conso_site.xml");
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
