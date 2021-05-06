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
            "(select * from staging_odyssey.pdf_plan_details where Group_Name = :Group )", nativeQuery = true)
    void createPlanDetails(@Param("Group") String Group);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE staging_odyssey.tst_plan_details;",nativeQuery = true)
    void truncatePlanDetails();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO staging_odyssey.tst_plan_details\n" +
            "(select * from staging_odyssey.pdf_plan_details a where a.Group_Name = :Group );",nativeQuery = true)
    void insertPlanDetails(@Param("Group") String Group);


    @Query(value = "select count(*) from INFORMATION_SCHEMA.Tables\n" +
            "where TABLE_NAME=CONCAT(:Group,'_plan_details');",nativeQuery = true)
    Integer checkTableExists(@Param("Group") String Group);


}