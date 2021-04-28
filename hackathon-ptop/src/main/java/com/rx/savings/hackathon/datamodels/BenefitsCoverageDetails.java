package com.rx.savings.hackathon.datamodels;

import java.util.List;
import java.util.Objects;

public class BenefitsCoverageDetails {

    private PlanDetails planDetails;
    private List<RxDetails> rxDetails;

    public BenefitsCoverageDetails() {

    }

    public PlanDetails getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(PlanDetails planDetails) {
        this.planDetails = planDetails;
    }

    public List<RxDetails> getRxDetails() {
        return rxDetails;
    }

    public void setRxDetails(List<RxDetails> rxDetails) {
        this.rxDetails = rxDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenefitsCoverageDetails that = (BenefitsCoverageDetails) o;
        return Objects.equals(planDetails, that.planDetails) && Objects.equals(rxDetails, that.rxDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planDetails, rxDetails);
    }

    @Override
    public String toString() {
        return "BenefitsCoverageDetails{" +
                "planDetails=" + planDetails +
                ", rxDetails=" + rxDetails +
                '}';
    }

}
