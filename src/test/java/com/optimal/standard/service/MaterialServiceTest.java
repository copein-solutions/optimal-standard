package com.optimal.standard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.MaterialRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

  @Mock
  private MaterialRepository materialRepository;

  private MaterialService materialService;

  @BeforeEach
  void init() {
    this.materialService = new MaterialService(this.materialRepository);
  }

  @Test
  void saveMaterialOk() {
    ArgumentCaptor<Material> argumentCaptor = ArgumentCaptor.forClass(Material.class);
    this.materialService.saveMaterial(MaterialDTO
        .builder()
        .name("Producto 1000")
        .brand("marca 1")
        .type("Cementicio")
        .component("bicomponente")
        .currency("USD")
        .presentationPrice(10.50)
        .presentationQuantity(10)
        .presentationUnit("kg")
        .priceDate(LocalDate.of(2023, 06, 17))
        .build());

    verify(this.materialRepository, times(1)).save(argumentCaptor.capture());
    Material material = argumentCaptor.getValue();
    assertEquals("Producto 1000", material.getName());
    assertEquals("marca 1", material.getBrand());
    assertEquals("Cementicio", material.getType());
    assertEquals("bicomponente", material.getComponent());
    assertEquals("USD", material.getCurrency());
    assertEquals(10.50, material.getPresentationPrice());
    assertEquals(10, material.getPresentationQuantity());
    assertEquals("kg", material.getPresentationUnit());
    assertEquals(LocalDate.of(2023, 06, 17), material.getPriceDate());
  }

  @Test
  void findAllOk() {
    when(this.materialRepository.findAll()).thenReturn(this.mockMaterials());

    List<ResponseMaterialDTO> responseMaterials = this.materialService.findAll();

    assertNotNull(responseMaterials);
    assertEquals(2, responseMaterials.size());
    assertEquals("Producto 1000", responseMaterials
        .get(0)
        .getName());
    assertEquals("Producto 2000", responseMaterials
        .get(1)
        .getName());
  }

  private List<Material> mockMaterials() {
    return List.of(Material
        .builder()
        .id(1L)
        .name("Producto 1000")
        .brand("marca 1")
        .type("Cementicio")
        .component("bicomponente")
        .currency("USD")
        .presentationPrice(10.50)
        .presentationQuantity(10)
        .presentationUnit("kg")
        .priceDate(LocalDate.of(2023, 06, 17))
        .build(), Material
        .builder()
        .id(1L)
        .name("Producto 2000")
        .brand("marca 2")
        .type("Cementicio")
        .component("monocomponente")
        .currency("USD")
        .presentationPrice(20.50)
        .presentationQuantity(10)
        .presentationUnit("kg")
        .priceDate(LocalDate.of(2023, 06, 18))
        .build());
  }

}
