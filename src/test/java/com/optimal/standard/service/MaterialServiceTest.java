package com.optimal.standard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.MaterialFileRepository;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.service.files.LocalFilesService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

  @Mock
  private MaterialRepository materialRepository;

  @Mock
  private MaterialFileRepository materialFileRepository;

  @Mock
  private LocalFilesService localFilesService;

  private MaterialService materialService;

  @BeforeEach
  void init() {
    this.materialService = new MaterialService(this.materialRepository, this.localFilesService, this.materialFileRepository);
  }

  @Test
  void saveMaterialOk() {
    ArgumentCaptor<Material> argumentCaptor = ArgumentCaptor.forClass(Material.class);
    this.materialService.saveMaterial(MaterialDTO
        .builder()
        .product("Producto 1000")
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
    assertEquals("Producto 1000", material.getProduct());
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
    when(this.materialRepository.findAllByDeletedFalse()).thenReturn(this.mockMaterials());

    List<MaterialDTO> responseMaterials = this.materialService.findAll();

    assertNotNull(responseMaterials);
    assertEquals(2, responseMaterials.size());
    assertEquals("Producto 1000", responseMaterials
        .get(0)
        .getProduct());
    assertEquals("Producto 2000", responseMaterials
        .get(1)
        .getProduct());
  }

  private List<Material> mockMaterials() {
    return List.of(Material
        .builder()
        .id(1L)
        .product("Producto 1000")
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
        .product("Producto 2000")
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

  @Test
  void shouldGeneratePassword() {
    String rawPassword = "qwerty";

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(rawPassword);

    System.out.println("Contraseña original: " + rawPassword);
    System.out.println("Contraseña encriptada: " + encodedPassword);
  }

}
