package com.rx.savings.hackathon.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pdf_plan_rx_details", catalog = "staging_odyssey")
public class PdfPlanRxDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "Group_Prefix_Code")
    private String groupPrefixCode;

    @Column(name = "Benfit_Beginning")
    private String benfitBeginning;

    @Column(name = "Benefit_End")
    private String BenefitEnd;

    @Column(name = "Plan_Name")
    private String planName;

    @Column(name = "Plan_ID")
    private String planId;

    @Column(name = "Drug_Tier")
    private String drugTier;

    @Column(name = "Pharmacy_Tier")
    private String pharmacyTier;

    @Column(name = "Is_Deductible_Paid_Before_Copay_Coinsurance_Kicks_in_")
    private Boolean deductiblePaidBeforeCopay;

    @Column(name = "Copay")
    private String copay;

    @Column(name = "Coinsurance")
    private String coinsurance;

    @Column(name = "Coinsurance_Minimum_Cost")
    private Integer coinsuranceMinCost;

    @Column(name = "Coinsurance_Maximum_Cost")
    private Integer coinsuranceMaxCost;

    @Column(name = "Hierarcy")
    private String hierarcy;
}