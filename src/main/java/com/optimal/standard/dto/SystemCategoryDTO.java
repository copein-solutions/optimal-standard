package com.optimal.standard.dto;

import com.optimal.standard.persistence.model.SystemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemCategoryDTO {

  private SystemCategory type;

}
