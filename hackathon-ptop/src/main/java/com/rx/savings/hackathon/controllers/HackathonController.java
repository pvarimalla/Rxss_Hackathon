package com.rx.savings.hackathon.controllers;
import com.rx.savings.hackathon.datamodels.BenefitsCoverageDetails;
import com.rx.savings.hackathon.datamodels.PlanDetails;
import com.rx.savings.hackathon.datamodels.RxDetails;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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

    @GetMapping("/ping")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Status is healthy!");
    }

    @PostMapping("/pdf-parse")
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
                } else {
                    newPlan.setEmbedded(false);
                }

                //newPlan.setPlanName(remainingText);

                if(remainingText.contains("If you need drugs to")) {
                    List<RxDetails> listOfRxDetails = new ArrayList<>();

                    split = remainingText.split("If you need drugs to \\ntreat your illness or \\ncondition");

                    String[] drugTypes = {"Generic Drugs", "Preferred brand drugs", "Non-preferred brand","Specialty drugs"};
                    String[] drugTires = {"Generic", "Brand Preferred", "Brand Non Preferred","Specialty"};
                    String medicalEvent = split[1];

                    for(int i=0;i<drugTypes.length;i++) {
                        System.out.println("index " + medicalEvent.indexOf(drugTypes[i]));

                        if(medicalEvent.indexOf(drugTypes[i]) != -1) {

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
                            newRxDetailsRetail.setPharmacyTier("Retail90");

                            RxDetails newRxDetailsInNetwork = new RxDetails();
                            newRxDetailsInNetwork.setDrugTier(drugTires[i]);
                            newRxDetailsInNetwork.setPlanId(planName.trim());
                            newRxDetailsInNetwork.setPlanName(planName.trim());
                            newRxDetailsInNetwork.setCoinsurance(split[0].trim());
                            newRxDetailsInNetwork.setPharmacyTier("in Network");

                            split = split[1].split("Mail Order:");

                            split = split[1].split("\\nCoinsurance");

                            RxDetails newRxDetailsMailOrder = new RxDetails();
                            newRxDetailsMailOrder.setDrugTier(drugTires[i]);
                            newRxDetailsMailOrder.setPlanId(planName.trim());
                            newRxDetailsMailOrder.setPlanName(planName.trim());
                            newRxDetailsMailOrder.setCoinsurance(split[0].trim());
                            newRxDetailsMailOrder.setPharmacyTier("MailOrder");


                            Boolean deductiblePaidBeforeCopay = split[1].contains("after satisfying the deductible");
                            newRxDetailsMailOrder.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                            newRxDetailsInNetwork.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);
                            newRxDetailsRetail.setDeductiblePaidBeforeCopay(deductiblePaidBeforeCopay);

                            if(retailMinMaxSplit != null && retailMinMaxSplit.length >2 && retailMinMaxSplit[2] != null) {
                                String retailMinMaxSplitString = retailMinMaxSplit[2];

                                System.out.println("min index " + retailMinMaxSplitString.indexOf("Min"));
                                if(retailMinMaxSplitString.indexOf("Min") != -1) {
                                    split = retailMinMaxSplitString.split("Min");
                                    String coinsuranceMinAmount[] = split[0].split("\\$");
                                    Integer coinsuranceMin = Integer.parseInt(coinsuranceMinAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsRetail.setCoinsuranceMinCost(coinsuranceMin);
                                    newRxDetailsInNetwork.setCoinsuranceMinCost(coinsuranceMin);
                                }
                                System.out.println("max index " + retailMinMaxSplitString.indexOf("Max"));

                                if(retailMinMaxSplitString.indexOf("Max") != -1) {
                                    split = retailMinMaxSplitString.split("Max");
                                    String coinsuranceMaxAmount[] = split[0].split("and \\$");
                                    Integer coinsuranceMax = Integer.parseInt(coinsuranceMaxAmount[1].trim().replaceAll(",", ""));
                                    newRxDetailsRetail.setCoinsuranceMaxCost(coinsuranceMax);
                                    newRxDetailsInNetwork.setCoinsuranceMaxCost(coinsuranceMax);
                                }

                                System.out.println("mail index " + retailMinMaxSplitString.indexOf("Mail"));

                                if(retailMinMaxSplitString.indexOf("Mail") != -1) {
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
                        } else {
                            System.out.println("medicalEvent " + medicalEvent);

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
}

