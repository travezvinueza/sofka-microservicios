package com.ricardotravez.clientepersona.clientApi;

import com.ricardotravez.clientepersona.dto.CuentaDTO;

import java.util.List;

public interface ClienteApi {
    List<CuentaDTO> getCuentaPorClienteId (String clientId);
}
