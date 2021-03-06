package com.rx.savings.hackathon.datamodels;

public class RxDetails {
    private String planName;
    private String planId;
    private String drugTier;
    private String pharmacyTier;
    private String coinsurance;
    private Integer coinsuranceMinCost;
    private Integer coinsuranceMaxCost;
    private String copay;
    private String hierarcy;
    private Boolean deductiblePaidBeforeCopay;
    private String coverageBeginDate;
    private String coverageEndDate;
    private String groupName;

    public RxDetails() {

    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getDrugTier() {
        return drugTier;
    }

    public void setDrugTier(String drugTier) {
        this.drugTier = drugTier;
    }

    public String getPharmacyTier() {
        return pharmacyTier;
    }

    public void setPharmacyTier(String pharmacyTier) {
        this.pharmacyTier = pharmacyTier;
    }

    public String getCoinsurance() {
        return coinsurance;
    }

    public void setCoinsurance(String coinsurance) {
        this.coinsurance = coinsurance;
    }

    public String getCopay() {
        return copay;
    }

    public void setCopay(String copay) {
        this.copay = copay;
    }

    public Integer getCoinsuranceMinCost() {
        return coinsuranceMinCost;
    }

    public void setCoinsuranceMinCost(Integer coinsuranceMinCost) {
        this.coinsuranceMinCost = coinsuranceMinCost;
    }

    public Integer getCoinsuranceMaxCost() {
        return coinsuranceMaxCost;
    }

    public void setCoinsuranceMaxCost(Integer coinsuranceMaxCost) {
        this.coinsuranceMaxCost = coinsuranceMaxCost;
    }

    public Boolean getDeductiblePaidBeforeCopay() {
        return deductiblePaidBeforeCopay;
    }

    public void setDeductiblePaidBeforeCopay(Boolean deductiblePaidBeforeCopay) {
        this.deductiblePaidBeforeCopay = deductiblePaidBeforeCopay;
    }

    public String getHierarcy() {
        return hierarcy;
    }

    public void setHierarcy(String hierarcy) {
        this.hierarcy = hierarcy;
    }

    public String getCoverageBeginDate() {
        return coverageBeginDate;
    }

    public void setCoverageBeginDate(String coverageBeginDate) {
        this.coverageBeginDate = coverageBeginDate;
    }

    public String getCoverageEndDate() {
        return coverageEndDate;
    }

    public void setCoverageEndDate(String coverageEndDate) {
        this.coverageEndDate = coverageEndDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
