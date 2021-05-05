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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

/**
 * HackathonController
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

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Status is healthy!");
    }

    @PostMapping("/single-pdf-parse")
    public BenefitsCoverageDetails singlePdfParse(@RequestParam("file") MultipartFile file) {
        BenefitsCoverageDetails benefitsCoverageDetails = new BenefitsCoverageDetails();

        try {
            String parsedText = "";
            PDFParser parser = new PDFParser(new RandomAccessFile(convert(file), "r"));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            if (parsedText != null && !parsedText.isEmpty()) {

                PlanDetails newPlan = new PlanDetails();
                newPlan.setPlanName(parsedText);
                benefitsCoverageDetails.setPlanDetails(newPlan);
            }

        } catch (Exception e) {
            System.out.println("Exception occurred while parsing the single PDF file " + e);
        }

        return benefitsCoverageDetails;
    }

    @PostMapping("/pdf-parse")
    public List<BenefitsCoverageDetails> pdfParse(@RequestParam("files") MultipartFile[] files) {
        List<BenefitsCoverageDetails> listOfBenefitDetails = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {

            try {
                BenefitsCoverageDetails benefitsCoverageDetails = new BenefitsCoverageDetails();
                String parsedText = "";
                PDFParser parser = new PDFParser(new RandomAccessFile(convert(file), "r"));
                parser.parse();
                COSDocument cosDoc = parser.getDocument();
                PDFTextStripper pdfStripper = new PDFTextStripper();
                PDDocument pdDoc = new PDDocument(cosDoc);
                parsedText = pdfStripper.getText(pdDoc);
                if (parsedText != null && !parsedText.isEmpty()) {

                    PlanDetails newPlan = new PlanDetails();

                    String[] split = parsedText.split("Coverage Period");
                    String remainingText = split[1];

                    split = remainingText.split("Summary of Benefits and Coverage");
                    String coveragePeriod = split[0];
                    remainingText = split[1];

                    LocalDate now = LocalDate.now(); // 2015-11-23
                    LocalDate coverageBeginPeriod = now.with(firstDayOfYear()); // 2015-01-01
                    LocalDate coverageEndPeriod = now.with(lastDayOfYear());
                    newPlan.setCoverageBeginDate(coverageBeginPeriod.toString());
                    newPlan.setCoverageEndDate(coverageEndPeriod.toString());

                    split = coveragePeriod.split("-");
                    String planName = split[1];

                    //Set plan name d attributes
                    String[] planNameSplit = planName.split("Page 1 of 7");

                    newPlan.setPlanId(planNameSplit[0].substring(10).trim());
                    newPlan.setPlanName(planNameSplit[0].substring(10).trim());

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

                    Boolean isDeductibleEmbedded = remainingText.contains("each family member must meet their own individual \n" + "deductible");

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

                    Boolean isOutOfPocketEmbedded = remainingText.contains("they have to meet \n" + "their own out-of-pocket limits");


                    //Set deductible embedded amounts
                    if (isDeductibleEmbedded) {
                        newPlan.setDeductibleEmbedded(true);
                        newPlan.setEmbeddedDeductibleAmount(indvDeducAmt);
                        // newPlan.setEmbeddedOutOfPocketAmount(oopIndvAmt);
                    } else {
                        newPlan.setDeductibleEmbedded(false);
                    }

                    //Set oop embedded amounts
                    if (isOutOfPocketEmbedded) {
                        newPlan.setOutOfPocketEmbedded(true);
                        // newPlan.setEmbeddedDeductibleAmount(indvDeducAmt);
                        newPlan.setEmbeddedOutOfPocketAmount(oopIndvAmt);
                    } else {
                        newPlan.setOutOfPocketEmbedded(false);
                    }

                    Boolean deductiblePaidBeforeCopay = remainingText.contains("All copayment and coinsurance costs shown in this chart are after your deductible has been met, if a deductible applies");

                    if (remainingText.contains("If you need drugs to")) {
                        List<RxDetails> listOfRxDetails = new ArrayList<>();

                        split = remainingText.split("If you need drugs to \\ntreat your illness or \\ncondition");

                        String[] drugTypes = {"Generic Drugs", "Preferred brand drugs", "Non-preferred brand", "Specialty drugs"}; //, "Specialty drugs"
                        String[] drugTires = {"Generic", "Brand Preferred", "Brand Non Preferred", "Specialty"};//, "Specialty"
                        String medicalEvent = split[1];

                        for (int i = 0; i < drugTypes.length; i++) {

                            if (medicalEvent.indexOf(drugTypes[i]) != -1) {

                                split = medicalEvent.split(drugTypes[i]);
                                split = split[1].split("Retail:");

                                String[] covalues = new String[0];

                                RxDetails newRxDetailsRetail = new RxDetails();
                                newRxDetailsRetail.setDrugTier(drugTires[i]);
                                newRxDetailsRetail.setPlanId(planName.trim());
                                newRxDetailsRetail.setPlanName(planName.trim());
                                newRxDetailsRetail.setPharmacyTier("Retail90");

                                RxDetails newRxDetailsInNetwork = new RxDetails();
                                newRxDetailsInNetwork.setDrugTier(drugTires[i]);
                                newRxDetailsInNetwork.setPlanId(planName.trim());
                                newRxDetailsInNetwork.setPlanName(planName.trim());
                                newRxDetailsInNetwork.setPharmacyTier("in Network");

                                if (split[1].indexOf("Copay") != -1) {
                                    covalues = split[1].split("Copay");
                                    newRxDetailsRetail.setCoinsurance(covalues[0].trim());
                                    newRxDetailsInNetwork.setCoinsurance(covalues[0].trim());

                                }

                                if (split[1].indexOf('%') != -1) {

                                    covalues = split[1].split("%");
                                    newRxDetailsRetail.setCoinsurance(covalues[0].trim());
                                    newRxDetailsInNetwork.setCoinsurance(covalues[0].trim());

                                    if (covalues[1].indexOf('(') != -1 && covalues[1].indexOf(')') != -1) {
                                        String minmaxstring = covalues[1].substring(covalues[1].indexOf('(') + 1, covalues[1].indexOf(')'));

                                        if (minmaxstring.indexOf("min") != -1) {
                                            String coinsuranceMinAmount[] = minmaxstring.split("min")[0].split("\\$");

                                            Integer coinsuranceMin = Integer.parseInt(coinsuranceMinAmount[1].trim().replaceAll(",", ""));
                                            newRxDetailsRetail.setCoinsuranceMinCost(coinsuranceMin);
                                            newRxDetailsInNetwork.setCoinsuranceMinCost(coinsuranceMin);
                                        }
                                        if (minmaxstring.indexOf("max") != -1) {
                                            String coinsuranceMaxAmount[] = minmaxstring.split("max")[0].split("/ \\$");

                                            Integer coinsuranceMax = Integer.parseInt(coinsuranceMaxAmount[1].trim().replaceAll(",", ""));
                                            newRxDetailsRetail.setCoinsuranceMaxCost(coinsuranceMax);
                                            newRxDetailsInNetwork.setCoinsuranceMaxCost(coinsuranceMax);
                                        }
                                    }

                                }


                                String[] mailOrdersplit = split[1].split("Mail Order:");


                                RxDetails newRxDetailsMailOrder = new RxDetails();
                                newRxDetailsMailOrder.setDrugTier(drugTires[i]);
                                newRxDetailsMailOrder.setPlanId(planName.trim());
                                newRxDetailsMailOrder.setPlanName(planName.trim());
                                newRxDetailsMailOrder.setPharmacyTier("MailOrder");


                                newRxDetailsMailOrder.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                                newRxDetailsInNetwork.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                                newRxDetailsRetail.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);

                                if (mailOrdersplit[1].indexOf('%') != -1) {
                                    covalues = mailOrdersplit[1].split("%");
                                    newRxDetailsMailOrder.setCoinsurance(covalues[0].trim());

                                    if (covalues[1].indexOf('(') != -1) {
                                        String minmaxstring = covalues[1].substring(covalues[1].indexOf('(') + 1);

                                        if (minmaxstring.indexOf("min") != -1) {
                                            String coinsuranceMinAmount[] = minmaxstring.split("min")[0].split("\\$");

                                            Integer coinsuranceMin = Integer.parseInt(coinsuranceMinAmount[1].trim().replaceAll(",", ""));
                                            newRxDetailsMailOrder.setCoinsuranceMinCost(coinsuranceMin);
                                        }
                                        if (minmaxstring.indexOf("max") != -1) {
                                            String coinsuranceMaxAmount[] = minmaxstring.split("max")[0].split("/ \\$");

                                            Integer coinsuranceMax = Integer.parseInt(coinsuranceMaxAmount[1].trim().replaceAll(",", ""));
                                            newRxDetailsMailOrder.setCoinsuranceMaxCost(coinsuranceMax);
                                        }
                                    }

                                }

                                listOfRxDetails.add(newRxDetailsInNetwork);
                                listOfRxDetails.add(newRxDetailsRetail);
                                listOfRxDetails.add(newRxDetailsMailOrder);


                            } else {
                                System.out.println("medicalEvent " + medicalEvent);

                            }
                        }

                        System.out.println("List of Rx Details size " + listOfRxDetails.size());

                        int size = listOfRxDetails.size();
                        for (int k = 0; k < size; k++) {

                            RxDetails newRxDetailsPrev = null;

                            System.out.println("List drug tiers " + listOfRxDetails.get(k).getDrugTier() + listOfRxDetails.get(k).getPharmacyTier());

                            if (listOfRxDetails.get(k).getDeductiblePaidBeforeCopay() == true) {

                                newRxDetailsPrev = new RxDetails();

                                newRxDetailsPrev.setDrugTier("Preventive " + listOfRxDetails.get(k).getDrugTier());
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
                    listOfBenefitDetails.add(benefitsCoverageDetails);
                }

            } catch (Exception e) {
                System.out.println("Exception occurred while parsing the PDF file " + e);
            }
        });
        return listOfBenefitDetails;
    }

    @GetMapping("/save-pdf")
    public void savePlanDetails(@RequestParam("file") MultipartFile[] file) {
        List<BenefitsCoverageDetails> benefitsCoverageDetails = pdfParse(file);
        System.out.println("print benefitsCoverageDetails response :" + benefitsCoverageDetails);

        System.out.println("inside benefitsCoverageDetails response :" + benefitsCoverageDetails.size());

        for (int i = 0; i < benefitsCoverageDetails.size(); i++) {

            System.out.println("inside looping: " + benefitsCoverageDetails.get(i).getPlanDetails());

            //Build Plan Details from the data obtained so far
            PdfPlanDetails planDetails = new PdfPlanDetails();
            planDetails.setGroupName("WCS");
            planDetails.setBrandOverGenericFlag(benefitsCoverageDetails.get(i).getPlanDetails().getBrandOverGeneric());
            planDetails.setPbm(benefitsCoverageDetails.get(i).getPlanDetails().getPbm());
            planDetails.setPlanId(benefitsCoverageDetails.get(i).getPlanDetails().getPlanId());
            planDetails.setPlanName(benefitsCoverageDetails.get(i).getPlanDetails().getPlanName());
            planDetails.setFormularyDescription(benefitsCoverageDetails.get(i).getPlanDetails().getFormularyDescription());
            planDetails.setNetworkDescription(benefitsCoverageDetails.get(i).getPlanDetails().getNetworkDescription());
            planDetails.setEmbeddedOOPAmt(benefitsCoverageDetails.get(i).getPlanDetails().getEmbeddedOutOfPocketAmount());
            planDetails.setEmbeddedDeductibleAmt(benefitsCoverageDetails.get(i).getPlanDetails().getEmbeddedDeductibleAmount());
            planDetails.setCoverageType("Individual/Family");
            if (benefitsCoverageDetails.get(i).getPlanDetails().getDeductibleEmbedded() == true) {
                planDetails.setIsDeductibleEmbedded("Yes");
            } else {
                planDetails.setIsDeductibleEmbedded("No");
            }
            if (benefitsCoverageDetails.get(i).getPlanDetails().getOutOfPocketEmbedded() == true) {
                planDetails.setIsOOPEmbedded("Yes");
            } else {
                planDetails.setIsOOPEmbedded("No");
            }
            planDetails.setIndvDeductible(benefitsCoverageDetails.get(i).getPlanDetails().getIndividualDeductibleAmount());
            planDetails.setIndvOOP(benefitsCoverageDetails.get(i).getPlanDetails().getIndividualOutOfPocketAmount());
            planDetails.setSharedDeductible(benefitsCoverageDetails.get(i).getPlanDetails().getFamilyDeductibleAmount());
            planDetails.setSharedOOP(benefitsCoverageDetails.get(i).getPlanDetails().getFamilyOutOfPocketAmount());
            planDetails.setMaintenanceFlag("N/A");
            planDetails.setSpecialConditions("N/A");
            pdfPlanDetailsRepository.save(planDetails);
            System.out.println("plan_details from the pdf " + planDetails);


            //Build Plan Rx Details from the data obtained so far
            Integer arrsize = benefitsCoverageDetails.get(i).getRxDetails().size();
            for (int k = 0; k < arrsize; k++) {
                PdfPlanRxDetails planRxDetails = new PdfPlanRxDetails();
                planRxDetails.setGroupPrefixCode("WCS");
                planRxDetails.setBenfitBeginning("2021-01-01");
                planRxDetails.setBenefitEnd("2021-12-31");
                planRxDetails.setPlanName(benefitsCoverageDetails.get(i).getRxDetails().get(k).getPlanName());
                planRxDetails.setPlanId(benefitsCoverageDetails.get(i).getRxDetails().get(k).getPlanId());
                planRxDetails.setDrugTier(benefitsCoverageDetails.get(i).getRxDetails().get(k).getDrugTier());
                planRxDetails.setPharmacyTier(benefitsCoverageDetails.get(i).getRxDetails().get(k).getPharmacyTier());
                planRxDetails.setDeductiblePaidBeforeCopay(benefitsCoverageDetails.get(i).getRxDetails().get(k).getDeductiblePaidBeforeCopay());
                planRxDetails.setCopay(benefitsCoverageDetails.get(i).getRxDetails().get(k).getCopay());
                if (benefitsCoverageDetails.get(i).getRxDetails().get(k).getCoinsurance() != null) {
                    planRxDetails.setCoinsurance(benefitsCoverageDetails.get(i).getRxDetails().get(k).getCoinsurance().replace("%", ""));
                }
                planRxDetails.setCoinsuranceMinCost(benefitsCoverageDetails.get(i).getRxDetails().get(k).getCoinsuranceMinCost());
                planRxDetails.setCoinsuranceMaxCost(benefitsCoverageDetails.get(i).getRxDetails().get(k).getCoinsuranceMaxCost());
                planRxDetails.setHierarcy("NULL");
                pdfPlanRxDetailsRepository.save(planRxDetails);
                System.out.println("plan_rx_details from the pdf " + planRxDetails);
            }

        }

    }

    @GetMapping("/create-table")
    public void createPlanDetails() {

        planDetailsRepository.createPlanDetails("TEST_plan_details");

        rxDetailsRepository.createRxDetails("Test_plan_rx_details");

    }

}


