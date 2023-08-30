package com.optimal.standard.service;

import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.GlobalVariable;
import com.optimal.standard.persistence.repository.GlobalVariableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GlobalVariableService {

  private static final String LABOR_COST = "labor_cost";

  private static final String QUOTATION_DOLLAR = "quotation_dollar";

  private GlobalVariableRepository globalVariableRepository;

  public double getLaborCost() {
    return this.fetchGlobalVariable(LABOR_COST);
  }

  public double getQuotationDollar() {
    return this.fetchGlobalVariable(QUOTATION_DOLLAR);
  }

  public void saveLaborCost(double laborCost) {
    this.saveGlobalVariable(LABOR_COST, laborCost);
  }

  public void saveQuotationDollar(double quotationDollar) {
    this.saveGlobalVariable(QUOTATION_DOLLAR, quotationDollar);
  }

  private double fetchGlobalVariable(String name) {
    return this.globalVariableRepository
        .findByName(name)
        .map(GlobalVariable::getValue)
        .orElse((double) 0);
  }

  private void saveGlobalVariable(String name, double value) {
    this.globalVariableRepository
        .findByName(name)
        .ifPresentOrElse(variable -> {
          variable.setValue(value);
          this.globalVariableRepository.save(variable);
        }, () -> {
          throw new BadRequestException("");
        });
  }


}
