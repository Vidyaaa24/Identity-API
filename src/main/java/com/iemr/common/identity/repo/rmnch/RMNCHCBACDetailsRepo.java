package com.iemr.common.identity.repo.rmnch;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHCBACdetails;

@Repository
public interface RMNCHCBACDetailsRepo extends CrudRepository<RMNCHCBACdetails, Long> {
	@Query(" SELECT t FROM RMNCHCBACdetails t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHCBACdetails getByIdAndVanID(@Param("vanSerialNo") Long vanSerialNo, @Param("vanID") int vanID);

	@Query(" SELECT t FROM RMNCHCBACdetails t WHERE t.BenRegId =:benRegID")
	public RMNCHCBACdetails getByRegID(@Param("benRegID") Long benRegID);

	@Query(value = "select beneficiary_visit_code,visit_category from db_iemr.i_ben_flow_outreach where beneficiary_reg_id=:benRegID AND beneficiary_visit_code is not null AND visit_category is not null order by created_date desc limit 1", nativeQuery = true)
	public List<Object[]> getVisitDetailsbyRegID(@Param("benRegID") Long benRegID);

	@Query(value = "select DiagnosisProvided from db_iemr.t_prescription where BeneficiaryRegID=:benRegID AND visitcode=:visitCode AND DiagnosisProvided is not null AND DiagnosisProvided <> '' ", nativeQuery = true)
	public List<Object> getDiagnosisProvidedCommon(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	// Add snomed code
	@Query(value = "select ProvisionalDiagnosis from db_iemr.t_PNCDiagnosis where BeneficiaryRegID=:benRegID AND visitcode=:visitCode AND ProvisionalDiagnosis is not null AND ProvisionalDiagnosis <> '' ", nativeQuery = true)
	public List<Object> getDiagnosisProvidedPNC(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
	@Query(value = "select NCD_Condition from db_iemr.t_ncddiagnosis where BeneficiaryRegID=:benRegID AND visitcode=:visitCode AND NCD_Condition is not null AND NCD_Condition <> '' "
	, nativeQuery = true)
	public List<Object> getDiagnosisProvidedNCD_Care(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
}