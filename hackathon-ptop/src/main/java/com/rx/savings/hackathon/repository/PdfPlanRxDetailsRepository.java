package com.rx.savings.hackathon.repository;

import com.rx.savings.hackathon.models.PdfPlanRxDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PdfPlanRxDetailsRepository extends JpaRepository<PdfPlanRxDetails, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pdf_plan_rx_details WHERE Group_Prefix_Code= :GroupCode" +
            "            ;", nativeQuery = true)
    void deletePdfPlanRxDetails(@Param("GroupCode") String GroupCode);
}