package com.ml.mutant.repositories;

import com.ml.mutant.model.MutantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepositories extends JpaRepository<MutantModel, Integer> {
    @Query(value = "select count(t.test_result) from MutantModel t where t.test_result=true")
    public int findMutantCount();
    @Query(value = "select count(t.test_result) from MutantModel t where t.test_result=false")
    public int findHmanCount();
}
