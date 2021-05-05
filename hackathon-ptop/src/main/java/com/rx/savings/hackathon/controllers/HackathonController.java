package com.rx.savings.hackathon.controllers;
import com.rx.savings.hackathon.datamodels.BenefitsCoverageDetails;
import com.rx.savings.hackathon.datamodels.PlanDetails;
import com.rx.savings.hackathon.datamodels.RxDetails;
import com.rx.savings.hackathon.models.PdfPlanDetails;
import com.rx.savings.hackathon.models.PdfPlanRxDetails;
import com.rx.savings.hackathon.repository.PdfPlanDetailsRepository;
import com.rx.savings.hackathon.repository.PdfPlanRxDetailsRepository;
import com.rx.savings.hackathon.repository.PlanDetailsRepository;
import com.rx.savings.hackathon.repository.RxDetailsRepository;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HackathonController
 *
 * @author praneeth
 *
 */
@RestController
@RequestMapping("/api")
public class HackathonController {

    @Autowired
    PdfPlanDetailsRepository pdfPlanDetailsRepository;

    @Autowired
    PdfPlanRxDetailsRepository pdfPlanRxDetailsRepository;

    @Autowired
    PlanDetailsRepository planDetailsRepository;

    @Autowired
    RxDetailsRepository rxDetailsRepository;

    @GetMapping("/ping")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Status is healthy!");
    }

    @GetMapping("/pdf-parse")
    public BenefitsCoverageDetails pdfParse(@RequestParam("file") MultipartFile file) {
        BenefitsCoverageDetails benefitsCoverageDetails = new BenefitsCoverageDetails();

        try {
            String parsedText = "";
            PDFParser parser = new PDFParser(new RandomAccessFile(convert(file), "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            if(parsedText != null && !parsedText.isEmpty()) {

                PlanDetails newPlan = new PlanDetails();

                String[] split = parsedText.split("Coverage Period");
                String planName = split[0];
                String remainingText = split[1];

                split = remainingText.split("Summary of Benefits and Coverage:");
                String coveragePeriod = split[0];
                remainingText = split[1];

                split = coveragePeriod.split("-");
                String coverageBeginPeriod = split[0];
                String coverageEndPeriod = split[1];

                //Set plan name and coverage period attributes
                newPlan.setCoverageBeginDate(coverageBeginPeriod.trim());
                newPlan.setCoverageEndDate(coverageEndPeriod.trim());
                newPlan.setPlanId(planName.trim());
                newPlan.setPlanName(planName.trim());
                //newPlan.setPlanName(parsedText);


                //Hardcoding Brand over generic for now as value doesn't exist in old files
                newPlan.setBrandOverGeneric("NO");
                //Hardcoding PBM for now as value doesn't exist in pdf
                newPlan.setPbm("NULL");

                //Hardcoding Formulary_Description for now as value doesn't exist in pdf
                newPlan.setFormularyDescription("STANDARD");

                //Hardcoding Network_Description for now as value doesn't exist in pdf
                newPlan.setNetworkDescription("STANDARD");

                split = remainingText.split("What is the overall \n" + "deductible?");
                remainingText = split[1];

                //Set individual deductible amounts
                split = split[1].split("Individual");
                String individualDeductibleAmount[] = split[0].split("\\$");
                Integer indvDeducAmt = Integer.parseInt(individualDeductibleAmount[1].trim().replaceAll(",", ""));
                newPlan.setIndividualDeductibleAmount(indvDeducAmt);

                //Set family deductible amounts
                split = split[1].split("Family");
                String familyDeductibleAmount[] = split[0].split("\\$");
                Integer famDeducAmt = Integer.parseInt(familyDeductibleAmount[1].trim().replaceAll(",", ""));
                newPlan.setFamilyDeductibleAmount(famDeducAmt);

                Boolean isEmbedded = remainingText.contains("each family member must meet their own individual");

                split = remainingText.split("What is the out-of-\\npocket limit for this \\nplan");
                remainingText = split[1];

                //Set individual out of pocket amounts
                split = split[1].split("Individual");
                String individualOutOfPocketAmount[] = split[0].split("\\$");
                Integer oopIndvAmt = Integer.parseInt(individualOutOfPocketAmount[1].trim().replaceAll(",", ""));
                newPlan.setIndividualOutOfPocketAmount(oopIndvAmt);

                //Set family out of pocket amounts
                split = split[1].split("Family");
                String familyOutOfPocketAmount[] = split[0].split("\\$");
                Integer oopFamAmt = Integer.parseInt(familyOutOfPocketAmount[1].trim().replaceAll(",", ""));
                newPlan.setFamilyOutOfPocketAmount(oopFamAmt);

                //Set embedded amounts
                if(isEmbedded){
                    newPlan.setEmbedded(true);
                    newPlan.setEmbeddedDeductibleAmount(indvDeducAmt);
                    newPlan.setEmbeddedOutOfPocketAmount(oopIndvAmt);
                    newPlan.setMaxFamilyDeductibleEmbedded("YES");
                    newPlan.setMaxFamilyOopEmbedded("YES");

                } else {
                    newPlan.setEmbedded(false);
                }

                if(remainingText.contains("If you need drugs to")) {
                    List<RxDetails> listOfRxDetails = new ArrayList<>();

                    split = remainingText.split("If you need drugs to \\ntreat your illness or \\ncondition");

                    String[] drugTypes = {"Generic Drugs", "Preferred brand drugs", "Non-preferred brand","Specialty drugs"};
                    String[] drugTires = {"Generic", "Brand Preferred", "Brand Non Preferred","Specialty"};
                    String[] specialtyDrugTires = {"Specialty Generic","Specialty Brand Preferred","Specialty Brnad Non Preferred"};
                    String medicalEvent = split[1];


                    for(int i=0;i<drugTypes.length;i++) {
                        System.out.println("index " + medicalEvent.indexOf(drugTypes[i]));

                        if(medicalEvent.indexOf(drugTypes[i]) != -1) {

//                            if (drugTypes[i].contains("Specialty")) {
//
//                                for (int s = 0; s < specialtyDrugTires.length; s++) {
//
//                                    RxDetails newRxDetailsSpecialty = new RxDetails();
//                                    newRxDetailsSpecialty.setDrugTier(specialtyDrugTires[s]);
//                                    newRxDetailsSpecialty.setPlanId(planName.trim());
//                                    newRxDetailsSpecialty.setPlanName(planName.trim());
//                                    newRxDetailsSpecialty.setCoinsurance(split[0].trim());
//                                    newRxDetailsSpecialty.setPharmacyTier("Specialty");
//
//                                    listOfRxDetails.add(newRxDetailsSpecialty);
//                                }
//                            }else {

                                split = medicalEvent.split(drugTypes[i]);
                                split = split[1].split("Retail:");

                                System.out.println("split " + Arrays.toString(split));
                                String[] retailMinMaxSplit = split;
                                split = split[1].split(" Coinsurance");

                                RxDetails newRxDetailsRetail = new RxDetails();
                                newRxDetailsRetail.setDrugTier(drugTires[i]);
                                newRxDetailsRetail.setPlanId(planName.trim());
                                newRxDetailsRetail.setPlanName(planName.trim());
                                newRxDetailsRetail.setCoinsurance(split[0].trim());
                                newRxDetailsRetail.setCopay("NULL");
                                newRxDetailsRetail.setPharmacyTier("Retail90");

                                RxDetails newRxDetailsInNetwork = new RxDetails();
                                newRxDetailsInNetwork.setDrugTier(drugTires[i]);
                                newRxDetailsInNetwork.setPlanId(planName.trim());
                                newRxDetailsInNetwork.setPlanName(planName.trim());
                                newRxDetailsInNetwork.setCoinsurance(split[0].trim());
                                newRxDetailsInNetwork.setCopay("NULL");
                                newRxDetailsInNetwork.setPharmacyTier("in Network");

                                split = split[1].split("Mail Order:");

                                split = split[1].split("\\nCoinsurance");

                                RxDetails newRxDetailsMailOrder = new RxDetails();
                                newRxDetailsMailOrder.setDrugTier(drugTires[i]);
                                newRxDetailsMailOrder.setPlanId(planName.trim());
                                newRxDetailsMailOrder.setPlanName(planName.trim());
                                newRxDetailsMailOrder.setCoinsurance(split[0].trim());
                                newRxDetailsMailOrder.setCopay("NULL");
                                newRxDetailsMailOrder.setPharmacyTier("MailOrder");



                            Boolean deductiblePaidBeforeCopay = split[1].contains("after satisfying the deductible");
                            newRxDetailsMailOrder.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                            newRxDetailsInNetwork.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                            newRxDetailsRetail.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);

                            if (retailMinMaxSplit != null && retailMinMaxSplit.length > 2 && retailMinMaxSplit[2] != null) {
                                String retailMinMaxSplitString = retailMinMaxSplit[2];

                                System.out.println("min index " + retailMinMaxSplitString.indexOf("Min"));
                                if (retailMinMaxSplitString.indexOf("Min") != -1) {
                                    split = retailMinMaxSplitString.split("Min");
                                    String coinsuranceMinAmount[] = split[0].split("\\$");
                                    Integer coinsuranceMin = Integer.parseInt(coinsuranceMinAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsRetail.setCoinsuranceMinCost(coinsuranceMin);
                                    newRxDetailsInNetwork.setCoinsuranceMinCost(coinsuranceMin);
                                }
                                System.out.println("max index " + retailMinMaxSplitString.indexOf("Max"));

                                if (retailMinMaxSplitString.indexOf("Max") != -1) {
                                    split = retailMinMaxSplitString.split("Max");
                                    String coinsuranceMaxAmount[] = split[0].split("and \\$");
                                    Integer coinsuranceMax = Integer.parseInt(coinsuranceMaxAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsRetail.setCoinsuranceMaxCost(coinsuranceMax);
                                    newRxDetailsInNetwork.setCoinsuranceMaxCost(coinsuranceMax);
                                }

                                System.out.println("mail index " + retailMinMaxSplitString.indexOf("Mail"));

                                if (retailMinMaxSplitString.indexOf("Mail") != -1) {
                                    String mailOrderSplit[] = retailMinMaxSplitString.split("Mail \\nOrder:");

                                    split = mailOrderSplit[1].split("Min");
                                    String coinsuranceMinAmount[] = split[0].split("\\$");
                                    Integer coinsuranceMin = Integer.parseInt(coinsuranceMinAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsMailOrder.setCoinsuranceMinCost(coinsuranceMin);
                                    split = mailOrderSplit[1].split("Max");
                                    String coinsuranceMaxAmount[] = split[0].split("and \\$");
                                    Integer coinsuranceMax = Integer.parseInt(coinsuranceMaxAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsMailOrder.setCoinsuranceMaxCost(coinsuranceMax);
                                }
                            }

                            listOfRxDetails.add(newRxDetailsInNetwork);
                            listOfRxDetails.add(newRxDetailsRetail);
                            listOfRxDetails.add(newRxDetailsMailOrder);


                            System.out.println("List of Rx Details size " + listOfRxDetails.size());

                        } else {
                            System.out.println("medicalEvent " + medicalEvent);

                        }
                    }

                    System.out.println("List of Rx Details size " + listOfRxDetails.size());

                    int size = listOfRxDetails.size();
                    for(int k=0;k<size;k++){

                        RxDetails newRxDetailsPrev = null;

                        System.out.println("List drug tiers " + listOfRxDetails.get(k).getDrugTier() + listOfRxDetails.get(k).getPharmacyTier());

                        if(listOfRxDetails.get(k).getDeductiblePaidBeforeCopay()==true){

                            newRxDetailsPrev = new RxDetails();

                            newRxDetailsPrev.setDrugTier("Preventive "+listOfRxDetails.get(k).getDrugTier());
                            newRxDetailsPrev.setPlanId(listOfRxDetails.get(k).getPlanId());
                            newRxDetailsPrev.setPlanName(listOfRxDetails.get(k).getPlanName());
                            newRxDetailsPrev.setCoinsurance(listOfRxDetails.get(k).getCoinsurance());
                            newRxDetailsPrev.setPharmacyTier(listOfRxDetails.get(k).getPharmacyTier());
                            newRxDetailsPrev.setDeductiblePaidBeforeCopay(false);
                            newRxDetailsPrev.setCopay(listOfRxDetails.get(k).getCopay());
                            newRxDetailsPrev.setCoinsuranceMaxCost(listOfRxDetails.get(k).getCoinsuranceMaxCost());
                            newRxDetailsPrev.setCoinsuranceMinCost(listOfRxDetails.get(k).getCoinsuranceMinCost());

                            System.out.println("List of Prev Rx Details size " + newRxDetailsPrev);

                            listOfRxDetails.add(newRxDetailsPrev);
                        }



                    }

                    benefitsCoverageDetails.setRxDetails(listOfRxDetails);
                }

                benefitsCoverageDetails.setPlanDetails(newPlan);
            }


        } catch(Exception e) {
            System.out.println("Exception occurred while parsing the PDF file " + e);
        }
        return benefitsCoverageDetails;
    }

    public static File convert(MultipartFile file) throws IOException
    {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    @GetMapping("/save-pdf")
    public void savePlanDetails(@RequestParam("file") MultipartFile file){
        BenefitsCoverageDetails benefitsCoverageDetails = pdfParse(file);
        System.out.println("print benefitsCoverageDetails response :"+benefitsCoverageDetails);
        //Build MedicationClaim from the data obtained so far
        PdfPlanDetails planDetails = new PdfPlanDetails();
        planDetails.setGroupName("ADS");
        planDetails.setBrandOverGenericFlag(benefitsCoverageDetails.getPlanDetails().getBrandOverGeneric());
        planDetails.setPbm(benefitsCoverageDetails.getPlanDetails().getPbm());
        planDetails.setPlanId(benefitsCoverageDetails.getPlanDetails().getPlanId());
        planDetails.setPlanName(benefitsCoverageDetails.getPlanDetails().getPlanName());
        planDetails.setFormularyDescription(benefitsCoverageDetails.getPlanDetails().getFormularyDescription());
        planDetails.setNetworkDescription(benefitsCoverageDetails.getPlanDetails().getNetworkDescription());
        planDetails.setEmbeddedOOPAmt(benefitsCoverageDetails.getPlanDetails().getEmbeddedOutOfPocketAmount());
        planDetails.setEmbeddedDeductibleAmt(benefitsCoverageDetails.getPlanDetails().getEmbeddedDeductibleAmount());
        planDetails.setCoverageType("Individual/Family");
        planDetails.setIsDeductibleEmbedded(benefitsCoverageDetails.getPlanDetails().getMaxFamilyDeductibleEmbedded());
        planDetails.setIsOOPEmbedded(benefitsCoverageDetails.getPlanDetails().getMaxFamilyOopEmbedded());
        planDetails.setIndvDeductible(benefitsCoverageDetails.getPlanDetails().getIndividualDeductibleAmount());
        planDetails.setIndvOOP(benefitsCoverageDetails.getPlanDetails().getIndividualOutOfPocketAmount());
        planDetails.setSharedDeductible(benefitsCoverageDetails.getPlanDetails().getFamilyDeductibleAmount());
        planDetails.setSharedOOP(benefitsCoverageDetails.getPlanDetails().getFamilyOutOfPocketAmount());
        planDetails.setMaintenanceFlag("N/A");
        planDetails.setSpecialConditions("N/A");
        pdfPlanDetailsRepository.save(planDetails);
        System.out.println("plan_details from the pdf " + planDetails);


        Integer arrsize = benefitsCoverageDetails.getRxDetails().size();

        for(int i = 0; i < arrsize; i++)
        {
            PdfPlanRxDetails planRxDetails = new PdfPlanRxDetails();
            planRxDetails.setGroupPrefixCode("TST");
            planRxDetails.setBenfitBeginning("2021-01-01");
            planRxDetails.setBenefitEnd("2021-12-31");
            planRxDetails.setPlanName(benefitsCoverageDetails.getRxDetails().get(i).getPlanName());
            planRxDetails.setPlanId(benefitsCoverageDetails.getRxDetails().get(i).getPlanId());
            planRxDetails.setDrugTier(benefitsCoverageDetails.getRxDetails().get(i).getDrugTier());
            planRxDetails.setPharmacyTier(benefitsCoverageDetails.getRxDetails().get(i).getPharmacyTier());
            planRxDetails.setDeductiblePaidBeforeCopay(benefitsCoverageDetails.getRxDetails().get(i).getDeductiblePaidBeforeCopay());
            planRxDetails.setCopay(benefitsCoverageDetails.getRxDetails().get(i).getCopay());
            planRxDetails.setCoinsurance(benefitsCoverageDetails.getRxDetails().get(i).getCoinsurance().replace("%", ""));
            planRxDetails.setCoinsuranceMinCost(benefitsCoverageDetails.getRxDetails().get(i).getCoinsuranceMinCost());
            planRxDetails.setCoinsuranceMaxCost(benefitsCoverageDetails.getRxDetails().get(i).getCoinsuranceMaxCost());
            planRxDetails.setHierarcy("NULL");
            pdfPlanRxDetailsRepository.save(planRxDetails);
            System.out.println("plan_rx_details from the pdf " + planRxDetails);
        }
    }

    @GetMapping("/create-table")
    public void createPlanDetails(){

        planDetailsRepository.createPlanDetails("TEST_plan_details");

        rxDetailsRepository.createRxDetails("Test_plan_rx_details");

    }

}

