package au.com.telstra.simcardactivator.repo;

import au.com.telstra.simcardactivator.model.SimActivationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimActivationRecordRepository extends JpaRepository<SimActivationRecord, Long> {
}
