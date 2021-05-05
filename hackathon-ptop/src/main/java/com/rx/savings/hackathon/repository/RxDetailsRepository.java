package com.rx.savings.hackathon.repository;

import com.rx.savings.hackathon.models.PdfPlanRxDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RxDetailsRepository extends JpaRepository<PdfPlanRxDetails, Integer> {

    @Transactional
    @Modifying
    @Query(value = "create table staging_odyssey.TST_plan_rx_details\n" +
            "as\n" +
            "(select * from staging_odyssey.pdf_plan_rx_details a where a.Group_Prefix_Code = :Group )", nativeQuery = true)
    void createRxDetails(@Param("Group") String Group);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE TST_plan_rx_details;",nativeQuery = true)
    void truncatePlanRxDetails();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TST_plan_rx_details\n" +
            "(select * from staging_odyssey.pdf_plan_rx_details a where a.Group_Prefix_Code = :Group );",nativeQuery = true)
    void insertPlanRxDetails(@Param("Group") String Group);


    @Query(value = "select count(*) from INFORMATION_SCHEMA.Tables\n" +
            "where TABLE_NAME=CONCAT(:Group,'_plan_rx_details');",nativeQuery = true)
    Integer checkRxTableExists(@Param("Group") String Group);

}
