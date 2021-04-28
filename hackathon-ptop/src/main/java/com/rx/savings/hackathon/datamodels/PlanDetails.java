package com.rx.savings.hackathon.datamodels;

import java.util.Objects;

public class PlanDetails {
    private String planName;
    private String planId;
    private String coverageBeginDate;
    private String coverageEndDate;
    private Integer individualDeductibleAmount;
    private Integer familyDeductibleAmount;
    private Integer individualOutOfPocketAmount;
    private Integer familyOutOfPocketAmount;
    private Boolean embedded;
    private Integer embeddedDeductibleAmount;
    private Integer embeddedOutOfPocketAmount;

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

    public Boolean getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Boolean embedded) {
        this.embedded = embedded;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanDetails that = (PlanDetails) o;
        return Objects.equals(planName, that.planName) && Objects.equals(planId, that.planId) && Objects.equals(coverageBeginDate, that.coverageBeginDate) && Objects.equals(coverageEndDate, that.coverageEndDate) && Objects.equals(individualDeductibleAmount, that.individualDeductibleAmount) && Objects.equals(familyDeductibleAmount, that.familyDeductibleAmount) && Objects.equals(individualOutOfPocketAmount, that.individualOutOfPocketAmount) && Objects.equals(familyOutOfPocketAmount, that.familyOutOfPocketAmount) && Objects.equals(embedded, that.embedded) && Objects.equals(embeddedDeductibleAmount, that.embeddedDeductibleAmount) && Objects.equals(embeddedOutOfPocketAmount, that.embeddedOutOfPocketAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planName, planId, coverageBeginDate, coverageEndDate, individualDeductibleAmount, familyDeductibleAmount, individualOutOfPocketAmount, familyOutOfPocketAmount, embedded, embeddedDeductibleAmount, embeddedOutOfPocketAmount);
    }

    @Override
    public String toString() {
        return "PlanDetails{" +
                "planName='" + planName + '\'' +
                ", planId='" + planId + '\'' +
                ", coverageBeginDate='" + coverageBeginDate + '\'' +
                ", coverageEndDate='" + coverageEndDate + '\'' +
                ", individualDeductibleAmount=" + individualDeductibleAmount +
                ", familyDeductibleAmount=" + familyDeductibleAmount +
                ", individualOutOfPocketAmount=" + individualOutOfPocketAmount +
                ", familyOutOfPocketAmount=" + familyOutOfPocketAmount +
                ", embedded=" + embedded +
                ", embeddedDeductibleAmount=" + embeddedDeductibleAmount +
                ", embeddedOutOfPocketAmount=" + embeddedOutOfPocketAmount +
                '}';
    }
}
