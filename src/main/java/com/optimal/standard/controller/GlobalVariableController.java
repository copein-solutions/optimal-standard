package com.optimal.standard.controller;

import com.optimal.standard.dto.GlobalVariableDTO;
import com.optimal.standard.service.GlobalVariableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/optimal_standard")
public class GlobalVariableController {

  private GlobalVariableService globalVariableService;

  @GetMapping("/user/global_variable/labor_cost")
  public double getLaborCost() {
    return this.globalVariableService.getLaborCost();
  }

  @GetMapping("/user/global_variable/quotation_dollar")
  public double getQuotationDollar() {
    return this.globalVariableService.getQuotationDollar();
  }

  @PostMapping("/admin/global_variable/labor_cost")
  public void saveLaborCost(@RequestBody GlobalVariableDTO globalVariableDTO) {
    this.globalVariableService.saveLaborCost(globalVariableDTO.getValue());
  }

  @PostMapping("/admin/global_variable/quotation_dollar")
  public void saveQuotationDollar(@RequestBody GlobalVariableDTO globalVariableDTO) {
    this.globalVariableService.saveQuotationDollar(globalVariableDTO.getValue());
  }

}
