package com.ml.mutant.model;

import javax.persistence.*;

@Entity
@Table(name="dna_test")
public class MutantModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="test_id")
    private int test_id;

    @Column(name="test_dna")
    private String test_dna;

    @Column(name="test_result")
    private boolean test_result;

    public MutantModel(){};

    public MutantModel(int test_id, String test_dna, boolean test_result) {
        this.test_id = test_id;
        this.test_dna = test_dna;
        this.test_result = test_result;
    }

    public boolean isTest_result() {
        return test_result;
    }

    @Override
    public String toString() {
        return "ID Query [test_id=" + test_id + ", test_dna=" + test_dna + ", test_result=" + test_result + "]";
    }
}
