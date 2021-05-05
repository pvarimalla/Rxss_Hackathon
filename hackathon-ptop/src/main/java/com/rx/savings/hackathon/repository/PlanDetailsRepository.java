package com.rx.savings.hackathon.repository;

import com.rx.savings.hackathon.models.PdfPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PlanDetailsRepository extends JpaRepository<PdfPlanDetails, Integer> {

    @Transactional
    @Modifying
     @Query
    (value = "create table staging_odyssey.tst_plan_details\n" +
            "as\n" +
            "(select * from staging_odyssey.pdf_plan_details where Group_Prefix_Code = :Group )", nativeQuery = true)
    void createPlanDetails(@Param("Group") String Group);

}