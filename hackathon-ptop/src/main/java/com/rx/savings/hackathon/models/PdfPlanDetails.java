package com.rx.savings.hackathon.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pdf_plan_details", catalog = "staging_odyssey")
public class PdfPlanDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "Group_Name")
    private String groupName;
    @Column(name = "Plan_Beginning")
    private LocalDateTime planBeginning;
    @Column(name = "Plan_End")
    private LocalDateTime endDate;
    @Column(name = "Plan_Name")
    private String planName;
    @Column(name = "Plan_ID")
    private String planId;
    @Column(name = "Brand_Over_Generic_Flag")
    private String brandOverGenericFlag;
    @Column(name = "PBM")
    private String pbm;
    @Column(name = "Formulary_Description")
    private String formularyDescription;
    @Column(name = "Network_Description")
    private String networkDescription;
    @Column(name = "Coverage_Type")
    private String coverageType;
    @Column(name = "Max_Family_Deductible_Amount")
    private Integer sharedDeductible;
    @Column(name = "Is_Family_Deductible_Embedded_")
    private String isDeductibleEmbedded;
    @Column(name = "Embedded_Deductible_Amount")
    private Integer embeddedDeductibleAmt;
    @Column(name = "Max_Individual_Deductible_Amount")
    private Integer indvDeductible;
    @Column(name = "Max_Family_OOP_Amount")
    private Integer sharedOOP;
    @Column(name = "Is_Max_Family_OOP_Embedded_")
    private String isOOPEmbedded;
    @Column(name = "Embedded_OOP_Amount")
    private Integer embeddedOOPAmt;
    @Column(name = "Max_Individual_OOP_Amount")
    private Integer indvOOP;
    @Column(name = "Mandatory_Maintenance_")
    private String maintenanceFlag;
    @Column(name = "Special_Conditions_")
    private String specialConditions;
}