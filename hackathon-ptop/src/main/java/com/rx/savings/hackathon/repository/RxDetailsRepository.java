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
    @Query(value = "create table staging_odyssey.tst_plan_rx_details\n" +
            "as\n" +
            "(select * from staging_odyssey.pdf_plan_rx_details where Group_Prefix_Code = :Group )", nativeQuery = true)
    void createRxDetails(@Param("Group") String Group);

}
