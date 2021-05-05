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
            "(select * from staging_odyssey.pdf_plan_rx_details a where a.Group_Prefix_Code = :Group )", nativeQuery = true)
    void createRxDetails(@Param("Group") String Group);

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE staging_odyssey.tst_plan_rx_details;",nativeQuery = true)
    void truncatePlanRxDetails();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO staging_odyssey.tst_plan_rx_details\n" +
            "(select * from staging_odyssey.pdf_plan_rx_details a where a.Group_Prefix_Code = :Group );",nativeQuery = true)
    void insertPlanRxDetails(@Param("Group") String Group);


    @Query(value = "select count(*) from INFORMATION_SCHEMA.Tables\n" +
            "where TABLE_NAME=CONCAT(:Group,'_plan_rx_details');",nativeQuery = true)
    Integer checkRxTableExists(@Param("Group") String Group);

    @Transactional
    @Modifying
    @Query(value = "update staging_odyssey.pdf_plan_rx_details\n" +
            "set Coinsurance = '10'\n" +
            "where Plan_Name like '%Actives%' and Drug_Tier = 'Specialty Generic';", nativeQuery = true)
    void updateSpecialtyGeneric();

    @Transactional
    @Modifying
    @Query(value = "update staging_odyssey.pdf_plan_rx_details\n" +
            "set Coinsurance = '15'\n" +
            "where Plan_Name like '%Actives%' and Drug_Tier = 'Specialty Brand Preferred';", nativeQuery = true)
    void updateSpecialtyBrandPreferred();

    @Transactional
    @Modifying
    @Query(value = "update staging_odyssey.pdf_plan_rx_details\n" +
            "set Coinsurance = '20'\n" +
            "where Plan_Name like '%Actives%' and Drug_Tier = 'Specialty Brand Non Preferred';", nativeQuery = true)
    void updateSpecialtyBrandNonPreferred();


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO staging_odyssey.`pdf_plan_rx_details` (`Group_Prefix_Code`, `Benfit_Beginning`, `Benefit_End`, `Plan_Name`, `Plan_ID`, `Drug_Tier`, `Pharmacy_Tier`, `Is_Deductible_Paid_Before_Copay_Coinsurance_Kicks_in_`, `Copay`, `Coinsurance`, `Coinsurance_Minimum_Cost`, `Coinsurance_Maximum_Cost`, `Hierarcy`)\n" +
            "VALUES\n" +
            "\t( 'tst', '2021-01-01 00:00:00', '2021-12-31 00:00:00', 'Grandfathered Plan - Actives', 'Grandfathered Plan - Actives', 'Brand Preferred', 'in Network', 'Yes', NULL, 10.00, '25', '50', NULL);\n", nativeQuery = true)
    void addNewRow();

    @Transactional
    @Modifying
    @Query(value = "delete from staging_odyssey.`pdf_plan_rx_details`\n" +
            "where Drug_Tier = \"Brand Preferred\" and Plan_Name = \"Grandfathered Plan - Actives\"\n" +
            "and Pharmacy_Tier = \"in Network\" order by id desc limit 1", nativeQuery = true)
    void deleteNewRow();



}
