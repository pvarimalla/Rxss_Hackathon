package com.rx.savings.hackathon.repository;

import com.rx.savings.hackathon.models.PdfPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public interface PlanDetailsRepository extends JpaRepository<PdfPlanDetails, Integer> {

    @Transactional
    @Modifying
    @Query(value = "CREATE TABLE Test_plan_details (\n" +
            "  `id` bigint(10) NOT NULL AUTO_INCREMENT,\n" +
            "  `Group_Name` varchar(255) DEFAULT NULL,\n" +
            "  `Plan_Beginning` datetime DEFAULT NULL,\n" +
            "  `Plan_End` datetime DEFAULT NULL,\n" +
            "  `Plan_Name` varchar(255) DEFAULT NULL,\n" +
            "  `Plan_ID` varchar(255) DEFAULT NULL,\n" +
            "  `Brand_Over_Generic_Flag` varchar(10) DEFAULT NULL,\n" +
            "  `PBM` varchar(255) DEFAULT NULL,\n" +
            "  `Formulary_Description` varchar(255) DEFAULT NULL,\n" +
            "  `Network_Description` varchar(255) DEFAULT NULL,\n" +
            "  `Coverage_Type` varchar(255) DEFAULT NULL,\n" +
            "  `Max_Family_Deductible_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Is_Family_Deductible_Embedded_` varchar(255) DEFAULT NULL,\n" +
            "  `Embedded_Deductible_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Max_Individual_Deductible_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Max_Family_OOP_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Is_Max_Family_OOP_Embedded_` varchar(255) DEFAULT NULL,\n" +
            "  `Embedded_OOP_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Max_Individual_OOP_Amount` varchar(255) DEFAULT NULL,\n" +
            "  `Mandatory_Maintenance_` varchar(255) DEFAULT NULL,\n" +
            "  `Special_Conditions_` varchar(255) DEFAULT NULL,\n" +
            "  `rxss_formulary_id` bigint(10) NOT NULL,\n" +
            "  `rxss_network_id` bigint(10) NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;", nativeQuery = true)
    void createPlanDetails(@Param("Group") String Group);

}