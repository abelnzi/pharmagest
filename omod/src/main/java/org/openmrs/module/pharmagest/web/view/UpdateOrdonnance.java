package org.openmrs.module.pharmagest.web.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmagest.PharmMedecin;
import org.openmrs.module.pharmagest.PharmOrdonnance;
import org.openmrs.module.pharmagest.PharmProduit;
import org.openmrs.module.pharmagest.PharmProgramme;
import org.openmrs.module.pharmagest.PharmRegime;
import org.openmrs.module.pharmagest.api.DispensationService;
import org.openmrs.module.pharmagest.api.MedecinService;
import org.openmrs.module.pharmagest.api.ParametresService;
import org.openmrs.module.pharmagest.api.ProduitService;
import org.openmrs.module.pharmagest.api.ProgrammeService;
import org.openmrs.module.pharmagest.api.RegimeService;

import au.com.bytecode.opencsv.CSVReader;

public class UpdateOrdonnance {
	private final static String RESOURCES_PATH = "C:/Users/MEASURE PC/Documents/workspacePharmaGest/";
	private final static String ELEVES_FILE_NAME = "Ordonnances_Adults.csv";
	private final static char SEPARATOR = ';';

	public List<String[]> parserFile() {
		try {
			File file = new File(RESOURCES_PATH + ELEVES_FILE_NAME);
			FileReader fr = new FileReader(file);
			CSVReader csvReader = new CSVReader(fr, SEPARATOR);

			List<String[]> data = (List<String[]>) new ArrayList<String[]>();

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

			return data;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void updateOrd() throws ParseException {
		List<String[]> data = this.parserFile();
		// traiter les données

		for (String[] oneData : data) {
			String dateString = oneData[0];
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date dateDisp = sf.parse(dateString);

			String codePatient = oneData[1];
			String regimeLib = oneData[2];
			String produitLib = oneData[3];
			String produitCode = oneData[4];
			String qte = oneData[5];
			String ctx = oneData[6];
			String codeCtx = oneData[7];
			String qteCtx = oneData[8];
			String prescripteur = oneData[9];

			// Trouver le patient
			Collection<PatientIdentifier> patientIdentifiers = Context.getService(ParametresService.class)
					.getPatientIdentifierByIdentifier(this.cleanIdentifer(codePatient));
			PatientIdentifier patientIdentifier = null;				
			for (PatientIdentifier pi : patientIdentifiers) {
				patientIdentifier=pi;
			}
			if (patientIdentifier != null) {
				PharmOrdonnance ordonnance = new PharmOrdonnance();
				ordonnance.setPatientIdentifier(patientIdentifier);
				ordonnance.setOrdDateDispen(dateDisp);
				PharmRegime regime = Context.getService(RegimeService.class).findRegimeByName(regimeLib);

				if (regime != null) {
					PharmProgramme programme = Context.getService(ProgrammeService.class).getPharmProgrammeById(1);
					PharmMedecin medecin = Context.getService(MedecinService.class).getPharmMedecinById(Integer.parseInt(prescripteur));
					
					ordonnance.setPharmRegime(regime);
					ordonnance.setPharmProgramme(programme);
					ordonnance.setPharmMedecin(medecin);

					PharmOrdonnance ord = Context.getService(DispensationService.class)
							.findOrdonnanceByAttribut(patientIdentifier, regime, dateDisp, programme, medecin);
					if(ord!=null){
						ordonnance=ord;
					}
					
					PharmProduit produit=Context.getService(ProduitService.class).findProduitByCode(produitCode);
					if(produit!=null){
						
					}

				}

			}
		}
	}

	public String cleanIdentifer(String id) {
		StringTokenizer st = new StringTokenizer(id, "/");
		int n = 0;
		String identifer = null;
		while (st.hasMoreTokens()) {
			String id1 = st.nextToken();
			String id2 = st.nextToken();
			String id3 = st.nextToken();
			String id4 = st.nextToken();
			identifer = id1 + "/" + id2 + "/" + id4 + "/" + id3;
		}
		// System.out.println("--------------identifer--------------
		// :"+identifer);

		return identifer;
	}

	public PatientIdentifier findPatient(String identifer) {
		Collection<PatientIdentifier> patientIdentifiers = Context.getService(ParametresService.class)
				.getPatientIdentifierByIdentifier(identifer);
		PatientIdentifier patientIdentifier = null;				
		for (PatientIdentifier pi : patientIdentifiers) {
			patientIdentifier=pi;
		}
		return patientIdentifier;
	}

}
