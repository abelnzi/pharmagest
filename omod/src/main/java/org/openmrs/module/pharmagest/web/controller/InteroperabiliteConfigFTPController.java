package org.openmrs.module.pharmagest.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.pharmagest.metier.FormulaireParametrage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The main controller.
 */
@Controller
@SessionAttributes("formulaireParametrage")
@RequestMapping(value = "/module/pharmagest/interoperabiliteConfig.form")
public class InteroperabiliteConfigFTPController {
	protected final Log log = LogFactory.getLog(getClass());
	public String path;

	@RequestMapping(method = RequestMethod.GET)
	public void index(HttpServletRequest request, ModelMap model) throws ParseException {
		path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/view/module/pharmagest/resources/scripts/envoieFTP/envoie_FTP/");
		path = path.replace("\\", "/");
		FormulaireParametrage formulaireParametrage = new FormulaireParametrage();
		String[] parametres = this.getParametres();
		if (parametres != null) {
			formulaireParametrage.setHote(parametres[0]);
			formulaireParametrage.setPort(parametres[1]);
			formulaireParametrage.setLogin(parametres[2]);
			formulaireParametrage.setMdp(parametres[3]);
			formulaireParametrage.setRepExtract(parametres[4]);
			
		}

		model.addAttribute("formulaireParametrage", formulaireParametrage);
	}

	
	@RequestMapping(method = RequestMethod.POST, params = { "btn_enregistrer" })
	public void writeProperties(@ModelAttribute("formulaireParametrage") FormulaireParametrage formulaireParametrage,
			BindingResult result, HttpSession session, ModelMap model) {
		

		final Properties prop = new Properties();
		InputStream input = null;
		String filePath = this.path + "/etl_restor/envoie_ftp_0_1/contexts/prod.properties";
		
		if (!result.hasErrors()) {
				
			try {
							
				// Gestion du critère de date
				
				input = new FileInputStream(filePath);
	
				// load a properties file
				prop.load(input);
				
				// update the properties value
			
				prop.setProperty("ftp_hote", formulaireParametrage.getHote());
				prop.setProperty("ftp_mdp",formulaireParametrage.getMdp());
				prop.setProperty("ftp_port",formulaireParametrage.getPort());
				prop.setProperty("ftp_utilisateur", formulaireParametrage.getLogin());
				prop.setProperty("fileRoot", formulaireParametrage.getRepExtract());
				prop.setProperty("logFolder", formulaireParametrage.getRepExtract()+"log/");
				prop.setProperty("sigdepExtract", formulaireParametrage.getRepExtract()+"extract/");
				prop.setProperty("sigdepTemp", formulaireParametrage.getRepExtract()+"temp/");
				
				
				FileOutputStream output = new FileOutputStream(filePath);
				// save properties to project root folder
				prop.store(output, null);
				
				File f = new File(formulaireParametrage.getRepExtract());
				if(f.exists()) {
					model.addAttribute("mess", "success");
				}else {
					model.addAttribute("mess", "echec");
				}
					
				
				
			} catch (final IOException io) {
				io.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			} // fin finaly
		
	  }//fin result.hasErrors
	}

	public String[] getParametres() {
		final Properties prop = new Properties();
		InputStream input = null;
		String filePath = this.path + "/etl_restor/envoie_ftp_0_1/contexts/prod.properties";
		
		try {

			input = new FileInputStream(filePath);

			// load a properties file
			prop.load(input);
			// get the properties value
			String hote = prop.getProperty("ftp_hote");
			String mdp = prop.getProperty("ftp_mdp");
			String port = prop.getProperty("ftp_port");
			String login = prop.getProperty("ftp_utilisateur");
			String path = prop.getProperty("fileRoot");
			path=path.replaceAll("file/", " ").trim();
			
			String[] parametres = { hote, port, login, mdp, path };
			System.out.println("==============path=============="+path);
			return parametres;

		} catch (final IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	

}
