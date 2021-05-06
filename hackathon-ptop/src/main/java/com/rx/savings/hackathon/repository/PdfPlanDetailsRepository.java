package com.rx.savings.hackathon.repository;

import com.rx.savings.hackathon.models.PdfPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PdfPlanDetailsRepository extends JpaRepository<PdfPlanDetails, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM staging_odyssey.pdf_plan_details WHERE Group_Name= :GroupCode" +
            "            ;", nativeQuery = true)
    void deletePdfPlanDetails(@Param("GroupCode") String GroupCode);
}
