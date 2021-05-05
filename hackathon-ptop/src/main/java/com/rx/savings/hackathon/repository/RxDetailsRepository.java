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
    @Query(value = "CREATE TABLE Test_plan_rx_details (\n" +
            "  `id` bigint(10) NOT NULL AUTO_INCREMENT,\n" +
            "  `Group_Prefix_Code` varchar(10) DEFAULT NULL,\n" +
            "  `Benfit_Beginning` datetime DEFAULT NULL,\n" +
            "  `Benefit_End` datetime DEFAULT NULL,\n" +
            "  `Plan_Name` varchar(255) DEFAULT NULL,\n" +
            "  `Plan_ID` varchar(255) DEFAULT NULL,\n" +
            "  `Drug_Tier` varchar(255) DEFAULT NULL,\n" +
            "  `Pharmacy_Tier` varchar(255) DEFAULT NULL,\n" +
            "  `Is_Deductible_Paid_Before_Copay_Coinsurance_Kicks_in_` varchar(255) DEFAULT NULL,\n" +
            "  `Copay` varchar(255) DEFAULT NULL,\n" +
            "  `Coinsurance` double(11,2) DEFAULT NULL,\n" +
            "  `Coinsurance_Minimum_Cost` varchar(255) DEFAULT NULL,\n" +
            "  `Coinsurance_Maximum_Cost` varchar(255) DEFAULT NULL,\n" +
            "  `Hierarcy` varchar(255) DEFAULT NULL,\n" +
            "  `rxss_formulary_id` bigint(10) NOT NULL,\n" +
            "  `rxss_network_id` bigint(10) NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;", nativeQuery = true)
    void createRxDetails(@Param("Group") String Group);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE Test_plan_rx_details;",nativeQuery = true)
    void truncatePlanRxDetails();


    @Query(value = "select count(*) from INFORMATION_SCHEMA.Tables\n" +
            "where TABLE_NAME=CONCAT(:Group,'_plan_rx_details');",nativeQuery = true)
    Integer checkRxTableExists(@Param("Group") String Group);

}
