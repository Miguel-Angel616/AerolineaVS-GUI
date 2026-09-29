package com.aerolineavs.tarifas;

/**
 * Contrato para obtener la tarifa recomendada a partir de los datos del cliente.
 */
public interface IPricingService {

    /**
     * Evalúa la tarifa que corresponde al cliente.
     *
     * @param cliente datos del cliente
     * @return tarifa resultante junto con las suposiciones aplicadas
     */
    ResultadoTarifa evaluar(ClientePotencial cliente);
}
