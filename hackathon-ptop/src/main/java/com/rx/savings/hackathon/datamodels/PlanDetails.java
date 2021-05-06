package com.rx.savings.hackathon.datamodels;

import java.util.Objects;

public class PlanDetails {
    private String planName;
    private String planId;
    private String brandOverGeneric;
    private String groupName;
    private String pbm;
    private String formularyDescription;
    private String networkDescription;
    private String coverageBeginDate;
    private String coverageEndDate;
    private Integer individualDeductibleAmount;
    private Integer familyDeductibleAmount;
    private Integer individualOutOfPocketAmount;
    private Integer familyOutOfPocketAmount;
    private Boolean isDeductibleEmbedded;
    private Boolean isOutOfPocketEmbedded;
    private Integer embeddedDeductibleAmount;
    private Integer embeddedOutOfPocketAmount;
    private String coverageType;
    private String mandatoryMaintenance;
    private String specialConditions;

    public PlanDetails() {

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

    public String getBrandOverGeneric() {
        return brandOverGeneric;
    }

    public void setBrandOverGeneric(String brandOverGeneric) {
        this.brandOverGeneric = brandOverGeneric;
    }


    public String getPbm() {
        return pbm;
    }

    public void setPbm(String pbm) {
        this.pbm = pbm;
    }


    public String getFormularyDescription() {
        return formularyDescription;
    }

    public void setFormularyDescription(String formularyDescription) {
        this.formularyDescription = formularyDescription;
    }

    public String getNetworkDescription() {
        return networkDescription;
    }

    public void setNetworkDescription(String networkDescription) {
        this.networkDescription = networkDescription;
    }


    public Integer getIndividualDeductibleAmount() {
        return individualDeductibleAmount;
    }

    public void setIndividualDeductibleAmount(Integer individualDeductibleAmount) {
        this.individualDeductibleAmount = individualDeductibleAmount;
    }

    public Integer getFamilyDeductibleAmount() {
        return familyDeductibleAmount;
    }

    public void setFamilyDeductibleAmount(Integer familyDeductibleAmount) {
        this.familyDeductibleAmount = familyDeductibleAmount;
    }

    public Integer getIndividualOutOfPocketAmount() {
        return individualOutOfPocketAmount;
    }

    public void setIndividualOutOfPocketAmount(Integer individualOutOfPocketAmount) {
        this.individualOutOfPocketAmount = individualOutOfPocketAmount;
    }

    public Integer getFamilyOutOfPocketAmount() {
        return familyOutOfPocketAmount;
    }

    public void setFamilyOutOfPocketAmount(Integer familyOutOfPocketAmount) {
        this.familyOutOfPocketAmount = familyOutOfPocketAmount;

    }

    public Boolean getDeductibleEmbedded() {
        return isDeductibleEmbedded;
    }

    public void setDeductibleEmbedded(Boolean deductibleEmbedded) {
        isDeductibleEmbedded = deductibleEmbedded;
    }

    public Boolean getOutOfPocketEmbedded() {
        return isOutOfPocketEmbedded;
    }

    public void setOutOfPocketEmbedded(Boolean outOfPocketEmbedded) {
        isOutOfPocketEmbedded = outOfPocketEmbedded;

    }

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }

    public Integer getEmbeddedDeductibleAmount() {
        return embeddedDeductibleAmount;
    }

    public void setEmbeddedDeductibleAmount(Integer embeddedDeductibleAmount) {
        this.embeddedDeductibleAmount = embeddedDeductibleAmount;
    }

    public Integer getEmbeddedOutOfPocketAmount() {
        return embeddedOutOfPocketAmount;
    }

    public void setEmbeddedOutOfPocketAmount(Integer embeddedOutOfPocketAmount) {
        this.embeddedOutOfPocketAmount = embeddedOutOfPocketAmount;
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

    public String getMandatoryMaintenance() {
        return mandatoryMaintenance;
    }

    public void setMandatoryMaintenance(String mandatoryMaintenance) {
        this.mandatoryMaintenance = mandatoryMaintenance;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanDetails that = (PlanDetails) o;
        return Objects.equals(planName, that.planName) && Objects.equals(planId, that.planId) && Objects.equals(brandOverGeneric, that.brandOverGeneric) && Objects.equals(pbm, that.pbm) && Objects.equals(formularyDescription, that.formularyDescription) && Objects.equals(networkDescription, that.networkDescription) && Objects.equals(coverageBeginDate, that.coverageBeginDate) && Objects.equals(coverageEndDate, that.coverageEndDate) && Objects.equals(individualDeductibleAmount, that.individualDeductibleAmount) && Objects.equals(familyDeductibleAmount, that.familyDeductibleAmount) && Objects.equals(individualOutOfPocketAmount, that.individualOutOfPocketAmount) && Objects.equals(familyOutOfPocketAmount, that.familyOutOfPocketAmount) && Objects.equals(isDeductibleEmbedded, that.isDeductibleEmbedded) && Objects.equals(isOutOfPocketEmbedded, that.isOutOfPocketEmbedded) && Objects.equals(embeddedDeductibleAmount, that.embeddedDeductibleAmount) && Objects.equals(embeddedOutOfPocketAmount, that.embeddedOutOfPocketAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planName, planId, brandOverGeneric, pbm, formularyDescription, networkDescription, coverageBeginDate, coverageEndDate, individualDeductibleAmount, familyDeductibleAmount, individualOutOfPocketAmount, familyOutOfPocketAmount, isDeductibleEmbedded, isOutOfPocketEmbedded, embeddedDeductibleAmount, embeddedOutOfPocketAmount);
    }

    @Override
    public String toString() {
        return "PlanDetails{" +
                "planName='" + planName + '\'' +
                ", planId='" + planId + '\'' +
                ", brandOverGeneric='" + brandOverGeneric + '\'' +
                ", pbm='" + pbm + '\'' +
                ", formularyDescription='" + formularyDescription + '\'' +
                ", networkDescription='" + networkDescription + '\'' +
                ", coverageBeginDate='" + coverageBeginDate + '\'' +
                ", coverageEndDate='" + coverageEndDate + '\'' +
                ", individualDeductibleAmount=" + individualDeductibleAmount +
                ", familyDeductibleAmount=" + familyDeductibleAmount +
                ", individualOutOfPocketAmount=" + individualOutOfPocketAmount +
                ", familyOutOfPocketAmount=" + familyOutOfPocketAmount +
                ", isDeductibleEmbedded=" + isDeductibleEmbedded +
                ", isOutOfPocketEmbedded=" + isOutOfPocketEmbedded +
                ", embeddedDeductibleAmount=" + embeddedDeductibleAmount +
                ", embeddedOutOfPocketAmount=" + embeddedOutOfPocketAmount +
                '}';
    }
}
